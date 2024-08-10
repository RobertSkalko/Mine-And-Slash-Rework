package com.robertx22.mine_and_slash.database.data.currency.jewel;

import com.robertx22.mine_and_slash.database.data.currency.base.JewelCurrency;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JewelCorruptCurrency extends JewelCurrency {

    @Override
    public ExplainedResult canBeModified(JewelItemData data) {
        return ExplainedResult.success();
    }

    @Override
    public ItemStack modify(LocReqContext ctx, ItemStack stack, JewelItemData data) {
        if (RandomUtils.roll(25)) {
            SoundUtils.playSound(ctx.player, SoundEvents.ANVIL_BREAK);

            return Items.GUNPOWDER.getDefaultInstance();
        } else {
            SoundUtils.playSound(ctx.player, SoundEvents.EXPERIENCE_ORB_PICKUP);
            data.corrupt();
            data.saveToStack(stack);
        }
        return stack;
    }

    @Override
    public String locDescForLangFile() {
        return "Corrupts a Jewel, granting it additional stats. Has a chance to Destroy it!";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Mesmerizing Chaos";
    }

    @Override
    public String GUID() {
        return "jewel_corrupt";
    }

    @Override
    public int Weight() {
        return 500;
    }
}
