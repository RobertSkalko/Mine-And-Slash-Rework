package com.robertx22.age_of_exile.maps.feature;

import com.robertx22.age_of_exile.maps.generator.BuiltRoom;
import com.robertx22.age_of_exile.maps.generator.DungeonBuilder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class DungeonFeature {


    /*
    public static int placeFeature(ServerLevel serverlevel, BlockPos pPos) throws CommandSyntaxException {

        ResourceKey<ConfiguredFeature<?, ?>> resourcekey = ResourceKey.create(Registries.CONFIGURED_FEATURE, WorldUtils.DUNGEON_DIM_ID);
        var key = getRegistry(serverlevel).getHolder(resourcekey).orElseThrow(() -> {
            return null;
        });
        ConfiguredFeature<?, ?> configuredfeature = key.value();
        ChunkPos chunkpos = new ChunkPos(pPos);
        checkLoaded(serverlevel, new ChunkPos(chunkpos.x - 1, chunkpos.z - 1), new ChunkPos(chunkpos.x + 1, chunkpos.z + 1));
        if (!configuredfeature.place(serverlevel, serverlevel.getChunkSource().getGenerator(), serverlevel.getRandom(), pPos)) {
            //throw ERROR_FEATURE_FAILED.create();
        } else {
            return 1;
        }
        return 0;
    }

    private static void checkLoaded(ServerLevel pLevel, ChunkPos pStart, ChunkPos pEnd) throws CommandSyntaxException {
        if (ChunkPos.rangeClosed(pStart, pEnd).filter((p_214542_) -> {
            return !pLevel.isLoaded(p_214542_.getWorldPosition());
        }).findAny().isPresent()) {
            throw BlockPosArgument.ERROR_NOT_LOADED.create();
        }
    }

     */

    public static boolean place(LevelAccessor level, RandomSource rand, BlockPos pos) {

        // todo
        if (level.registryAccess().registry(Registries.DIMENSION_TYPE).get().getKey(level.dimensionType()).equals(WorldUtils.DUNGEON_DIM_ID)) {
            return generateStructure(level, new ChunkPos(pos), rand);
        }


        return false;
    }

    private static boolean generateStructure(LevelAccessor world, ChunkPos cpos, RandomSource random) {


        DungeonBuilder builder = new DungeonBuilder(0, cpos);
        builder.build();

        if (!builder.dungeon.hasRoomForChunk(cpos)) {
            return false;
        }

        BuiltRoom room = builder.dungeon.getRoomForChunk(cpos);

        if (room == null) {
            return false;
        }


        var template = world.getServer().getStructureManager().get(room.getStructure()).get();
        StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE)
                .setRotation(room.data.rotation)
                .setIgnoreEntities(false);

        settings.setBoundingBox(settings.getBoundingBox());

        BlockPos position = cpos.getBlockAt(0, 50, 0);

        if (template == null) {
            System.out.println("FATAL ERROR: Structure does not exist (" + room.getStructure() + ")");
            return false;
        }

        // next if the structure is to be rotated then it must also be offset, because rotating a structure also moves it
        if (room.data.rotation == Rotation.COUNTERCLOCKWISE_90) {
            // west: rotate CCW and push +Z
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            position = position.offset(0, 0, template.getSize()
                    .getZ() - 1);
        } else if (room.data.rotation == Rotation.CLOCKWISE_90) {
            // east rotate CW and push +X
            settings.setRotation(Rotation.CLOCKWISE_90);
            position = position.offset(template.getSize()
                    .getX() - 1, 0, 0);
        } else if (room.data.rotation == Rotation.CLOCKWISE_180) {
            // south: rotate 180 and push both +X and +Z
            settings.setRotation(Rotation.CLOCKWISE_180);
            position = position.offset(template.getSize()
                    .getX() - 1, 0, template.getSize()
                    .getZ() - 1);
        } else //if (nextRoom.rotation == Rotation.NONE)
        {                // north: no rotation
            settings.setRotation(Rotation.NONE);
        }

        template.placeInWorld((ServerLevelAccessor) world, position, position, settings, random, 2);

        return true;


    }

}
