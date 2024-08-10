package com.robertx22.mine_and_slash.database.data.currency.gear;

import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OrbSocketAdder extends GearCurrency {

    @Override
    public List<GearOutcome> getOutcomes() {
        return Arrays.asList(
                new GearOutcome() {
                    @Override
                    public Words getName() {
                        return Words.AddSocket;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.GOOD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                        gear.sockets.addSocket();
                        StackSaving.GEARS.saveTo(stack, gear);
                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 1000;
                    }
                },
                new GearOutcome() {
                    @Override
                    public Words getName() {

                        return Words.Nothing;
                    }

                    @Override
                    public OutcomeType getOutcomeType() {
                        return OutcomeType.BAD;
                    }

                    @Override
                    public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {

                        return stack;
                    }

                    @Override
                    public int Weight() {
                        return 1000;
                    }
                }

        );
    }


    @Override
    public int getPotentialLoss() {
        return 10;
    }

    @Override
    public ExplainedResult canBeModified(GearItemData data) {
        return data.sockets.canAddSocket(data);
    }

    @Override
    public String locDescForLangFile() {
        return "Has a chance to add a socket or do nothing";
    }

    @Override
    public String locNameForLangFile() {
        return "Orb of Digging";
    }

    @Override
    public String GUID() {
        return "socket_adder";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
