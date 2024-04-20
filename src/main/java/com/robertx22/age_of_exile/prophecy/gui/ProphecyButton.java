package com.robertx22.age_of_exile.prophecy.gui;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.prophecy.AcceptProphecyPacket;
import com.robertx22.age_of_exile.prophecy.ProphecyData;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;

public class ProphecyButton extends ImageButton {
    static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/prophecy/icon.png");

    ProphecyData data;

    
    public ProphecyButton(ProphecyData data, boolean canTake, int x, int y) {
        super(x, y, 16, 16, 0, 0, 1, ID, (action) -> {
            if (canTake) {
                Packets.sendToServer(new AcceptProphecyPacket(data.uuid));
                Minecraft.getInstance().setScreen(null);
            }
        });
        this.data = data;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(SlashRef.guiId("prophecy/icon"), getX(), getY(), 0, 0, 16, 16, 16, 16);

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(data.getTooltip())));

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);


    }
}
