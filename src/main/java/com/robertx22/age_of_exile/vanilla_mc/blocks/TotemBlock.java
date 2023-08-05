package com.robertx22.age_of_exile.vanilla_mc.blocks;

import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.OpaqueBlock;
import net.minecraft.world.level.material.Material;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class TotemBlock extends OpaqueBlock {

    public TotemBlock() {
        super(Properties.of(Material.STONE)
            .strength(5F, 2));
    }
}
