package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class Profession implements JsonExileRegistry<Profession>, IAutoGson<Profession> {
    public static Profession SERIALIZER = new Profession();

    public String id = "";

    public HashMap<String, Integer> tier_1 = new HashMap<>();
    public HashMap<String, Integer> tier_2 = new HashMap<>();
    public HashMap<String, Integer> tier_3 = new HashMap<>();
    public HashMap<String, Integer> tier_4 = new HashMap<>();
    public HashMap<String, Integer> tier_5 = new HashMap<>();

    public Type type = Type.OTHER;

    public enum Type {
        BLOCK, ENTITY, OTHER
    }

    public HashMap<String, Integer> getMap(SkillItemTier tier) {
        if (tier == SkillItemTier.TIER0) {
            return tier_1;
        }
        if (tier == SkillItemTier.TIER1) {
            return tier_2;
        }
        if (tier == SkillItemTier.TIER2) {
            return tier_3;
        }
        if (tier == SkillItemTier.TIER3) {
            return tier_4;
        }
        if (tier == SkillItemTier.TIER4) {
            return tier_5;
        }
        return null;
    }

    public void onKill(Player p, Entity en) {
        if (this.type == Type.ENTITY) {

            Data data = getData(en.getType());

            if (data.exp > 0) {
                data.giveExp(p);
            }

        }
    }

    public void onMine(Player p, Block block) {
        if (this.type == Type.BLOCK) {
            Data data = getData(block);

            if (data.exp > 0) {
                data.giveExp(p);
            }
        }
    }


    public Data getData(Block block) {
        return getData(VanillaUTIL.REGISTRY.blocks().getKey(block).toString());
    }

    public Data getData(EntityType en) {
        return getData(BuiltInRegistries.ENTITY_TYPE.getKey(en).toString());
    }

    public Data getData(String id) {
        for (SkillItemTier value : SkillItemTier.values()) {
            if (getMap(value).containsKey(id)) {
                return new Data(getMap(value).get(id), value.tier);
            }
        }
        return new Data(0, 0);
    }

    private class Data {

        
        public int exp;
        public int tier;

        public Data(int exp, int tier) {
            this.exp = exp;
            this.tier = tier;
        }

        public void giveExp(Player p) {
            Load.player(p).professions.addExp(GUID(), exp);
        }

        public void giveLoot(Player p, BlockPos pos) {
            if (RandomUtils.roll(getLootChance(p))) {

                // todo random profession loot
            }
        }

        public int getLevelOfMastery() {
            return SkillItemTier.of(tier).levelRange.getMinLevel();
        }

        public float getLootChance(Player p) {
            float lvlmulti = MathHelper.clamp((float) Load.player(p).professions.getLevel(GUID()) / (float) getLevelOfMastery(), 0F, 1F);
            return exp * lvlmulti;
        }
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PROFESSION;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<Profession> getClassForSerialization() {
        return Profession.class;
    }
}
