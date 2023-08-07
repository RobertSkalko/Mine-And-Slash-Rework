package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;

public enum ArmorTier {
    ZERO(0, ArmorMaterials.IRON, LevelRanges.STARTER_0),
    ONE(1, ArmorMaterials.IRON, LevelRanges.LOW_1),
    TWO(2, ArmorMaterials.DIAMOND, LevelRanges.MIDDLE_2),
    THREE(3, ArmorMaterials.DIAMOND, LevelRanges.HIGH_3),
    FOUR(4, ArmorMaterials.NETHERITE, LevelRanges.ENDGAME_4);

    int tier;
    ArmorMaterial vanillaMat;
    LevelRange lvl;

    ArmorTier(int tier, ArmorMaterial vanillaMat, LevelRange lvl) {
        this.tier = tier;
        this.vanillaMat = vanillaMat;
        this.lvl = lvl;
    }


    public static ArmorTier from(LevelRange range) {

        for (ArmorTier tier : ArmorTier.values()) {
            if (tier.lvl == range) {
                return tier;
            }
        }
        return null;

    }
}
