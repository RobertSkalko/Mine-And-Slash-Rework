package com.robertx22.mine_and_slash.a_libraries.neat;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.robertx22.mine_and_slash.mixins.AccessorRenderType;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;

public class NeatRenderType extends RenderStateShard {

    //https://github.com/UpcraftLP/Orderly/blob/master/src/main/resources/assets/orderly/textures/ui/default_health_bar.png
    public static final ResourceLocation HEALTH_BAR_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/health_bar_texture.png");
    public static final RenderType BAR_TEXTURE_TYPE = getHealthBarType();

    private NeatRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    private static RenderType getHealthBarType() {
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                .setTextureState(new TextureStateShard(NeatRenderType.HEALTH_BAR_TEXTURE, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
        return AccessorRenderType.neat_create("neat_health_bar", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, true, renderTypeState);
    }
}