package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class RenderLayersRegister {
    public static void setup() {

        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.BLACK_HOLE.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.BLUE_TOTEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.PROJECTILE_TOTEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.GUARD_TOTEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.TRAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.GREEN_TOTEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.GLYPH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.MAGMA_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.THORN_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SlashBlocks.FROST_FLOWER.get(), RenderType.cutout());

     
        //  RenderTypeLookup.setRenderLayer(SlashBlocks.RUNEWORD.get(), RenderType.cutout());

    }
}
