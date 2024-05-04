package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.aoe_data.database.stats.EffectStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;

public class SummonSpells implements ExileRegistryInit {
    public static String SUMMON_FIRE_GOLEM = "summon_fire_golem";
    public static String SUMMON_COLD_GOLEM = "summon_cold_golem";
    public static String SUMMON_LIGHTNING_GOLEM = "summon_lightning_golem";
    public static String SUMMON_SPIRIT_WOLF = "summon_spirit_wolf";
    public static String SUMMON_ZOMBIE = "summon_zombie";
    public static String SUMMON_SPIDER = "summon_spider";
    public static String SUMMON_SKELETAL_ARMY = "summon_skeleton_army";
    public static String RETURN_SUMMONS = "return_summons";
    public static String CHILLING_TOUCH = "chilling_touch";
    public static String EXPLODE_MINIONS = "explode_minions";

    @Override
    public void registerAll() {

        SpellBuilder.of(SUMMON_FIRE_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(40, 20 * 60, 30).setSummonType(SummonType.GOLEM), "Summon Fire Golem",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.golem, SpellTags.has_pet_ability, SpellTags.FIRE))
                .manualDesc("Summon a Golem that can cast Fire Nova to aid you in combat.")
                .summons(SlashEntities.FIRE_GOLEM.get(), 20 * 60 * 3, 1, SummonType.GOLEM)
                .levelReq(20)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(10, 50))
                .addStat(new SummonHealth().mod(10, 100))
                .addStat(EffectStats.APPLY_GOLEM_EFFECT.get(ModEffects.FIRE_GOLEM_BUFF).mod(1, 1))

                .build();

        SpellBuilder.of(SUMMON_COLD_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(40, 20 * 60, 30).setSummonType(SummonType.GOLEM), "Summon Frost Golem",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.golem, SpellTags.has_pet_ability, SpellTags.COLD))
                .manualDesc("Summon a Golem that can cast Frost Nova to aid you in combat.")
                .summons(SlashEntities.COLD_GOLEM.get(), 20 * 60 * 3, 1, SummonType.GOLEM)
                .levelReq(20)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(10, 50))
                .addStat(new SummonHealth().mod(10, 100))
                .addStat(EffectStats.APPLY_GOLEM_EFFECT.get(ModEffects.ICE_GOLEM_BUFF).mod(1, 1))
                .build();

        SpellBuilder.of(SUMMON_LIGHTNING_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(40, 20 * 60, 30).setSummonType(SummonType.GOLEM), "Summon Lightning Golem",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.golem, SpellTags.has_pet_ability, SpellTags.LIGHTNING))
                .manualDesc("Summon a Golem that can cast Lightning Nova to aid you in combat .")
                .summons(SlashEntities.LIGHTNING_GOLEM.get(), 20 * 60 * 3, 1, SummonType.GOLEM)
                .levelReq(20)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(10, 50))
                .addStat(new SummonHealth().mod(10, 100))
                .addStat(EffectStats.APPLY_GOLEM_EFFECT.get(ModEffects.LIGHTNING_GOLEM_BUFF).mod(1, 1))
                .build();


        SpellBuilder.of(SUMMON_SPIRIT_WOLF, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(30, 30 * 20, 30).setSummonType(SummonType.BEAST), "Summon Spirit Wolf",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.beast, SpellTags.has_pet_ability, SpellTags.PHYSICAL))
                .manualDesc("Summon a Spirit Wolf to aid you in combat.")
                .summons(SlashEntities.SPIRIT_WOLF.get(), 20 * 30, 1, SummonType.BEAST)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(5, 25))
                .addStat(new SummonHealth().mod(30, 300))
                .levelReq(1)
                .build();

        SpellBuilder.of(SUMMON_ZOMBIE, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(30, 20 * 60, 40).setSummonType(SummonType.UNDEAD), "Summon Zombie",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.has_pet_ability, SpellTags.PHYSICAL))
                .manualDesc("Summon a Zombie to aid you in combat.")
                .summons(SlashEntities.ZOMBIE.get(), 20 * 60 * 2, 1, SummonType.UNDEAD)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(10, 50))
                .addStat(new SummonHealth().mod(20, 200))
                .levelReq(1)
                .build();

        SpellBuilder.of(SUMMON_SPIDER, PlayStyle.INT, SpellConfiguration.Builder.instant(15, 1)
                                .setSummonBasicAttack(PetSpells.SPIDER)
                                .setChargesAndRegen("spider", 3, 20 * 15).setSummonType(SummonType.UNDEAD), "Summon Spider",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.has_pet_ability, SpellTags.CHAOS))
                .manualDesc("Summon a fast moving spider to aid you in combat.")
                .summons(SlashEntities.SPIDER.get(), 20 * 60 * 2, 1, SummonType.SPIDER)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(5, 25))
                .addStat(new AilmentChance(Ailments.POISON).mod(10, 50))
                .levelReq(1)
                .build();

        SpellBuilder.of(SUMMON_SKELETAL_ARMY, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(35, 20 * 3, 40).setSummonType(SummonType.UNDEAD), "Summon Skeleton",
                        Arrays.asList(SpellTags.summon, SpellTags.damage, SpellTags.has_pet_ability, SpellTags.PHYSICAL))
                .manualDesc("Summon Skeleton to fight for you using ranged attacks.")
                .summons(SlashEntities.SKELETON.get(), 20 * 60 * 3, 1, SummonType.UNDEAD)
                .levelReq(30)
                .addStat(OffenseStats.SUMMON_DAMAGE.get().mod(10, 150))
                .addStat(new SummonHealth().mod(10, 100))

                .build();


        SpellBuilder.of(RETURN_SUMMONS, PlayStyle.STR, SpellConfiguration.Builder.instant(15, 20 * 30), "Return Summons",
                        Arrays.asList(SpellTags.area, SpellTags.heal, SpellTags.summon))
                .manualDesc("Regroup your summons and heal them for " + SpellCalcs.HEALING_AURA.getLocDmgTooltip() + " health.")
                .onCast(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addActions(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 20D * 5))
                        .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL, Elements.Physical))
                        .addActions(SpellAction.EXILE_EFFECT.create(ModEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                        .addTarget(TargetSelector.AOE.create(30D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.pets))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(SpellCalcs.HEALING_AURA)))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create()))
                )
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
                .levelReq(10)
                .build();


        SpellBuilder.of(CHILLING_TOUCH, PlayStyle.STR, SpellConfiguration.Builder.instant(8, 20 * 5)
                                .setSwingArm(), "Chilling Touch",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.COLD))
                .manualDesc("Strike enemies in front for " +
                        SpellCalcs.CHILLING_TOUCH.getLocDmgTooltip(Elements.Cold) + ", and command your summons to attack them.")
                .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
                .onCast(PartBuilder.swordSweepParticles())
                .onCast(PartBuilder.damageInFront(SpellCalcs.CHILLING_TOUCH, Elements.Cold, 2D, 3D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 100D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.COMMAND_SUMMONS_ATTACK.create()))
                )
                .levelReq(1)
                .build();


        SpellBuilder.of(EXPLODE_MINIONS, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 20 * 5)
                                .setSwingArm(), "Explode Minions",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.minion_explode, SpellTags.summon, SpellTags.FIRE))
                .manualDesc("Explodes all your nearby minions for " + SpellCalcs.EXPLODE_MINION.getLocDmgTooltip(Elements.Fire)
                        + ". Summon HP stat acts as a damage bonus for this spell.")

                .addSpecificAction("explode", PartBuilder.damageInAoe(SpellCalcs.EXPLODE_MINION, Elements.Fire, 3D))
                // todo not sure why this works as "specific" action but not otherwise

                .onCast(PartBuilder.selectSummons(10D)
                        .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                        .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 10D, 3D))
                        .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION_EMITTER, 2D, 1D))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.SPECIFIC_ACTION.create("explode")))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.EXPIRE.create()))
                )
                .levelReq(1)
                .build();

    }


}
