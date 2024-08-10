package com.robertx22.mine_and_slash.aoe_data.datapacks.models;

import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.item.Item;

public class ItemModelManager {

    public static ItemModelManager INSTANCE = new ItemModelManager();

    public void generateModels() {

        VanillaUTIL.REGISTRY.items().getAll().forEach(x -> {
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
