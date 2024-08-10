package com.robertx22.mine_and_slash.database.data.profession.items;

import com.robertx22.mine_and_slash.database.data.currency.base.ProfCurrency;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.ItemStack;

public class ProfDropTierPickerCurrency extends ProfCurrency {
    SkillItemTier tier;

    public ProfDropTierPickerCurrency(SkillItemTier tier) {
        this.tier = tier;
    }

    @Override
    public ExplainedResult canBeModified(ProfessionToolData data) {
        return ExplainedResult.success();
    }

    @Override
    public ItemStack modify(LocReqContext ctx, ItemStack stack, ProfessionToolData data) {
        data.force_lvl = tier.levelRange.getMinLevel();
        StackSaving.TOOL.saveTo(stack, data);
        return stack;
    }

    @Override
    public String locDescForLangFile() {
        return "Caps the level of drops possible with a profession tool. \nUseful in case you want lower level drops, in case you still need materials \nto level up your crafting skills.";
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " Mastery Seal";
    }

    @Override
    public String GUID() {
        return "mastery_seal" + tier.tier;
    }

    @Override
    public int Weight() {
        return 0;
    }
}
