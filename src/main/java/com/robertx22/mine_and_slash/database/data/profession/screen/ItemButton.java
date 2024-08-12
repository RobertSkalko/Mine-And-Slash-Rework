package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;

public class ItemButton extends ImageButton {
    public static int xSize = 16;
    public static int ySize = 16;
    static ResourceLocation buttonLoc = new ResourceLocation("library_of_exile", "");
    static ResourceLocation fancyBorderLoc = new ResourceLocation("library_of_exile", "textures/gui/pretty_icon_border.png");
    static int FX = 20;
    static int FY = 20;
    ItemStack stack;
    Minecraft mc;
    public boolean renderFancyBorder;

    public ItemButton(ItemStack stack, int xPos, int yPos) {
        this(stack, xPos, yPos, (button) -> {
        });
        this.stack = stack;
    }

    public ItemButton(ItemStack stack, int xPos, int yPos, Button.OnPress onclick) {
        super(xPos + 1, yPos + 1, xSize, ySize, 0, 0, ySize + 1, buttonLoc, onclick);
        this.mc = Minecraft.getInstance();
        this.renderFancyBorder = false;
        this.stack = stack;
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

    }


    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(this.stack.getTooltipLines(this.mc.player, TooltipFlag.NORMAL))));
        pGuiGraphics.renderItem(stack, getX(), getY());
        pGuiGraphics.renderItemDecorations(mc.font, stack, getX(), getY());


        var tip = new ArrayList<Component>();
        tip.add(Itemtips.RECIPE_MATERIAL.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        tip.addAll(stack.getTooltipLines(mc.player, TooltipFlag.NORMAL));

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tip)));


        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        //pGuiGraphics.renderite
        // RenderUtils.renderStack(pGuiGraphics, this.stack, this.getX(), this.getY());

    }
}
