package com.robertx22.mine_and_slash.gui.wiki.reworked;

import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryGroup;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class NewGroupButton extends ImageButton {
    public static int SIZE = 20;

    static ResourceLocation GROUP_BUTTON_TEXTURE = new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/bestiary_group_buttons.png");


    BestiaryGroup group;
    NewWikiScreen screen;

    public NewGroupButton(NewWikiScreen screen, BestiaryGroup group, int xPos, int yPos) {
        super(xPos, yPos, 20, 20, 0, 0, 20, GROUP_BUTTON_TEXTURE, (button) -> {
        });

        this.screen = screen;
        this.group = group;

    }

    @Override
    public void onPress() {
        super.onPress();
        screen.setGroup(group);
    }


    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
        RenderUtils.render16Icon(gui, group.getTextureLoc(), this.getX() + 2, this.getY() + 2);
    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.literal(ChatFormatting.BLUE + "" + ChatFormatting.BOLD + CLOC.translate(group.getName())));
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }
}