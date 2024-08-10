package com.robertx22.mine_and_slash.prophecy.gui;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class MainProphecyButton extends ImageButton {

    public static int FAVOR_BUTTON_SIZE_X = 34;
    public static int FAVOR_BUTTON_SIZE_Y = 34;

    Minecraft mc = Minecraft.getInstance();

    public MainProphecyButton(int xPos, int yPos) {
        super(xPos, yPos, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y, 0, 0, FAVOR_BUTTON_SIZE_Y, new ResourceLocation("empty"), (button) -> {
        });

    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SlashRef.guiId("prophecy/button"), getX(), getY(), FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X);

    }

    public void setModTooltip() {

        var data = Load.player(mc.player).prophecy;


        List<MutableComponent> list = new ArrayList<>();

        list.add(Words.PROPHECIES.locName());

        list.addAll(splitLongText(Chats.PROPHECIES_GUIDE.locName()));

        list.add(Component.literal(""));

        list.add(Words.CURRENT_PROPHECY_CURRENCY.locName(data.getCurrency()));
        //list.add(Words.AVG_LVL.locName(data.getAverageLevel()));
        //list.add(Words.AVG_TIER.locName(data.getAverageTier()));


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(list)));

    }


}