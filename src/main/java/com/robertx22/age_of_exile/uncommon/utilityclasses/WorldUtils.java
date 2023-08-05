package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import java.util.Arrays;

public class WorldUtils {

    public static void spawnEntity(Level world, Entity entity) {

        world.addFreshEntity(entity);

    }

    public static boolean isNearSurface(BlockPos pos, Level world, int buffer) {

        BlockPos surface = WorldUtils.getSurface(world, pos);

        if (pos.getY() > surface.getY() - buffer) {
            return true;
        }

        return false;
    }

    public static BlockPos getSurfaceCenterOfChunk(LevelAccessor world, BlockPos pos) {

        int x = world.getChunk(pos)
                .getPos().x + 8;
        int z = world.getChunk(pos)
                .getPos().z + 8;

        pos = furtherby8(pos);

        pos = getSurface(world, pos);

        return pos;
    }

    public static BlockPos furtherby8(BlockPos pos) {

        int x = 0;
        int z = 0;

        if (pos.getX() > 0) {
            x = pos.getX() + 8;
        } else {
            x = pos.getX() - 8;
        }

        if (pos.getZ() > 0) {
            z = pos.getZ() + 8;
        } else {
            z = pos.getZ() - 8;
        }

        pos = new BlockPos(x, pos.getY(), z);

        return pos;

    }

    public static boolean surfaceIsWater(LevelAccessor world, BlockPos pos) {

        BlockPos surface = getSurface(world, pos);

        for (BlockPos x : Arrays.asList(surface.above(), surface.above(2), surface.below(), surface.below(2), surface)) {
            if (world.getBlockState(x)
                    .getMaterial() == Material.WATER) {
                return true;
            }
        }

        return false;

    }

    public static BlockPos getSurface(LevelAccessor world, BlockPos pos) {

        pos = new BlockPos(pos.getX(), world.getSeaLevel(), pos.getZ());

        boolean goingDown = world.isEmptyBlock(pos);

        while (world.isEmptyBlock(pos) || world.getBlockState(pos)
                .getBlock() instanceof LeavesBlock) {

            if (goingDown) {
                pos = pos.below();
            } else {
                pos = pos.above();
            }

        }

        while (world.isEmptyBlock(pos.above()) == false) {
            pos = pos.above();
        }

        return pos.above();

    }

    public static boolean isDungeonWorld(LevelReader world) {
        return false;
    }

    public static boolean isMapWorldClass(LevelReader world) {
        return false; // todo

    }

    static boolean isId(LevelReader world, ResourceLocation dimid) {

        if (MMORPG.server == null) {
            return false;
        }
        ResourceLocation id = MMORPG.server.registryAccess()
                .dimensionTypes()
                .getKey(world.dimensionType());

        if (id != null) {
            return id.equals(dimid);
        }

        return false;
    }

}
