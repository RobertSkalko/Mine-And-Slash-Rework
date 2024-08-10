package com.robertx22.mine_and_slash.aoe_data.database.unique_gears;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ErrorUtils;
import com.robertx22.library_of_exile.registry.DataGenKey;

import java.util.List;

public class UniqueGearBuilder {


    UniqueGear uniq = new UniqueGear();

    public static UniqueGearBuilder of(String id, String locname, String basegear) {

        UniqueGearBuilder b = new UniqueGearBuilder();
        b.uniq.langName = locname;
        b.uniq.guid = id;

        b.uniq.base_gear = basegear;

        return b;

    }


    public static UniqueGearBuilder of(String id, String locname, DataGenKey<BaseGearType> gearType) {
        return of(id, locname, gearType.GUID());
    }

    public UniqueGearBuilder stats(List<StatMod> stats) {
        this.uniq.unique_stats = stats;

        return this;
    }


    public UniqueGearBuilder stat(StatMod stat) {
        this.uniq.unique_stats.add(stat);
        return this;
    }

    public UniqueGearBuilder addWeaponDamage(UniquePower power) {
        this.uniq.unique_stats.addAll(power.getWeaponDamageMods());
        return this;
    }

    public UniqueGearBuilder rarityWeight(UniqueRarityTier rar) {
        this.uniq.weight = rar.weight;
        return this;
    }

    public UniqueGearBuilder weight(int w) {
        this.uniq.weight = w;
        return this;
    }

    public UniqueGearBuilder setFlavorText(String txt) {
        this.uniq.flavor_text = txt;
        return this;
    }


    // delete this not needed
    public UniqueGearBuilder setReplacesName() {
        this.uniq.replaces_name = true;
        return this;
    }

    public UniqueGearBuilder keepsBaseName() {
        this.uniq.replaces_name = false;
        return this;
    }

    public UniqueGearBuilder leagueOnly(String comment) {
        this.uniq.league = comment;
        return this;
    }

    public UniqueGearBuilder devComment(String comment) {
        // OMAE WA MOU SHINDEIRU
        return this;
    }

    public UniqueGear build() {
        ErrorUtils.ifFalse(!uniq.unique_stats.isEmpty());

        uniq.addToSerializables();
        return uniq;
    }

}