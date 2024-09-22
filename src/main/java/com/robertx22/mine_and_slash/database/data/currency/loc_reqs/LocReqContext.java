package com.robertx22.mine_and_slash.database.data.currency.loc_reqs;

import com.robertx22.mine_and_slash.database.data.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LocReqContext {

    public LocReqContext(Player player, ItemStack stack, ItemStack currency) {
        this.stack = ExileStack.of(stack);
        this.Currency = currency;
        this.data = ICommonDataItem.load(stack);
        this.player = player;

        if (currency.getItem() instanceof IItemAsCurrency cur) {
            effect = cur.currencyEffect(currency);
        }
    }

    public Player player;

    public ExileStack stack;
    public ItemStack Currency;

    public ICommonDataItem data;
    public CodeCurrency effect;

    public boolean isValid() {
        return !stack.getStack().isEmpty() && !Currency.isEmpty();
    }

    public boolean isGear() {
        return data instanceof GearItemData;
    }
}
