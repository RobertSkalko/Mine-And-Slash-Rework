package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
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
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

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
//                System.out.println(this.getX() + "_" + getY() + " : " + perk.GUID());


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
        return (int) (this.getX() * multi) + offset;

    }

    int yPos(int offset, float multi) {
        return (int) (getY() * multi) + offset;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick) {
        setTooltipMOD(gui, mouseX, mouseY);

        gui.pose().pushPose();

        float scale = 2 - screen.zoom;

        float posMulti = 1F / scale;


        float zoomOffset = Mth.lerp(screen.zoom, 0.0f, -0.04f);
        gui.pose().translate(width / 2f, height / 2f, 0);
        gui.pose().scale(scale, scale, scale);
        float inverse = 1F / scale;
        gui.pose().translate((-(width / 2f) - zoomOffset / 4f) * inverse, (-(height / 2f) - zoomOffset / 4f) * inverse, 0);
        PerkStatus status = playerData.talents.getStatus(Minecraft.getInstance().player, school, point);


        int offset = perk.getType().getOffset();

        // background
        RenderSystem.enableDepthTest();

        boolean containsSearchStat = !search.isEmpty() && perk.stats.stream()
                .anyMatch(item -> item.getStat().translate().toLowerCase().contains(search.toLowerCase()));
        float opacity = containsSearchStat || search.isEmpty() ? 1.0f : 0.2f;

        gui.setColor(1.0F, 1.0F, 1.0F, opacity);

        gui.blit(ID, xPos(0, posMulti), yPos(0, posMulti), perk.getType().getXOffset(), status.getYOffset(), this.width, this.height);

        gui.blit(perk.getIcon(), xPos(offset, posMulti), yPos(offset, posMulti), 0, 0, 16, 16, 16, 16);


        gui.pose().scale(1F / scale, 1F / scale, 1F / scale);
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.pose().popPose();

    }
}