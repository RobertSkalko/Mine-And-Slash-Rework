package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.itemstack.ExileStack;

public class ResultItem {

    public ExileStack stack;
    public ModifyResult resultEnum;
    public ExplainedResult result;

    public ItemModification.OutcomeType outcome = ItemModification.OutcomeType.GOOD;

    public ResultItem(ExileStack stack, ModifyResult resultEnum, ExplainedResult result) {
        this.stack = stack;
        this.resultEnum = resultEnum;
        this.result = result;
    }
}
