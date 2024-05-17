package com.robertx22.age_of_exile.maps.processors.mob;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
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

public class MobProcessor extends DataProcessor {

    public MobProcessor() {
        super("mob");
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return true;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var map = Load.mapAt(world, pos);
        EntityType<? extends Mob> type = SpawnedMob.random(map).getType();

        int amount = RandomUtils.RandomRange(ServerContainer.get().MOB_MIN.get(), ServerContainer.get().MOB_MAX.get()); // hacky solution

        MobBuilder.of(type, x -> {
            x.amount = amount;
        }).summonMobs(world, pos);
    }
}