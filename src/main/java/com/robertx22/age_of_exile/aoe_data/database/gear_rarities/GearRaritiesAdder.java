package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {


        GearRarity common = new GearRarity().edit(x -> {
            x.favor_loot_multi = 0.75F;
            x.favor_needed = 0;
            x.mob = MobRarity.of(0, 0, 0);
            x.min_affixes = 1;
            x.weight = 5000;
            x.item_tier_power = 1;
            x.backpack_slots = 9;
            x.item_tier = 0;
            x.pot = new GearRarity.Potential(100, 0.5F);
            x.item_value_multi = 1;
            x.item_model_data_num = 1;
            x.max_sockets = 0;
            x.socket_chance = 0;
            x.higher_rar = IRarity.UNCOMMON;
            x.stat_percents = new MinMax(0, 10);
            x.setCommonFields();
            x.addToSerializables();
        });

        GearRarity uncommon = new GearRarity().edit(x -> {
            x.favor_loot_multi = 0.9F;
            x.favor_needed = 50;
            x.mob = MobRarity.of(0, 0.25F, 0);
            x.max_sockets = 1;
            x.socket_chance = 5;
            x.max_spell_links = 2;
            x.backpack_slots = 2 * 9;
            x.pot = new GearRarity.Potential(100, 1);
            x.min_affixes = 2;
            x.weight = 2000;
            x.item_tier = 1;
            x.item_model_data_num = 2;
            x.item_tier_power = 1.25F;
            x.item_value_multi = 1.25F;
            x.higher_rar = IRarity.RARE_ID;
            x.stat_percents = new MinMax(10, 20);
            x.setUncommonFields();
            x.addToSerializables();
        });

        GearRarity rar = new GearRarity().edit(x -> {
            x.favor_loot_multi = 1;
            x.favor_needed = 100;
            x.mob = MobRarity.of(3, 2, 1);
            x.max_sockets = 2;
            x.socket_chance = 15;
            x.max_spell_links = 3;
            x.min_lvl = 10;
            x.backpack_slots = 3 * 9;
            x.lootable_gear_tier = GearRarity.LootableGearTier.MID;
            x.pot = new GearRarity.Potential(100, 1.5F);
            x.item_tier = 2;
            x.item_model_data_num = 3;
            x.min_affixes = 3;
            x.weight = 500;
            x.item_tier_power = 1.5F;
            x.item_value_multi = 1.5F;
            x.higher_rar = IRarity.EPIC_ID;
            x.stat_percents = new MinMax(20, 40);
            x.setRareFields();
            x.addToSerializables();

        });
        GearRarity epic = new GearRarity().edit(x -> {
            x.favor_loot_multi = 1.05F;
            x.favor_needed = 250;
            x.mob = MobRarity.of(10, 3, 1);
            x.max_sockets = 3;
            x.socket_chance = 20;
            x.min_lvl = 25;
            x.max_spell_links = 4;
            x.backpack_slots = 4 * 9;
            x.lootable_gear_tier = GearRarity.LootableGearTier.MID;
            x.pot = new GearRarity.Potential(100, 1.5F);
            x.min_affixes = 4;
            x.weight = 100;
            x.item_tier = 3;
            x.item_model_data_num = 4;
            x.item_tier_power = 1.7F;
            x.item_value_multi = 1.7F;
            x.higher_rar = IRarity.LEGENDARY_ID;
            x.stat_percents = new MinMax(40, 60);
            x.setEpicFields();
            x.addToSerializables();
        });

        GearRarity legendary = new GearRarity().edit(x -> {
            x.favor_loot_multi = 1.2F;
            x.favor_needed = 500;
            x.mob = MobRarity.of(20, 5, 2);
            x.max_sockets = 3;
            x.socket_chance = 30;
            x.max_spell_links = 5;
            x.min_lvl = 50;
            x.backpack_slots = 5 * 9;
            x.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
            x.pot = new GearRarity.Potential(100, 1.75F);
            x.min_affixes = 5;
            x.weight = 50;
            x.item_tier = 4;
            x.item_model_data_num = 5;
            x.item_tier_power = 2;
            x.item_value_multi = 2;
            x.announce_in_chat = true;
            x.higher_rar = IRarity.MYTHIC_ID;
            x.stat_percents = new MinMax(60, 80);
            x.setLegendFields();
            x.addToSerializables();
        });

        GearRarity mythic = new GearRarity().edit(x -> {
            x.favor_loot_multi = 1.25F;
            x.favor_needed = 1000;
            x.mob = MobRarity.of(25, 10, 3);
            x.max_sockets = 3;
            x.min_lvl = 75;
            x.backpack_slots = 6 * 9;
            x.max_spell_links = 5;
            x.socket_chance = 50;
            x.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
            x.pot = new GearRarity.Potential(100, 2F);
            x.min_affixes = 6;
            x.weight = 25;
            x.item_tier = 5;
            x.item_model_data_num = 6;
            x.item_tier_power = 3;
            x.item_value_multi = 3;
            x.announce_in_chat = true;
            x.stat_percents = new MinMax(80, 100);
            x.setMythicFields();
            x.addToSerializables();
        });

        GearRarity unique = new GearRarity().edit(x -> {
            x.mob = MobRarity.of(25, 10, 3);
            x.max_sockets = 3;
            x.socket_chance = 20;
            x.pot = new GearRarity.Potential(100, 1);
            x.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
            x.min_affixes = 0;
            x.weight = 25;
            x.item_tier_power = 2;
            x.item_value_multi = 2;
            x.item_tier = 5;
            x.setUniqueFields();
            x.addToSerializables();
            x.announce_in_chat = true;
            x.is_unique_item = true;
        });

        GearRarity runeword = new GearRarity().edit(x -> {
            x.mob = MobRarity.of(25, 10, 3);
            x.max_sockets = 3;
            x.socket_chance = 50;
            x.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
            x.pot = new GearRarity.Potential(100, 1);
            x.min_affixes = 0;
            x.weight = 0;
            x.item_tier_power = 2;
            x.item_value_multi = 2;
            x.item_tier = 5;
            x.setRunewordFields();
            x.addToSerializables();
            x.announce_in_chat = false;
            x.is_unique_item = true;
        });


    }
}
