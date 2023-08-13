package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles;

import com.robertx22.age_of_exile.a_libraries.curios.interfaces.IRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.world.item.ArmorItem;

public class ItemRing extends BaseBaublesItem implements IRing {

    VanillaMaterial mat;

    public ItemRing(VanillaMaterial mat) {
        super(new Properties().durability(500 + mat.armormat.getDurabilityForType(ArmorItem.Type.CHESTPLATE) * 2)
                , "Ring");
        this.mat = mat;
    }
}
