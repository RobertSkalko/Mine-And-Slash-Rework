package com.robertx22.age_of_exile.aoe_data.database.mob_rarities;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

public class MobRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {


        MobRarity normal = new MobRarity();
        normal.affixes = 0;
        normal.stat_multi = 1;
        normal.min_lvl = 0;
        normal.dmg_multi = 1;
        normal.extra_hp_multi = 1F;
        normal.exp_multi = 1;
        normal.loot_multi = 1;
        normal.weight = 1000;
        normal.higher_rar = IRarity.ELITE_ID;

        normal.guid = IRarity.COMMON_ID;
        normal.loc_name = "";
        normal.text_format = ChatFormatting.GRAY.name();

        normal.addToSerializables();

        MobRarity elite = new MobRarity();
        elite.name_add = "\u2605"; // star
        elite.affixes = 1;
        elite.stat_multi = 3;
        elite.min_lvl = 5;
        elite.dmg_multi = 2;
        elite.extra_hp_multi = 3;
        elite.exp_multi = 2.5F;
        elite.loot_multi = 2;
        elite.weight = 100;
        elite.higher_rar = IRarity.CHAMPION_ID;

        elite.guid = IRarity.ELITE_ID;
        elite.loc_name = "Elite";
        elite.text_format = ChatFormatting.GREEN.name();

        elite.addToSerializables();

        MobRarity champ = new MobRarity();
        champ.name_add = "\u2605"; // star
        champ.affixes = 2;
        champ.stat_multi = 3.5F;
        champ.min_lvl = 15;
        champ.dmg_multi = 3;
        champ.extra_hp_multi = 5f;
        champ.exp_multi = 4F;
        champ.loot_multi = 4F;
        champ.weight = 50;

        champ.guid = IRarity.CHAMPION_ID;
        champ.loc_name = "Champion";
        champ.text_format = ChatFormatting.GOLD.name();


        champ.higher_rar = IRarity.BOSS_ID;
        champ.addToSerializables();

        MobRarity boss = new MobRarity();
        boss.name_add = "\u2620"; // skull
        boss.affixes = 2;
        boss.stat_multi = 3.75F;
        boss.min_lvl = 20;
        boss.dmg_multi = 5F;
        boss.extra_hp_multi = 10f;
        boss.exp_multi = 10F;
        boss.loot_multi = 6F;
        boss.weight = 2;

        boss.guid = IRarity.BOSS_ID;
        boss.loc_name = "Boss";
        boss.text_format = ChatFormatting.RED.name();


        boss.boss = true;
        boss.loot_lvl_modifier = 1;
        boss.setBossFields();
        boss.addToSerializables();

    }
}
