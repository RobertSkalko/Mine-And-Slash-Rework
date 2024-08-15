package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.gui.screens.OpenJewelsScreen;
import com.robertx22.mine_and_slash.uncommon.interfaces.INeedsNBT;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JewelItem extends Item implements INeedsNBT {

    public JewelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (pLevel.isClientSide()) {
            new OpenJewelsScreen().openContainer();
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }
}
