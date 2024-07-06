package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.database.data.profession.LeveledItem;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
@RequiredArgsConstructor
public class LeveledItemBlock extends AbstractTextBlock {

    private final ItemStack stack;
    @Override
    public List<? extends Component> getAvailableComponents() {
        return ImmutableList.of(
                Itemtips.LEVEL_TIP.locName(Component.literal("" + LeveledItem.getLevel(stack)).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD),
                Itemtips.TIER_TIP.locName(Component.literal("" + LeveledItem.getTierNum(stack)).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD)
        );
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.LEVELED_ITEM;
    }
}
