package com.robertx22.mine_and_slash.database.data.currency.base;

import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public abstract class SkillGemCurrency extends Currency {

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        SkillGemData gem = StackSaving.SKILL_GEM.loadFrom(stack);
        return modify(ctx, stack, gem);
    }

    @Override
    public ExplainedResult canItemBeModified(LocReqContext context) {

        SkillGemData data = StackSaving.SKILL_GEM.loadFrom(context.stack);
        if (data == null || data.type != SkillGemData.SkillGemType.SKILL) {
            return ExplainedResult.failure(Chats.NOT_SKILLGEM.locName());
        }
        var can = canBeModified(data);
        if (!can.can) {
            return can;
        }


        return super.canItemBeModified(context);
    }

    public abstract ExplainedResult canBeModified(SkillGemData data);

    public abstract ItemStack modify(LocReqContext ctx, ItemStack stack, SkillGemData data);

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
