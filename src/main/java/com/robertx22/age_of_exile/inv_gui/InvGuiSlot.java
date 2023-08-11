package com.robertx22.age_of_exile.inv_gui;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.screens.spell.AscendancyClassScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class InvGuiSlot extends ImageButton {

    static ResourceLocation TEX = SlashRef.guiId("inv_gui.png");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    AscendancyClassScreen screen;

    Spell spell;

    public InvGuiSlot(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation pResourceLocation, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pResourceLocation, pOnPress);
    }
    /*

    public InvGuiSlot(AscendancyClassScreen screen, Spell spell, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, TEX, (button) -> {

            Packets.sendToServer(new AllocateClassPointPacket(screen.currentSchool(), spell, AllocateClassPointPacket.ACTION.ALLOCATE));

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
                .getLevelNeededToAllocate(screen.currentSchool().perks.get(spell.GUID()));

        tooltip.add(Component.literal("Required Level: " + reqlvl).withStyle(ChatFormatting.RED));


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }


     */

}
