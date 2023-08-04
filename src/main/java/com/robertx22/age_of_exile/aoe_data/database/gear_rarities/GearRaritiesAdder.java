package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
        common.min_affixes = 0;
        common.weight = 5000;
        common.item_tier_power = 1;
        common.item_tier = 0;
        common.item_value_multi = 1;
        common.item_model_data_num = 1;
        common.higher_rar = IRarity.UNCOMMON;

        common.setCommonFields();
        common.addToSerializables();

        GearRarity uncommon = new GearRarity();
        uncommon.potential = 200;
        uncommon.min_affixes = 1;
        uncommon.weight = 2000;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.potential = 300;
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.min_affixes = 3;
        rare.weight = 500;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.setRareFields();
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.potential = 400;
        epic.min_affixes = 4;
        epic.weight = 100;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.higher_rar = IRarity.LEGENDARY_ID;
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity legendary = new GearRarity();
        legendary.potential = 500;
        legendary.min_affixes = 5;
        legendary.weight = 50;
        legendary.item_tier = 4;
        legendary.item_model_data_num = 5;
        legendary.item_tier_power = 2;
        legendary.item_value_multi = 2;
        legendary.announce_in_chat = true;
        legendary.higher_rar = IRarity.MYTHIC_ID;
        legendary.setLegendFields();
        legendary.addToSerializables();


        GearRarity mythic = new GearRarity();
        mythic.potential = 1000;
        mythic.min_affixes = 6;
        mythic.weight = 25;
        mythic.item_tier = 5;
        mythic.item_model_data_num = 6;
        mythic.item_tier_power = 3;
        mythic.item_value_multi = 3;
        mythic.announce_in_chat = true;
        mythic.setMythicFields();
        mythic.addToSerializables();

        GearRarity unique = new GearRarity();
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
