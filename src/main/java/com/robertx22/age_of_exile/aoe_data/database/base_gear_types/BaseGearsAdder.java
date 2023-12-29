package com.robertx22.age_of_exile.aoe_data.database.base_gear_types;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;

public class BaseGearsAdder implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        // todo cant do this with a gear defense stat, i need to make it work differently


        // todo make base gears actually base gears with a simple builder, each base gear will have many variations like .of("serpent","Serpent Dagger", Stats.crit)
        // each will have a blank item in case there's ZERO base gears in that lvl range, the blank one will have weight 1

        List<Item> boots = Arrays.asList(Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
        List<Item> chest = Arrays.asList(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
        List<Item> legs = Arrays.asList(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
        List<Item> helmet = Arrays.asList(Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);

        List<Item> sword = Arrays.asList(Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
        List<Item> staff = Arrays.asList(SlashItems.GearItems.STAFFS.get(VanillaMaterial.WOOD).get(), SlashItems.GearItems.STAFFS.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.STAFFS.get(VanillaMaterial.DIAMOND).get());
        List<Item> ring = Arrays.asList(SlashItems.GearItems.RINGS.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.RINGS.get(VanillaMaterial.GOLD).get(), SlashItems.GearItems.RINGS.get(VanillaMaterial.DIAMOND).get());
        List<Item> necklace = Arrays.asList(SlashItems.GearItems.NECKLACES.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.NECKLACES.get(VanillaMaterial.GOLD).get(), SlashItems.GearItems.NECKLACES.get(VanillaMaterial.DIAMOND).get());
        List<Item> tomes = Arrays.asList(SlashItems.GearItems.TOMES.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.TOMES.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.TOMES.get(VanillaMaterial.DIAMOND).get());
        List<Item> totems = Arrays.asList(SlashItems.GearItems.ENERGY_DODGE_OFFHAND.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.ENERGY_DODGE_OFFHAND.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.ENERGY_DODGE_OFFHAND.get(VanillaMaterial.DIAMOND).get());


        // ARMOR
        BaseGearBuilder.of(BaseGearTypes.PLATE_BOOTS, GearSlots.BOOTS, "Plate Boots", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.PLATE_BOOTS, SlotTags.armor_stat, SlotTags.boots, SlotTags.armor_family, SlotTags.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.BOOTS))
                .items(boots)
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_PANTS, GearSlots.PANTS, "Plate Pants", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.PLATE_PANTS, SlotTags.armor_stat, SlotTags.pants, SlotTags.armor_family, SlotTags.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.PANTS))
                .items(legs)
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_CHEST, GearSlots.CHEST, "Plate Chest", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.PLATE_CHEST, SlotTags.armor_stat, SlotTags.chest, SlotTags.armor_family, SlotTags.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.CHEST))
                .items(chest)
                .build();

        BaseGearBuilder.of(BaseGearTypes.PLATE_HELMET, GearSlots.HELMET, "Plate Helmet", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.PLATE_HELMET, SlotTags.armor_stat, SlotTags.helmet, SlotTags.armor_family, SlotTags.strength))
                .baseStat(getStat(ArmorStat.ARMOR, ArmorSlot.HELMET))
                .items(helmet)
                .build();
        // ARMOR

        // MS
        BaseGearBuilder.of(BaseGearTypes.CLOTH_BOOTS, GearSlots.BOOTS, "Cloth Boots", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.CLOTH_BOOTS, SlotTags.magic_shield_stat, SlotTags.boots, SlotTags.armor_family, SlotTags.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.BOOTS))
                .items(boots)
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_PANTS, GearSlots.PANTS, "Cloth Pants", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.CLOTH_PANTS, SlotTags.magic_shield_stat, SlotTags.pants, SlotTags.armor_family, SlotTags.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.PANTS))
                .items(legs)
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_CHEST, GearSlots.CHEST, "Cloth Chest", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.CLOTH_CHEST, SlotTags.magic_shield_stat, SlotTags.chest, SlotTags.armor_family, SlotTags.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.CHEST))
                .items(chest)
                .build();

        BaseGearBuilder.of(BaseGearTypes.CLOTH_HELMET, GearSlots.HELMET, "Cloth Helmet", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.CLOTH_HELMET, SlotTags.magic_shield_stat, SlotTags.helmet, SlotTags.armor_family, SlotTags.intelligence))
                .baseStat(getStat(ArmorStat.MAGIC_SHIELD, ArmorSlot.HELMET))
                .items(helmet)
                .build();
        // MS

        // DODGE
        BaseGearBuilder.of(BaseGearTypes.LEATHER_BOOTS, GearSlots.BOOTS, "Leather Boots", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.LEATHER_BOOTS, SlotTags.dodge_stat, SlotTags.boots, SlotTags.armor_family, SlotTags.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.BOOTS))
                .items(boots)
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_PANTS, GearSlots.PANTS, "Leather Pants", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.LEATHER_PANTS, SlotTags.dodge_stat, SlotTags.pants, SlotTags.armor_family, SlotTags.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.PANTS))
                .items(legs)
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_CHEST, GearSlots.CHEST, "Leather Chest", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.LEATHER_CHEST, SlotTags.dodge_stat, SlotTags.chest, SlotTags.armor_family, SlotTags.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.CHEST))
                .items(chest)
                .build();

        BaseGearBuilder.of(BaseGearTypes.LEATHER_HELMET, GearSlots.HELMET, "Leather Helmet", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.LEATHER_HELMET, SlotTags.dodge_stat, SlotTags.helmet, SlotTags.armor_family, SlotTags.dexterity))
                .baseStat(getStat(ArmorStat.DODGE, ArmorSlot.HELMET))
                .items(helmet)
                .build();
        // DODGE

        BaseGearBuilder.of(BaseGearTypes.RING, GearSlots.RING, "Ring", StatRequirement.of())
                .tags(new TagList(SlotTags.ring, SlotTags.jewelry_family))
                .items(ring)
                .build();

        BaseGearBuilder.of(BaseGearTypes.NECKLACE, GearSlots.NECKLACE, "Necklace", StatRequirement.of())
                .tags(new TagList(SlotTags.necklace, SlotTags.jewelry_family))
                .items(necklace)
                .build();

        BaseGearBuilder.of(BaseGearTypes.ARMOR_SHIELD, GearSlots.SHIELD, "Tower Shield", StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.shield, SlotTags.offhand_family, SlotTags.armor_stat, SlotTags.strength))
                .baseStat(new StatMod(6, 12, Armor.getInstance(), ModType.FLAT))
                .items(Arrays.asList(Items.SHIELD))
                .build();

        BaseGearBuilder.of(BaseGearTypes.TOME, GearSlots.TOME, "Tome", StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.tome, SlotTags.offhand_family, SlotTags.magic_shield_stat, SlotTags.intelligence))
                .baseStat(new StatMod(3, 6, MagicShield.getInstance(), ModType.FLAT))
                .items(tomes)
                .build();

        BaseGearBuilder.of(BaseGearTypes.TOTEM, GearSlots.TOTEM, "Totem", StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.totem, SlotTags.offhand_family, SlotTags.dodge_stat, SlotTags.dexterity))
                .baseStat(new StatMod(6, 12, DodgeRating.getInstance(), ModType.FLAT))
                .items(totems)
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.BOW, GearSlots.BOW, WeaponTypes.bow, StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.bow, SlotTags.weapon_family, SlotTags.ranged_weapon, SlotTags.dexterity))
                .items(Arrays.asList(Items.BOW))
                .build();

        BaseGearBuilder.weapon(BaseGearTypes.CROSSBOW, GearSlots.CROSBOW, WeaponTypes.crossbow, StatRequirement.of(PlayStyle.DEX))
                .tags(new TagList(SlotTags.crossbow, SlotTags.ranger_casting_weapon, SlotTags.weapon_family, SlotTags.ranged_weapon, SlotTags.dexterity))
                .items(Arrays.asList(Items.CROSSBOW))
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.SWORD, GearSlots.SWORD, WeaponTypes.sword, StatRequirement.of(PlayStyle.STR))
                .tags(new TagList(SlotTags.sword, SlotTags.melee_weapon, SlotTags.weapon_family, SlotTags.strength, SlotTags.dexterity))
                .items(sword)
                .build();


        BaseGearBuilder.weapon(BaseGearTypes.STAFF, GearSlots.STAFF, WeaponTypes.staff, StatRequirement.of(PlayStyle.INT))
                .tags(new TagList(SlotTags.mage_weapon, SlotTags.staff, SlotTags.weapon_family, SlotTags.melee_weapon, SlotTags.intelligence))
                .items(staff)
                .build();

    }
}
