package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class DowngradeAffixItemMod extends GearModification {

    public Data data;


    public static record Data(UpgradeAffixItemMod.AffixFinderData finder_data) {
    }

    public DowngradeAffixItemMod(String id, Data data) {
        super(ItemModificationSers.DOWNGRADE_AFFIX_RARITY, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            data.finder_data.finder().getAffix(gear.affixes.getPrefixesAndSuffixes(), data.finder_data).ifPresent(affix -> {
                affix.downgradeRarity();
            });
        });

    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.BAD;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return DowngradeAffixItemMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.finder_data.finder().getTooltip(data.finder_data));
    }

    @Override
    public String locDescForLangFile() {
        return "Downgrades Rarity and re-rolls Numbers of %1$s";
    }
}
