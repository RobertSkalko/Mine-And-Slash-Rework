package com.robertx22.age_of_exile.gui.screens.spell;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateClassPointPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;


public class LearnClassPointButton extends ImageButton {

    static ResourceLocation SPELL_SLOT = SlashRef.guiId("spells/slots/spell");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    AscendancyClassScreen screen;

    Perk perk;


    public LearnClassPointButton(AscendancyClassScreen screen, Perk perk, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SPELL_SLOT, (button) -> {
            Packets.sendToServer(new AllocateClassPointPacket(screen.currentSchool(), perk, AllocateClassPointPacket.ACTION.ALLOCATE));

        });
        this.screen = screen;
        this.perk = perk;

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) {
            setModTooltip();
        }
        Minecraft mc = Minecraft.getInstance();

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SPELL_SLOT, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(perk.getIcon(), getX() + 1, getY() + 1, 16, 16, 16, 16, 16, 16);

        int currentlvl = Load.player(mc.player).ascClass.getLevel(perk.GUID());
        int maxlvl = perk.getMaxLevel();
        String lvltext = currentlvl + "/" + maxlvl;
        TextUtils.renderText(gui, 0.8F, lvltext, getX() + BUTTON_SIZE_X / 2, (int) (getY() + BUTTON_SIZE_Y * 0.85F), ChatFormatting.GREEN);

        super.render(gui, mouseX, mouseY, delta);

    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        // this.renderTexture(pGuiGraphics, this.resourceLocation, this.getX(), this.getY(), this.xTexStart, this.yTexStart, this.yDiffTex, this.width, this.height, this.textureWidth, this.textureHeight);
    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();

        TooltipInfo info = new TooltipInfo(mc.player);

        tooltip.addAll(perk.GetTooltipString(info));

        /*
        int reqlvl = screen.currentSchool()
                .getLevelNeededToAllocate(screen.currentSchool().perks.get(perk.GUID()));

        tooltip.add(Component.literal("Required Level: " + reqlvl).withStyle(ChatFormatting.RED));
        
         */


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }


}
