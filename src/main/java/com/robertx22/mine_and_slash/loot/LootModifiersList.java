package com.robertx22.mine_and_slash.loot;

import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class LootModifiersList {

    public List<LootModifier> all = new ArrayList<>();


    public void add(LootModifier mod) {
        all.add(mod);
    }

    public MutableComponent getHoverText() {
        var hovertext = Component.empty();
        hovertext.append(Words.LOOT_MODIFIERS_INFO.locName().withStyle(ChatFormatting.BLUE, ChatFormatting.BOLD).append("\n"));

        for (LootModifier mod : all) {
            hovertext.append(mod.name.getFullName(mod.multi)).append("\n");
        }

        //var hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hovertext);

        return hovertext;
    }


}
