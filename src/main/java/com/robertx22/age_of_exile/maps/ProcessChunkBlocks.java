package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.capability.world.WorldData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.maps.feature.DungeonFeature;
import com.robertx22.age_of_exile.maps.generator.BuiltRoom;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.generator.DungeonBuilder;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.maps.processors.DataProcessors;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;

public class ProcessChunkBlocks {

    private static void logRoomForPos(Level world, BlockPos pos) {

        try {
            ChunkPos cpos = new ChunkPos(pos);

            DungeonBuilder builder = new DungeonBuilder(0, cpos); // todo
            builder.build();
            BuiltRoom room = builder.builtDungeon.getRoomForChunk(cpos);

            System.out.println("Room affected: " + room.getStructure()
                    .toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void process(ServerLevel level, BlockPos pos) {

        try {
            if (level.isClientSide) {
                return;
            }
            if (WorldUtils.isMapWorldClass(level)) {
                WorldData mapdata = Load.worldData(level);

                ChunkPos start = new ChunkPos(pos);

                var opt = mapdata.map.getMap(start);

                if (!opt.isPresent()) {
                    return;
                }

                List<ChunkPos> terrainChunks = new ArrayList<>();
                terrainChunks.add(start);
                List<ChunkPos> mobChunks = new ArrayList<>();
                mobChunks.add(start);

                int terrain = ServerContainer.get().MAP_GEN_TERRAIN_RADIUS.get();
                int mob = ServerContainer.get().MAP_GEN_MOB_RADIUS.get();

                for (int x = -terrain; x < terrain; x++) {
                    for (int z = -terrain; z < terrain; z++) {
                        terrainChunks.add(new ChunkPos(start.x + x, start.z + z));
                    }
                }
                for (int x = -mob; x < mob; x++) {
                    for (int z = -mob; z < mob; z++) {
                        mobChunks.add(new ChunkPos(start.x + x, start.z + z));
                    }
                }

                int gened = 0;

                int maxtogen = 3;

                DungeonBuilder builder = new DungeonBuilder(0, start);
                builder.build();


                for (ChunkPos cpos : terrainChunks) {
                    if (!level.hasChunk(cpos.x, cpos.z)) {
                        continue;
                    }
                    ChunkAccess c = level.getChunk(cpos.x, cpos.z);

                    if (c instanceof LevelChunk chunk) {

                        var chunkdata = Load.chunkData(chunk);

                        if (!chunkdata.generatedTerrain) {

                            chunkdata.generatedTerrain = true;

                            DungeonFeature.place(opt.get(), level, level.getRandom(), cpos.getBlockAt(0, 0, 0));

                            var ran = DungeonBuilder.createRandom(0l, cpos);

                            for (LeagueMechanic mech : opt.get().map.getLeagueMechanics()) {
                                mech.tryGenerate(level, cpos, ran);
                            }
                            gened++;
                            if (gened >= maxtogen) {
                                return;
                            }
                        }

                    }
                }

                for (ChunkPos cpos : mobChunks) {
                    if (!level.hasChunk(cpos.x, cpos.z)) {
                        continue;
                    }
                    ChunkAccess c = level.getChunk(cpos.x, cpos.z);

                    if (c instanceof LevelChunk chunk) {

                        var chunkdata = Load.chunkData(chunk);

                        if (!chunkdata.generatedMobs) {

                            chunkdata.generatedMobs = true;

                            BuiltRoom room = builder.builtDungeon.getRoomForChunk(cpos);


                            // this will gen both the league mechs and the dungeon if it runs after the league mechs gen
                            generateData(level, chunk, room);

                        }

                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void generateData(ServerLevel level, LevelChunk chunk, BuiltRoom room) {


        ChunkProcessData data = new ChunkProcessData(chunk, room);


        for (BlockPos tilePos : chunk.getBlockEntitiesPos()) {

            BlockEntity tile = level.getBlockEntity(tilePos);
            var text = DataProcessor.getData(tile);
            if (!text.isEmpty()) {


                boolean any = false;

                // todo make this work on either signs or these blocks

                for (DataProcessor processor : DataProcessors.getAll()) {
                    boolean did = processor.process(text, tilePos, level, data);
                    if (did) {
                        any = true;
                    }
                }

                if (!any) {
                    // todo do i just summon mobs when the tag fails?
                }

                if (any) {
                    // only set to air if the processor didnt turn it into another block
                    if (level.getBlockState(tilePos).getBlock() == Blocks.STRUCTURE_BLOCK || level.getBlockState(tilePos).getBlock() == Blocks.COMMAND_BLOCK) {
                        level.setBlock(tilePos, Blocks.AIR.defaultBlockState(), 2); // delete data block
                        level.removeBlockEntity(tilePos);
                    }

                } else {
                    System.out.println("Data block with tag: " + text + " matched no processors! " + tilePos.toString());
                    logRoomForPos(level, tilePos);
                }
            }


        }
    }
}