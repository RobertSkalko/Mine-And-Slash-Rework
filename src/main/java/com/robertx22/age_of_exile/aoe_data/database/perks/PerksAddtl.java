package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class PerksAddtl implements ExileRegistryInit {

    @Override
    public void registerAll() {

        Stats.ELE_DOT_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        Stats.ELEMENTAL_ANY_WEAPON_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        PerkBuilder.stat("spell_atk_style_dmg", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.magic), ModType.FLAT));
        PerkBuilder.stat("attack_atk_style_dmg", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.weapon_skill), ModType.FLAT));
        PerkBuilder.bigStat("spell_atk_style_dmg", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.magic), ModType.FLAT));
        PerkBuilder.bigStat("attack_atk_style_dmg", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.weapon_skill), ModType.FLAT));

        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.PROJECTILE_SPEED.get()));
        PerkBuilder.bigStat(new OptScaleExactStat(8, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat("curse_duration", new OptScaleExactStat(6, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.curse), ModType.FLAT));
        PerkBuilder.bigStat("curse_duration_big", new OptScaleExactStat(20, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.curse), ModType.FLAT));

        PerkBuilder.stat("curse_effect", new OptScaleExactStat(3, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.FLAT));
        PerkBuilder.bigStat("curse_effect_big", new OptScaleExactStat(10, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.GOLEM_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.GOLEM_DAMAGE.get(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, new AilmentDuration(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(12, new AilmentDuration(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentDamage(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentDamage(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.stat("shock_proc_chance", new OptScaleExactStat(6, new AilmentProcStat(Ailments.ELECTRIFY), ModType.FLAT));
        PerkBuilder.bigStat("shock_proc_chance_big", new OptScaleExactStat(20, new AilmentProcStat(Ailments.ELECTRIFY), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentChance(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentChance(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, new AilmentDuration(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(12, new AilmentDuration(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentDamage(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentDamage(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.stat("shatter_proc_chance", new OptScaleExactStat(6, new AilmentProcStat(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.bigStat("shatter_proc_chance_big", new OptScaleExactStat(20, new AilmentProcStat(Ailments.FREEZE), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentChance(Ailments.BLEED), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentChance(Ailments.BLEED), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, new AilmentDuration(Ailments.BLEED), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(12, new AilmentDuration(Ailments.BLEED), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentDamage(Ailments.BLEED), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentDamage(Ailments.BLEED), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentChance(Ailments.BURN), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentChance(Ailments.BURN), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, new AilmentDuration(Ailments.BURN), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(12, new AilmentDuration(Ailments.BURN), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentDamage(Ailments.BURN), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentDamage(Ailments.BURN), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentChance(Ailments.POISON), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentChance(Ailments.POISON), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(4, new AilmentDuration(Ailments.POISON), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(12, new AilmentDuration(Ailments.POISON), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, new AilmentDamage(Ailments.POISON), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, new AilmentDamage(Ailments.POISON), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, AllAilmentDamage.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(8, AllAilmentDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, SummonHealth.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(15, SummonHealth.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.GOLEM_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.GOLEM_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(6, Stats.ACCURACY.get(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(20, Stats.ACCURACY.get(), ModType.PERCENT));

        PerkBuilder.stat("mana_on_hit_big", new OptScaleExactStat(10, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)), ModType.FLAT));
        PerkBuilder.stat("health_on_hit_big", new OptScaleExactStat(10, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.hit)), ModType.FLAT));

        PerkBuilder.stat("ms_steal", new OptScaleExactStat(2, Stats.SPELL_MSSTEAL.get(), ModType.FLAT));

        PerkBuilder.stat("chain_damage", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.chaining), ModType.FLAT));
        PerkBuilder.bigStat("chain_damage_big", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.chaining), ModType.FLAT));

        PerkBuilder.stat("trap_damage", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.trap), ModType.FLAT));
        PerkBuilder.bigStat("trap_damage_big", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.trap), ModType.FLAT));

        PerkBuilder.stat("trap_area_dmg", new OptScaleExactStat(3, Stats.TRAP_AREA_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat("trap_area_dmg_big", new OptScaleExactStat(10, Stats.TRAP_AREA_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat("trap_cdr", new OptScaleExactStat(2, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.trap), ModType.FLAT));
        PerkBuilder.bigStat("trap_cdr_big", new OptScaleExactStat(10, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.trap), ModType.FLAT));

        PerkBuilder.stat("song_damage", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.song), ModType.FLAT));
        PerkBuilder.bigStat("song_damage_big", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.song), ModType.FLAT));

        PerkBuilder.stat("song_cdr", new OptScaleExactStat(2, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.song), ModType.FLAT));
        PerkBuilder.bigStat("song_cdr_big", new OptScaleExactStat(10, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.song), ModType.FLAT));

        PerkBuilder.stat("song_effect", new OptScaleExactStat(6, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT));
        PerkBuilder.bigStat("song_effect_big", new OptScaleExactStat(20, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.song), ModType.FLAT));

        PerkBuilder.stat("positive_effect", new OptScaleExactStat(4, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.positive), ModType.FLAT));
        PerkBuilder.bigStat("positive_effect_big", new OptScaleExactStat(12, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.positive), ModType.FLAT));

        PerkBuilder.stat("song_duration", new OptScaleExactStat(6, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.song), ModType.FLAT));
        PerkBuilder.bigStat("song_duration_big", new OptScaleExactStat(20, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.song), ModType.FLAT));

        PerkBuilder.stat("totem_damage", new OptScaleExactStat(3, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT));
        PerkBuilder.bigStat("totem_damage_big", new OptScaleExactStat(10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT));

        PerkBuilder.stat("totem_cdr", new OptScaleExactStat(2, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT));
        PerkBuilder.bigStat("totem_cdr_big", new OptScaleExactStat(10, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT));

        PerkBuilder.stat("totem_duration", new OptScaleExactStat(6, Stats.TOTEM_DURATION.get(), ModType.FLAT));
        PerkBuilder.bigStat("totem_duration_big", new OptScaleExactStat(20, Stats.TOTEM_DURATION.get(), ModType.FLAT));

        PerkBuilder.stat("total_damage", new OptScaleExactStat(2, Stats.TOTAL_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat("total_damage_big", new OptScaleExactStat(8, Stats.TOTAL_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat("armor_dodge", new OptScaleExactStat(2, Armor.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("armor_dodge_big", new OptScaleExactStat(8, Armor.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, DodgeRating.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("dodge_ms", new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, MagicShield.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("dodge_ms_big", new OptScaleExactStat(8, DodgeRating.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(6, MagicShield.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("armor_ms", new OptScaleExactStat(2, Armor.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, MagicShield.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("armor_ms_big", new OptScaleExactStat(8, Armor.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(6, MagicShield.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_mana_regen", new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("hp_mana_regen_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, ManaRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("ms_energy_regen", new OptScaleExactStat(2, MagicShield.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("ms_energy_regen_big", new OptScaleExactStat(6, MagicShield.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, EnergyRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("mana_regen_energy_regen", new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("mana_regen_energy_regen_big", new OptScaleExactStat(8, ManaRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, EnergyRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("mana_regen_hp_regen", new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, HealthRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("mana_regen_hp_regen_big", new OptScaleExactStat(8, ManaRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, HealthRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_regen_energy_regen", new OptScaleExactStat(3, HealthRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("hp_regen_energy_regen_big", new OptScaleExactStat(8, HealthRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, EnergyRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_energy_regen", new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("hp_energy_regen_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, EnergyRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_ms", new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, MagicShield.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("hp_ms_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(6, MagicShield.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_armor", new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, Armor.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat("hp_armor_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(6, Armor.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("hp_dodge_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(8, DodgeRating.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("hp_max_fire_res_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(1, new MaxElementalResist(Elements.Fire), ModType.FLAT));

        PerkBuilder.stat("healing_received", new OptScaleExactStat(4, Stats.HEALING_RECEIVED.get(), ModType.FLAT));
        PerkBuilder.bigStat("healing_received_big", new OptScaleExactStat(10, Stats.HEALING_RECEIVED.get(), ModType.FLAT));
        PerkBuilder.bigStat("hp_healing_received_big", new OptScaleExactStat(6, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(10, Stats.HEALING_RECEIVED.get(), ModType.FLAT));

        PerkBuilder.stat("physical_chaos_damage", new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT),
                new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.FLAT));
        PerkBuilder.bigStat("physical_chaos_damage_big", new OptScaleExactStat(6, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT),
                new OptScaleExactStat(6, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.FLAT));

        PerkBuilder.stat("fire_water_damage", new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT));
        PerkBuilder.bigStat("fire_water_damage_big", new OptScaleExactStat(6, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new OptScaleExactStat(6, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT));

        PerkBuilder.bigStat("dot_dmg_hp_regen_big", new OptScaleExactStat(8, Stats.DOT_DAMAGE.get(), ModType.FLAT),
                new OptScaleExactStat(8, HealthRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.stat(new OptScaleExactStat(1, Stats.MANASTEAL.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(3, Stats.MANASTEAL.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, Stats.INCREASED_LEECH.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(15, Stats.INCREASED_LEECH.get(), ModType.FLAT));

    }
}
