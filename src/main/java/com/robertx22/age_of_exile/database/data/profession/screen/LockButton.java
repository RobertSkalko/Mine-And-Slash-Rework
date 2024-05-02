package com.robertx22.age_of_exile.database.data.profession.screen;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.vanilla_mc.packets.LockTogglePacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class LockButton extends ImageButton {

    public static int XS = 18;
    public static int YS = 18;


    Minecraft mc = Minecraft.getInstance();
    CraftingStationScreen s;

    public LockButton(int xPos, int yPos, CraftingStationScreen s) {
        super(xPos, yPos, XS, YS, 0, s.getSyncedData().recipe_locked ? 18 : 0, YS, SlashRef.guiId("lockbutton"), (button) -> {
            Packets.sendToServer(new LockTogglePacket(s.getSyncedData().getBlockPos()));
        });
        this.s = s;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        ResourceLocation tex = SlashRef.guiId("lockbutton");
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), 0, s.getSyncedData().recipe_locked ? 18 : 0, 18, 18);

        //super.renderWidget(gui, mouseX, mouseY, delta);
    }

    public void setModTooltip() {
        if (s.getSyncedData().recipe_locked)
            this.setTooltip(Tooltip.create(Component.literal("Unlock Recipe").withStyle(ChatFormatting.DARK_AQUA)));
        else
            this.setTooltip(Tooltip.create(Component.literal("Lock Recipe").withStyle(ChatFormatting.DARK_AQUA)));
    }

}
