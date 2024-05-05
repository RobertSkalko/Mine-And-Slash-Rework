package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.*;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;

// todo try make each reg init have a registry type, and sort the register order based on the order of the registry type instead of manually in code?
// make them all have 2 methods, loadclass and registerall
public class ModEffects implements ExileRegistryInit {

    public static List<EffectCtx> ALL = new ArrayList<>();

    public static EffectCtx ICE_GOLEM_BUFF = new EffectCtx("ice_golem_buff", "Ice Golem Buff", Elements.Cold, EffectType.beneficial);
    public static EffectCtx LIGHTNING_GOLEM_BUFF = new EffectCtx("lightning_golem_buff", "Lightning Golem Buff", Elements.Nature, EffectType.beneficial);
    public static EffectCtx FIRE_GOLEM_BUFF = new EffectCtx("fire_golem_buff", "Fire Golem Buff", Elements.Fire, EffectType.beneficial);

    public static EffectCtx POWER_CHARGE = new EffectCtx("power_charge", "Power Charge", Elements.Physical, EffectType.beneficial);
    public static EffectCtx FRENZY_CHARGE = new EffectCtx("frenzy_charge", "Frenzy Charge", Elements.Physical, EffectType.beneficial);
    public static EffectCtx ENDURANCE_CHARGE = new EffectCtx("endurance_charge", "Endurance Charge", Elements.Physical, EffectType.beneficial);

    public static EffectCtx VAMPIRIC_BLOOD = new EffectCtx("vamp_blood", "Vamp Blood", Elements.Fire, EffectType.beneficial);
    public static EffectCtx DRACONIC_BLOOD = new EffectCtx("draconic_blood", "Dragon Blood", Elements.Fire, EffectType.beneficial);
    public static EffectCtx REJUVENATE = new EffectCtx("rejuvenation", "Rejuvenation", Elements.Physical, EffectType.beneficial);
    public static EffectCtx VALOR = new EffectCtx("valor", "Valor", Elements.Physical, EffectType.beneficial);
    public static EffectCtx PERSEVERANCE = new EffectCtx("perseverance", "Perseverance", Elements.Physical, EffectType.beneficial);
    public static EffectCtx VIGOR = new EffectCtx("vigor", "Vigor", Elements.Physical, EffectType.beneficial);
    public static EffectCtx TAUNT_STANCE = new EffectCtx("taunt_stance", "Taunt Stance", Elements.Physical, EffectType.beneficial);
    public static EffectCtx UNDYING_WILL = new EffectCtx("undying_will", "Undying Will", Elements.Physical, EffectType.beneficial);
    public static EffectCtx MAGE_CIRCLE = new EffectCtx("mage_circle", "Mage Circle", Elements.Elemental, EffectType.beneficial);
    public static EffectCtx PETRIFY = new EffectCtx("petrify", "Petrify", Elements.Shadow, EffectType.negative);
    public static EffectCtx BLIND = new EffectCtx("blind", "Blind", Elements.Shadow, EffectType.negative);
    public static EffectCtx STUN = new EffectCtx("stun", "Stun", Elements.Physical, EffectType.negative);

    // these could be used for map affixes
    public static EffectCtx SLOW = new EffectCtx("slow", "Slow", Elements.Physical, EffectType.negative);
    public static EffectCtx WOUNDS = new EffectCtx("wounds", "Wounds", Elements.Physical, EffectType.negative);
    public static EffectCtx CURSE_AGONY = new EffectCtx("agony", "Physical Agony", Elements.Elemental, EffectType.negative);
    public static EffectCtx CURSE_WEAKNESS = new EffectCtx("weak", "Elemental Weakness", Elements.Elemental, EffectType.negative);
    public static EffectCtx DESPAIR = new EffectCtx("despair", "Despair of Chaos", Elements.Elemental, EffectType.negative);

