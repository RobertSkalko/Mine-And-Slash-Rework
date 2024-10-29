package com.robertx22.mine_and_slash.characters.reworked_gui;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.characters.CreateCharPacket;
import com.robertx22.mine_and_slash.characters.ToonActionPacket;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.ConfirmScreen;

public class ToonActionButton extends AbstractButton {

    public enum Action {
        CREATE_CHARACTER(Words.NEW_CHARACTER, Words.NEW_CHARACTER_DESC, ChatFormatting.GREEN),
        LOAD(Words.LOAD, Words.LOAD_CHAR_DESC, ChatFormatting.YELLOW),
        RENAME(Words.RENAME, Words.RENAME_DESC, ChatFormatting.AQUA),
        DELETE(Words.DELETE, Words.DELETE_DESC, ChatFormatting.RED);


        public Words word;
        public Words desc;

        public ChatFormatting color;

        Action(Words word, Words desc, ChatFormatting color) {
            this.word = word;
            this.desc = desc;
            this.color = color;
        }
    }

    Action action;

    public static int WIDTH = 120;
    public static int HEIGHT = 30;

    ToonScreen screen;

    public ToonActionButton(ToonScreen screen, Action action, int pX, int pY) {
        super(pX, pY, WIDTH, HEIGHT, action.word.locName().withStyle(action.color, ChatFormatting.BOLD));
        this.action = action;
        this.screen = screen;

        var tip = Tooltip.create(TextUTIL.mergeList(TooltipUtils.splitLongText(action.desc.locName())));
        this.setTooltip(tip);
    }

    @Override
    public void onPress() {

        if (action == Action.CREATE_CHARACTER) {
            Packets.sendToServer(new CreateCharPacket(screen.searchBox.getValue()));
            Minecraft.getInstance().setScreen(null);
        } else {

            String name = screen.searchBox.getValue();

            if (screen.selectedEntry != null) {

                if (action == Action.LOAD || action == Action.RENAME) {
                    Packets.sendToServer(new ToonActionPacket(action, screen.selectedEntry.num, name));
                    Minecraft.getInstance().setScreen(null);
                }

                if (action == Action.DELETE) {
                    Minecraft.getInstance().setScreen(new ConfirmScreen(x -> {
                                Packets.sendToServer(new ToonActionPacket(action, screen.selectedEntry.num, name));
                                Minecraft.getInstance().setScreen(null);

                            },
                                    Words.DELETE.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD),
                                    Words.CHAR_DELETE_CONFIRM.locName(this.screen.selectedEntry.data.name).withStyle(ChatFormatting.RED))
                    );
                }
            }
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}




