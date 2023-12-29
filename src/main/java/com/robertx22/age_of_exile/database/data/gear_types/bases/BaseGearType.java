package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.NormalWeaponMechanic;
import com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics.WeaponMechanic;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BaseGearType implements IAutoLocName, JsonExileRegistry<BaseGearType>, IAutoGson<BaseGearType> {

    public static BaseGearType SERIALIZER = new BaseGearType();

    protected String guid;
    public String gear_slot = "";

    public int weight = 1000;
    public PlayStyle style = PlayStyle.STR;

    public StatRequirement req = new StatRequirement();

    public List<StatMod> base_stats = new ArrayList<>();

    public WeaponTypes weapon_type = WeaponTypes.none;
    public TagList tags = new TagList();

    public List<ItemChance> possible_items = new ArrayList<>();

    protected transient String locname;

    public BaseGearType(String slot, String guid, String locname) {
        this.guid = guid;
        this.locname = locname;
        this.gear_slot = slot;

    }


    private BaseGearType() {

    }

    public static class ItemChance implements IWeighted {
        public int weight = 1000;
        public String item_id = "";
        public String min_rar = IRarity.COMMON_ID;

        public ItemChance(int weight, String item_id, String min_rar) {
            this.weight = weight;
            this.item_id = item_id;
            this.min_rar = min_rar;
        }

        public Item getItem() {
            return VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id));
        }

        @Override
        public int Weight() {
            return weight;
        }
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

    public Item getRandomItem(GearRarity rar) {
        var list = this.possible_items.stream().filter(x -> rar.item_tier >= ExileDB.GearRarities().get(x.min_rar).item_tier).collect(Collectors.toList());
        return RandomUtils.weightedRandom(list).getItem();
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }


    public final EquipmentSlot getVanillaSlotType() {

        if (tags.contains(SlotTags.shield)) {
            return EquipmentSlot.OFFHAND;
        }
        if (tags.contains(SlotTags.boots)) {
            return EquipmentSlot.FEET;
        }
        if (tags.contains(SlotTags.chest)) {
            return EquipmentSlot.CHEST;
        }
        if (tags.contains(SlotTags.pants)) {
            return EquipmentSlot.LEGS;
        }
        if (tags.contains(SlotTags.helmet)) {
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
                .contains(SlotTags.melee_weapon);
    }

    public boolean isShield() {
        return getTags().contains(SlotTags.shield);
    }

    public final SlotFamily family() {
        return this.getGearSlot().fam;
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
        return getTags().contains(SlotTags.mage_weapon);
    }

    @Override
    public Class<BaseGearType> getClassForSerialization() {
        return BaseGearType.class;
    }
}
