package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.runewords.RunewordBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
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

    public UniqueGearBuilder rarityWeight(UniqueRarityTier rar) {
        this.uniq.weight = rar.weight;
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

    public UniqueGearBuilder makeRuneWordOnly(List<RuneItem.RuneType> runes) {
        RunewordBuilder.of(uniq.guid, uniq.guid, runes, uniq.getBaseGear().gear_slot);
        this.uniq.rarity = IRarity.RUNEWORD_ID;
        this.uniq.weight = 0;
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