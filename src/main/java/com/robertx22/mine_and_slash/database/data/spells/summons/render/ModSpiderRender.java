package com.robertx22.mine_and_slash.database.data.spells.summons.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.resources.ResourceLocation;


public class ModSpiderRender<T extends SummonEntity> extends MobRenderer<T, SpiderModel<T>> {
    private ResourceLocation SPIDER_LOCATION = new ResourceLocation("textures/entity/spider/spider.png");

    public ModSpiderRender(ResourceLocation id, EntityRendererProvider.Context p_174401_) {
        this(p_174401_, ModelLayers.SPIDER);
        this.SPIDER_LOCATION = id;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        float s = 0.45F;
        pMatrixStack.scale(s, s, s);
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    public ModSpiderRender(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext, new SpiderModel<>(pContext.bakeLayer(pLayer)), 0.25F);
        this.addLayer(new SpiderEyesLayer<>(this));
    }

    protected float getFlipDegrees(T pLivingEntity) {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(T pEntity) {
        return SPIDER_LOCATION;
    }
}