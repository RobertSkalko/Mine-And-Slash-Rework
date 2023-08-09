package com.robertx22.age_of_exile.maps.generator.processors;

import com.robertx22.age_of_exile.maps.MobSpawnUtils;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.mobs.SpawnedMob;
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
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        EntityType<? extends Mob> type = SpawnedMob.random(data.getRoom()).type;

        int amount = RandomUtils.RandomRange(4, 7); // add variability

        for (int i = 0; i < amount; i++) {
            MobSpawnUtils.summon(type, world, pos);
            // todo
        }
    }
}