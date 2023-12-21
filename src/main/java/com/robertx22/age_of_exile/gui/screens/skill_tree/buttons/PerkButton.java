package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

import java.util.List;

public class PerkButton extends ImageButton {

    public static int SPACING = 26;
    public static int BIGGEST = 33;

    static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/perk_buttons.png");
    public static ResourceLocation LOCKED_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/locked.png");

    public Perk perk;
    public PointData point;
    public TalentTree school;
    public PlayerData playerData;
    public String search;

    public int originalWidth;
    public int originalHeight;

    public int origX;
    public int origY;
    Minecraft mc = Minecraft.getInstance();
    SkillTreeScreen screen;

    public PerkButton(SkillTreeScreen screen, PlayerData playerData, TalentTree school, PointData point, Perk perk, int x, int y) {
        super(x, y, perk.getType().width, perk.getType().height, 0, 0, 1, ID, (action) -> {
        });
        this.perk = perk;
        this.point = point;
        this.school = school;
        this.playerData = playerData;

        this.origX = x;
        this.origY = y;
        this.originalWidth = this.width;
        this.originalHeight = this.height;
        this.screen = screen;

    }

    public boolean isInside(int x, int y) {

        float scale = 2 - screen.zoom;
        return GuiUtils.isInRect((int) (this.getX() - ((width / 4) * scale)), (int) (this.getY() - ((height / 4) * scale)), (int) (width * scale), (int) (height * scale), x, y);
    }


