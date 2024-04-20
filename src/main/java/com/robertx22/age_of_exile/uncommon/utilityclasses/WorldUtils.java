package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;

import java.util.Arrays;

public class WorldUtils {

    public static ResourceLocation DUNGEON_DIM_ID = SlashRef.id("dungeon");

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
            if (world.getBlockState(x).is(Blocks.WATER)) {
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

    // todo clean up
    public static boolean isDungeonWorld(Level world) {
        return isMapWorldClass(world);
    }

    public static boolean isMapWorldClass(Level world) {
        if (world == null) {
            return false;
        }
        return VanillaUTIL.REGISTRY.dimensionTypes(world).getKey(world.dimensionType()).equals(DUNGEON_DIM_ID); // todo

    }

    static boolean isId(Level world, ResourceLocation dimid) {

        ResourceLocation id = VanillaUTIL.REGISTRY.dimensionTypes((Level) world)
                .getKey(world.dimensionType());

        if (id != null) {
            return id.equals(dimid);
        }

        return false;
    }

}
