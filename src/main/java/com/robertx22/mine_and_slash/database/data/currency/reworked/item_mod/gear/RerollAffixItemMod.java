package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class RerollAffixItemMod extends GearModification {


    public Data data;

    public static record Data(UpgradeAffixItemMod.AffixFinderData finder_data, String result_rar) {
    }


    transient String rarname;

    public RerollAffixItemMod(String id, Data data, String rarname) {
        super(ItemModificationSers.REROLL_AFFIX, id);
        this.data = data;

        this.rarname = rarname;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            var opt = data.finder_data.finder().getAffix(gear.affixes.getPrefixesAndSuffixes(), data.finder_data);

            if (opt.isPresent()) {
                AffixData affixData = opt.get();
                affixData.RerollFully(gear);

                if (ExileDB.GearRarities().isRegistered(data.result_rar)) {
                    affixData.rar = data.result_rar;
                    affixData.RerollNumbers();
                }
            }
        });

    }

    @Override
    public Class<?> getClassForSerialization() {
        return RerollAffixItemMod.class;
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        Component rar = Words.RANDOM_RARITY.locName();
        if (ExileDB.GearRarities().isRegistered(data.result_rar)) {
            rar = ExileDB.GearRarities().get(data.result_rar).coloredName();
        }
        return this.getDescParams(data.finder_data.finder().getTooltip(data.finder_data), rar);
    }

    @Override
    public String locDescForLangFile() {
        return "Rerolls %1$s into %2$s";
    }
}
