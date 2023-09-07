package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.Weighted;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class GearCurrency extends Currency {


    public abstract List<GearOutcome> getOutcomes();

    public abstract int getPotentialLoss();

    public boolean isAffectedByPotential() {
        return getPotentialLoss() > 0;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        GearItemData data = StackSaving.GEARS.loadFrom(stack);
        GearOutcome outcome = getOutcome(data.getPotential().multi + data.getAdditionalPotentialMultiFromQuality());
        data.setPotential(data.getPotentialNumber() - getPotentialLoss());

        Player player = ctx.player;
        if (outcome.getOutcomeType() == GearOutcome.OutcomeType.GOOD) {
            SoundUtils.ding(player.level(), player.blockPosition());
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);
        } else {
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.GLASS_BREAK, 1, 1);
            SoundUtils.playSound(player.level(), player.blockPosition(), SoundEvents.VILLAGER_NO, 1, 1);
        }
        return outcome.modify(ctx, data, stack);
    }

    private GearOutcome getOutcome(float multi) {

        List<Weighted<GearOutcome>> list = new ArrayList<>();

        for (GearOutcome o : getOutcomes()) {
            int w = o.Weight();

            if (isAffectedByPotential()) {
                if (o.getOutcomeType() == GearOutcome.OutcomeType.GOOD) {
                    w *= multi;
                }
            }
            list.add(new Weighted<>(o, w));
        }


        return RandomUtils.weightedRandom(list).obj;

    }

    public boolean canItemBeModified(LocReqContext context) {

        GearItemData data = StackSaving.GEARS.loadFrom(context.stack);
        if (data == null) {
            return false;
        }
        return super.canItemBeModified(context) && canBeModified(data);
    }

    public abstract boolean canBeModified(GearItemData data);


    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
