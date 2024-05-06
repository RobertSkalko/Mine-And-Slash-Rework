package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.*;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.BlockChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysicalToElement;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.database.data.stats.types.summon.GolemSpellChance;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GameChangerPerksAddtl implements ExileRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("curse_master", "Curse Master",
                new OptScaleExactStat(20, OffenseStats.DAMAGE_TO_CURSED.get(), ModType.MORE),
                new OptScaleExactStat(-20, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.MORE)
        );

        PerkBuilder.gameChanger("minion_catapult", "Minion Explosion",
                new OptScaleExactStat(20, OffenseStats.SUMMON_DAMAGE.get(), ModType.MORE),
                new OptScaleExactStat(-33, SummonHealth.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("conservation", "Conservation",
                new OptScaleExactStat(20, MagicShieldRegen.getInstance(), ModType.MORE),
                new OptScaleExactStat(-50, HealthRegen.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("masochism", "Masochism",
                new OptScaleExactStat(30, OffenseStats.DAMAGE_WHEN_LOW_HP.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("shade", "Looming Shade",
                new OptScaleExactStat(10, ResourceStats.DOT_LIFESTEAL.get(), ModType.MORE),
                new OptScaleExactStat(-20, OffenseStats.DOT_DAMAGE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("golemancer", "Golemancer",
                new OptScaleExactStat(50, SummonHealth.getInstance(), ModType.MORE),
                new OptScaleExactStat(50, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.golem), ModType.MORE),
                new OptScaleExactStat(25, GolemSpellChance.getInstance(), ModType.FLAT),
                new OptScaleExactStat(-2, SpellChangeStats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("energy_claws", "Energy Claws",
                new OptScaleExactStat(5, ResourceStats.SPELL_MSSTEAL.get(), ModType.FLAT),
                new OptScaleExactStat(-90, MagicShieldRegen.getInstance(), ModType.MORE),
                new OptScaleExactStat(-80, ResourceStats.LIFESTEAL.get(), ModType.MORE),
                new OptScaleExactStat(-80, ResourceStats.SPELL_LIFESTEAL.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("ghast", "Ghast",
                new OptScaleExactStat(5, DatapackStats.DODGE_PER_MS, ModType.FLAT),
                new OptScaleExactStat(-25, MagicShield.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("acrobat", "Acrobat",
                new OptScaleExactStat(35, new ElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.gameChanger("vital_point", "Vital Points",
                new OptScaleExactStat(30, OffenseStats.DAMAGE_WHEN_TARGET_IS_FULL_HP.get(), ModType.FLAT),
                new OptScaleExactStat(-50, OffenseStats.CRIT_CHANCE.get(), ModType.MORE)
        );

        PerkBuilder.gameChanger("projection", "Projection",
                new OptScaleExactStat(25, DodgeRating.getInstance(), ModType.MORE),
                new OptScaleExactStat(-15, Health.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("egoist", "Egoist",
                new OptScaleExactStat(30, AuraEffect.getInstance(), ModType.FLAT),
                new OptScaleExactStat(-40, AuraCapacity.getInstance(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("wind_dancer", "Wind Dancer",
                new OptScaleExactStat(25, SpellChangeStats.PROJECTILE_SPEED.get(), ModType.FLAT),
                new OptScaleExactStat(-30, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("versatility", "Versatility",
                new OptScaleExactStat(25, BlockChance.getInstance(), ModType.MORE),
                new OptScaleExactStat(-5, new MaxElementalResist(Elements.Elemental), ModType.FLAT)
        );

        PerkBuilder.gameChanger("fortified_reflexes", "Fortified Reflexes",
                new OptScaleExactStat(5, DatapackStats.ARMOR_PER_DODGE, ModType.FLAT),
                new OptScaleExactStat(-25, DodgeRating.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("bloody_dance", "Bloody Dance",
                new OptScaleExactStat(33, new AilmentDamage(Ailments.BLEED), ModType.FLAT),
                new OptScaleExactStat(-50, new AilmentDuration(Ailments.BLEED), ModType.FLAT)
        );

        PerkBuilder.gameChanger("collateral", "Collateral",
                new OptScaleExactStat(20, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.weapon_skill), ModType.MORE),
                new OptScaleExactStat(-50, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("diamond_grip", "Diamond Grip",
                new OptScaleExactStat(1, DatapackStats.PROJ_DMG_PER_STR, ModType.FLAT)
        );

        PerkBuilder.gameChanger("diamond_will", "Diamond Will",
                new OptScaleExactStat(1, DatapackStats.SPELL_DMG_PER_STR, ModType.FLAT)
        );

        PerkBuilder.gameChanger("reckless_defender", "Reckless Defender",
                new OptScaleExactStat(-10, DefenseStats.DAMAGE_RECEIVED.get(), ModType.FLAT),
                new OptScaleExactStat(-50, Armor.getInstance(), ModType.MORE),
                new OptScaleExactStat(-50, BlockChance.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("wandering_bard", "Wandering Bard",
                new OptScaleExactStat(50, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT),
                new OptScaleExactStat(33, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.song), ModType.FLAT),
                new OptScaleExactStat(-50, EffectStats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.song), ModType.FLAT)
        );

        PerkBuilder.gameChanger("witchs_brew", "Witch's Brew",
                new OptScaleExactStat(1, DatapackStats.HP_REGEN_PER_MS_REGEN, ModType.FLAT),
                new OptScaleExactStat(-50, HealthRegen.getInstance(), ModType.MORE),
                new OptScaleExactStat(-50, ResourceStats.LEECH_CAP.get(ResourceType.health), ModType.MORE)
        );

        PerkBuilder.gameChanger("nether_enthusiast", "Nether Enthusiast",
                new OptScaleExactStat(50, new PhysicalToElement(Elements.Fire), ModType.FLAT),
                new OptScaleExactStat(-50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE),
                new OptScaleExactStat(-50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.MORE)
        );

        PerkBuilder.gameChanger("glancing_strikes", "Glancing Strikes",
                new OptScaleExactStat(100, BlockChance.getInstance(), ModType.MORE),
                new OptScaleExactStat(25, DefenseStats.DAMAGE_RECEIVED.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("performer", "Performer",
                new OptScaleExactStat(20, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.song), ModType.MORE),
                new OptScaleExactStat(-10, SpellChangeStats.CAST_SPEED.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("heretic", "Heretic",
                new OptScaleExactStat(50, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                new OptScaleExactStat(20, SpellChangeStats.TOTEM_DURATION.get(), ModType.MORE),
                new OptScaleExactStat(-50, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.weapon_skill), ModType.FLAT)
        );

        PerkBuilder.gameChanger("necromantic_defenses", "Necromantic Defenses",
                new OptScaleExactStat(100, DatapackStats.SUMMON_HP_PER_HP, ModType.FLAT),
                new OptScaleExactStat(-25, Health.getInstance(), ModType.MORE)
        );

        PerkBuilder.gameChanger("generosity", "Generosity",
                new OptScaleExactStat(25, EffectStats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.positive), ModType.FLAT)
        );

        PerkBuilder.gameChanger("defender", "Defender",
                new OptScaleExactStat(10, Health.getInstance(), ModType.MORE),
                new OptScaleExactStat(10, MagicShield.getInstance(), ModType.MORE),
                new OptScaleExactStat(50, SpellChangeStats.THREAT_GENERATED.get(), ModType.FLAT)
        );

        PerkBuilder.gameChanger("focused_magician", "Focused Magician",
                new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.MORE),
                new OptScaleExactStat(6, SpellChangeStats.CAST_SPEED.get(), ModType.MORE),
                new OptScaleExactStat(-100, DodgeRating.getInstance(), ModType.MORE)
        );

    }

}
