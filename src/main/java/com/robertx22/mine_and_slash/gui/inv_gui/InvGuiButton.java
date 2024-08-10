package com.robertx22.mine_and_slash.gui.inv_gui;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.packets.InvGuiPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class InvGuiButton extends ImageButton {

    static ResourceLocation TEX = SlashRef.guiId("inv_gui.png");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    GuiItemData data;


    public InvGuiButton(GuiItemData data, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, TEX, (button) -> {
            if (!data.isEmpty()) {
                Packets.sendToServer(new InvGuiPacket(data));
                data.getAction().clientAction(ClientOnly.getPlayer(), data);
            }

        });
        this.data = data;

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        if (data.isEmpty()) {
            return;
        }

        setModTooltip();


        super.render(gui, mouseX, mouseY, delta);

    }

    @Override
    public void renderWidget(GuiGraphics gui, int pMouseX, int pMouseY, float pPartialTick) {


        if (data.getAction().getBackGroundIcon() != null) {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(data.getAction().getBackGroundIcon(), getX() + 1, getY() + 1, 16, 16, 16, 16, 16, 16);
        }
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(data.getAction().getIcon(), getX() + 2, getY() + 1, 16, 16, 16, 16, 16, 16);

    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();


        for (Object c : data.getAction().getTooltip(mc.player)) {
            tooltip.add((Component) c);
        }


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }


}
