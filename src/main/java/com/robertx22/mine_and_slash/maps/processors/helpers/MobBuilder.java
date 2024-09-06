package com.robertx22.mine_and_slash.maps.processors.helpers;

import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.event_hooks.entity.OnMobSpawn;
import com.robertx22.mine_and_slash.mmorpg.ModErrors;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
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
    public GearRarity rarity;
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
        Load.Unit(mob).isCorrectlySpawnedMapMob = true;
        
        try {
            if (WorldUtils.isMapWorldClass(world)) {
                var map = Load.mapAt(world, p);
                Load.Unit(mob).mapUUID = map.map.uuid;
            }
        } catch (Exception e) {
            ModErrors.print(e);
        }

        world.addFreshEntity(mob);

        return mob;
    }

}
