package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.content.ubers.UberBossArena;
import com.robertx22.age_of_exile.database.Serializers;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.auto_item.AutoItem;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.chaos_stats.ChaosStat;
import com.robertx22.age_of_exile.database.data.custom_item.CustomItem;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.map_affix.MapAffix;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuff;
import com.robertx22.age_of_exile.database.data.prophecy.ProphecyModifier;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayer;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.maps.dungeon_reg.Dungeon;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import com.robertx22.library_of_exile.registry.loaders.BaseDataPackLoader;
import net.minecraft.ChatFormatting;

public class ExileRegistryTypes {

    public static ExileRegistryType GEAR_RARITY = ExileRegistryType.register(SlashRef.MODID, "gear_rarity", 0, GearRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_LAYER = ExileRegistryType.register(SlashRef.MODID, "stat_layer", 0, StatLayer.SERIALIZER, SyncTime.ON_LOGIN);

    public static ExileRegistryType STAT = ExileRegistryType.register(SlashRef.MODID, "stat", 1, BaseDatapackStat.MAIN_SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SLOT = ExileRegistryType.register(SlashRef.MODID, "gear_slot", 3, GearSlot.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType EXILE_EFFECT = ExileRegistryType.register(SlashRef.MODID, "exile_effect", 3, ExileEffect.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_TYPE = ExileRegistryType.register(SlashRef.MODID, "base_gear_types", 4, BaseGearType.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEM = ExileRegistryType.register(SlashRef.MODID, "gems", 6, Gem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNE = ExileRegistryType.register(SlashRef.MODID, "runes", 7, Rune.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_AFFIX = ExileRegistryType.register(SlashRef.MODID, "mob_affix", 8, new MobAffix("empty", "empty", ChatFormatting.AQUA, Affix.Type.prefix), SyncTime.ON_LOGIN);
    public static ExileRegistryType AFFIX = ExileRegistryType.register(SlashRef.MODID, "affixes", 10, EmptyAffix.getInstance(), SyncTime.ON_LOGIN);
    public static ExileRegistryType UNIQUE_GEAR = ExileRegistryType.register(SlashRef.MODID, "unique_gears", 11, UniqueGear.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNEWORDS = ExileRegistryType.register(SlashRef.MODID, "runeword", 12, RuneWord.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType CURRENCY_ITEMS = ExileRegistryType.register(new ExileRegistryType(SlashRef.MODID, "currency_item", 12, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    });
    public static ExileRegistryType DIMENSION_CONFIGS = ExileRegistryType.register(SlashRef.MODID, "dimension_config", 13, DimensionConfig.EMPTY, SyncTime.ON_LOGIN);
    public static ExileRegistryType ENTITY_CONFIGS = ExileRegistryType.register(SlashRef.MODID, "entity_config", 14, Serializers.ENTITY_CONFIG_SER, SyncTime.NEVER);
    public static ExileRegistryType SPELL = ExileRegistryType.register(SlashRef.MODID, "spells", 17, Spell.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PERK = ExileRegistryType.register(SlashRef.MODID, "perk", 18, Perk.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType TALENT_TREE = ExileRegistryType.register(SlashRef.MODID, "talent_tree", 19, TalentTree.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType BASE_STATS = ExileRegistryType.register(SlashRef.MODID, "base_stats", 22, BaseStatsConfig.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType VALUE_CALC = ExileRegistryType.register(SlashRef.MODID, "value_calc", 40, ValueCalculation.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_EFFECT = ExileRegistryType.register(SlashRef.MODID, "stat_effect", 32, StatEffect.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType STAT_CONDITION = ExileRegistryType.register(SlashRef.MODID, "stat_condition", 32, StatCondition.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType GAME_BALANCE = ExileRegistryType.register(SlashRef.MODID, "game_balance", 26, GameBalanceConfig.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SPELL_SCHOOL = ExileRegistryType.register(SlashRef.MODID, "spell_school", 26, SpellSchool.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType AILMENT = ExileRegistryType.register(SlashRef.MODID, "ailment", 27, null, SyncTime.NEVER);
    public static ExileRegistryType AURA = ExileRegistryType.register(SlashRef.MODID, "aura", 28, AuraGem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType SUPPORT_GEM = ExileRegistryType.register(SlashRef.MODID, "support_gem", 29, SupportGem.SER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MAP_AFFIX = ExileRegistryType.register(SlashRef.MODID, "map_affix", 30, MapAffix.SER, SyncTime.ON_LOGIN);
    public static ExileRegistryType BOSS_SPELL = ExileRegistryType.register(SlashRef.MODID, "boss", 31, null, SyncTime.NEVER);

    public static ExileRegistryType LEAGUE_MECHANIC = ExileRegistryType.register(new ExileRegistryType(SlashRef.MODID, "league_mechanic", 32, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    });
    public static ExileRegistryType LOOT_CHEST = ExileRegistryType.register(new ExileRegistryType(SlashRef.MODID, "loot_chest", 33, null, SyncTime.NEVER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    });

    public static ExileRegistryType DUNGEON = ExileRegistryType.register(SlashRef.MODID, "dungeon", 34, Dungeon.SERIALIZER, SyncTime.ON_LOGIN); // todo does the client need this?
    public static ExileRegistryType PROFESSION = ExileRegistryType.register(SlashRef.MODID, "profession", 35, Profession.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RECIPE = ExileRegistryType.register(SlashRef.MODID, "profession_recipe", 35, ProfessionRecipe.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_BUFF = ExileRegistryType.register(SlashRef.MODID, "stat_buff", 36, StatBuff.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType AUTO_ITEM = ExileRegistryType.register(SlashRef.MODID, "auto_item", 37, AutoItem.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType CUSTOM_ITEM = ExileRegistryType.register(SlashRef.MODID, "custom_item", 38, CustomItem.SERIALIZER, SyncTime.NEVER);
    public static ExileRegistryType MOB_RARITY = ExileRegistryType.register(SlashRef.MODID, "mob_rarity", 39, MobRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PROPHECY_MODIFIER = ExileRegistryType.register(SlashRef.MODID, "prophecy_modifier", 40, ProphecyModifier.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType PROPHECY_START = ExileRegistryType.register(SlashRef.MODID, "prophecy_start", 41, null, SyncTime.NEVER);
    public static ExileRegistryType CHAOS_STAT = ExileRegistryType.register(SlashRef.MODID, "chaos_stat", 42, ChaosStat.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT_COMPAT = ExileRegistryType.register(SlashRef.MODID, "stat_compat", 44, StatCompat.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType UBER_BOSS = ExileRegistryType.register(SlashRef.MODID, "uber_boss", 45, UberBossArena.SERIALIZER, SyncTime.ON_LOGIN);


}
