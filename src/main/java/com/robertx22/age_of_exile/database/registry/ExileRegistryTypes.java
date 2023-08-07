package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.Serializers;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import com.robertx22.library_of_exile.registry.loaders.BaseDataPackLoader;
import net.minecraft.ChatFormatting;

public class ExileRegistryTypes {

    public static ExileRegistryType GEAR_RARITY = ExileRegistryType.register(SlashRef.MODID, "gear_rarity", 0, GearRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_RARITY = ExileRegistryType.register(SlashRef.MODID, "mob_rarity", 0, MobRarity.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType STAT = ExileRegistryType.register(SlashRef.MODID, "stat", 1, BaseDatapackStat.MAIN_SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_SLOT = ExileRegistryType.register(SlashRef.MODID, "gear_slot", 3, GearSlot.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType EXILE_EFFECT = ExileRegistryType.register(SlashRef.MODID, "exile_effect", 3, ExileEffect.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEAR_TYPE = ExileRegistryType.register(SlashRef.MODID, "base_gear_types", 4, BaseGearType.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType GEM = ExileRegistryType.register(SlashRef.MODID, "gems", 6, Gem.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType RUNE = ExileRegistryType.register(SlashRef.MODID, "runes", 7, Rune.SERIALIZER, SyncTime.ON_LOGIN);
    public static ExileRegistryType MOB_AFFIX = ExileRegistryType.register(SlashRef.MODID, "mob_affix", 8, new MobAffix("empty", "empty", ChatFormatting.AQUA), SyncTime.ON_LOGIN);
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
    public static ExileRegistryType AURA = ExileRegistryType.register(SlashRef.MODID, "aura", 28, null, SyncTime.NEVER);
    public static ExileRegistryType SUPPORT_GEM = ExileRegistryType.register(SlashRef.MODID, "support_gem", 29, null, SyncTime.NEVER);

}
