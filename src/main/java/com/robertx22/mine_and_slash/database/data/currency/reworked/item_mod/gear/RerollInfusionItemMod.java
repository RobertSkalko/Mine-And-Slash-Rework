package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.GearInfusionData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import net.minecraft.network.chat.MutableComponent;

public class RerollInfusionItemMod extends GearModification {


    public RerollInfusionItemMod(String id) {
        super(ItemModificationSers.REROLL_INFUSION, id);

    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            GearInfusionData en = new GearInfusionData();

            Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                return x.type == Affix.AffixSlot.enchant && x.requirements.satisfiesAllRequirements(new GearRequestedFor(gear)) && x.getAllTagReq().contains(SlotTags.enchantment.GUID());
            }).random();

            en.en = affix.GUID();
        });
    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return RerollInfusionItemMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Re-rolls Infusion Affix";
    }
}
