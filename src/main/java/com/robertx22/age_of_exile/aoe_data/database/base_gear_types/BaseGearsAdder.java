package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseGearsAdder implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        // todo cant do this with a gear defense stat, i need to make it work differently

        /*
        // ARMOR/MS
        BaseGearBuilder.of(BaseGearTypes.ARMOR_MS_BOOTS, GearSlots.BOOTS, "Chain Boots", StatRequirement.of(PlayStyle.INT, PlayStyle.STR))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.armor_stat, SlotTag.intelligence, SlotTag.strength))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.BOOTS))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.ARMOR_MS_PANTS, GearSlots.PANTS, "Chain Pants", StatRequirement.of(PlayStyle.INT, PlayStyle.STR))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.armor_stat, SlotTag.intelligence, SlotTag.strength))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.PANTS))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.ARMOR_MS_CHEST, GearSlots.CHEST, "Chain Chest", StatRequirement.of(PlayStyle.INT, PlayStyle.STR))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.armor_stat, SlotTag.intelligence, SlotTag.strength))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.CHEST))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.ARMOR_MS_HELMET, GearSlots.HELMET, "Chain Helmet", StatRequirement.of(PlayStyle.INT, PlayStyle.STR))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.armor_stat, SlotTag.intelligence, SlotTag.strength))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.HELMET))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.HELMET))
                .build();
        // ARMOR/MS


        // DODGE/MS
        BaseGearBuilder.of(BaseGearTypes.DODGE_MS_BOOTS, GearSlots.BOOTS, "Bone Boots", StatRequirement.of(PlayStyle.INT, PlayStyle.DEX))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.intelligence, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.BOOTS))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_MS_PANTS, GearSlots.PANTS, "Bone Pants", StatRequirement.of(PlayStyle.INT, PlayStyle.DEX))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.intelligence, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.PANTS))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_MS_CHEST, GearSlots.CHEST, "Bone Chest", StatRequirement.of(PlayStyle.INT, PlayStyle.DEX))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.intelligence, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.CHEST))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_MS_HELMET, GearSlots.HELMET, "Bone Helmet", StatRequirement.of(PlayStyle.INT, PlayStyle.DEX))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.intelligence, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.HELMET))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.HELMET))
                .build();
        // DODGE/MS

        // DODGE/ARMOR
        BaseGearBuilder.of(BaseGearTypes.DODGE_ARMOR_BOOTS, GearSlots.BOOTS, "Scale Boots", StatRequirement.of(PlayStyle.STR, PlayStyle.DEX))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.strength, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.BOOTS))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_ARMOR_PANTS, GearSlots.PANTS, "Scale Pants", StatRequirement.of(PlayStyle.STR, PlayStyle.DEX))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.strength, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.PANTS))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_ARMOR_CHEST, GearSlots.CHEST, "Scale Chest", StatRequirement.of(PlayStyle.STR, PlayStyle.DEX))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.strength, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.CHEST))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.DODGE_ARMOR_HELMET, GearSlots.HELMET, "Scale Helmet", StatRequirement.of(PlayStyle.STR, PlayStyle.DEX))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.dodge_stat, SlotTag.strength, SlotTag.dexterity))
                .baseStat(halfStat(ArmorStat.ARMOR, ArmorSlot.HELMET))
                .baseStat(halfStat(ArmorStat.DODGE, ArmorSlot.HELMET))
                .build();
        // DODGE/ARMOR

         */

        // ARMOR
        BaseGearBuilder.of(BaseGearTypes.PLATE_BOOTS, GearSlots.BOOTS, "Plate Boots", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_PANTS, GearSlots.PANTS, "Plate Pants", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_CHEST, GearSlots.CHEST, "Plate Chest", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_HELMET, GearSlots.HELMET, "Plate Helmet", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.armor_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.HELMET))
                .build();
        // ARMOR

        // MS
        BaseGearBuilder.of(BaseGearTypes.CLOTH_BOOTS, GearSlots.BOOTS, "Cloth Boots", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_PANTS, GearSlots.PANTS, "Cloth Pants", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_CHEST, GearSlots.CHEST, "Cloth Chest", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_HELMET, GearSlots.HELMET, "Cloth Helmet", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.magic_shield_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.HELMET))
                .build();
        // MS

        // DODGE
        BaseGearBuilder.of(BaseGearTypes.LEATHER_BOOTS, GearSlots.BOOTS, "Leather Boots", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.dodge_stat, SlotTag.boots, SlotTag.armor_family, SlotTag.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.BOOTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_PANTS, GearSlots.PANTS, "Leather Pants", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.dodge_stat, SlotTag.pants, SlotTag.armor_family, SlotTag.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.PANTS))
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_CHEST, GearSlots.CHEST, "Leather Chest", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.dodge_stat, SlotTag.chest, SlotTag.armor_family, SlotTag.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.CHEST))
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_HELMET, GearSlots.HELMET, "Leather Helmet", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.dodge_stat, SlotTag.helmet, SlotTag.armor_family, SlotTag.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.HELMET))
                .build();
        // DODGE

        BaseGearBuilder.of(BaseGearTypes.RING, GearSlots.RING, "Ring", StatRequirement.of())
                .tags(new TagList(SlotTag.ring, SlotTag.jewelry_family))
                .build();

        BaseGearBuilder.of(BaseGearTypes.NECKLACE, GearSlots.NECKLACE, "Necklace", StatRequirement.of())
                .tags(new TagList(SlotTag.necklace, SlotTag.jewelry_family))
                .build();

        BaseGearBuilder.of(BaseGearTypes.ARMOR_SHIELD, GearSlots.SHIELD, "Tower Shield", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.shield, SlotTag.offhand_family, SlotTag.armor_stat, SlotTag.strength))
                .baseStat(new StatMod(6, 12, Armor.getInstance(), ModType.FLAT))
                .build();

        BaseGearBuilder.of(BaseGearTypes.TOME, GearSlots.TOME, "Tome", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.tome, SlotTag.offhand_family, SlotTag.magic_shield_stat, SlotTag.intelligence))
                .baseStat(new StatMod(3, 6, MagicShield.getInstance(), ModType.FLAT))
                .build();

        BaseGearBuilder.of(BaseGearTypes.TOTEM, GearSlots.TOTEM, "Totem", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.totem, SlotTag.offhand_family, SlotTag.dodge_stat, SlotTag.dexterity))
                .baseStat(new StatMod(6, 12, DodgeRating.getInstance(), ModType.FLAT))
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.BOW, GearSlots.BOW, WeaponTypes.bow, StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.ranger_casting_weapon, SlotTag.bow, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
                .build();

        BaseGearBuilder.weapon(BaseGearTypes.CROSSBOW, GearSlots.CROSBOW, WeaponTypes.crossbow, StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTag.crossbow, SlotTag.ranger_casting_weapon, SlotTag.weapon_family, SlotTag.ranged_weapon, SlotTag.dexterity))
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.SWORD, GearSlots.SWORD, WeaponTypes.sword, StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTag.warrior_casting_weapon, SlotTag.sword, SlotTag.melee_weapon, SlotTag.weapon_family, SlotTag.strength, SlotTag.dexterity))
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.STAFF, GearSlots.STAFF, WeaponTypes.staff, StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTag.mage_weapon, SlotTag.staff, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.intelligence))
                .build();

    }
}
