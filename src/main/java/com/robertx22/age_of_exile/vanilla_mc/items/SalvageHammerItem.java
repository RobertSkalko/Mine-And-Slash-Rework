package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SalvageHammerItem extends AutoItem {

    public SalvageHammerItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Salvage Hammer";
    }

    @Override
    public String GUID() {
        return "salvage_hammer";
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.literal("Click on items to salvage them.").withStyle(ChatFormatting.RED));
    }
}
