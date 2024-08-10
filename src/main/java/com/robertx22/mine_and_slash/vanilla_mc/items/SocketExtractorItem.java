package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SocketExtractorItem extends AutoItem {

    public SocketExtractorItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Socket Extractor Tool";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Itemtips.SOCKET_EXTRACTOR_USAGE.locName().withStyle(ChatFormatting.RED));
    }
}
