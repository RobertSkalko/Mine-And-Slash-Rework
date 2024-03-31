package com.robertx22.age_of_exile.aoe_data.database.stat_compats;

import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.ForgeRegistries;

public class StatCompats implements ExileRegistryInit {
    @Override
    public void registerAll() {

        new StatCompat("armor").editAndReg(x -> {
            x.attribute_id = ForgeRegistries.ATTRIBUTES.getKey(Attributes.ARMOR).toString();
            x.mns_stat_id = Armor.getInstance().GUID();
            x.mod_type = ModType.PERCENT;
        });

    }
}
