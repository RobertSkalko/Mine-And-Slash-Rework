package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseGearBuilder implements GearDataHelper {

    private PlayStyle style = PlayStyle.STR;
    private String locnamesuffix;
    private String id;
    private String slot;
    private TagList tags;
    private List<StatMod> basestats = new ArrayList<>();
    private List<StatMod> implicitstats = new ArrayList<>();
    private List<BaseGearType.ItemChance> items = new ArrayList<>();
    private StatRequirement req = new StatRequirement();
    private WeaponTypes wep = WeaponTypes.none;
    private int weight = 1000;

    public static BaseGearBuilder of(DataGenKey<BaseGearType> id, String slot, String locnamesuffix, StatRequirement req) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = locnamesuffix;
        b.id = id.GUID();
        b.req = req;
        b.slot = slot;
        return b;
    }

    public static BaseGearBuilder weapon(DataGenKey<BaseGearType> id, String slot, WeaponTypes type, StatRequirement req) {
        BaseGearBuilder b = new BaseGearBuilder();
        b.locnamesuffix = type.locName();
        b.id = id.GUID();
        b.slot = slot;
        b.weaponType(type);
        b.attackStyle(type.style);
        b.baseStat(b.getAttackDamageStat(type));
        b.req = req;

        return b;
    }

    public BaseGearBuilder attackStyle(PlayStyle style) {
        this.style = style;
        return this;
    }

    public BaseGearBuilder items(List<Item> items) {

        for (int i = 0; i < items.size(); i++) {
            String rar = IRarity.COMMON_ID;

            if (i > 0) {
                rar = IRarity.RARE_ID;
            }
            if (i > 1) {
                rar = IRarity.LEGENDARY_ID;
            }

            String key = VanillaUTIL.REGISTRY.items().getKey(items.get(i)).toString();

            this.items.add(new BaseGearType.ItemChance(1000, key, rar));
        }

        return this;
    }

    public BaseGearBuilder tags(TagList tags) {
        this.tags = tags;
        return this;
    }

    public BaseGearBuilder weight(int weight) {
        this.weight = weight;
        return this;
    }

    public BaseGearBuilder weaponType(WeaponTypes wep) {
        this.wep = wep;
        return this;
    }


    public BaseGearBuilder baseStat(StatMod... mod) {
        this.basestats.addAll(Arrays.asList(mod));
        return this;
    }

    public BaseGearBuilder implicitStat(StatMod mod) {
        this.implicitstats.add(mod);
        return this;
    }

    public DataGenKey<BaseGearType> build() {


        String name = /*namePrefixes.get(x) + " " + */locnamesuffix;
        String id = this.id;
        BaseGearType type = new BaseGearType(slot, id, name);
        type.weapon_type = wep;
        type.tags = tags;
        type.implicit_stats = implicitstats;
        type.base_stats = basestats;
        type.weight = weight;
        type.style = style;
        type.req = req;
        type.possible_items = items;
        type.addToSerializables();


        return new DataGenKey<>(type.GUID());

    }

}

