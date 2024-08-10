package com.robertx22.mine_and_slash.database.data.spells.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;

public class MySpriteRenderer<T extends Entity & IMyRenderAsItem> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public MySpriteRenderer(EntityRendererProvider.Context ctx, ItemRenderer itemRenderer, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = itemRenderer;
        this.scale = scale;
        this.lit = lit;
    }

    public MySpriteRenderer(EntityRendererProvider.Context dispatcher, ItemRenderer itemRenderer) {
        this(dispatcher, itemRenderer, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos blockPos) {
        return this.lit ? 15 : super.getBlockLightLevel(entity, blockPos);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            matrices.pushPose();

            float scale = 1; // todo

            matrices.scale(scale, scale, scale);
            matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrices.mulPose(Axis.YP.rotationDegrees(180.0F));

            this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.level(), entity.getId());


            matrices.popPose();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    public ResourceLocation getTextureLocation(Entity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
