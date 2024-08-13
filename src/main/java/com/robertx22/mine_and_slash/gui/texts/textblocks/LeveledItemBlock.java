package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LeveledItemBlock extends AbstractTextBlock {

    private final ItemStack stack;

    public LeveledItemBlock(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        var tier = LeveledItem.getTier(stack);

        return Arrays.asList(
                Itemtips.LEVEL_RANGE.locName(Component.literal(tier.levelRange.getMinLevel() + " - " + tier.levelRange.getMaxLevel()).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GREEN)
        );
        /*
        return ImmutableList.of(
                Itemtips.LEVEL_TIP.locName(Component.literal("" + LeveledItem.getLevel(stack)).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD),
                Itemtips.TIER_TIP.locName(Component.literal("" + LeveledItem.getTierNum(stack)).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD)
        );

         */
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.LEVELED_ITEM;
    }
}
