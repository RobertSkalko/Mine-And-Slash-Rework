package com.robertx22.age_of_exile.maps.processors.helpers;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MobBuilder {

    private EntityType type;
    public MobRarity rarity;
    public int amount = 1;

    private MobBuilder() {
    }

    public static MobBuilder of(EntityType type, Consumer<MobBuilder> co) {
        MobBuilder b = new MobBuilder();
        b.type = type;
        co.accept(b);
        return b;
    }

    public <T extends Mob> List<T> summonMobs(Level world, BlockPos p) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            T mob = (T) summon(type, world, p);
            list.add(mob);
        }
        return list;
    }

    private <T extends Mob> T summon(EntityType<T> type, Level world, BlockPos p) {
        MyPosition vec = new MyPosition(p);

        
        T mob = (T) type.create(world);

        mob.finalizeSpawn((ServerLevelAccessor) world, world.getCurrentDifficultyAt(p), MobSpawnType.REINFORCEMENT, null, null);
        mob.setPos(vec.x(), vec.y(), vec.z());

        OnMobSpawn.setupNewMobOnSpawn(mob);

        if (rarity != null) {
            Load.Unit(mob).setRarity(rarity.GUID());
        }

        world.addFreshEntity(mob);

        return mob;
    }

}
