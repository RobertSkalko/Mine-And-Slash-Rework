package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;

public class BeneficialEffects implements ExileRegistryInit {

    public static EffectCtx VAMPIRIC_BLOOD = new EffectCtx("vamp_blood", "Vamp Blood", 3, Elements.Fire, EffectType.beneficial);
    public static EffectCtx DRACONIC_BLOOD = new EffectCtx("draconic_blood", "Dragon Blood", 4, Elements.Fire, EffectType.beneficial);
    public static EffectCtx REGENERATE = new EffectCtx("regenerate", "Nature Balm", 5, Elements.Chaos, EffectType.beneficial);
    public static EffectCtx VALOR = new EffectCtx("valor", "Valor", 8, Elements.Physical, EffectType.beneficial);
    public static EffectCtx PERSEVERANCE = new EffectCtx("perseverance", "Perseverance", 9, Elements.Physical, EffectType.beneficial);
    public static EffectCtx VIGOR = new EffectCtx("vigor", "Vigor", 10, Elements.Physical, EffectType.beneficial);
    public static EffectCtx TAUNT_STANCE = new EffectCtx("taunt_stance", "Taunt Stance", 11, Elements.Physical, EffectType.beneficial);
    public static EffectCtx UNDYING_WILL = new EffectCtx("undying_will", "Undying Will", 11, Elements.Physical, EffectType.beneficial);
    public static EffectCtx MAGE_CIRCLE = new EffectCtx("mage_circle", "Mage Circle", 12, Elements.Elemental, EffectType.beneficial);

    @Override
    public void registerAll() {


        ExileEffectBuilder.of(DRACONIC_BLOOD)
                .stat(2, 4, Stats.SPELL_LIFESTEAL.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.positive)
                .build();

        ExileEffectBuilder.of(VAMPIRIC_BLOOD)
                .stat(2, 5, Stats.LIFESTEAL.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.positive)
                .build();

        ExileEffectBuilder.of(MAGE_CIRCLE)
                .stat(10, 25, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
                .stat(5, 20, SkillDamage.getInstance(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.offensive)
                .build();


        ExileEffectBuilder.of(TAUNT_STANCE)
                .stat(10, 25, Stats.THREAT_GENERATED.get())
                .stat(25, 50, Stats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                                .setTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                                .tickRequirement(60D))
                        .buildForEffect())
                .maxStacks(1)
                .build();

        ExileEffectBuilder.of(UNDYING_WILL)
                .stat(-25, -75, Stats.DAMAGE_RECEIVED.get())
                .stat(1, 2, HealthRegen.getInstance())
                .maxStacks(1)
                .build();

        ExileEffectBuilder.of(VIGOR)
                .stat(0.25F, 0.5F, HealthRegen.getInstance())
                .stat(0.25F, 0.5F, ManaRegen.getInstance())
                .stat(10, 10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song)
                .build();

        ExileEffectBuilder.of(PERSEVERANCE)
                .stat(5, 10, new ElementalResist(Elements.Physical))
                .stat(5, 10, new ElementalResist(Elements.Chaos))
                .stat(5, 10, new ElementalResist(Elements.Fire))
                .stat(5, 10, new ElementalResist(Elements.Cold))
                .stat(5, 10, new ElementalResist(Elements.Lightning))
                .stat(10, 10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song, EffectTags.defensive)
                .build();

        ExileEffectBuilder.of(VALOR)
                .stat(5, 10, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
                .stat(2, 5, Stats.CRIT_CHANCE.get(), ModType.FLAT)
                .stat(10, 10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song, EffectTags.offensive)
                .build();


        ExileEffectBuilder.of(REGENERATE)
                .maxStacks(3)
                .addTags(EffectTags.heal_over_time)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(SpellCalcs.NATURE_BALM))
                                .setTarget(TargetSelector.TARGET.create())
                                .tickRequirement(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                                .tickRequirement(20D))
                        .buildForEffect())
                .build();


    }
}
