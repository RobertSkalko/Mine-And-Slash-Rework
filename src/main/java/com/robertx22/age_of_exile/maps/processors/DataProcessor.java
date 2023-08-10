package com.robertx22.age_of_exile.maps.processors;


import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<String> getData(BlockEntity be) {

        if (be instanceof StructureBlockEntity struc) {
            CompoundTag nbt = struc.saveWithoutMetadata();
            return Arrays.asList(nbt.getString("metadata"));
        }
        if (be instanceof SignBlockEntity sign) {
            List<String> list = new ArrayList<>();
            for (Component msg : sign.getFrontText().getMessages(false)) {
                list.add(msg.getString()); // todo make sure this works
            }
        }

        return null;
    }

    public final boolean process(List<String> list, BlockPos pos, Level world, ChunkProcessData chunkData) {
        String key = list.get(0);

        if (list.size() > 1) {
            key = "";
            for (String s : list) {
                key += s + ";"; // todo this might be temporary until i get rid of all data blocks and turn all into sings, then it will all be split by sign row
            }
        }

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
