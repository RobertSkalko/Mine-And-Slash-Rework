package com.robertx22.age_of_exile.maps.feature;

import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.generator.BuiltRoom;
import com.robertx22.age_of_exile.maps.generator.DungeonBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class DungeonFeature {

    public static boolean place(MapData mapData, LevelAccessor level, RandomSource rand, BlockPos pos) {
        return generateStructure(mapData, level, new ChunkPos(pos), rand);
    }

    private static boolean generateStructure(MapData mapData, LevelAccessor world, ChunkPos cpos, RandomSource random) {


        DungeonBuilder builder = new DungeonBuilder(0, cpos);
        builder.build();

        if (!builder.dungeon.hasRoomForChunk(cpos)) {
            return false;
        }

        var bpos = cpos.getMiddleBlockPosition(50);

        ChunkPos start = MapData.getStartChunk(bpos);

        // if its the start of the dungeon, we init some stuff
        if (cpos.equals(start)) {
            if (builder.group.netherParticles) {
                mapData.netherParticles = true;
            }
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
