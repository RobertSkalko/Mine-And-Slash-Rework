package com.robertx22.mine_and_slash.itemstack;

import com.google.common.collect.ImmutableList;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CommonTooltips {

    public static AdditionalBlock potentialCorruptionAndQuality(ExileStack exStack) {
        return new AdditionalBlock(
                ImmutableList.of(
                        exStack.isCorrupted() ? Component.literal("").append(Itemtips.POTENTIAL.locName(exStack.get(StackKeys.POTENTIAL).getOrCreate().potential).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH)).append(Component.literal(" ")).append(Words.Corrupted.locName().withStyle(ChatFormatting.RED)) : Itemtips.POTENTIAL.locName(exStack.get(StackKeys.POTENTIAL).getOrCreate().potential).withStyle(ChatFormatting.GOLD),
                        Itemtips.QUALITY.locName(exStack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.QUALITY)).withStyle(ChatFormatting.GOLD)
                )
        ).showWhen(() -> Screen.hasShiftDown());
    }
}
