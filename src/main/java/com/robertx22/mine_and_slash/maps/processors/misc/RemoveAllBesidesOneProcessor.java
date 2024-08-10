package com.robertx22.mine_and_slash.maps.processors.misc;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RemoveAllBesidesOneProcessor extends DataProcessor {

    public RemoveAllBesidesOneProcessor() {
        super("remove_all_besides_one", Type.CONTAINS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {
        try {
            String[] parts = key.split(":");
            String blockID = parts[1];

            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockID));

            List<BlockPos> list = new ArrayList<>();

            Function<BlockPos, Boolean> function = null;

            if (blockID.equals("button")) {
                function = x -> {

                    BlockState state = world.getBlockState(x);

                    if (state.getBlock()
                            instanceof ButtonBlock) {
                        return true;

                    }
                    return false;
                };
            }

            for (BlockPos blockPos : data.getBlockPosList()) {
                if (function.apply(blockPos)) {
                    list.add(blockPos);
                }
            }
            if (!list.isEmpty()) {

                list.remove(RandomUtils.RandomRange(0, list.size() - 1));

                list.forEach(x -> world.setBlock(x, Blocks.AIR.defaultBlockState(), 2));

            } else {
                ExileLog.get().warn("Didn't find any correct blocks?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

