package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class TpBackItem extends AutoItem {

    public TpBackItem() {
        super(new Properties());


    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            Load.playerRPGData(p).map.teleportBack(p);
        }
        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Right click to return from the Map"));
        pTooltipComponents.add(Component.literal("Exits the Map Dimension."));
    }


    @Override
    public String locNameForLangFile() {
        return "Pearl of Home";
    }

    @Override
    public String GUID() {
        return "tpback";
    }
}
