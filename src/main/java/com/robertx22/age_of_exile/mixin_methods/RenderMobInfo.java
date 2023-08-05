package com.robertx22.age_of_exile.mixin_methods;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LookUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import com.mojang.math.Matrix4f;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

public class RenderMobInfo {

    static Entity lastLooked;

    public static void renderLivingEntityLabelIfPresent(Font textRenderer, EntityRenderDispatcher dispatcher, LivingEntity entity,
                                                        PoseStack matrixStack,
                                                        MultiBufferSource vertex, int i) {
        try {

            if (!ClientConfigs.getConfig().RENDER_MOB_HEALTH_GUI.get()) {
                return;
            }

            double squaredDistance =
                dispatcher.distanceToSqr(entity);
            if (squaredDistance <= 300) {

                if (ClientConfigs.getConfig().ONLY_RENDER_MOB_LOOKED_AT.get()) {
                    if (LookUtils.getEntityLookedAt(Minecraft.getInstance().player) != entity) {
                        if (lastLooked != entity) {
                            return;
                        }
                    } else {
                        lastLooked = entity;
                    }
                }
                if (entity instanceof ArmorStand) {
                    return;
                }
                if (entity.hasPassenger(Minecraft.getInstance().player)) {
                    return; // dont display horse's bar if the player is riding it
                }

                float yOffset = entity.getBbHeight() + 0.5F;

                EntityData data = Load.Unit(entity);

                boolean hidelvl = data.getLevel() - 10 > Load.Unit(Minecraft.getInstance().player)
                    .getLevel();

                MutableComponent lvlcomp =
                    new TextComponent(" [" + data.getLevel() + "]").withStyle(ChatFormatting.YELLOW);

                if (hidelvl) {
                    lvlcomp =
                        new TextComponent(" [" + "???" + "]").withStyle(ChatFormatting.YELLOW);
                }

                Component text = data.getName()
                    .append(lvlcomp)
                    .withStyle(ChatFormatting.RED, ChatFormatting.BOLD);

                float percent = HealthUtils.getCurrentHealth(entity) / HealthUtils.getMaxHealth(entity) * 100F;

                MutableComponent hpText = new TextComponent("[").withStyle(ChatFormatting.DARK_RED);
                int times = 0;

                for (int x = 0; x < 10; x++) {
                    times++;

                    if (percent > 0) {
                        hpText.append(new TextComponent("|")
                            .withStyle(ChatFormatting.RED)
                        );
                    } else {
                        hpText.append(new TextComponent("|")
                            .withStyle(ChatFormatting.DARK_RED)
                        );
                    }

                    if (times == 5) {
                        hpText.append(new TextComponent((int) HealthUtils.getCurrentHealth(entity) + "").withStyle(ChatFormatting.GOLD));
                    }
                    percent -= 10;

                }

                hpText.append(new TextComponent("]").withStyle(ChatFormatting.DARK_RED));

                matrixStack.pushPose();
                matrixStack.translate(0.0D, yOffset, 0.0D);
                matrixStack.mulPose(dispatcher.cameraOrientation());
                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = matrixStack.last()
                    .pose();
                float bgAlpha =
                    Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int bgColor = (int) (bgAlpha * 255.0F) << 24;

                if (matrix4f == null || textRenderer == null) {
                    return;
                }

                try {
                    textRenderer.drawInBatch(text,
                        -textRenderer.width(text) / 2.0f,
                        -12, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                    textRenderer.drawInBatch(hpText, -textRenderer.width(hpText) / 2.0f,
                        0, -1, true, matrix4f,
                        vertex, false, bgColor, i);

                    MobRarity rar = ExileDB.MobRarities()
                        .get(data.getRarity());

                    String icon = rar.name_add;
                    if (!icon.isEmpty()) {
                        icon = rar.textFormatting() + icon;

                        matrixStack.scale(2, 2, 2);
                        textRenderer.drawInBatch(icon, -textRenderer.width(icon) / 2.0f,
                            -15, -1, true, matrix4f,
                            vertex, false, ChatFormatting.YELLOW
                                .getId(), i);
                        matrixStack.scale(0.5F, 0.5F, 0.5F);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                matrixStack.popPose();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
