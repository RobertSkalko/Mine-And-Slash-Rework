package com.robertx22.mine_and_slash.aoe_data.database.perks;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.stats.EffectStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.stats.types.UnknownStat;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.*;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.PhysicalToElement;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SkillDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShieldHeal;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;

public class Perks implements ExileRegistryInit {

    public static String UNKNOWN_ID = "unknown";

    @Override
    public void registerAll() {

        // todo place on talents
        PerkBuilder.socket();


        for (Spell spell : ExileDB.Spells().getSerializable()) {
            PerkBuilder.spell(spell.GUID());
        }

        PerkBuilder.stat(UNKNOWN_ID, new OptScaleExactStat(1, new UnknownStat(), ModType.FLAT));


        PerkBuilder.stat("int", new OptScaleExactStat(1, DatapackStats.INT, ModType.FLAT));
        PerkBuilder.stat("dex", new OptScaleExactStat(1, DatapackStats.DEX, ModType.FLAT));
        PerkBuilder.stat("str", new OptScaleExactStat(1, DatapackStats.STR, ModType.FLAT));

        PerkBuilder.bigStat("int_big", new OptScaleExactStat(10, DatapackStats.INT, ModType.FLAT));
        PerkBuilder.bigStat("dex_big", new OptScaleExactStat(10, DatapackStats.DEX, ModType.FLAT));
        PerkBuilder.bigStat("str_big", new OptScaleExactStat(10, DatapackStats.STR, ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, AuraCapacity.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, AuraCapacity.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, AuraEffect.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, AuraEffect.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(5, ArmorPenetration.getInstance(), ModType.PERCENT));
        PerkBuilder.bigStat(new OptScaleExactStat(15, ArmorPenetration.getInstance(), ModType.PERCENT));

        PerkBuilder.stat(new OptScaleExactStat(1, BlockChance.getInstance(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(2, BlockChance.getInstance(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, SpellChangeStats.CAST_SPEED.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(-2, SpellChangeStats.MANA_COST.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(-5, SpellChangeStats.MANA_COST.get(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(2, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(2, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT));


        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(5, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT));

   
        PerkBuilder.stat(new OptScaleExactStat(2, OffenseStats.SUMMON_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.SUMMON_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(1, ResourceStats.LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(1, ResourceStats.SPELL_LIFESTEAL.get(), ModType.FLAT));

        PerkBuilder.bigStat(new OptScaleExactStat(4, ResourceStats.LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(4, ResourceStats.SPELL_LIFESTEAL.get(), ModType.FLAT));


        PerkBuilder.stat(new OptScaleExactStat(3, OffenseStats.AREA_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.AREA_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, DamageShield.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, SkillDamage.getInstance(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(4, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT));

        PerkBuilder.stat(new OptScaleExactStat(3, OffenseStats.DOT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.DOT_DAMAGE.get(), ModType.FLAT));

        PerkBuilder.stat("small_positive_effect_increase",
                new OptScaleExactStat(3, EffectStats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.positive))
        );


        PerkBuilder.stat("cdr", new OptScaleExactStat(3, SpellChangeStats.COOLDOWN_REDUCTION.get()));
        PerkBuilder.bigStat("cdr_big", new OptScaleExactStat(10, SpellChangeStats.COOLDOWN_REDUCTION.get()));


        PerkBuilder.stat(new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, MagicShieldRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.stat(new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.PERCENT));


        PerkBuilder.stat("less_aggro", new OptScaleExactStat(-2, SpellChangeStats.THREAT_GENERATED.get(), ModType.FLAT));


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


        PerkBuilder.stat(new OptScaleExactStat(3, SpellChangeStats.PROJECTILE_SPEED.get()));

        PerkBuilder.stat(new OptScaleExactStat(3, OffenseStats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, OffenseStats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.stat(new OptScaleExactStat(3, OffenseStats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.FLAT));

        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.bigStat(new OptScaleExactStat(10, OffenseStats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.FLAT));


        PerkBuilder.stat("mana_on_hit", new OptScaleExactStat(1, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)), ModType.FLAT));
        PerkBuilder.stat("health_on_hit", new OptScaleExactStat(1, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.hit)), ModType.FLAT));


        for (PhysicalToElement x : PhysicalToElement.MAP.MAP.values()) {
            PerkBuilder.stat(new OptScaleExactStat(10, x, ModType.FLAT));
            PerkBuilder.bigStat(new OptScaleExactStat(25, x, ModType.FLAT));
        }

        OffenseStats.ELEMENTAL_SPELL_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                    PerkBuilder.stat(x.GUID() + "_and_dot", new OptScaleExactStat(1, x, ModType.FLAT), new OptScaleExactStat(3, OffenseStats.ELE_DOT_DAMAGE.get(x.getElement()), ModType.FLAT));
                });

        OffenseStats.WEAPON_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });


        ResourceStats.RESOURCE_ON_KILL.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(10, x, ModType.FLAT));
                });

        OffenseStats.ELEMENTAL_WEAPON_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(3, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        OffenseStats.ELEMENTAL_DAMAGE.getAll()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        new ElementalResist(Elements.Shadow).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(4, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));

                });

        new ElementalPenetration(Elements.Shadow).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(new OptScaleExactStat(4, x, ModType.FLAT));
                    PerkBuilder.bigStat(new OptScaleExactStat(10, x, ModType.FLAT));
                });

        new BonusAttackDamage(Elements.Shadow).generateAllPossibleStatVariations()
                .forEach(x -> {
                    PerkBuilder.stat(x.GUID(), new OptScaleExactStat(2, x, ModType.PERCENT));

                });

    }
}
