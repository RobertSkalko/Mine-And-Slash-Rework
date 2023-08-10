package com.robertx22.age_of_exile.maps.processors.reward;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ChestProcessor extends DataProcessor {

    public ChestProcessor() {
        super("chest", Type.CONTAINS);
        this.detectIds.add("puzzle"); // this is because i removed puzzle block
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        boolean isTrapped = this.detectIds.contains("trap");

        boolean useVanilla = RandomUtils.roll(20);

        int chestChance = RandomUtils.RandomRange(1, 4);

        if (chestChance > 1) {
            if (isTrapped) {
                world.setBlock(pos, Blocks.TRAPPED_CHEST
                        .defaultBlockState(), 2);

            } else {
                world.setBlock(pos, Blocks.CHEST
                        .defaultBlockState(), 2);

            }

            BlockEntity tile = world.getBlockEntity(pos);

            if (tile instanceof ChestBlockEntity) {
                ChestBlockEntity chest = (ChestBlockEntity) tile;


                ResourceLocation table = BuiltInLootTables.SIMPLE_DUNGEON;
                // todo add special

                chest.setLootTable(world, world.getRandom(), pos, table);

            } else {
                System.out.println("Chest gen failed, tile not instanceof vanilla chest.");
            }


        }
    }
}
