package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.RarityRegistryContainer;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RarityListData {
    public List<GearRarity> rar;

    public RarityListData(List<GearRarity> rar) {
        this.rar = rar;
    }


    public List<MutableComponent> getTooltip() {
        List<MutableComponent> tip = new ArrayList<>();
        tip.add(Component.literal(UNICODE.STAR + " ").append(Words.RARITIES.locName()).withStyle(ChatFormatting.GREEN));


        RarityRegistryContainer<GearRarity> gearRarityRarityRegistryContainer = ExileDB.GearRarities();
        List<GearRarity> allRarities = gearRarityRarityRegistryContainer.getList();

        allRarities.sort(Comparator.comparingInt(x -> x.item_tier));
        if (!Screen.hasShiftDown()) {
            MutableComponent starter = Component.literal("");
            String block = "\u25A0";
            allRarities
                    .forEach(x -> {
                        if (rar.contains(x)) {
                            starter.append(Component.literal(block).withStyle(x.textFormatting()));
                        } else {
                            starter.append(Component.literal(block).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    });
            tip.add(starter);
            return tip;
        } else {
            List<MutableComponent> list = allRarities
                    .stream().map(x -> {
                        if (rar.contains(x)) {
                            return x.locName().withStyle(x.textFormatting());
                        } else {
                            return x.locName().withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC, ChatFormatting.STRIKETHROUGH);
                        }
                    })
                    .toList();

            tip.add(TooltipUtils.joinMutableComps(list.iterator(), Gui.COMMA_SEPARATOR.locName()));
            return tip;

        }
    }
}
