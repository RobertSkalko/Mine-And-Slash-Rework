package com.robertx22.age_of_exile.aoe_data.database.stat_compats;

import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

public class StatCompats implements ExileRegistryInit {
    @Override
    public void registerAll() {

        new StatCompat("armor").editAndReg(x -> {
            x.attribute_id = ForgeRegistries.ATTRIBUTES.getKey(Attributes.ARMOR).toString();
            x.mns_stat_id = Armor.getInstance().GUID();
            x.mod_type = ModType.PERCENT;
        });

        new StatCompat("sharpness_damage").editAndReg(x -> {
            x.enchant_id = ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.SHARPNESS).toString();
            x.mns_stat_id = OffenseStats.TOTAL_DAMAGE.get().GUID();
            x.mod_type = ModType.FLAT;
            x.conversion = 5;
            x.maximum_cap = 50;
        });

    }
}
