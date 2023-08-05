package com.robertx22.age_of_exile.aoe_data.datapacks.models;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ItemModelManager {

    public static ItemModelManager INSTANCE = new ItemModelManager();

    public void generateModels() {

        VanillaUTIL.REGISTRY.items().forEach(x -> {
            if (x instanceof IAutoModel) {
                IAutoModel auto = (IAutoModel) x;
                auto.generateModel(this);
            }
        });

    }

    public void generated(Item item) {
        new ModelHelper(item, ModelHelper.Type.GENERATED).generate();
    }

    public void handheld(Item item) {
        new ModelHelper(item, ModelHelper.Type.HANDHELD).generate();
    }

}
