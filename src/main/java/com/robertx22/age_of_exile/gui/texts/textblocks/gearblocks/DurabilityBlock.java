package com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
public class DurabilityBlock extends AbstractTextBlock {

    @Nonnull
    public ItemStack stack;

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
}
