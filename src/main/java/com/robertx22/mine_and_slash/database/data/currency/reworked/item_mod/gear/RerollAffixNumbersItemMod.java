package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import net.minecraft.network.chat.MutableComponent;

public class RerollAffixNumbersItemMod extends GearModification {


    public RerollAffixNumbersItemMod(String id) {
        super(ItemModificationSers.REROLL_AFFIX_NUMBERS, id);
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            for (AffixData affix : gear.affixes.getPrefixesAndSuffixes()) {
                affix.RerollNumbers();
            }
        });
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }


    @Override
    public Class<?> getClassForSerialization() {
        return RerollAffixNumbersItemMod.class;
    }


    @Override
    public String locDescForLangFile() {
        return "Re-rolls Affix Numbers";
    }
}
