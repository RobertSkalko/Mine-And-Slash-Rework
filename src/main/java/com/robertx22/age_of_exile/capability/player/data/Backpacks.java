package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.capability.player.helper.BackpackInventory;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;

public class Backpacks {

    public Backpacks(Player player) {

        this.player = player;

        gears = new BackpackInventory(player, BackpackType.GEARS, MAX_SIZE);
        currencies = new BackpackInventory(player, BackpackType.CURRENCY, MAX_SIZE);
        skillGems = new BackpackInventory(player, BackpackType.SKILL_GEMS, MAX_SIZE);

    }

    public enum BackpackType {
        GEARS("gear", Words.Gear) {
            @Override
            public boolean isValid(ItemStack stack) {
                return StackSaving.GEARS.has(stack) || StackSaving.JEWEL.has(stack);
            }
        },
        CURRENCY("currency", Words.Currency) {
            @Override
            public boolean isValid(ItemStack stack) {
                return stack.getItem() instanceof IItemAsCurrency || stack.getItem() instanceof RuneItem;
            }
        },
        SKILL_GEMS("skill_gem", Words.SkillGem) {
            @Override
            public boolean isValid(ItemStack stack) {
                return StackSaving.SKILL_GEM.has(stack);
            }
        };


        public String id;
        public Words name;

        BackpackType(String id, Words name) {
            this.id = id;
            this.name = name;
        }

        public abstract boolean isValid(ItemStack stack);
    }

    Player player;

    public static int MAX_SIZE = 6 * 9;

    private BackpackInventory gears;
    private BackpackInventory currencies;
    private BackpackInventory skillGems;


    public BackpackInventory getInv(BackpackType type) {
        BackpackInventory inv = null;
        if (type == BackpackType.CURRENCY) {
            inv = currencies;
        }
        if (type == BackpackType.GEARS) {
            inv = gears;
        }
        if (type == BackpackType.SKILL_GEMS) {
            inv = skillGems;
        }


        return inv;
    }


    public void tryAutoPickup(ItemStack stack) {

        for (BackpackType type : BackpackType.values()) {
            if (type.isValid(stack)) {
                var bag = getInv(type);

                if (bag.hasFreeSlots()) {
                    bag.addItem(stack.copy());
                    stack.shrink(stack.getCount() + 10); // just in case
                    SoundUtils.playSound(this.player, SoundEvents.ITEM_PICKUP);
                    return;
                }
            }
        }


    }
    // todo every time before you open backpack, it will replace locked slots with blocked slots that cant be clicked on and throw out/give items back

    public void openBackpack(BackpackType type, Player p) {
        if (!p.level().isClientSide) {
            BackpackInventory inv = getInv(type);

            inv.throwOutBlockedSlotItems();

            p.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> {
                return ChestMenu.sixRows(i, playerInventory, inv);
            }, Component.literal("")));
        }
    }
}
