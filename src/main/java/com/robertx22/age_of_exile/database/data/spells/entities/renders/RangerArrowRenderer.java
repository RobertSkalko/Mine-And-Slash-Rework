package com.robertx22.age_of_exile.database.data.spells.entities.renders;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.resources.ResourceLocation;

public class RangerArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {
    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public RangerArrowRenderer(EntityRenderDispatcher manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(T en) {
        return RES_ARROW;
    }

}