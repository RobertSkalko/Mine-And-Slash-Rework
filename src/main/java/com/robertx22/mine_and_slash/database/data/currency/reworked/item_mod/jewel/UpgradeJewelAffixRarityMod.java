package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.jewel;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.JewelModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear.UpgradeAffixItemMod;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class UpgradeJewelAffixRarityMod extends JewelModification {

    public UpgradeAffixItemMod.AffixFinderData data;


    public UpgradeJewelAffixRarityMod(String id, UpgradeAffixItemMod.AffixFinderData data) {
        super(ItemModificationSers.UPGRADE_JEWEL_AFFIX_RARITY, id);
        this.data = data;
    }

    @Override
    public void modifyJewel(ExileStack stack) {
        stack.get(StackKeys.JEWEL).edit(x -> {
            data.finder().getAffix(x.affixes, data).ifPresent(affix -> {
                affix.upgradeRarity();
            });
        });

    }

    @Override
    public ItemModification.OutcomeType getOutcomeType() {
        return ItemModification.OutcomeType.GOOD;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return UpgradeJewelAffixRarityMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.finder().getTooltip(data));
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades Rarity and re-rolls Numbers of %1$s";
    }
}
