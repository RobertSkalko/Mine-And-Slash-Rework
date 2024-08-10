package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;

public final class MobRarity implements JsonExileRegistry<MobRarity>, IAutoGson<MobRarity>, IAutoLocName {
    public static MobRarity SERIALIZER = new MobRarity();


    public static MobRarity of(String id, String name, int weight, int minlvl, float bonusstatmulti, int affixes, ChatFormatting color, boolean summon) {

        float lootmulti = 1 + (bonusstatmulti * 0.5F);

        MobRarity r = new MobRarity();
        r.text_format = color.name();
        r.id = id;
        r.name = name;
        r.weight = weight;

        r.min_lvl = minlvl;
        r.stat_multi = 1 + bonusstatmulti * 1F;
        r.extra_hp_multi = 1 + bonusstatmulti * 1F;
        r.dmg_multi = 1 + bonusstatmulti * 0.25F;

        r.loot_multi = lootmulti;
        r.exp_multi = lootmulti;

        r.affixes = affixes;

        if (summon) {
            r.stat_multi = 1;
            r.extra_hp_multi = 1;
            r.dmg_multi = 1;
        }

        r.addToSerializables();

        return r;
    }


    public MobRarity setForceCustomHP(int hp) {
        this.force_custom_hp = hp;
        return this;
    }

    public String text_format;

    String name = "";

    public String id = "";

    public int weight = 1000;

    public int min_lvl;

    public int force_custom_hp = -1;

    public float dmg_multi;
    public float extra_hp_multi;
    public float stat_multi;
    public float loot_multi;
    public float exp_multi;
    public int affixes = 0;


    public boolean forcesCustomHp() {
        return force_custom_hp > 0;
    }

    public ChatFormatting textFormatting() {
        try {
            return ChatFormatting.valueOf(text_format);
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        return ChatFormatting.GRAY;
    }

    public int minMobLevelForRandomSpawns() {
        return min_lvl;
    }

    public float DamageMultiplier() {
        return dmg_multi;
    }

    public float ExtraHealthMulti() {
        return extra_hp_multi;
    }

    public float StatMultiplier() {
        return stat_multi;
    }

    public float LootMultiplier() {
        return loot_multi;
    }

    public float expMulti() {
        return exp_multi;
    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MOB_RARITY;
    }

    @Override
    public Class<MobRarity> getClassForSerialization() {
        return MobRarity.class;
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
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rarities;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".mob_rarity." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }
}
