package com.robertx22.mine_and_slash.gui.card_picker;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class CardPickButton extends ImageButton {

    public static int SIZE_X = 124;
    public static int SIZE_Y = 191;
    static int iconSize = 34;

    Minecraft mc = Minecraft.getInstance();
    ICard card;

    public CardPickButton(ICard card, int xPos, int yPos) {
        super(xPos, yPos, SIZE_X, SIZE_Y, 0, 0, 0, SlashRef.guiId("pick_card"), (button) -> {
            card.onClick(ClientOnly.getPlayer());
            Minecraft.getInstance().setScreen(null);
        });
        this.card = card;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(this.resourceLocation, getX(), getY(), 0, 0, SIZE_X, SIZE_Y);
        ResourceLocation tex = card.getIcon();
        gui.blit(tex, getX() + 45, getY() + 38, iconSize, iconSize, iconSize, iconSize, iconSize, iconSize);

        gui.drawString(mc.font, card.getName(), this.getX() + SIZE_X / 2 - mc.font.width(card.getName()) / 2, getY() + 20, ChatFormatting.RED.getColor());


        var tip = TextUTIL.mergeList(card.getScreenText(mc.player));

        var split = mc.font.split(tip, 96);

        int max = 0;
        for (FormattedCharSequence c : split) {
            int size = mc.font.width(c);
            if (size > max) {
                max = size;
            }
        }

        gui.drawWordWrap(mc.font, tip, this.getX() + SIZE_X / 2 - max / 2, getY() + 100, 96, ChatFormatting.YELLOW.getColor());
    }


    public void setModTooltip() {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(card.getTooltip(mc.player))));
    }


}