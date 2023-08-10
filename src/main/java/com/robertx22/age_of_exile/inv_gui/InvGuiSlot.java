package com.robertx22.age_of_exile.inv_gui;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.TextUtils;
import com.robertx22.age_of_exile.gui.screens.spell.SpellScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateSpellPacket;
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

public class InvGuiSlot extends ImageButton {

    static ResourceLocation TEX = SlashRef.guiId("inv_gui.png");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    SpellScreen screen;

    Spell spell;

    public InvGuiSlot(SpellScreen screen, Spell spell, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, TEX, (button) -> {

            Packets.sendToServer(new AllocateSpellPacket(screen.currentSchool(), spell, AllocateSpellPacket.ACTION.ALLOCATE));

        });
        this.screen = screen;
        this.spell = spell;

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();

        Minecraft mc = Minecraft.getInstance();

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(TEX, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(spell.getIconLoc(), getX() + 1, getY() + 1, 16, 16, 16, 16, 16, 16);

        int currentlvl = Load.spells(mc.player)
                .getLevelOf(spell.GUID());
        int maxlvl = spell.getMaxLevel();
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

        tooltip.addAll(spell.GetTooltipString(info));

        int reqlvl = screen.currentSchool()
                .getLevelNeededToAllocate(screen.currentSchool().spells.get(spell.GUID()));

        tooltip.add(Component.literal("Required Level: " + reqlvl).withStyle(ChatFormatting.RED));


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }


}
