package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

// this stores data that can be lost without issue, stats that are recalculated all the time

public class Unit {

    public static Unit EMPTY = new Unit();


    private StatContainer stats = new StatContainer();

  
    public void toNbt(CompoundTag main) {
        CompoundTag nbt = new CompoundTag();
        int i = 0;
        for (StatData stat : stats.stats.values()) {
            CompoundTag tag = new CompoundTag();
            tag.putFloat("v", stat.getValue());
            tag.putFloat("m", stat.getMoreStatTypeMulti());
            tag.putString("i", stat.getId());
            nbt.put("" + i, tag);
            i++;
        }
        main.put(SlashRef.MODID + "_unit", nbt);
        main.putInt(SlashRef.MODID + "_unit_sn", stats.stats.size());

    }

    public void fromNbt(CompoundTag main) {

        this.stats = new StatContainer();

        CompoundTag nbt = main.getCompound(SlashRef.MODID + "_unit");

        int num = main.getInt(SlashRef.MODID + "_unit_sn");

        for (int i = 0; i < num; i++) {
            CompoundTag tag = nbt.getCompound(i + "");
            String id = tag.getString("i");
            float val = tag.getFloat("v");
            float mul = tag.getFloat("m");
            StatData data = new StatData(id, val, mul);
            stats.stats.put(data.getId(), data);
        }

    }

    public boolean isBloodMage() {
        return getCalculatedStat(BloodUser.getInstance()).getValue() > 0;
    }

    public void clearStats() {
        this.stats = new StatContainer();
    }

    public StatContainer getStats() {
        if (stats == null) {
            stats = new StatContainer();
        }

        return stats;
    }

    public void setStats(StatContainer c) {
        this.stats = c;
    }

    public StatData getCalculatedStat(Stat stat) {
        return getCalculatedStat(stat.GUID());
    }

    public StatData getCalculatedStat(String guid) {
        if (getStats().stats == null) {
            this.initStats();
        }
        return getStats().stats.getOrDefault(guid, new StatData(guid, 0, 1));
    }

    /*
    public StatData getOrCreateCalculatedStat(String guid) {
        if (getStats().stats == null) {
            this.initStats();
        }
        var data = getStats().stats.getOrDefault(guid, new StatData(guid, 0, 1));
        getStats().stats.put(guid, data);
        return data;
    }

     */

    public Unit() {

    }

    public void initStats() {
        getStats().stats = new HashMap<String, StatData>();
    }


    // Stat shortcuts
    public Health health() {
        return Health.getInstance();
    }

    public Mana mana() {
        return Mana.getInstance();
    }

    public StatData healthData() {
        try {
            return getCalculatedStat(Health.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData bloodData() {
        try {
            return getCalculatedStat(Blood.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData energyData() {
        try {
            return getCalculatedStat(Energy.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public StatData magicShieldData() {
        try {
            return getCalculatedStat(MagicShield.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public StatData manaData() {
        try {
            return getCalculatedStat(Mana.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public String randomRarity(int lvl, EntityData data) {

        List<MobRarity> rarities = ExileDB.MobRarities()
                .getList()
                .stream()
                .filter(x -> data.getLevel() >= x.minMobLevelForRandomSpawns() || data.getLevel() >= GameBalanceConfig.get().MAX_LEVEL)
                .collect(Collectors.toList());


        if (rarities.isEmpty()) {
            rarities.add(ExileDB.MobRarities().get(IRarity.COMMON_ID));
        }


        MobRarity finalRarity = RandomUtils.weightedRandom(rarities);


        return finalRarity.GUID();

    }


    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        if (ServerContainer.get().DONT_SYNC_DATA_OF_AMBIENT_MOBS.get()) {
            return en.getType().getCategory() != MobCategory.AMBIENT && en.getType().getCategory() != MobCategory.WATER_AMBIENT;
        }
        return true;
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, EntityData data) {
        return new EfficientMobUnitPacket(en, data);
    }

}
