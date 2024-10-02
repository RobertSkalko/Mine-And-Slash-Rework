package com.robertx22.mine_and_slash.characters.reworked_gui;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;

import static net.minecraft.client.gui.components.AbstractWidget.WIDGETS_LOCATION;

public class ToonEntry extends ObjectSelectionList.Entry<ToonEntry> {

    ToonData entry;
    ToonList list;


    public ToonEntry(ToonList wikiEntryList, ToonData entry) {
        this.list = wikiEntryList;
        this.entry = entry;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {


        if (list.screen.selectedEntry == entry) {
            list.screen.selectedEntry = null;
        } else {
            this.list.screen.selectedEntry = entry;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public Component getNarration() {
        return Component.empty();
    }

    private int getTextureY(int pMouseX, int pMouseY) {
        int i = 1;
        if (this.isMouseOver(pMouseX, pMouseY)) {
            i = 0;
        } else if (this.isFocused()) {
            i = 2;
        }
        return 46 + i * 20;
    }

    @Override
    public void render(GuiGraphics gui, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {


        // taken from abstractbutton
        if (this.list.screen.selectedEntry == this.entry || this.isMouseOver(pMouseX, pMouseY)) {
            gui.blitNineSliced(WIDGETS_LOCATION, pLeft - 1, pTop - 1, pWidth + 20, 20, 20, 4, pWidth, 20, 0, this.getTextureY(pMouseX, pMouseY));
        }

        if (this.isMouseOver(pMouseX, pMouseY)) {
            var tip = Tooltip.create(TextUTIL.mergeList(entry.data.getTooltip()));
            list.screen.setTooltipForNextRenderPass(tip, DefaultTooltipPositioner.INSTANCE, true);
        }

        var mc = Minecraft.getInstance();
        var data = entry.data;

        int x = pLeft;
        int y = pTop;

        Component lvl = Component.literal("" + data.lvl).withStyle(ChatFormatting.YELLOW);
        gui.drawCenteredString(mc.font, lvl, x + 10, y + 5, ChatFormatting.YELLOW.getColor());
        Component nameTxt = Component.literal("" + data.name).withStyle(ChatFormatting.GREEN);
        gui.drawString(mc.font, nameTxt, x + 75, y + 5, ChatFormatting.GREEN.getColor());


        x = 25;
        y = 1;
        for (SpellSchool school : data.getClasses()) {
            var size = 16;
            gui.blit(school.getIconLoc(), pLeft + x, pTop + y, size, size, size, size, size, size);
            x += 20;
        }

    }
}
