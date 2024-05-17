package com.robertx22.age_of_exile.maps.processors.mob;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.maps.processors.helpers.MobBuilder;
import com.robertx22.age_of_exile.maps.spawned_map_mobs.SpawnedMob;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class EliteMobHorde extends DataProcessor {

    public EliteMobHorde() {
        super("elite_mob_horde");
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return true;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var map = Load.mapAt(world, pos);

        EntityType<? extends Mob> type = SpawnedMob.random(map).getType();

        int amount = RandomUtils.RandomRange(4, 7); // add variability


        MobBuilder.of(type, x -> {
            x.amount = amount;
            x.rarity = ExileDB.GearRarities().getFilterWrapped(e -> e.item_tier > 3).random();
        }).summonMobs(world, pos);

    }
}