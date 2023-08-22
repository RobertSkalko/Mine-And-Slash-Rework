package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.boss_spell.SummonThornMobs;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.league.HarvestLeague;
import com.robertx22.age_of_exile.database.data.loot_chest.GearLootChest;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.empty_entries.EmptyStat;
import com.robertx22.age_of_exile.database.registrators.StatsRegister;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import net.minecraft.ChatFormatting;

public class ExileDBInit {

    public static void registerAllItems() {
        try {
            registerAllNonDatapackEntries();
        } catch (ExceptionInInitializerError e) {
            // leave this, once this error happened and we don't know why. this is to know the cause if it happens again
            e.printStackTrace();
            e.getCause()
                    .printStackTrace();
        }
    }

    private static void registerAllNonDatapackEntries() {
        Ailments.init();
        new StatsRegister().registerAll();


    }

    public static void initRegistries() {
        // data pack ones

        Database.addRegistry(new RarityRegistryContainer<>(ExileRegistryTypes.GEAR_RARITY, new GearRarity()).setIsDatapack());
        Database.addRegistry(new RarityRegistryContainer<MobRarity>(ExileRegistryTypes.MOB_RARITY, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_SLOT, new GearSlot("", "", SlotFamily.NONE, 0, -1, 0)).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEAR_TYPE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNEWORDS, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.EXILE_EFFECT, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.AFFIX, EmptyAffix.getInstance()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.MOB_AFFIX, new MobAffix("empty", "empty", ChatFormatting.AQUA)).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.UNIQUE_GEAR, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GEM, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.RUNE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL, Spell.SERIALIZER).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.PERK, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.TALENT_TREE, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SPELL_SCHOOL, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.BASE_STATS, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.GAME_BALANCE, new GameBalanceConfig()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.VALUE_CALC, new ValueCalculation()).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_EFFECT, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT_CONDITION, null).setIsDatapack());
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DIMENSION_CONFIGS, DimensionConfig.DefaultExtra()
                ).logAdditions()
                        .setIsDatapack()
                        .dontErrorMissingEntriesOnAccess()
        );
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.ENTITY_CONFIGS, new EntityConfig("", 0)).logAdditions()
                .setIsDatapack());

        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.STAT, EmptyStat.getInstance()));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.AILMENT, null));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.CURRENCY_ITEMS, null));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.SUPPORT_GEM, null));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.AURA, null));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.MAP_AFFIX, null));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.BOSS_SPELL, new SummonThornMobs()));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.LEAGUE_MECHANIC, new HarvestLeague()));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.LOOT_CHEST, new GearLootChest()));
        Database.addRegistry(new ExileRegistryContainer<>(ExileRegistryTypes.DUNGEON, null));


    }
}
