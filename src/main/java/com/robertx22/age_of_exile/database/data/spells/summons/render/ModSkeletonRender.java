package com.robertx22.age_of_exile.database.data.spells.summons.render;

import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ModSkeletonRender extends HumanoidMobRenderer<SummonEntity, SkeletonModel<SummonEntity>> {
    private ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public ModSkeletonRender(ResourceLocation tex, EntityRendererProvider.Context ctx) {
        this(ctx, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
        SKELETON_LOCATION = tex;
    }

    public ModSkeletonRender(EntityRendererProvider.Context pContext, ModelLayerLocation pSkeletonLayer, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new SkeletonModel<>(pContext.bakeLayer(pSkeletonLayer)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new SkeletonModel(pContext.bakeLayer(pInnerModelLayer)), new SkeletonModel(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
    }

  
    public ResourceLocation getTextureLocation(SummonEntity pEntity) {
        return SKELETON_LOCATION;
    }

    protected boolean isShaking(SummonEntity pEntity) {
        return false;
    }
}