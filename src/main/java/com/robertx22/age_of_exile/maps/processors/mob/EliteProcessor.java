package com.robertx22.age_of_exile.maps.processors.mob;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.mobs.SpawnedMob;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.maps.processors.helpers.MobBuilder;
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

        EntityType<? extends Mob> type = SpawnedMob.random(data.getRoom()).type;


        MobBuilder.of(type, x -> {
            x.amount = 1;
            x.rarity = ExileDB.GearRarities().getFilterWrapped(e -> e.item_tier > 3).random();
        }).summonMobs(world, pos);

    }
}