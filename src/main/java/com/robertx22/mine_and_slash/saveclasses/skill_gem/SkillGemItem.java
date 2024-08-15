package com.robertx22.mine_and_slash.saveclasses.skill_gem;

import com.robertx22.mine_and_slash.capability.player.container.SkillGemsMenu;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.INeedsNBT;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SkillGemItem extends Item implements INeedsNBT {
    public SkillGemItem() {
        super(new Properties().stacksTo(1));


    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
            pPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                    return new SkillGemsMenu(Load.player(pPlayer), pContainerId, pPlayerInventory);
                }
            });
        }


        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        try {
            if (pLevel != null && !pLevel.isClientSide()) {
                return;
            }

            SkillGemData data = StackSaving.SKILL_GEM.loadFrom(stack);
            if (data != null) {
                list.clear();
                for (Component m : data.getTooltip(ClientOnly.getPlayer())) {
                    list.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
