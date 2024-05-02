package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.league.LeagueStructure;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.generators.BaseLootGen;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class LootInfo {

    private LootInfo(LootOrigin lootOrigin) {
        this.lootOrigin = lootOrigin;
    }

    public enum LootOrigin {
        CHEST, MOB, PLAYER, OTHER, LOOT_CRATE;
    }

    public int amount = 0;
    public int level = 0;
    public int map_tier = 0;

    public LootOrigin lootOrigin;
    public EntityData mobData;
    public EntityData playerEntityData;
    public LivingEntity mobKilled;
    public Player player;
    public Level world;
    public float multi = 1;
    private int minItems = 0;
    private int maxItems = 50;
    public boolean isMapWorld = false;
    public MapData map;
    public BlockPos pos;


    public LeagueMechanic league = LeagueMechanics.NONE;


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
            info.playerEntityData = Load.Unit(player);
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
        info.maxItems = 7;
        info.setupAllFields();

        if (WorldUtils.isMapWorldClass(player.level())) {
            info.multi += 10;
        } else {
            info.multi += 5;
        }

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
        setTier();
        setLevel();

        if (player != null) {
            playerEntityData = Load.Unit(player);
        }
    }

    private LootInfo setTier() {
        if (map != null) {
            this.map_tier = map.map.tier;
        }


        return this;

    }


    private void setLevel() {

        if (level <= 0) {
            if (mobData != null) {
                level = mobData.getLevel();
            } else {
                if (WorldUtils.isMapWorldClass(world)) {
                    try {
                        var data = Load.mapAt(world, pos);
                        if (data != null) {
                            level = data.map.getLevel();
                            return;
                        } else {
                            System.out.print("A mob spawned in a dungeon world without a dungeon data nearby!");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    level = LevelUtils.determineLevel(world, pos, player).level;
                }
            }
        }

    }

    private void errorIfClient() {
        if (world != null && world.isClientSide) {
            throw new RuntimeException("Can't use Loot Info on client side!!!");
        }
    }

    private void setWorld() {
        if (world != null) {
            if (WorldUtils.isMapWorldClass(world)) {

                var data = Load.worldData(world).map.getMap(this.pos);

                if (data.get() != null) {
                    this.isMapWorld = true;
                    this.map = data.get();
                    this.league = LeagueStructure.getMechanicFromPosition((ServerLevel) world, pos);
                }

            }
        }
    }

    public void setup(BaseLootGen gen) {

        float multiplicativeMod = 1F;

        List<Float> multis = new ArrayList<>();

        multis.add(multi);


        if (mobKilled != null && mobData != null) {
            if (this.playerEntityData != null) {
                multis.add(LootUtils.getLevelDistancePunishmentMulti(mobData.getLevel(), playerEntityData.getLevel()));
            }
            multis.add(LootUtils.getMobHealthBasedLootMulti(mobData, mobKilled));
            multis.add((float) ExileDB.getEntityConfig(mobKilled, this.mobData).loot_multi);
            multis.add(mobData.getUnit().getCalculatedStat(ExtraMobDropsStat.getInstance()).getMultiplier());
            multis.add(mobData.getMobRarity().LootMultiplier());
        }

        if (this.playerEntityData != null) {
            multis.add(Load.player(player).favor.getLootExpMulti());
            if (lootOrigin != LootOrigin.LOOT_CRATE) {
                multis.add(playerEntityData.getUnit().getCalculatedStat(TreasureQuantity.getInstance()).getMultiplier());
            }
        }
        if (world != null) {
            multis.add(ExileDB.getDimensionConfig(world).all_drop_multi);
        }

        if (this.isMapWorld) {
            multis.add(this.map.map.getBonusLootMulti());


        }


        //float additiveMod = 1;

        for (Float m : multis) {
            multiplicativeMod *= m;
            //additiveMod += (m - 1F);
        }


        float chance = gen.baseDropChance();

        if (gen.chanceIsModified()) {
            chance *= multiplicativeMod;
            chance = ExileEvents.SETUP_LOOT_CHANCE.callEvents(new ExileEvents.OnSetupLootChance(mobKilled, player, chance)).lootChance;
        }

        amount = LootUtils.WhileRoll(chance);

    }

}
