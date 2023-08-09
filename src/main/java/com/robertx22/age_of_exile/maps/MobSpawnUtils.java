package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class MobSpawnUtils {


    public static <T extends Mob> void summonMinions(EntityType<T> type, int amount, Level world, BlockPos p) {
        for (int i = 0; i < amount; i++) {
            summon(type, world, p);
        }
    }

    /*
        public static <T extends Mob> T summonBoss(EntityType<T> type, Level world, BlockPos p,
                                                   com.robertx22.mine_and_slash.database.bosses.base.Boss boss) {
            MyPosition vec = new MyPosition(p);
            // vec = vec.add(0.5F, 0, 0.5F);
    
            T bossEntity = (T) type.create(world.getWorld());
            bossEntity.onInitialSpawn(world, world.getDifficultyForLocation(p), SpawnReason.REINFORCEMENT, null, null);
            bossEntity.setPosition(vec.getX(), vec.getY(), vec.getZ());
    
            OnMobSpawn.setupNewMobOnSpawn(bossEntity);
    
            Load.boss(bossEntity)
                    .setBoss(boss);
    
            Load.Unit(bossEntity)
                    .setRarity(IRarity.Boss);
    
            world.addEntity(bossEntity);
    
            return bossEntity;
        }
    
    
     */


    public static <T extends Mob> T summon(EntityType<T> type, Level world, BlockPos p) {
        MyPosition vec = new MyPosition(p);

        T mob = (T) type.create(world);
        mob.finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(p), MobSpawnType.REINFORCEMENT, null, null);
        mob.setPos(vec.x(), vec.y(), vec.z());

        OnMobSpawn.setupNewMobOnSpawn(mob);

        world.addFreshEntity(mob);

        return mob;
    }


     
    /*

    public static BlockPos randomPosNearPlayer(Level world) {
        BlockPos pos = world.players()
                .get(0)
                .getPosition();
        pos = new BlockPos(pos.getX() + RandomUtils.RandomRange(-50, 50), pos.getY(),
                pos.getZ() + RandomUtils.RandomRange(-50, 50)
        );
        pos = WorldUtils.getSurface(world, pos).above();

        return pos;

    }
    
     */
}

