package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {


        GearRarity common = new GearRarity();
        common.min_affixes = 1;
        common.weight = 5000;
        common.item_tier_power = 1;
        common.backpack_slots = 9;
        common.item_tier = 0;
        common.pot = new GearRarity.Potential(100, 0.5F);
        common.item_value_multi = 1;
        common.item_model_data_num = 1;
        common.max_sockets = 0;
        common.socket_chance = 0;
        common.higher_rar = IRarity.UNCOMMON;
        common.skill_gem_percents = new MinMax(0, 10);
        common.setCommonFields();
        common.addToSerializables();

        GearRarity uncommon = new GearRarity();
        uncommon.max_sockets = 1;
        uncommon.socket_chance = 5;
        uncommon.backpack_slots = 2 * 9;
        uncommon.pot = new GearRarity.Potential(100, 1);
        uncommon.min_affixes = 2;
        uncommon.weight = 2000;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.skill_gem_percents = new MinMax(10, 20);
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.max_sockets = 2;
        rare.socket_chance = 15;
        rare.backpack_slots = 3 * 9;
        rare.lootable_gear_tier = GearRarity.LootableGearTier.MID;
        rare.pot = new GearRarity.Potential(200, 1.5F);
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.min_affixes = 3;
        rare.weight = 500;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.skill_gem_percents = new MinMax(20, 40);
        rare.setRareFields();
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.max_sockets = 3;
        epic.socket_chance = 20;
        epic.backpack_slots = 4 * 9;
        epic.lootable_gear_tier = GearRarity.LootableGearTier.MID;
        epic.pot = new GearRarity.Potential(250, 1.5F);
        epic.min_affixes = 4;
        epic.weight = 100;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.higher_rar = IRarity.LEGENDARY_ID;
        epic.skill_gem_percents = new MinMax(40, 60);
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity legendary = new GearRarity();
        legendary.max_sockets = 3;
        legendary.socket_chance = 30;
        legendary.backpack_slots = 5 * 9;
        legendary.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
        legendary.pot = new GearRarity.Potential(300, 1.75F);
        legendary.min_affixes = 5;
        legendary.weight = 50;
        legendary.item_tier = 4;
        legendary.item_model_data_num = 5;
        legendary.item_tier_power = 2;
        legendary.item_value_multi = 2;
        legendary.announce_in_chat = true;
        legendary.higher_rar = IRarity.MYTHIC_ID;
        legendary.skill_gem_percents = new MinMax(60, 80);
        legendary.setLegendFields();
        legendary.addToSerializables();


        GearRarity mythic = new GearRarity();
        mythic.max_sockets = 3;
        mythic.backpack_slots = 6 * 9;
        mythic.socket_chance = 50;
        mythic.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
        mythic.pot = new GearRarity.Potential(350, 2F);
        mythic.min_affixes = 6;
        mythic.weight = 25;
        mythic.item_tier = 5;
        mythic.item_model_data_num = 6;
        mythic.item_tier_power = 3;
        mythic.item_value_multi = 3;
        mythic.announce_in_chat = true;
        mythic.skill_gem_percents = new MinMax(80, 100);
        mythic.setMythicFields();
        mythic.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.max_sockets = 3;
        unique.socket_chance = 20;
        unique.pot = new GearRarity.Potential(100, 1);
        unique.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
        unique.min_affixes = 0;
        unique.weight = 25;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.item_tier = 5;
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

        GearRarity runeword = new GearRarity();
        runeword.max_sockets = 3;
        runeword.socket_chance = 50;
        runeword.lootable_gear_tier = GearRarity.LootableGearTier.HIGH;
        runeword.pot = new GearRarity.Potential(100, 1);
        runeword.min_affixes = 0;
        runeword.weight = 0;
        runeword.item_tier_power = 2;
        runeword.item_value_multi = 2;
        runeword.item_tier = 5;
        runeword.setRunewordFields();
        runeword.addToSerializables();
        runeword.announce_in_chat = true;
        runeword.is_unique_item = true;

    }
}
