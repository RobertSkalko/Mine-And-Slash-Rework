package com.robertx22.mine_and_slash.a_libraries.neat;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.robertx22.mine_and_slash.mixins.AccessorRenderType;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.HashMap;
import java.util.Map;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;

public class NeatRenderType extends RenderStateShard {

    //https://github.com/UpcraftLP/Orderly/blob/master/src/main/resources/assets/orderly/textures/ui/default_health_bar.png
    public static final ResourceLocation HEALTH_BAR_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/health_bar_texture.png");
    public static final String barKey = "bar";
    public static final String iconKey = "icon";
    //have to do this otherwise sometime the game will crash after hotswap.
    //prob is a mixin bug
    private static Map<String, RenderType> caches;

    private NeatRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    private static Map<String, RenderType> getCaches(){
        if (NeatRenderType.caches == null){
            NeatRenderType.caches = new HashMap<>();
        }
        return NeatRenderType.caches;
    }

    public static RenderType getHealthBarType(){
        return getCaches().computeIfAbsent(NeatRenderType.barKey, x -> NeatRenderType.generateHealthBarType());
    }

    public static RenderType getHealthBarIconType(ResourceLocation location){
        return getCaches().computeIfAbsent(NeatRenderType.iconKey + location.getPath(), x -> {
            RenderType.CompositeState renderTypeState = RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setTextureState(new TextureStateShard(location, false, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false);
            return AccessorRenderType.neat_create("neat_health_bar_icon", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, true, renderTypeState);
        });
    }

    private static RenderType generateHealthBarType() {
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER)
                .setTextureState(new TextureStateShard(NeatRenderType.HEALTH_BAR_TEXTURE, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .createCompositeState(false);
        return AccessorRenderType.neat_create("neat_health_bar", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, true, renderTypeState);
    }
}