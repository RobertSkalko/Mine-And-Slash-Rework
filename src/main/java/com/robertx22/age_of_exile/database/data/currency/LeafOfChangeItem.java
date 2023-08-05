package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LeafOfChangeItem extends CurrencyItem implements ICurrencyItemEffect {
    @Override
    public String GUID() {
        return "currency/reroll_primary_stats_numbers";
    }

    @Override
    public int getWeight() {
        return 1000;
    }

    private static final String name = SlashRef.MODID + ":currency/reroll_primary_stats_numbers";

    public LeafOfChangeItem() {

        super(name);

    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.baseStats.RerollNumbers(gear);

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_PRIMARY_STATS);
    }

    @Override
    public String getRarityRank() {
        return EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Leaf of Change";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls implicit stat numbers.";
    }


}