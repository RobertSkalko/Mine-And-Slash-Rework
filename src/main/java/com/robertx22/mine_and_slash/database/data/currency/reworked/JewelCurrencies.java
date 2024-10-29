package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemMods;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.ExileKey;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.ExileKeyHolderSection;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.IdKey;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.MaxUsesKey;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

public class JewelCurrencies extends ExileKeyHolderSection<ExileCurrencies> {
    public JewelCurrencies(ExileCurrencies holder) {
        super(holder);
    }

    public ExileKey<ExileCurrency, IdKey> JEWEL_CORRUPT = ExileCurrency.Builder.of("jewel_corrupt", "Orb of Mesmerizing Chaos", WorksOnBlock.ItemType.JEWEL)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.UNIQUE_ID)
            .addModification(ItemMods.INSTANCE.JEWEL_CORRUPTION, 50)
            .addModification(ItemMods.INSTANCE.DESTROY_ITEM, 50)
            .potentialCost(0)
            .weight(CodeCurrency.Weights.RARE)
            .build(get());

    public ExileKey<ExileCurrency, IdKey> JEWEL_UPGRADE_AFFIX = ExileCurrency.Builder.of("jewel_upgrade_affix", "Orb of Glimmering Light", WorksOnBlock.ItemType.JEWEL)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.EPIC_ID)
            .addModification(ItemMods.INSTANCE.UPGRADE_JEWEL_AFFIX_RARITY, 85)
            .addModification(ItemMods.INSTANCE.DESTROY_ITEM, 15)
            .potentialCost(3)
            .weight(CodeCurrency.Weights.RARE)
            .build(get());

    public ExileKey<ExileCurrency, IdKey> JEWEL_UPGRADE_AFFIX_SURE = ExileCurrency.Builder.of("jewel_sure_upgrade", "Orb of Mystery", WorksOnBlock.ItemType.JEWEL)
            .addRequirement(ItemReqs.INSTANCE.IS_NOT_CORRUPTED)
            .rarity(IRarity.MYTHIC_ID)
            .addModification(ItemMods.INSTANCE.UPGRADE_JEWEL_AFFIX_RARITY, 90)
            .addModification(ItemMods.INSTANCE.DO_NOTHING, 10)
            .potentialCost(10)
            .maxUses(new MaxUsesKey(ItemReqs.Datas.MAX_JEWEL_UPGRADE_USES))
            .weight(CodeCurrency.Weights.MEGA_UBER)
            .build(get());


    @Override
    public void init() {

    }
}
