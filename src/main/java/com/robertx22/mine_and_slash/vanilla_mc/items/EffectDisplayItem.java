package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import net.minecraft.world.item.Item;

public class EffectDisplayItem extends Item implements IAutoModel {

    public EffectDisplayItem() {
        super(new Properties());
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }
}
