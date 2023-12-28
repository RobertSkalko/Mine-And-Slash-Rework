package com.robertx22.age_of_exile.maps.processors;


import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class DataProcessor {

    public DataProcessor(String detectIds) {
        this.detectIds.add(detectIds);
        this.type = Type.EQUALS;
    }

    public DataProcessor(String detectIds, Type type) {
        this.detectIds.add(detectIds);
        this.type = type;
    }

    public enum Type {
        EQUALS, CONTAINS
    }

    Type type;

    // ways to detect
    public List<String> detectIds = new ArrayList<>();

    public static String getData(BlockEntity be) {

        if (be instanceof StructureBlockEntity struc) {
            CompoundTag nbt = struc.saveWithoutMetadata();
            return nbt.getString("metadata");
        }
        if (be instanceof CommandBlockEntity cb) {
            return cb.getCommandBlock().getCommand();
        }

        return "";
    }

    public final boolean process(String key, BlockPos pos, Level world, ChunkProcessData chunkData) {

        if (type == Type.EQUALS && detectIds.contains(key)) {
            processImplementation(key, pos, world, chunkData);
            return true;
        } else if (type == Type.CONTAINS && detectIds.stream().anyMatch(key::contains)) {
            processImplementation(key, pos, world, chunkData);
            return true;
        }

        return false;
    }

    public abstract void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data);

}