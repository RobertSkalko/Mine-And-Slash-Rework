package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles;

import com.robertx22.age_of_exile.a_libraries.curios.interfaces.INecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.world.entity.EquipmentSlot;

public class ItemNecklace extends BaseBaublesItem implements INecklace {

    VanillaMaterial mat;

    public ItemNecklace(VanillaMaterial mat) {
        super(new Properties().durability((int) (mat.armormat.getDurabilityForSlot(EquipmentSlot.CHEST) * 3))
                .tab(CreativeTabs.MyModTab), "Necklace");
        this.mat = mat;
    }

}
