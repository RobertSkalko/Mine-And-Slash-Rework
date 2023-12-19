package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.Attributes.*;

public class NegativeEffects implements ExileRegistryInit {

    public static EffectCtx PETRIFY = new EffectCtx("petrify", "Petrify", Elements.Chaos, EffectType.negative);
    public static EffectCtx WOUNDS = new EffectCtx("wounds", "Wounds", Elements.Physical, EffectType.negative);
    public static EffectCtx BLIND = new EffectCtx("blind", "Blind", Elements.Chaos, EffectType.negative);
    public static EffectCtx STUN = new EffectCtx("stun", "Stun", Elements.Physical, EffectType.negative);
    public static EffectCtx SLOW = new EffectCtx("slow", "Slow", Elements.Physical, EffectType.negative);
    public static EffectCtx CURSE_AGONY = new EffectCtx("agony", "Curse of Agony", Elements.Elemental, EffectType.negative);
    public static EffectCtx CURSE_WEAKNESS = new EffectCtx("weak", "Curse of Weakness", Elements.Elemental, EffectType.negative);
    public static EffectCtx DESPAIR = new EffectCtx("despair", "Curse of Despair", Elements.Elemental, EffectType.negative);
    public static EffectCtx CHARM = new EffectCtx("charm", "Charm", Elements.Elemental, EffectType.negative);
    public static EffectCtx GROUNDING = new EffectCtx("ground", "Grounding", Elements.Physical, EffectType.negative);
    public static EffectCtx SHRED = new EffectCtx("shred", "Shred", Elements.Physical, EffectType.negative);
    public static EffectCtx THORN = new EffectCtx("thorn", "Thorn", Elements.Physical, EffectType.negative);

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(THORN)
                .maxStacks(5)
                .spell(SpellBuilder.forEffect()
                        .addSpecificAction(SpellCtx.ON_ENTITY_ATTACKED, PartBuilder.damage(SpellCalcs.THORN_CONSUME, Elements.Physical))
                        .addSpecificAction(SpellCtx.ON_ENTITY_ATTACKED, PartBuilder.removeExileEffectStacksToTarget(NegativeEffects.THORN.resourcePath))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 2D, 0.5D).tickRequirement(2D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(SHRED)
                .maxStacks(3)
                .stat(-10, -25, Armor.getInstance(), ModType.FLAT)
                .stat(-1, -3, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .stat(-2, -5, Armor.getInstance(), ModType.PERCENT)
                .build();


        ExileEffectBuilder.of(GROUNDING)
                .maxStacks(1)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.Downwards))
                                .addTarget(TargetSelector.CASTER.create()))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 2D, 0.5D)
                                .tickRequirement(20D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(CHARM)
                .maxStacks(5)
                .stat(-1, -3, Armor.getInstance(), ModType.PERCENT)
                .stat(-1, -3, DodgeRating.getInstance(), ModType.PERCENT)
                .stat(-1, -3, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(CURSE_AGONY)
                .maxStacks(1)
                .stat(-10, -20, Armor.getInstance(), ModType.MORE)
                .stat(5, 10, DatapackStats.MOVE_SPEED)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 2D, 0.5D)
                                .tickRequirement(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(CURSE_WEAKNESS)
                .maxStacks(1)
                .stat(-10, -20, new ElementalResist(Elements.Elemental))

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 2D, 0.5D)
                                .tickRequirement(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(DESPAIR)
                .maxStacks(1)
                .stat(-15, -25, new ElementalResist(Elements.Chaos))

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 2D, 0.5D)
                                .tickRequirement(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(SLOW)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -0.25F, ModType.MORE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -0.10F, ModType.MORE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .addTags(EffectTags.negative)
                .build();

        ExileEffectBuilder.of(STUN)
                .addTags(EffectTags.immobilizing, EffectTags.negative)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -10.0F, ModType.MORE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -10.0F, ModType.MORE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .vanillaStat(VanillaStatData.create(ATTACK_DAMAGE, -10.0F, ModType.MORE, UUID.fromString("10fb60a7-904b-462f-a7cb-a557f02e362e")))
                .build();


        ExileEffectBuilder.of(BLIND)
                .vanillaStat(VanillaStatData.create(ATTACK_DAMAGE, -10.0F, ModType.MORE, UUID.fromString("5eccf34c-29f7-4eea-bbad-82a905594064")))
                .vanillaStat(VanillaStatData.create(ATTACK_SPEED, -10.0F, ModType.MORE, UUID.fromString("57eb6210-2a42-4ad3-a604-6f679d440a9b")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 3D, 1D)
                                .tickRequirement(20D))
                        .buildForEffect()
                )
                .addTags(EffectTags.immobilizing)
                .build();

        ExileEffectBuilder.of(WOUNDS)
                .stat(-25, -25, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(PETRIFY)
                .addTags(EffectTags.immobilizing)
                .vanillaStat(VanillaStatData.create(MOVEMENT_SPEED, -10.0F, ModType.MORE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd5")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D)
                                .tickRequirement(20D))
                        .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PETRIFY, Elements.Chaos))
                                .setTarget(TargetSelector.TARGET.create()))
                        .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 15D, 1D))
                        .onExpire(PartBuilder.justAction(SpellAction.PLAY_SOUND.create(SoundEvents.SHEEP_SHEAR, 1D, 1D)))
                        .buildForEffect())
                .build();

    }
}
