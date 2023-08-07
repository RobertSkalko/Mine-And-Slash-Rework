package com.robertx22.age_of_exile.saveclasses.skill_gem;

import com.robertx22.age_of_exile.capability.player.container.SkillGemsMenu;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

public class SkillGemItem extends Item {
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

                @org.jetbrains.annotations.Nullable
                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                    return new SkillGemsMenu(Load.playerRPGData(pPlayer), pContainerId, pPlayerInventory);
                }
            });
        }
        

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

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

            list.add(ExileText.ofText("Right Click to Open Gui").get());
        }


    }
}
