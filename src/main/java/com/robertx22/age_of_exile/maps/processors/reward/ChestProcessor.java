package com.robertx22.age_of_exile.maps.processors.reward;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

public class ChestProcessor extends DataProcessor {

    public ChestProcessor() {
        super("chest", Type.EQUALS);
        this.detectIds.add("big_chest"); // this is because i removed puzzle block
        this.detectIds.add("puzzle"); // this is because i removed puzzle block
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        boolean isTrapped = this.detectIds.contains("trap");

        boolean useVanilla = RandomUtils.roll(20);

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

            ResourceLocation table = ModLootTables.TIER_1_DUNGEON_CHEST;

            var map = Load.mapAt(world, pos);

            if (map != null) {

                float lvm = LevelUtils.getMaxLevelMultiplier(map.map.lvl);


                if (lvm > 0.3F) {
                    table = ModLootTables.TIER_2_DUNGEON_CHEST;
                }
                if (lvm > 0.5F) {
                    table = ModLootTables.TIER_3_DUNGEON_CHEST;
                }
                if (lvm > 0.7F) {
                    table = ModLootTables.TIER_4_DUNGEON_CHEST;
                }
                if (lvm > 0.9F) {
                    table = ModLootTables.TIER_5_DUNGEON_CHEST;
                }
            }

            chest.setLootTable(world, world.getRandom(), pos, table);

        } else {
            System.out.println("Chest gen failed, tile not instanceof vanilla chest.");
        }


    }
}
