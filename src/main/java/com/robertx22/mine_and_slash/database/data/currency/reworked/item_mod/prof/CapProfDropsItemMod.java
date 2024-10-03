package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.prof;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ProfessionModification;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class CapProfDropsItemMod extends ProfessionModification {

    public Data data;

    public static record Data(int lvl) {
    }


    public CapProfDropsItemMod(String id, Data data) {
        super(ItemModificationSers.CAP_PROF_DROPS, id);
        this.data = data;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return CapProfDropsItemMod.class;
    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return getDescParams(data.lvl);
    }

    @Override
    public void modifyProfessionItem(ExileStack data) {
        data.get(StackKeys.TOOL).edit(x -> x.force_lvl = this.data.lvl);
    }

    @Override
    public String locDescForLangFile() {
        return "Caps Profession Drops To Level %1$s";
    }
}
