package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear.UpgradeAffixItemMod;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class UpgradeCorruptionAffixMod extends ItemModification {

    UpgradeAffixItemMod.AffixFinderData data;

    public UpgradeCorruptionAffixMod(String id, UpgradeAffixItemMod.AffixFinderData data) {
        super(ItemModificationSers.UPGRADE_CORRUPT_AFFIX_RARITY, id);
        this.data = data;
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public void applyINTERNAL(ExileStack stack) {

        stack.get(StackKeys.GEAR).editIfHas(gear -> {
            data.finder().getAffix(gear.affixes.cor, data).ifPresent(affix -> {
                affix.upgradeRarity();
            });
        });
        stack.get(StackKeys.JEWEL).editIfHas(jewel -> {
            data.finder().getAffix(jewel.cor, data).ifPresent(affix -> {
                affix.upgradeRarity();
            });
        });

    }

    @Override
    public Class<?> getClassForSerialization() {
        return UpgradeCorruptionAffixMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.finder().getTooltip(data));
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades Rarity of %1$s of Corruption";
    }
}
