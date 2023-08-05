package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.generators.BaseLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LootInfo {

    private LootInfo(LootOrigin lootOrigin) {
        this.lootOrigin = lootOrigin;
    }

    public enum LootOrigin {
        CHEST, MOB, PLAYER, OTHER, LOOT_CRATE;
    }

    public int amount = 0;
    public int level = 0;
    public int tier = 1;

    public LootOrigin lootOrigin;
    public EntityData mobData;
    public EntityData playerData;
    public LivingEntity mobKilled;
    public Player player;
    public Level world;
    public float multi = 1;
    private int minItems = 0;
    private int maxItems = 50;
    public boolean isMapWorld = false;
    public RPGPlayerData rpgData;
    public BlockPos pos;

    public int getMinItems() {
        return minItems;

    }


    public int getMaxItems() {
        return maxItems;
    }

    public static LootInfo ofMobKilled(Player player, LivingEntity mob) {

        LootInfo info = new LootInfo(LootOrigin.MOB);

        try {
            info.world = mob.level();
            info.mobData = Load.Unit(mob);
            info.playerData = Load.Unit(player);
            info.mobKilled = mob;
            info.player = player;
            info.pos = mob.blockPosition();

            info.setupAllFields();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    public static LootInfo ofPlayer(Player player) {
        LootInfo info = new LootInfo(LootOrigin.PLAYER);
        info.world = player.level();
        info.pos = player.blockPosition();
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofChestLoot(Player player, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.CHEST);
        info.player = player;
        info.world = player.level();
        info.pos = pos;
        info.multi = 1.5F;
        info.maxItems = 7;
        info.setupAllFields();


        return info;
    }

    public static LootInfo ofDummyForClient(int level) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.level = level;
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofLevel(int level) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.level = level;
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofLockedChestItem(Player player, int level) {
        LootInfo info = new LootInfo(LootOrigin.LOOT_CRATE);
        info.player = player;
        info.world = player.level();
        info.pos = player.blockPosition();
        info.level = level;
        info.multi = 5;
        info.minItems = 3;
        info.maxItems = 6;
        info.setupAllFields();

        info.isMapWorld = false;
        return info;
    }

    public static LootInfo ofSpawner(Player player, Level world, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.world = world;
        info.pos = pos;
        info.player = player;
        info.setupAllFields();
        info.maxItems = 1;
        return info;
    }

    private void setupAllFields() {
        // order matters
        errorIfClient();
        setWorld();
        setDifficulty();
        setLevel();

        if (player != null) {
            playerData = Load.Unit(player);
        }
    }


    private LootInfo setDifficulty() {

        if (world != null && pos != null) {
            if (WorldUtils.isMapWorldClass(world)) {
            }
        }
        return this;

    }

    private void setLevel() {


        if (level <= 0) {
            if (mobData != null) {
                level = mobData.getLevel();
            } else {
                level = LevelUtils.determineLevel(world, pos, player).level;
            }
        }


        this.tier = LevelUtils.levelToTier(level);
    }

    private void errorIfClient() {
        if (world != null && world.isClientSide) {
            throw new RuntimeException("Can't use Loot Info on client side!!!");
        }
    }

    private void setWorld() {
        if (world != null) {
        }
        if (isMapWorld) {

        }
    }

    public void setup(BaseLootGen gen) {

        float modifier = 1F;

        modifier += multi - 1F;

        if (mobKilled != null && mobData != null) {


            if (this.playerData != null) {
                modifier += LootUtils.getLevelDistancePunishmentMulti(mobData.getLevel(), playerData.getLevel()) - 1F;
            }

            modifier += LootUtils.getMobHealthBasedLootMulti(mobData, mobKilled) - 1F;

            modifier += ExileDB.getEntityConfig(mobKilled, this.mobData).loot_multi - 1F;
            modifier += mobData.getUnit()
                    .getCalculatedStat(ExtraMobDropsStat.getInstance())
                    .getMultiplier() - 1;
            modifier += ExileDB.MobRarities()
                    .get(mobData.getRarity())
                    .LootMultiplier() - 1;

        }

        if (this.playerData != null) {
            if (lootOrigin != LootOrigin.LOOT_CRATE) {
                if (this.lootOrigin == LootOrigin.CHEST) {
                    modifier += playerData.getUnit()
                            .getCalculatedStat(TreasureQuantity.getInstance())
                            .getMultiplier() - 1F;
                }
            }
        }

        if (world != null) {
            modifier += ExileDB.getDimensionConfig(world).all_drop_multi - 1F;
        }

        if (isMapWorld) {

        }

        float chance = gen.baseDropChance() * modifier;

        chance = ExileEvents.SETUP_LOOT_CHANCE.callEvents(new ExileEvents.OnSetupLootChance(mobKilled, player, chance)).lootChance;

        amount = LootUtils.WhileRoll(chance);

    }

}
