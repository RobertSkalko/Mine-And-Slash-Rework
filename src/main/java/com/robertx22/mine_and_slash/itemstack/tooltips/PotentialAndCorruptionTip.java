package com.robertx22.mine_and_slash.itemstack.tooltips;

import com.google.common.collect.ImmutableList;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileTipCtx;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;


// do reasons for tooltips like a tooltip for "Potential and Corruption" instead of attaching tooltips to data components


// todo, in this case, we need both corruption and potential datas to render a mixed tooltip

// what about order?
// what about MAIN tooltips like gear that replace others? but still allow some?
// so have a MAIN tooltip that replaces other main tooltips, and have priority to them?

// like gear is main, tool is secondary etc
// maybe separate items so they cant have 2 of 1 type, like item cant have both gear and jewel, so thats 1 thing not to worry about

// what's the best way to do it?
public class PotentialAndCorruptionTip extends TooltipStrategy {

    public PotentialAndCorruptionTip() {
        super();
    }

    @Override
    public void apply(ExileTooltips tip, ExileTipCtx ctx) {
        var exStack = ctx.stack;

        int pot = exStack.POTENTIAL.getOrCreate().potential;

        boolean hasPot = exStack.POTENTIAL.has();
        boolean hasQual = exStack.CUSTOM.hasAndTrue(x -> x.data.get(CustomItemData.KEYS.QUALITY) > 0);

        // todo have to rework this so it works for items without any or with all

        tip.accept(new AdditionalBlock(
                ImmutableList.of(
                        exStack.isCorrupted() ? Component.literal("").append(Itemtips.POTENTIAL.locName(pot).withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.STRIKETHROUGH)).append(Component.literal(" ")).append(Words.Corrupted.locName().withStyle(ChatFormatting.RED)) : Itemtips.POTENTIAL.locName(pot).withStyle(ChatFormatting.GOLD),
                        Itemtips.QUALITY.locName(exStack.CUSTOM.getOrCreate().data.get(CustomItemData.KEYS.QUALITY)).withStyle(ChatFormatting.GOLD)
                )
        ).showWhen(() -> ctx.shift));
    }
}
