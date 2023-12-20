package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class WaterSpells implements ExileRegistryInit {
    public static String FROZEN_ORB = "frozen_orb";
    public static String FROST_NOVA_AOE = "frost_nova";
    public static String MAGE_CIRCLE = "mage_circle";
    public static String FROST_ARMOR = "frost_armor";
    public static String TIDAL_STRIKE = "tidal_strike";
    public static String NOURISHMENT = "nourishment";
    public static String HEART_OF_ICE = "heart_of_ice";
    public static String ICY_WEAPON = "ice_weapon";
    public static String CHILLING_FIELD = "chilling_field";
    public static String ICE_COMET = "ice_comet";

    public static String FROST_FLOWER = "frost_flower";

    @Override
    public void registerAll() {


        SpellBuilder.of(FROZEN_ORB, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 20 * 30)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Frozen orb",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.area))
                .manualDesc(
                        "Throw out an orb of ice which slowly moves towards enemies and deals " + SpellCalcs.ICEBALL.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + " in an area.")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 20 * 10D, false)
                        .put(MapField.TRACKS_ENEMIES, true)
                        .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                ))

                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 2D, 0.15D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SNOWFLAKE, 7D, 0.15D))

                .onTick(PartBuilder.damageInAoe(SpellCalcs.ICEBALL, Elements.Cold, 3D).tickRequirement(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 3D).tickRequirement(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SNOWFLAKE, 15D, 3D).tickRequirement(20D))
                .levelReq(20)
                .build();


        SpellBuilder.of(FROST_FLOWER, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 20 * 60)
                                .setSwingArm(), "Frost Totem",
                        Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.totem))
                .manualDesc("Summon a frost totem that deals "
                        + SpellCalcs.FROST_FLOWER.getLocDmgTooltip(Elements.Cold) + " in an area every second.")

                .onCast(PartBuilder.playSound(SoundEvents.GRASS_PLACE, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.FROST_FLOWER.get(), 20D * 8)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 20D, 1.5D, 0.2D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 5D, 1.5D, 0.2D))
                .onTick("block", PartBuilder.playSound(SoundEvents.PLAYER_HURT_FREEZE, 1D, 1D).tickRequirement(20D))

                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.FROST_FLOWER, Elements.Cold, 6D).tickRequirement(20D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 100D, 1D, 0.1D))
                )

                .levelReq(20)
                .build();

        SpellBuilder.of(ICE_COMET, PlayStyle.INT, SpellConfiguration.Builder.instant(18, 20).setChargesAndRegen(ICE_COMET, 3, 20 * 20),
                        "Ice Comet",
                        Arrays.asList(SpellTag.area, SpellTag.damage)
                )
                .manualDesc("Summon a meteor that falls from the sky, dealing " +
                        SpellCalcs.ICE_COMET.getLocDmgTooltip(Elements.Cold))


                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 7D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.PACKED_ICE, 200D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, true)))
                .onTick("block", PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 20D, 0.5D))
                .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.SNOWFLAKE, 20D, 1D))
                .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.ICE_COMET, Elements.Cold, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 300D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.SNOWFLAKE, 300D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
                .onExpire("block", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .levelReq(20)
                .build();

        SpellBuilder.of(CHILLING_FIELD, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 20 * 30)
                                .setSwingArm(), "Chilling Field",
                        Arrays.asList(SpellTag.damage, SpellTag.area))
                .weaponReq(CastingWeapon.ANY_WEAPON)

                .manualDesc("Spawn a cloud of bone-chilling frost, damaging enemies for "
                        + SpellCalcs.CHILLING_FIELD.getLocDmgTooltip()
                        + Elements.Cold.getIconNameDmg() + " every second and applying slow.")

                .onCast(PartBuilder.playSound(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundParticles(ParticleTypes.CLOUD, 5D, 3D, 0.2D))
                .onTick("block", PartBuilder.groundParticles(ParticleTypes.SNOWFLAKE, 30D, 3D, 0.2D))
                .onTick("block", PartBuilder.playSound(SoundEvents.HORSE_BREATHE, 1.1D, 1.5D)
                        .tickRequirement(20D))
                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.CHILLING_FIELD, Elements.Cold, 3D)
                        .disableKnockback()
                        .tickRequirement(20D))
                .onTick("block", PartBuilder.addEffectToEnemiesInAoe(MobEffects.MOVEMENT_SLOWDOWN, 3D, 20D))

                .levelReq(20)
                .build();


        SpellBuilder.of(HEART_OF_ICE, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 10)
                                .setChargesAndRegen(HEART_OF_ICE, 3, 20 * 30)
                        , "Heart of Ice",
                        Arrays.asList(SpellTag.heal))
                .manualDesc(
                        "Heal allies around you for " + SpellCalcs.HEART_OF_ICE.getLocDmgTooltip() +
                                " health.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.SPLASH, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.DRIPPING_WATER, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
                .onCast(PartBuilder.healInAoe(SpellCalcs.HEART_OF_ICE, 5D))
                .levelReq(20)
                .build();


        SpellBuilder.of(MAGE_CIRCLE, PlayStyle.INT, SpellConfiguration.Builder.instant(10, 20 * 45)
                        , "Mage Circle", Arrays.asList(SpellTag.movement))

                .manualDesc(
                        "Summon a Magic Circle. Standing in it increases your damage." +
                                " After a certain duration you will be teleported to its location.")

                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.GLYPH.get(), 20D * 10)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addTarget(TargetSelector.CASTER.create()))
                .onExpire(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))

                .onTick("block", PartBuilder.giveSelfExileEffect(ModEffects.MAGE_CIRCLE, 20D)
                        .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.alliesInRadius(2D)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 3D, 1.2D, 0.5D)
                        .addCondition(EffectCondition.EVERY_X_TICKS.create(3D)))
                .levelReq(30)
                .build();


        SpellBuilder.of(TIDAL_STRIKE, PlayStyle.STR, SpellConfiguration.Builder.instant(8, 12)
                                .setSwingArm(), "Tidal Strike",
                        Arrays.asList(SpellTag.weapon_skill, SpellTag.area, SpellTag.damage))
                .manualDesc("Strike enemies in front of you for " + SpellCalcs.TIDAL_STRIKE.getLocDmgTooltip(Elements.Cold))
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.TRIDENT_THROW, 1D, 1D))
                .onCast(PartBuilder.swordSweepParticles())
                .onCast(PartBuilder.damageInFront(SpellCalcs.TIDAL_STRIKE, Elements.Cold, 2D, 3D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.BUBBLE, 100D, 1D, 0.1D))
                )
                .levelReq(10)
                .build();


        SpellBuilder.of(FROST_NOVA_AOE, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 25 * 20), "Frost Nova",
                        Arrays.asList(SpellTag.area, SpellTag.damage))
                .manualDesc(
                        "Explode with frost around you, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + " to nearby enemies.")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ITEM_SNOWBALL, 300D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.SNOWFLAKE, 300D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.BUBBLE_POP, 250D, 5D, 0.5D))
                .onCast(PartBuilder.playSound(SoundEvents.DROWNED_HURT, 0.5D, 1D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.FROST_NOVA, Elements.Cold, 5D)
                        .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.DROWNED_HURT, 1D, 1D)))
                .levelReq(1)
                .build();


    }
}
