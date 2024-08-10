package com.robertx22.mine_and_slash.maps.processors.mob;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.maps.processors.helpers.MobBuilder;
import com.robertx22.mine_and_slash.maps.spawned_map_mobs.SpawnedMob;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class EliteProcessor extends DataProcessor {

    public EliteProcessor() {
        super("elite_mob");
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return true;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var map = Load.mapAt(world, pos);

        EntityType<? extends Mob> type = SpawnedMob.random(map).getType();


        MobBuilder.of(type, x -> {
            x.amount = 1;
            x.rarity = ExileDB.GearRarities().getFilterWrapped(e -> e.item_tier > 3).random();
        }).summonMobs(world, pos);

    }
}