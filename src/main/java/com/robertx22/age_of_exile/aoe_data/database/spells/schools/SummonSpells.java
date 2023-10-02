package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
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
    public static String SUMMON_SKELETAL_ARMY = "summon_skeleton_army";
    public static String RETURN_SUMMONS = "return_summons";
    public static String CHILLING_TOUCH = "chilling_touch";

    @Override
    public void registerAll() {

        SpellBuilder.of(SUMMON_FIRE_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Fire Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem that can cast Fire Nova to aid you in combat.")
                .summons(SlashEntities.FIRE_GOLEM.get(), 20 * 60 * 3, 1)
                .levelReq(20)
                .build();

        SpellBuilder.of(SUMMON_COLD_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Frost Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem that can cast Frost Nova to aid you in combat.")
                .summons(SlashEntities.COLD_GOLEM.get(), 20 * 60 * 3, 1)
                .levelReq(20)
                .build();

        SpellBuilder.of(SUMMON_LIGHTNING_GOLEM, PlayStyle.INT, SpellConfiguration.Builder.instant(40, 20 * 60).setSummonType(SummonType.GOLEM), "Summon Lightning Golem",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.golem))
                .manualDesc("Summon a Golem that can cast Lightning Nova to aid you in combat .")
                .summons(SlashEntities.LIGHTNING_GOLEM.get(), 20 * 60 * 3, 1)
                .levelReq(20)
                .build();


        SpellBuilder.of(SUMMON_SPIRIT_WOLF, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 30 * 20).setSummonType(SummonType.BEAST), "Summon Spirit Wolf",
                        Arrays.asList(SpellTag.summon, SpellTag.damage, SpellTag.beast))
                .manualDesc("Summon a Spirit Wolf to aid you in combat.")
                .summons(SlashEntities.SPIRIT_WOLF.get(), 20 * 30, 1)
                .levelReq(1)
                .build();

        SpellBuilder.of(SUMMON_ZOMBIE, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 20 * 60).setSummonType(SummonType.UNDEAD), "Summon Zombie",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summon a Zombie to aid you in combat.")
                .summons(SlashEntities.ZOMBIE.get(), 20 * 60 * 2, 1)
                .levelReq(1)
                .build();

        SpellBuilder.of(SUMMON_SKELETAL_ARMY, PlayStyle.INT, SpellConfiguration.Builder.instant(60, 20 * 60).setSummonType(SummonType.UNDEAD), "Summon Skeletons",
                        Arrays.asList(SpellTag.summon, SpellTag.damage))
                .manualDesc("Summon a horde of Skeletons to fight for you for a short duration.")
                .summons(SlashEntities.SKELETON.get(), 20 * 20, 3)
                .levelReq(30)
                .build();


        SpellBuilder.of(RETURN_SUMMONS, PlayStyle.STR, SpellConfiguration.Builder.instant(15, 20 * 30), "Return Summons",
                        Arrays.asList(SpellTag.area, SpellTag.heal))
                .manualDesc("Regroup your summons and heal them for " + SpellCalcs.HEALING_AURA.getLocDmgTooltip() + " health.")
                .onCast(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addActions(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 20D * 5))
                        .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL, Elements.Physical))
                        .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                        .addTarget(TargetSelector.AOE.create(30D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.pets))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(SpellCalcs.HEALING_AURA)))
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create()))
                )
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
                .levelReq(10)
                .build();


        SpellBuilder.of(CHILLING_TOUCH, PlayStyle.STR, SpellConfiguration.Builder.instant(8, 20 * 5)
                                .setSwingArm(), "Chilling Touch",
                        Arrays.asList(SpellTag.area, SpellTag.damage))
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

    }


}
