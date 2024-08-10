package com.robertx22.mine_and_slash.maps.processors.mob;


import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.maps.processors.helpers.MobBuilder;
import com.robertx22.mine_and_slash.maps.spawned_map_mobs.SpawnedMob;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;


public class BossProcessor extends DataProcessor {

    public BossProcessor() {
        super("boss_mob");
        this.detectIds.add("boss");
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var map = Load.mapAt(world, pos);

        EntityType<? extends Mob> type = SpawnedMob.random(map).getType();

        for (Mob en : MobBuilder.of(type, x -> {
            x.rarity = ExileDB.GearRarities().get(IRarity.MYTHIC_ID);
        }).summonMobs(world, pos)) {
            Load.Unit(en).setupRandomBoss(); // todo
        }


    }

}