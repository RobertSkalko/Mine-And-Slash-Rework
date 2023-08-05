package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.GearEnumLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.BaseStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OrbOfBlessingItem extends CurrencyItem implements ICurrencyItemEffect {
    @Override
    public String GUID() {
        return "currency/number_reroll";
    }

    @Override
    public int getWeight() {
        return 500;
    }

    private static final String name = SlashRef.MODID + ":currency/number_reroll";

    public OrbOfBlessingItem() {

        super(name);

    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        gear.GetAllRerollable()
                .stream()
                .filter(x -> !(x instanceof BaseStatsData))
                .forEach(x -> x.RerollNumbers(gear));

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, GearEnumLocReq.REROLL_NUMBERS, SimpleGearLocReq.IS_NOT_UNIQUE);
    }

    @Override
    public String getRarityRank() {
        return IRarity.RARE_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb Of Blessing";
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls explicit numbers on a gear.";
    }


}