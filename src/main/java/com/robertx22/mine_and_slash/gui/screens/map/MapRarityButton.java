package com.robertx22.mine_and_slash.gui.screens.map;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;

public class MapRarityButton extends ImageButton {

    MapSyncData data;
    static int SIZE = 34;


    public MapRarityButton(MapSyncData data, int xPos, int yPos) {
        super(xPos, yPos, SIZE, SIZE, 0, 0, 0, SlashRef.guiId(""), (button) -> {
        });
        this.data = data;
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
        gui.blit(data.data.map.getRarity().getMapIconTexture(), this.getX(), this.getY(), SIZE, SIZE, 0, 0, SIZE, SIZE, SIZE, SIZE);

//        var p = ClientOnly.getPlayer();
        //      this.setTooltip(Tooltip.create(TextUTIL.mergeList(getBarTooltip())));
    }


}
