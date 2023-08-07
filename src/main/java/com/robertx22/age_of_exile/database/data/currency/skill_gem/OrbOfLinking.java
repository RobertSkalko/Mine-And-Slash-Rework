package com.robertx22.age_of_exile.database.data.currency.skill_gem;

import com.robertx22.age_of_exile.database.data.currency.base.SkillGemCurrency;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class OrbOfLinking extends SkillGemCurrency {


    @Override
    public boolean canBeModified(SkillGemData data) {
        return data.links < SkillGemData.MAX_LINKS;
    }

    @Override
    public ItemStack modify(LocReqContext ctx, ItemStack stack, SkillGemData data) {
        data.reRollLinks();
        StackSaving.SKILL_GEM.saveTo(stack, data);
        return stack;
    }


    @Override
    public int Weight() {
        return 1000;
    }


    @Override
    public String locNameForLangFile() {
        return "Orb of Linking";
    }

    @Override
    public String GUID() {
        return "orb_of_linking";
    }


    @Override
    public String locDescForLangFile() {
        return "Re-rolls gem links, min is 1, max is 5 links.";
    }
}
