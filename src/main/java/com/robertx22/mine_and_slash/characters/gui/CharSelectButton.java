package com.robertx22.mine_and_slash.characters.gui;

import com.robertx22.mine_and_slash.characters.LoadCharPacket;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class CharSelectButton extends ImageButton {

    public static int BUTTON_SIZE_X = 232;
    public static int BUTTON_SIZE_Y = 19;

    int num;


    public CharSelectButton(Integer num, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SlashRef.guiId(""), (button) -> {
            Packets.sendToServer(new LoadCharPacket(num));
            Minecraft.getInstance().setScreen(null);
        });

        this.num = num;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        var data = Load.player(ClientOnly.getPlayer()).characters.map.get(num);

        if (data != null) {

            if (this.isHovered()) {
                this.setTooltip(Tooltip.create(TextUTIL.mergeList(data.getTooltip())));
            }
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            Minecraft mc = Minecraft.getInstance();


            Component lvl = Component.literal("" + data.lvl).withStyle(ChatFormatting.YELLOW);
            gui.drawCenteredString(mc.font, lvl, this.getX() + 7, this.getY() + 7, ChatFormatting.YELLOW.getColor());
            Component nameTxt = Component.literal("" + data.name).withStyle(ChatFormatting.GREEN);
            gui.drawString(mc.font, nameTxt, this.getX() + 25, this.getY() + 7, ChatFormatting.GREEN.getColor());


            int x = 100;
            int y = 3;
            for (SpellSchool school : data.getClasses()) {
                var size = 16;

                //gui.blit(SlashRef.guiId("empty_school"), getX() + x, getY() + y, size, size, size, size, size, size);
                gui.blit(school.getIconLoc(), getX() + x, getY() + y, size, size, size, size, size, size);
                x += 20;
            }

            gui.blit(SlashRef.guiId("load_char"), getX() + 160, getY() + 3, 45, 15, 45, 15, 45, 15);
            gui.drawCenteredString(mc.font, Words.LOAD.locName(), this.getX() + 183, this.getY() + 7, ChatFormatting.WHITE.getColor());


        }
    }

}
