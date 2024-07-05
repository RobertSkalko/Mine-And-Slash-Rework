package com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks;

import com.robertx22.age_of_exile.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class DragableBlock extends AdditionalBlock {
    public DragableBlock(@NotNull AvailableTarget target) {
        super(target.component);
    }



    public enum AvailableTarget{
        GEAR_SOUL(Itemtips.SOUL_MODIFIER_USE_TIP.locName().withStyle(ChatFormatting.BLUE)),

        GEAR(Itemtips.GEAR_SOUL_USE_TIP.locName().withStyle(ChatFormatting.BLUE));

        public final Component component;

        AvailableTarget(Component component) {
            this.component = component;
        }
    }
}
