package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.WeaponMechanic;
import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public final class BaseGearType implements IAutoLocName, JsonExileRegistry<BaseGearType>, IAutoGson<BaseGearType> {

    public static BaseGearType SERIALIZER = new BaseGearType();

    protected String guid;
    protected LevelRange level_range;
    public String gear_slot = "";

    public int weight = 1000;
    public PlayStyle style = PlayStyle.STR;

    public List<StatMod> implicit_stats = new ArrayList<>();
    public List<StatMod> base_stats = new ArrayList<>();

    public WeaponTypes weapon_type = WeaponTypes.none;
    public TagList tags = new TagList();

    protected transient String locname;

    public BaseGearType(String slot, String guid, LevelRange levelRange, String locname) {
        this.guid = guid;
        this.level_range = levelRange;
        this.locname = locname;
        this.gear_slot = slot;
    }

    private BaseGearType() {

    }

    public List<StatMod> implicitStats() {
        return implicit_stats;
    }

    public List<StatMod> baseStats() {
        return base_stats;
    }

    public WeaponTypes weaponType() {
        return weapon_type;
    }

    public TagList getTags() {
        return tags;
    }

    @Override
    public final String GUID() {
        return guid;
    }

    public GearSlot getGearSlot() {
        return ExileDB.GearSlots()
                .get(gear_slot);
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    public LevelRange getLevelRange() {
        return level_range;
    }

    public final EquipmentSlot getVanillaSlotType() {

        if (tags.contains(SlotTag.shield)) {
            return EquipmentSlot.OFFHAND;
        }
        if (tags.contains(SlotTag.boots)) {
            return EquipmentSlot.FEET;
        }
        if (tags.contains(SlotTag.chest)) {
            return EquipmentSlot.CHEST;
        }
        if (tags.contains(SlotTag.pants)) {
            return EquipmentSlot.LEGS;
        }
        if (tags.contains(SlotTag.helmet)) {
            return EquipmentSlot.HEAD;
        }
        if (isWeapon()) {
            return EquipmentSlot.MAINHAND;
        }

        return null;
    }

    public int Weight() {
        return weight;
    }


    public final boolean isArmor() {
        return family() == SlotFamily.Armor;
    }

    public final boolean isJewelry() {
        return family() == SlotFamily.Jewelry;
    }

    public final boolean isWeapon() {
        return family() == SlotFamily.Weapon;
    }

    public final boolean isMeleeWeapon() {
        return this.getTags()
                .contains(SlotTag.melee_weapon);
    }

    public boolean isShield() {
        return getTags().contains(SlotTag.shield);
    }

    public enum SlotTag {

        sword(SlotFamily.Weapon),
        scepter(SlotFamily.Weapon),
        staff(SlotFamily.Weapon),
        hammer(SlotFamily.Weapon),
        spear(SlotFamily.Weapon),
        axe(SlotFamily.Weapon),
        bow(SlotFamily.Weapon),
        crossbow(SlotFamily.Weapon),

        boots(SlotFamily.Armor),
        helmet(SlotFamily.Armor),
        pants(SlotFamily.Armor),
        chest(SlotFamily.Armor),

        necklace(SlotFamily.Jewelry),
        ring(SlotFamily.Jewelry),
        shield(SlotFamily.OffHand),

        mage_weapon(SlotFamily.NONE), melee_weapon(SlotFamily.NONE), ranged_weapon(SlotFamily.NONE),

        armor_stat(SlotFamily.NONE),
        magic_shield_stat(SlotFamily.NONE),
        dodge_stat(SlotFamily.NONE),

        warrior_casting_weapon(SlotFamily.Weapon),
        ranger_casting_weapon(SlotFamily.Weapon),

        weapon_family(SlotFamily.NONE),
        armor_family(SlotFamily.NONE),
        jewelry_family(SlotFamily.NONE),
        offhand_family(SlotFamily.NONE),

        intelligence(SlotFamily.NONE),
        dexterity(SlotFamily.NONE),
        strength(SlotFamily.NONE);

        public SlotFamily family = SlotFamily.NONE;

        SlotTag(SlotFamily family) {
            this.family = family;
        }
    }

    public final SlotFamily family() {
        return getTags().getFamily();
    }

    public final WeaponMechanic getWeaponMechanic() {
        return new NormalWeaponMechanic();
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_TYPE;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Slots;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".gear_type." + GUID();
    }

    public final boolean isMageWeapon() {
        return getTags().contains(SlotTag.mage_weapon);
    }

    @Override
    public Class<BaseGearType> getClassForSerialization() {
        return BaseGearType.class;
    }
}
