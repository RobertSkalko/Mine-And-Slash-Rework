package com.robertx22.age_of_exile.uncommon;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;

import java.util.Arrays;
import java.util.List;

public class UnstuckMobs {

    static List<Direction> dirs = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP, Direction.DOWN);

    public static void unstuckFromWalls(Entity en) {
        if (en.isInWall()) {
            for (Direction dir : dirs) {
                for (int i = 1; i < 3; i++) {
                    if (!en.level().getBlockState(en.blockPosition().relative(dir)).isSolid()) {
                        var tp = en.blockPosition().relative(dir, i);
                        en.teleportTo(tp.getX(), tp.getY(), tp.getZ());
                        break;
                    }
                }
            }
        }
        if (en.isInWall()) {
            en.hurt(en.damageSources().fellOutOfWorld(), 200000);
        }
    }
}
