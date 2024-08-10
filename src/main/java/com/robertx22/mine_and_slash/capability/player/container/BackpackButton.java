package com.robertx22.mine_and_slash.capability.player.container;

import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenBackpackPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class BackpackButton extends ImageButton {

    public static int SX = 16;
    public static int SY = 16;

    Minecraft mc = Minecraft.getInstance();

    public Backpacks.BackpackType type;

    public BackpackButton(Backpacks.BackpackType type, int xPos, int yPos) {
        super(xPos, yPos, SX, SY, 0, 0, SY, new ResourceLocation("empty"), (button) -> {
            //MOUSEPOS.X = Minecraft.getInstance().mouseHandler.xpos(); todo this isnt working for some reason
            //MOUSEPOS.Y = Minecraft.getInstance().mouseHandler.ypos();
            //MOUSEPOS.SET_MOUSE = true;
            Packets.sendToServer(new OpenBackpackPacket(type));
        });
        this.type = type;

    }


    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        ResourceLocation tex = type.getIcon();
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), SX, SX, SX, SX, SX, SX);

    }

    public void setModTooltip() {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(Arrays.asList(
                this.type.name.locName()
        ))));

    }


}