package com.robertx22.mine_and_slash.gui.screens.spell;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class PointsDisplayButton extends ImageButton {

    PlayerPointsType type;

    public PointsDisplayButton(PlayerPointsType type, int xPos, int yPos) {
        super(xPos, yPos, 85, 15, 0, 0, 0, SlashRef.guiId(""), (button) -> {
        });
        this.type = type;
    }

    @Override
    public void onPress() {

    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }


    @Override
    public void renderWidget(GuiGraphics gui, int pMouseX, int pMouseY, float pPartialTick) {

        var p = ClientOnly.getPlayer();

        String points = Words.CURRENT_POINTS.locName(type.word().locName(), String.valueOf(type.getFreePoints(p))).getString();

        //  GuiUtils.renderScaledText(gui, getX() + 1, getY() + 1, 1, points, ChatFormatting.WHITE);

        gui.drawString(Minecraft.getInstance().font, points, (int) getX() + 3, (int) getY() + 3, ChatFormatting.WHITE.getColor());

        List<MutableComponent> all = new ArrayList<>();
        all.add(Gui.RESPEC_POINTS.locName(String.valueOf(type.getResetPoints(p))));
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(all)));
    }


}