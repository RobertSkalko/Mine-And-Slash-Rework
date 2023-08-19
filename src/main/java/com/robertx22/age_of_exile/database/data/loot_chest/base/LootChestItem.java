package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LootChestItem extends Item {
    public LootChestItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack stack = p.getItemInHand(pUsedHand);

        try {
            if (!pLevel.isClientSide) {

                LootChestData data = StackSaving.LOOT_CHEST.loadFrom(stack);

                if (data != null) {

                    if (data.canOpen(p)) {
                        if (data.isLocked()) {
                            data.spendKey(p);
                        }
                        stack.shrink(1);

                        for (ItemStack loot : data.getLootChest().generateAll(data)) {
                            PlayerUtils.giveItem(loot, p);
                        }

                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

}
