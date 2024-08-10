package com.robertx22.mine_and_slash.database.data.spells.summons.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.database.data.spells.summons.model.ModWolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ModWolfRender<T extends SummonEntity> extends MobRenderer<T, ModWolfModel<T>> {
    private ResourceLocation WOLF_LOCATION;

    public ModWolfRender(ResourceLocation tex, EntityRendererProvider.Context p_174452_) {

        super(p_174452_, new ModWolfModel<>(p_174452_.bakeLayer(ModelLayers.WOLF)), 0.5F);
        WOLF_LOCATION = tex;
    }


    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {


        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);


    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return WOLF_LOCATION;

    }
}