    private void setTooltipMOD(GuiGraphics gui, int mouseX, int mouseY) {

        int MmouseX = (int) (1F / screen.zoom * mouseX);
        int MmouseY = (int) (1F / screen.zoom * mouseY);

        if (this.isInside(MmouseX, MmouseY)) {
            List<Component> tooltip = perk.GetTooltipString(new TooltipInfo(Minecraft.getInstance().player));
            setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) {
                screen.setTooltipForNextRenderPass(this.getTooltip(), this.createTooltipPositioner(), true);
            }
            //GuiUtils.renderTooltip(gui, tooltip, mouseX, mouseY);
        } else {
            setTooltip(null);
        }
    }

    // copied from abstractbutton
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        screen.mouseRecentlyClickedTicks = 25;
        screen.pointClicked = this.point;

        mouseX = 1F / screen.zoom * mouseX;
        mouseY = 1F / screen.zoom * mouseY;

        if (this.active && this.visible) {

            boolean bl = this.clicked(mouseX, mouseY);
            if (bl) {
                this.playDownSound(Minecraft.getInstance()
                        .getSoundManager());

                if (button == 0) {
                    Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.ALLOCATE));
                }
                if (button == 1) {
                    Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.REMOVE));
                }
                this.onClick(mouseX, mouseY);

                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    int xPos(int offset, float multi) {
        float scale = 2 - screen.zoom;
        return (int) ((this.getX() - ((width / 6) * scale)) * multi) + offset;
    }

    int yPos(int offset, float multi) {
        float scale = 2 - screen.zoom;
        return (int) ((this.getY() - ((height / 6) * scale)) * multi) + offset;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick) {
        setTooltipMOD(gui, mouseX, mouseY);

        float scale = 2 - screen.zoom;

        float posMulti = 1F / scale;

        if (posMulti > 1.5F) {
            posMulti = 1.5F;
        }
        float zoomOffset = Mth.lerp(screen.zoom, 0.0f, -0.04f);
        gui.pose().translate(width / 2f, height / 2f, 0);
        gui.pose().scale(scale, scale, scale);
        float inverse = 1F / scale;
        gui.pose().translate((-(width / 2f) - zoomOffset / 4f) * inverse, (-(height / 2f) - zoomOffset / 4f) * inverse, 0);
        PerkStatus status = playerData.talents.getStatus(Minecraft.getInstance().player, school, point);


        int offset = 4;

        // background
        RenderSystem.enableDepthTest();

        boolean containsSearchStat = !search.isEmpty() && perk.stats.stream()
                .anyMatch(item -> item.getStat().translate().toLowerCase().contains(search.toLowerCase()));
        float opacity = containsSearchStat || search.isEmpty() ? 1.0f : 0.2f;

        this.blit(ID, xPos(0, posMulti), yPos(0, posMulti), perk.getType()
                .getXOffset(), status.getYOffset(), this.width, this.height, opacity, gui.pose());

        if (this.perk.getType() == Perk.PerkType.STAT) {
            // icon
            this.blit(perk.getIcon(), xPos(offset, posMulti), yPos(offset, posMulti), 0, 0, 16, 16, 16, 16, opacity, gui.pose());
        } else if (this.perk.getType() == Perk.PerkType.MAJOR) {
            // icon
            offset = 9;
            RenderUtils.render16Icon(gui, perk.getIcon(), xPos(offset, posMulti), yPos(offset, posMulti));
        } else if (perk.getType() == Perk.PerkType.START) {
            offset = 6;
            if (perk.icon == null || perk.icon.isEmpty()) {
                RenderUtils.render16Icon(gui, new ResourceLocation(school.icon), xPos(offset, posMulti), yPos(offset, posMulti));
            } else {
                RenderUtils.render16Icon(gui, perk.getIcon(), xPos(offset, posMulti), yPos(offset, posMulti));
            }
        } else if (perk.getType() == Perk.PerkType.SPECIAL) {

            // icon
            offset = 6;
            this.blit(perk.getIcon(), xPos(offset, posMulti), yPos(offset, posMulti), 0, 0, 16, 16, 16, 16, opacity, gui.pose());
        }

        gui.pose().scale(1F / scale, 1F / scale, 1F / scale);
    }

    void blit(ResourceLocation pAtlasLocation, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight, float pAlpha, PoseStack pose) {
        this.blit(pAtlasLocation, pX, pY, 0, (float)pUOffset, (float)pVOffset, pUWidth, pVHeight, 256, 256, pAlpha, pose);
    }

    void blit(ResourceLocation pAtlasLocation, int pX, int pY, int pBlitOffset, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight, float pAlpha, PoseStack pose) {
        this.blit(pAtlasLocation, pX, pX + pUWidth, pY, pY + pVHeight, pBlitOffset, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight, pAlpha, pose);
    }

    void blit(ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight, float pAlpha, PoseStack pose) {
        this.blit(pAtlasLocation, pX, pX + pWidth, pY, pY + pHeight, 0, pUWidth, pVHeight, pUOffset, pVOffset, pTextureWidth, pTextureHeight, pAlpha, pose);
    }

    void blit(ResourceLocation pAtlasLocation, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight, float pAlpha, PoseStack pose) {
        this.blit(pAtlasLocation, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight, pAlpha, pose);
    }

    void blit(ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, int pUWidth, int pVHeight, float pUOffset, float pVOffset, int pTextureWidth, int pTextureHeight, float pAlpha, PoseStack pose) {
        this.innerBlit(pAtlasLocation, pX1, pX2, pY1, pY2, pBlitOffset, (pUOffset + 0.0F) / (float)pTextureWidth, (pUOffset + (float)pUWidth) / (float)pTextureWidth, (pVOffset + 0.0F) / (float)pTextureHeight, (pVOffset + (float)pVHeight) / (float)pTextureHeight, 1.0f, 1.0f, 1.0f, pAlpha, pose);
    }

    void innerBlit(ResourceLocation pAtlasLocation, int pX1, int pX2, int pY1, int pY2, int pBlitOffset, float pMinU, float pMaxU, float pMinV, float pMaxV, float pRed, float pGreen, float pBlue, float pAlpha, PoseStack pose) {
        RenderSystem.setShaderTexture(0, pAtlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = pose.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMinV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMaxV).endVertex();
        bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMinV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

//     void innerBlit(PoseStack stack, ResourceLocation pAtlasLocation, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight, float pAlpha) {
//         int pX1 = pX;
//         int pX2 = pX + pUWidth;
//         int pY1 = pY;
//         int pY2 = pY + pVHeight;
//
//         int pBlitOffset = 0;
//         int pTextureWidth = 256;
//         int pTextureHeight = 256;
//
//         float pMinU = ((float)pUOffset + 0.0F) / (float)pTextureWidth;
//         float pMaxU = ((float)pUOffset + (float)pUWidth) / (float)pTextureWidth;
//         float pMinV = ((float)pVOffset + 0.0F) / (float)pTextureHeight;
//         float pMaxV = ((float)pVOffset + (float)pVHeight) / (float)pTextureHeight;
//         float pRed = 1.0f;
//         float pGreen = 1.0f;
//         float pBlue = 1.0f;
//
//         RenderSystem.setShaderTexture(0, pAtlasLocation);
//         RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
//         RenderSystem.enableBlend();
//         Matrix4f matrix4f = stack.last().pose();
//         BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
//         bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
//         bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMinV).endVertex();
//         bufferbuilder.vertex(matrix4f, (float)pX1, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMinU, pMaxV).endVertex();
//         bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY2, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMaxV).endVertex();
//         bufferbuilder.vertex(matrix4f, (float)pX2, (float)pY1, (float)pBlitOffset).color(pRed, pGreen, pBlue, pAlpha).uv(pMaxU, pMinV).endVertex();
//         BufferUploader.drawWithShader(bufferbuilder.end());
//         RenderSystem.disableBlend();
//    }
}