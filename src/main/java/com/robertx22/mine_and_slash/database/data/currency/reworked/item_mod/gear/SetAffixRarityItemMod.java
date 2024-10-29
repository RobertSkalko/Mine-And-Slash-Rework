package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class SetAffixRarityItemMod extends GearModification {

    public Data data;


    public static record Data(UpgradeAffixItemMod.AffixFinderData finder_data, String rar) {
    }

    public SetAffixRarityItemMod(String id, Data data) {
        super(ItemModificationSers.SET_AFFIX_RARITY, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            data.finder_data.finder().getAffix(gear.affixes.getPrefixesAndSuffixes(), data.finder_data).ifPresent(affix -> {
                affix.rar = data.rar;
                affix.RerollNumbers();
            });
        });

    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return SetAffixRarityItemMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        var rar = ExileDB.GearRarities().get(data.rar);
        return this.getDescParams(data.finder_data.finder().getTooltip(data.finder_data), rar.locName().withStyle(rar.textFormatting()));
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades %1$s to %2$s Rarity";
    }
}
