package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
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
    public boolean canItemBeModified(LocReqContext context) {

        SkillGemData data = StackSaving.SKILL_GEM.loadFrom(context.stack);
        if (data == null) {
            return false;
        }
        return data.type == SkillGemData.SkillGemType.SKILL && super.canItemBeModified(context) && canBeModified(data);
    }

    public abstract boolean canBeModified(SkillGemData data);

    public abstract ItemStack modify(LocReqContext ctx, ItemStack stack, SkillGemData data);

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
