package com.robertx22.age_of_exile.aoe_data.database;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

public interface GearDataHelper {


    public enum ArmorSlot {
        HELMET(0.5F),
        CHEST(1F),
        PANTS(0.8F),
        BOOTS(0.5F);

        public float multi;

        ArmorSlot(float multi) {
            this.multi = multi;
        }
    }

    public enum ArmorStat {
        ARMOR(10, 15, Armor.getInstance()),
        MAGIC_SHIELD(5, 10, MagicShield.getInstance()),
        DODGE(10, 15, DodgeRating.getInstance());

        public float min;
        public float max;
        public Stat stat;

        ArmorStat(float min, float max, Stat stat) {
            this.min = min;
            this.max = max;
            this.stat = stat;
        }
    }

    public default StatMod getStat(ArmorStat stat, ArmorSlot slot) {

        float v1min = stat.min * slot.multi;
        float v1max = stat.max * slot.multi;

        return new StatMod(v1min, v1max, stat.stat, ModType.FLAT);
    }

    public default StatMod halfStat(ArmorStat stat, ArmorSlot slot) {

        float v1min = stat.min * slot.multi * 0.5F;
        float v1max = stat.max * slot.multi * 0.5F;

        return new StatMod(v1min, v1max, stat.stat, ModType.FLAT);
    }

    public default StatMod getAttackDamageStat(WeaponTypes weapon) {

        float v1min = 2;
        float v1max = 6;

        return new StatMod(v1min, v1max, WeaponDamage.getInstance(), ModType.FLAT);
    }

}
