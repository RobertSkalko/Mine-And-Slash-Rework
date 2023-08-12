package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysicalToElement;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldHeal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCostReduction;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class Perks implements ExileRegistryInit {

    @Override
    public void registerAll() {
        PerkBuilder.stat(new OptScaleExactStat(1, new UnknownStat(), ModType.FLAT));


        PerkBuilder.stat("int", new OptScaleExactStat(1, DatapackStats.INT, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, DatapackStats.DEX, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, DatapackStats.STR, ModType.FLAT));

        PerkBuilder.bigStat("int_big", new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT));
        PerkBuilder.bigStat("dex_big", new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT));
        PerkBuilder.bigStat("str_big", new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, AuraCostReduction.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, AuraCostReduction.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, AuraEffect.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, AuraEffect.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, ArmorPenetration.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, ArmorPenetration.getInstance(), ModType.PERCENT));

        PerkBuilder.stat(new OptScaleExactStat(3, BlockChance.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(6, BlockChance.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CAST_SPEED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(-2, Stats.MANA_COST.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(-5, Stats.MANA_COST.get(), ModType.FLAT));

        //  PerkBuilder.stat(new OptScaleExactStat(3, Stats.ATTACK_SPEED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.CRIT_CHANCE.get(), ModType.FLAT));


        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(5, Stats.CRIT_CHANCE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(1, Stats.LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, Stats.SPELL_LIFESTEAL.get(), ModType.FLAT));

        PerkBuilder.bigStat(new OptScaleExactStat(4, Stats.LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(4, Stats.SPELL_LIFESTEAL.get(), ModType.FLAT));

   
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.AREA_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.INCREASED_AREA.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.AREA_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.INCREASED_AREA.get(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(2, Stats.CRIT_CHANCE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, DamageShield.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, SkillDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, Stats.HEAL_STRENGTH.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.HEAL_STRENGTH.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.DOT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.DOT_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat("small_positive_effect_increase",
                new OptScaleExactStat(3, Stats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.positive))
        );


        PerkBuilder.stat("cdr", new OptScaleExactStat(3, Stats.COOLDOWN_REDUCTION.get()));
        PerkBuilder.bigStat("cdr_big", new OptScaleExactStat(10, Stats.COOLDOWN_REDUCTION.get()));


        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, MagicShieldRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.PERCENT));


        PerkBuilder.stat("less_aggro", new OptScaleExactStat(-2, Stats.THREAT_GENERATED.get(), ModType.FLAT));


        PerkBuilder.stat("hp_dodge_small", new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT));

        PerkBuilder.stat("hp_mana_small", new OptScaleExactStat(3, Mana.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("hp_mana_big", new OptScaleExactStat(5, Mana.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(5, Health.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("mana_ms_percent_small", new OptScaleExactStat(2, Mana.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(2, MagicShield.getInstance(), ModType.PERCENT));


        PerkBuilder.stat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(3, Armor.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(2, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(2, MagicShield.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(3, Mana.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(3, Energy.getInstance(), ModType.PERCENT));


        PerkBuilder.bigStat(new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Armor.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, MagicShield.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Mana.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Energy.getInstance(), ModType.PERCENT));


        PerkBuilder.bigStat(new OptScaleExactStat(10, MagicShieldRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("hp_hp_regen_big", new OptScaleExactStat(5, Health.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.PERCENT));

        PerkBuilder.bigStat("mana_ene_regen_small", new OptScaleExactStat(3, ManaRegen.getInstance(), ModType.PERCENT),
                new OptScaleExactStat(3, HealthRegen.getInstance(), ModType.PERCENT));


        PerkBuilder.stat(new OptScaleExactStat(5, MagicShieldHeal.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, Stats.PROJECTILE_SPEED.get()));

        PerkBuilder.stat(new OptScaleExactStat(3, Stats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.FLAT));

        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.FLAT));


        PerkBuilder.stat("mana_on_hit", new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)), ModType.FLAT));
        PerkBuilder.stat("health_on_hit", new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack)), ModType.FLAT));


        for (PhysicalToElement x : PhysicalToElement.MAP.MAP.values()) {
            PerkBuilder.stat(new OptScaleExactStat(10, x, ModType.FLAT));
            PerkBuilder.bigStat(new OptScaleExactStat(25, x, ModType.FLAT));
        }

        Stats.ELEMENTAL_SPELL_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                    PerkBuilder.stat(x.GUID() + "_and_dot", new OptScaleExactStat(1, x, ModType.FLAT), new OptScaleExactStat(3, Stats.ELE_DOT_DAMAGE.get(x.getElement()), ModType.FLAT));
                });

        Stats.WEAPON_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });


        Stats.RESOURCE_ON_KILL.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(10, x, ModType.FLAT));
                });

        Stats.ELEMENTAL_WEAPON_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));

                });

        Stats.ELEMENTAL_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        new ElementalResist(Elements.Chaos).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(4, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));

                });

        new ElementalPenetration(Elements.Chaos).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(4, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        new BonusAttackDamage(Elements.Chaos).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.PERCENT));

                });

    }
}
