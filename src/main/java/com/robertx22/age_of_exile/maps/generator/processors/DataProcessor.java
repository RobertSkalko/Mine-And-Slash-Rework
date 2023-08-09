package com.robertx22.age_of_exile.maps.generator.processors;


import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class DataProcessor {

    public DataProcessor(String data) {
        this.data = data;
        this.type = Type.EQUALS;
    }

    public DataProcessor(String data, Type type) {
        this.data = data;
        this.type = type;
    }

    public enum Type {
        EQUALS, CONTAINS
    }

    Type type;

    public String data;

    public final boolean process(String key, BlockPos pos, Level world, ChunkProcessData chunkData) {
        if (type == Type.EQUALS && this.data.equals(key)) {
            processImplementation(key, pos, world, chunkData);
            return true;
        } else if (type == Type.CONTAINS && key.contains(this.data)) {
            processImplementation(key, pos, world, chunkData);
            return true;
        }

        return false;
    }

    public abstract void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data);

}