    public static EffectCtx CHARM = new EffectCtx("charm", "Charm", Elements.Elemental, EffectType.negative);
    public static EffectCtx GROUNDING = new EffectCtx("ground", "Grounding", Elements.Physical, EffectType.negative);
    public static EffectCtx SHRED = new EffectCtx("shred", "Shred", Elements.Physical, EffectType.negative);
    public static EffectCtx THORN = new EffectCtx("thorn", "Thorn", Elements.Physical, EffectType.negative);
    public static EffectCtx INNER_CALM = new EffectCtx("inner_calm", "Inner Calm", Elements.Physical, EffectType.beneficial);
    public static EffectCtx BONE_CHILL = new EffectCtx("bone_chill", "Bone Chill", Elements.Cold, EffectType.negative);
    public static EffectCtx FROST_LICH = new EffectCtx("frost_lich", "Frost Lich", Elements.Cold, EffectType.beneficial);
    public static EffectCtx ESSENCE_OF_FROST = new EffectCtx("essence_of_frost", "Essence of Frost", Elements.Cold, EffectType.beneficial);
    public static EffectCtx MISSILE_BARRAGE = new EffectCtx("missile_barrage", "Missile Barrage", Elements.NONE, EffectType.beneficial);

    public static List<EffectCtx> getCurses() {

        return Arrays.asList(
                ModEffects.CURSE_AGONY,
                ModEffects.DESPAIR,
                ModEffects.CURSE_WEAKNESS,
                ModEffects.SLOW,
                ModEffects.WOUNDS
        );
    }

    public static int ESSENCE_OF_FROST_MAX_STACKS = 5;

