package com.robertx22.age_of_exile.saveclasses.skill_gem;

import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SkillGemItem extends Item {
    public SkillGemItem() {
        super(new Properties().stacksTo(1));

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        if (pLevel != null && !pLevel.isClientSide()) {
            return;
        }

        SkillGemData data = StackSaving.SKILL_GEM.loadFrom(stack);
        if (data != null) {
            list.clear();
            for (MutableComponent m : data.getTooltip(ClientOnly.getPlayer())) {
                list.add(m);
            }
        }


    }
}
