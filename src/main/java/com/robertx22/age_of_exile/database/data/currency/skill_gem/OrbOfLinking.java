package com.robertx22.age_of_exile.database.data.currency.skill_gem;

import com.robertx22.age_of_exile.database.data.currency.base.SkillGemCurrency;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import net.minecraft.world.item.ItemStack;

public class OrbOfLinking extends SkillGemCurrency {


    @Override
    public ExplainedResult canBeModified(SkillGemData data) {
        if (data.links < data.getRarity().max_spell_links) {
            return ExplainedResult.success();
        }
        return ExplainedResult.failure(Chats.ALREADY_MAX_LINKS.locName());
    }

    @Override
    public ItemStack modify(LocReqContext ctx, ItemStack stack, SkillGemData data) {
        int old = data.links;
        data.reRollLinks();
        if (data.links < old) {
            data.links = old; // we don't want to punish players for attempting to reroll
        }
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
