package com.robertx22.mine_and_slash.aoe_data.database.spells.schools;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.a_libraries.player_animations.AnimationHolder;
import com.robertx22.mine_and_slash.a_libraries.player_animations.SpellAnimations;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailments;
import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.aoe_data.database.spells.builders.DamageBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.builders.ParticleBuilder;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.mine_and_slash.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.mine_and_slash.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashEntities;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashSounds;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
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
    public static String BLIZZARD = "blizzard";

    public static String FROST_FLOWER = "frost_flower";

    public static String BONE_SHATTER_PROC = "bone_shatter";

    public static String FROST_LICH_ARMOR = "frost_lich_armor";

    @Override
    public void registerAll() {


        SpellBuilder.of(FROST_LICH_ARMOR, PlayStyle.INT, SpellConfiguration.Builder.instant(100, 20 * 60), "Frost Lich Armor",
                        Arrays.asList(SpellTags.BUFF, SpellTags.COLD, SpellTags.PHYSICAL))
                .manualDesc("Surround yourself with Deadly Frost, granting you additional powers.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .showOtherSpellTooltip(BONE_SHATTER_PROC)
                .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.SPLASH, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.DRIPPING_WATER, 50D, 5D, 0.2D))
                .onCast(PartBuilder.giveSelfExileEffect(ModEffects.FROST_LICH, 20D * 60))
                .build();


        SpellBuilder.of(BONE_SHATTER_PROC, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(0, 20 * 3, 0),
                        "Bone Shatter", Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.COLD, SpellTags.PHYSICAL))
                .manualDesc("Shatters the ice dealing " + SpellCalcs.SHATTER_PROC.getLocDmgTooltip(Elements.Cold) + " and same amount of Physical Damage.")
                .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))
                .derivesLevelFromSpell(FROST_LICH_ARMOR)

                .onCast(ParticleBuilder.of(ParticleTypes.SNOWFLAKE, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(200).build())
                .onCast(ParticleBuilder.of(ParticleTypes.ITEM_SNOWBALL, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(100).build())
                .onCast(ParticleBuilder.of(ParticleTypes.CRIT, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(55).build())

                .onCast(DamageBuilder.radius(Elements.Cold, 3, SpellCalcs.SHATTER_PROC).build().noKnock())
                .onCast(DamageBuilder.radius(Elements.Physical, 3, SpellCalcs.SHATTER_PROC).build().noKnock())

                .build();

        SpellBuilder.of(BLIZZARD, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(35, 20 * 25, 30),
                        "Blizzard",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.COLD, SpellTags.PHYSICAL, SpellTags.SHATTER)
                )
                .animations(SpellAnimations.STAFF_CAST_WAVE_LOOP, SpellAnimations.STAFF_CAST_FINISH)
                .manualDesc("Create a Cloud that sends cold waves, damaging enemies for " + SpellCalcs.BLIZZARD.getLocDmgTooltip(Elements.Cold)
                )
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))

                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                        .put(MapField.ENTITY_NAME, "cloud")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("cloud", ParticleBuilder.of(ParticleTypes.SNOWFLAKE, 3f).shape(ParticleShape.CIRCLE_2D).amount(80).randomY(0.5F).height(6).build())
                .onTick("cloud", ParticleBuilder.of(ParticleTypes.ITEM_SNOWBALL, 3f).shape(ParticleShape.CIRCLE_2D).amount(40).randomY(0.5F).height(6).build())

                .onTick("cloud", DamageBuilder.radius(Elements.Cold, 3, SpellCalcs.BLIZZARD).build().noKnock().tick(20D))

                .build();

        SpellBuilder.of(FROZEN_ORB, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(30, 20 * 30, 25)
                                .setSwingArm(), "Frozen orb",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.area, SpellTags.COLD))
                .manualDesc(
                        "Throw out an orb of ice which slowly moves towards enemies and deals " + SpellCalcs.ICEBALL.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + " in an area.")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.SNOWBALL, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 20 * 10D, false)
                        .put(MapField.TRACKS_ENEMIES, true)
                        .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                ))
                .onTick(ParticleBuilder.of(ParticleTypes.SNOWFLAKE, 0.15F).amount(2).build())
                .onTick(ParticleBuilder.of(ParticleTypes.ITEM_SNOWBALL, 0.15F).amount(7).build())
                .onTick(ParticleBuilder.of(ParticleTypes.SNOWFLAKE, 3F).amount(15).build().tick(20D))
                .onTick(ParticleBuilder.of(ParticleTypes.ITEM_SNOWBALL, 3F).amount(5).build().tick(20D))

                .onTick(DamageBuilder.radius(Elements.Cold, 3, SpellCalcs.ICEBALL).build().tick(20D))

                .levelReq(20)
                .build();

        SpellBuilder.of(FROST_FLOWER, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 20 * 60)
                                .setSwingArm(), "Frost Totem",
                        Arrays.asList(SpellTags.damage, SpellTags.area, SpellTags.totem, SpellTags.COLD))
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
                .onTick("block", PartBuilder.playSound(SoundEvents.PLAYER_HURT_FREEZE, 1D, 1D).tick(20D))

                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.FROST_FLOWER, Elements.Cold, 6D).tick(20D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.RAIN, 75D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SPLASH, 50D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 100D, 1D, 0.1D))
                )

                .levelReq(20)
                .build();


        SpellBuilder.of(ICE_COMET, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(18, 20, 20).setChargesAndRegen(ICE_COMET, 3, 20 * 20),
                        "Ice Comet",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.COLD)
                )
                .animations(SpellAnimations.HAND_UP_CAST, SpellAnimations.CAST_FINISH)
                .manualDesc("Summon a meteor that falls from the sky, dealing " +
                        SpellCalcs.ICE_COMET.getLocDmgTooltip(Elements.Cold))

                .addStat(new AilmentDamage(Ailments.FREEZE).mod(25, 50).more())

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

        SpellBuilder.of(CHILLING_FIELD, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(30, 20 * 30, 30)
                                .setSwingArm(), "Chilling Field",
                        Arrays.asList(SpellTags.damage, SpellTags.area, SpellTags.COLD))
                .weaponReq(CastingWeapon.ANY_WEAPON)

                .manualDesc("Spawn a cloud of bone-chilling frost, damaging enemies for "
                        + SpellCalcs.CHILLING_FIELD.getLocDmgTooltip() + " "
                        + Elements.Cold.getIconNameDmg() + " every second and applying slow and Bone Chill.")

                .onCast(PartBuilder.playSound(SoundEvents.END_PORTAL_SPAWN, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 5D, 3D, 0.2D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 30D, 2.5D, 0.2D))
                .onTick("block", PartBuilder.playSound(SoundEvents.HORSE_BREATHE, 0.8D, 1.5D)
                        .tick(20D))
                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.CHILLING_FIELD, Elements.Cold, 4D)
                        .noKnock()
                        .tick(20D))
                .onTick("block", PartBuilder.addExileEffectToEnemiesInAoe(ModEffects.BONE_CHILL.resourcePath, 4D, 20 * 8D)
                        .tick(20D))
                .onTick("block", PartBuilder.addEffectToEnemiesInAoe(MobEffects.MOVEMENT_SLOWDOWN, 4D, 20D))

                .levelReq(20)
                .build();


        SpellBuilder.of(HEART_OF_ICE, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 10)
                                .setChargesAndRegen(HEART_OF_ICE, 3, 20 * 30)
                        , "Heart of Ice",
                        Arrays.asList(SpellTags.heal, SpellTags.COLD))
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
                        , "Mage Circle", Arrays.asList(SpellTags.movement))

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
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.COLD))
                .animations(AnimationHolder.none(), SpellAnimations.MELEE_SLASH)
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
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.COLD))
                .manualDesc(
                        "Explode with frost around you, dealing " + SpellCalcs.FROST_NOVA.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + " to nearby enemies.")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 1D, 1D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 4D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.ITEM_SNOWBALL, 200D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.SNOWFLAKE, 200D, 2.5D, 0.5D))
                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 0.8D, 1.5D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.FROST_NOVA, Elements.Cold, 5D)
                        .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D)))
                .levelReq(1)
                .build();


    }
}
