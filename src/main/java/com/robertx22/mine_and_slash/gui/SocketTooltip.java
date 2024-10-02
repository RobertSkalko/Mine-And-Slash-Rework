package com.robertx22.mine_and_slash.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.SocketData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import java.util.List;

public class SocketTooltip implements ClientTooltipComponent {

    public static final ResourceLocation SOCKET = new ResourceLocation(SlashRef.MODID, "textures/gui/socket.png");

    private final SocketComponent comp;
    private final int spacing = Minecraft.getInstance().font.lineHeight + 2;


    public SocketTooltip(SocketComponent comp) {
        this.comp = comp;
    }

    @Override
    public int getHeight() {
        return this.spacing * this.comp.gems.size();
    }

    @Override
    public int getWidth(Font font) {
        int maxWidth = 0;
        for (SocketData gem : this.comp.gems) {
            maxWidth = Math.max(maxWidth, font.width(getSocketDesc(this.comp.socketed, gem)) + 12);
        }
        return maxWidth;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics gfx) {
        for (int i = 0; i < this.comp.gems.size(); i++) {
            gfx.blit(SOCKET, x, y + this.spacing * i, 0, 0, 0, 9, 9, 9, 9);
        }
        for (SocketData gem : this.comp.gems()) {
            if (!gem.isEmpty()) {
                PoseStack pose = gfx.pose();
                pose.pushPose();
                pose.scale(0.5F, 0.5F, 1);
                gfx.renderFakeItem(gem.getOriginalItemStack(), 2 * x + 1, 2 * y + 1);
                pose.popPose();
            }
            y += this.spacing;
        }
    }

    @Override
    public void renderText(Font pFont, int pX, int pY, Matrix4f pMatrix4f, MultiBufferSource.BufferSource pBufferSource) {
        for (int i = 0; i < this.comp.gems.size(); i++) {
            pFont.drawInBatch(getSocketDesc(this.comp.socketed, this.comp.gems.get(i)), pX + 12, pY + 1 + this.spacing * i, 0xAABBCC, true, pMatrix4f, pBufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        }
    }

    public static Component getSocketDesc(ItemStack socketed, SocketData gemStack) {
        return gemStack.GetTooltipString(new StatRangeInfo(ModRange.always(gemStack.p)), ExileStack.of(socketed), false).get(0);

    }

    public static record SocketComponent(ItemStack socketed, List<SocketData> gems) implements TooltipComponent {
    }

}