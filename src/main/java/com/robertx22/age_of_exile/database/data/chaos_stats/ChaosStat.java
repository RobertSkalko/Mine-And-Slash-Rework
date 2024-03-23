package com.robertx22.age_of_exile.database.data.chaos_stats;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.ArrayList;
import java.util.List;

// todo will be used for both uniques, runed items, normal items etc

public class ChaosStat implements JsonExileRegistry<ChaosStat>, IAutoGson<ChaosStat>, IAutoLocName {

    public static ChaosStat SERIALIZER = new ChaosStat();


    public String id = "";
    public String name = "";
    public int weight = 1000;
    public int affix_number = 0;
    public Affix.Type affix_type = Affix.Type.chaos_stat;
    public int bonus_sockets = 0;
    public List<String> for_item_rarities = new ArrayList<>();

    public ChaosStat() {
    }

    public ChaosStat(String id, String name, int weight, int affix_number, Affix.Type affix_type, int bonus_sockets, List<String> for_item_rarities) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.affix_number = affix_number;
        this.affix_type = affix_type;
        this.bonus_sockets = bonus_sockets;
        this.for_item_rarities = for_item_rarities;
    }

    public void applyToGear(GearItemData gear) {

        gear.data.set(GearItemData.KEYS.CORRUPT, true);
        gear.setPotential(0);

        for (int i = 0; i < bonus_sockets; i++) {
            gear.sockets.addSocket();
        }

        for (int i = 0; i < affix_number; i++) {

            FilterListWrap<Affix> list = ExileDB.Affixes().getFilterWrapped(x -> x.type == this.affix_type && gear.canGetAffix(x));

            var affix = list.random();

            AffixData data = new AffixData(Affix.Type.chaos_stat);
            data.create(gear, affix);

            gear.affixes.cor.add(data);

        }


    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CHAOS_STAT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public Class<ChaosStat> getClassForSerialization() {
        return ChaosStat.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.CHAOS_STAT;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".chaos_stat." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }
}
