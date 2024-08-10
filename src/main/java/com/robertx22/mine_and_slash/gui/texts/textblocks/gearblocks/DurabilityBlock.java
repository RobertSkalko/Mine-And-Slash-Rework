package com.robertx22.mine_and_slash.gui.texts.textblocks.gearblocks;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class DurabilityBlock extends AbstractTextBlock {

    @Nonnull
    public ItemStack stack;

    public DurabilityBlock(@Nonnull ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        if (ClientConfigs.getConfig().SHOW_DURABILITY.get()) {
            if (stack.isDamageableItem()) {
                return Collections.singletonList(Itemtips.Durability.locName().withStyle(ChatFormatting.GRAY)
                        .append(stack.getMaxDamage() - stack.getDamageValue() + "/" + stack.getMaxDamage()));
            } else {
                return Collections.singletonList(Itemtips.Unbreakable.locName().withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.DURABILITY;
    }
}
