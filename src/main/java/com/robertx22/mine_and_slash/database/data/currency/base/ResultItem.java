package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import net.minecraft.world.item.ItemStack;

public class ResultItem {

    public ItemStack stack;
    public ModifyResult resultEnum;
    public ExplainedResult result;

    public ResultItem(ItemStack stack, ModifyResult resultEnum, ExplainedResult result) {
        this.stack = stack;
        this.resultEnum = resultEnum;
        this.result = result;
    }
}
