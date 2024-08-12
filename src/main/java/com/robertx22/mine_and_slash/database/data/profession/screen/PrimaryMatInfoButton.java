package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class PrimaryMatInfoButton extends ImageButton {

    public static int XS = 18;
    public static int YS = 19;

    Minecraft mc = Minecraft.getInstance();

    public static class InfoData {


        public Words word;
        public Item icon;
        public List<Item> allPossibleItems;

        public InfoData(Words word, Item icon, List<Item> allPossibleItems) {
            this.word = word;
            this.icon = icon;
            this.allPossibleItems = allPossibleItems;
        }
    }

    InfoData info;

    public PrimaryMatInfoButton(InfoData info, int xPos, int yPos) {
        super(xPos, yPos, XS, YS, 0, 0, YS, SlashRef.guiId(""), (button) -> {

        });
        this.info = info;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        gui.renderFakeItem(info.icon.getDefaultInstance(), getX() + 1, getY() + 6);

        //     ResourceLocation tex = SlashRef.guiId("craftbutton");
        //    gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        //    gui.blit(tex, getX(), getY(), 0, (pbe.getSyncedData().craftingState == Crafting_State.ACTIVE || pbe.getSyncedData().craftingState == Crafting_State.IDLE) ? 0 : 19, 18, 19);
    }

    public void setModTooltip() {

        List<MutableComponent> list = new ArrayList<>();
        list.add(info.word.locName().withStyle(ChatFormatting.AQUA));

        boolean cut = false;
        int count = 0;
        for (Item item : this.info.allPossibleItems) {
            count++;
            if (count < 10) {
                list.add(Component.literal(" - ").append(item.getName(item.getDefaultInstance())).withStyle(ChatFormatting.YELLOW));
            } else {
                cut = true;
            }
        }
        if (cut) {
            list.add(Component.literal("...").withStyle(ChatFormatting.YELLOW));
        }

        list.add(Component.literal(""));

        list.addAll(TooltipUtils.splitLongText(Itemtips.PRIMARY_PROFESSION_MAT_INFO.locName().withStyle(ChatFormatting.RED)));

        list.add(Itemtips.PRIMARY_PROFESSION_USE_JEI.locName().withStyle(ChatFormatting.GREEN));


        this.setTooltip(Tooltip.create(TooltipUtils.joinMutableComps(list.listIterator(), Component.literal("\n"))));
    }

}