    public static void init() {

    }

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ENDURANCE_CHARGE)
                .stat(5, 5, new ElementalResist(Elements.Physical), ModType.FLAT)
                .stat(5, 5, new ElementalResist(Elements.Fire), ModType.FLAT)
                .stat(5, 5, new ElementalResist(Elements.Cold), ModType.FLAT)
                .stat(5, 5, new ElementalResist(Elements.Nature), ModType.FLAT)
                .maxStacks(3)
                .addTags(EffectTags.offensive, EffectTags.charge)
                .build();

        ExileEffectBuilder.of(FRENZY_CHARGE)
                .stat(3, 3, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE)
                .stat(5, 5, SpellChangeStats.CAST_SPEED.get(), ModType.FLAT)
                .maxStacks(3)
                .addTags(EffectTags.offensive, EffectTags.charge)
                .build();

        ExileEffectBuilder.of(POWER_CHARGE)
                .stat(25, 25, OffenseStats.CRIT_CHANCE.get(), ModType.PERCENT)
                .maxStacks(3)
                .addTags(EffectTags.offensive, EffectTags.charge)
                .build();

        ExileEffectBuilder.of(ICE_GOLEM_BUFF)
                .stat(2, 5, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT)
                .stat(5, 10, OffenseStats.ACCURACY.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.offensive, EffectTags.golem)
                .build();


        ExileEffectBuilder.of(FIRE_GOLEM_BUFF)
                .stat(10, 20, OffenseStats.TOTAL_DAMAGE.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.offensive, EffectTags.golem)
                .build();


        ExileEffectBuilder.of(LIGHTNING_GOLEM_BUFF)
                .stat(3, 6, SpellChangeStats.CAST_SPEED.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.offensive, EffectTags.golem)
                .build();


        ExileEffectBuilder.of(MISSILE_BARRAGE)
                .maxStacks(5)
                .stat(SpellChangeStats.CAST_TIME_PER_SPELL_TAG.get(SpellTags.MISSILE).mod(50, 50))
                .stat(OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.MISSILE).mod(25, 25))
                .disableStackingStatBuff()
                .removeOnSpellCastWithTag(SpellTags.MISSILE)
                .build();

        ExileEffectBuilder.of(ModEffects.ESSENCE_OF_FROST)
                .maxStacks(ESSENCE_OF_FROST_MAX_STACKS)
                .stat(OffenseStats.CRIT_DAMAGE.get().mod(1, 5))
                .stat(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(1, 5))
                .stat(new AilmentDamage(Ailments.FREEZE).mod(2, 5))
                .build();


        ExileEffectBuilder.of(ModEffects.FROST_LICH)
                .maxStacks(1)
                .stat(DatapackStats.ARMOR_PER_MANA.mod(1, 5))
                .stat(ProcStats.PROC_SHATTER.get().mod(25, 100))
                .stat(ProcStats.PROC_SHATTER_MAX_FROST_ESSENCE.get().mod(10, 50))
                .stat(OffenseStats.TOTAL_DAMAGE.get().mod(-25, -25))
                .stat(new ElementalResist(Elements.Fire).mod(-25, -25))

                .build();

        ExileEffectBuilder.of(ModEffects.BONE_CHILL)
                .maxStacks(5)
                .stat(-5, -10, new AilmentResistance(Ailments.FREEZE), ModType.FLAT)
                .stat(-2, -5, new ElementalResist(Elements.Cold), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(ModEffects.THORN)
                .maxStacks(5)
                .spell(SpellBuilder.forEffect()
                        .addSpecificAction(SpellCtx.ON_ENTITY_ATTACKED, PartBuilder.damage(SpellCalcs.THORN_CONSUME, Elements.Physical))
                        .addSpecificAction(SpellCtx.ON_ENTITY_ATTACKED, PartBuilder.removeExileEffectStacksToTarget(ModEffects.THORN.resourcePath))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 2D, 0.5D).tick(2D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(ModEffects.SHRED)
                .maxStacks(3)
                .stat(-10, -25, Armor.getInstance(), ModType.FLAT)
                .stat(-1, -3, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .stat(-2, -5, Armor.getInstance(), ModType.PERCENT)
                .build();


        ExileEffectBuilder.of(ModEffects.GROUNDING)
                .maxStacks(1)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.Downwards))
                                .addTarget(TargetSelector.CASTER.create()))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 2D, 0.5D)
                                .tick(20D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(ModEffects.CHARM)
                .maxStacks(5)
                .stat(-1, -3, Armor.getInstance(), ModType.PERCENT)
                .stat(-1, -3, DodgeRating.getInstance(), ModType.PERCENT)
                .stat(-1, -3, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(ModEffects.CURSE_AGONY)
                .maxStacks(1)
                .stat(-10, -20, Armor.getInstance(), ModType.MORE)
                .stat(5, 10, DatapackStats.MOVE_SPEED)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 2D, 0.5D)
                                .tick(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(ModEffects.CURSE_WEAKNESS)
                .maxStacks(1)
                .stat(-10, -20, new ElementalResist(Elements.Elemental))

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 2D, 0.5D)
                                .tick(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(ModEffects.DESPAIR)
                .maxStacks(1)
                .stat(-15, -25, new ElementalResist(Elements.Shadow))

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 2D, 0.5D)
                                .tick(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(ModEffects.SLOW)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -0.25F, ModType.MORE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -0.10F, ModType.MORE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .addTags(EffectTags.negative)
                .build();

        ExileEffectBuilder.of(ModEffects.STUN)
                .addTags(EffectTags.immobilizing, EffectTags.negative)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -10.0F, ModType.MORE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -10.0F, ModType.MORE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .vanillaStat(VanillaStatData.create(ATTACK_DAMAGE, -10.0F, ModType.MORE, UUID.fromString("10fb60a7-904b-462f-a7cb-a557f02e362e")))
                .build();


        ExileEffectBuilder.of(ModEffects.BLIND)
                .vanillaStat(VanillaStatData.create(ATTACK_DAMAGE, -10.0F, ModType.MORE, UUID.fromString("5eccf34c-29f7-4eea-bbad-82a905594064")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -10.0F, ModType.MORE, UUID.fromString("57eb6210-2a42-4ad3-a604-6f679d440a9b")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 3D, 1D)
                                .tick(20D))
                        .buildForEffect()
                )
                .addTags(EffectTags.immobilizing)
                .build();

        ExileEffectBuilder.of(ModEffects.WOUNDS)
                .stat(-25, -25, ResourceStats.HEAL_STRENGTH.get(), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(ModEffects.PETRIFY)
                .addTags(EffectTags.immobilizing)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -10.0F, ModType.MORE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd5")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D)
                                .tick(20D))
                        .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PETRIFY, Elements.Shadow))
                                .setTarget(TargetSelector.TARGET.create()))
                        .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 15D, 1D))
                        .onExpire(PartBuilder.justAction(SpellAction.PLAY_SOUND.create(SoundEvents.SHEEP_SHEAR, 1D, 1D)))
                        .buildForEffect())
                .build();


        ExileEffectBuilder.of(DRACONIC_BLOOD)
                .stat(2, 4, ResourceStats.SPELL_LIFESTEAL.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.positive)
                .build();

        ExileEffectBuilder.of(VAMPIRIC_BLOOD)
                .stat(2, 5, ResourceStats.LIFESTEAL.get(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.positive)
                .build();

        ExileEffectBuilder.of(MAGE_CIRCLE)
                .stat(10, 25, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT)
                .stat(5, 20, SkillDamage.getInstance(), ModType.FLAT)
                .maxStacks(1)
                .addTags(EffectTags.offensive)
                .build();


        ExileEffectBuilder.of(TAUNT_STANCE)
                .stat(10, 25, SpellChangeStats.THREAT_GENERATED.get())
                .stat(25, 50, SpellChangeStats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                                .setTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                                .tick(60D))
                        .buildForEffect())
                .maxStacks(1)
                .build();

        ExileEffectBuilder.of(UNDYING_WILL)
                .stat(-25, -75, DefenseStats.DAMAGE_RECEIVED.get())
                .stat(1, 2, HealthRegen.getInstance())
                .maxStacks(1)
                .build();

        ExileEffectBuilder.of(VIGOR)
                .stat(0.25F, 0.5F, HealthRegen.getInstance())
                .stat(0.25F, 0.5F, ManaRegen.getInstance())
                .stat(10, 10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song)
                .build();

        ExileEffectBuilder.of(PERSEVERANCE)
                .stat(5, 10, new ElementalResist(Elements.Physical))
                .stat(5, 10, new ElementalResist(Elements.Shadow))
                .stat(5, 10, new ElementalResist(Elements.Fire))
                .stat(5, 10, new ElementalResist(Elements.Cold))
                .stat(5, 10, new ElementalResist(Elements.Nature))
                .stat(10, 10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song, EffectTags.defensive)
                .build();

        ExileEffectBuilder.of(VALOR)
                .stat(5, 10, OffenseStats.TOTAL_DAMAGE.get(), ModType.FLAT)
                .stat(2, 5, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT)
                .stat(10, 10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.song), ModType.MORE)
                .maxStacks(3)
                .addTags(EffectTags.song, EffectTags.offensive)
                .build();


        ExileEffectBuilder.of(REJUVENATE)
                .maxStacks(5)
                .addTags(EffectTags.heal_over_time)
                .stat(ResourceStats.REJUV_HEAL_SELF.get().mod(25, 50).more())
                .stat(OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.thorns).mod(2, 10).more())
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(SpellCalcs.REJUVENATION))
                                .setTarget(TargetSelector.TARGET.create())
                                .tick(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                                .tick(20D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(INNER_CALM)
                .maxStacks(1)
                .addTags(EffectTags.heal_over_time)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.RESTORE_MANA.create(SpellCalcs.REJUVENATION))
                                .setTarget(TargetSelector.TARGET.create())
                                .tick(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 15D, 1D)
                                .tick(20D))
                        .buildForEffect())
                .build();


    }
}
