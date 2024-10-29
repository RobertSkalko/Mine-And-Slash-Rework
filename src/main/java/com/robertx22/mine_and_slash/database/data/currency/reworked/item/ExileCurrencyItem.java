package com.robertx22.mine_and_slash.database.data.currency.reworked.item;

import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRarityItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ExileCurrencyItem extends Item implements IAutoLocName, IAutoModel, IRarityItem {

    //ExileCurrency effect;

    String name;

    public ExileCurrencyItem(ExileCurrency effect) {
        super(new Item.Properties());
        this.name = effect.locname;
    }


    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return VanillaUTIL.REGISTRY.items().getKey(this).toString();
    }


    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "";
    }


    @Override
    public GearRarity getItemRarity(ItemStack stack) {
        try {
            return ExileCurrency.get(stack).get().getRarity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
