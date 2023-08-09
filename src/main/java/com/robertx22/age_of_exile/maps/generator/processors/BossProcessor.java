package com.robertx22.age_of_exile.maps.generator.processors;


import com.robertx22.age_of_exile.maps.MobSpawnUtils;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.mobs.SpawnedMob;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;


public class BossProcessor extends DataProcessor {

    public BossProcessor() {
        super("boss_mob");
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        EntityType<? extends Mob> type = SpawnedMob.random(data.getRoom()).type;

        LivingEntity en = MobSpawnUtils.summon(type, world, pos);
        Load.Unit(en).setRarity(IRarity.BOSS_ID);
        // todo
        // need to make custom bosses with spells etc

    }
}