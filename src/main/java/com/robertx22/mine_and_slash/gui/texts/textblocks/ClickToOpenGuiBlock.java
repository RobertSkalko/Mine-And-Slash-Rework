package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ClickToOpenGuiBlock extends AdditionalBlock {
    public ClickToOpenGuiBlock() {
        super(Component.literal(UNICODE.ROTATED_CUBE + " ").append(Itemtips.GEM_OPEN_GUI_TIP.locName()).withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
