package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.*;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class RangerSpells implements ExileRegistryInit {

    public static String EXPLOSIVE_ARROW_ID = "explosive_arrow";
    public static String CHARGED_BOLT = "charged_bolt";
    public static String ARROW_STORM = "arrow_storm";
    public static String RECOIL_SHOT = "recoil_shot";
    public static String DASH_ID = "dash";

    public static String FROST_TRAP = "frost_trap";
    public static String POISON_TRAP = "poison_trap";
    public static String FIRE_TRAP = "fire_trap";
    public static String HUNTER_POTION = "hunter_potion";
    public static String SMOKE_BOMB = "smoke_bomb";
    public static String BARRAGE = "arrow_barrage";

    @Override

    public void registerAll() {

        SpellBuilder.of("arrow_totem", PlayStyle.DEX, SpellConfiguration.Builder.instant(25, 10)
                                .setChargesAndRegen("arrow_totem", 3, 20 * 30)
                                .applyCastSpeedToCooldown(), "Arrow Totem",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.totem))

                .manualDesc("Summons a totem that rapidly shoots arrows dealing " + SpellCalcs.ARROW_TOTEM.getLocDmgTooltip(Elements.Physical))

                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.PROJECTILE_TOTEM.get(), 20D * 7.5D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 3D, 0.5D).tickRequirement(2D))

                .onTick("block", PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR,
                                1D, 2.5D, SlashEntities.SIMPLE_ARROW.get(), 40D, false)
                        .put(MapField.ENTITY_NAME, "arrow")
                        .put(MapField.POS_SOURCE, PositionSource.SOURCE_ENTITY.name())
                        .put(MapField.SHOOT_DIRECTION, SummonProjectileAction.ShootWay.FIND_ENEMY.name())
                ).tickRequirement(5D))
                .onTick("block", PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D).tickRequirement(5D))

                .onExpire("arrow", PartBuilder.damageInAoe(SpellCalcs.ARROW_TOTEM, Elements.Physical, 1.5D))

                .build();


        SpellBuilder.of("boomerang", PlayStyle.DEX, SpellConfiguration.Builder.instant(10, 20 * 5)
                                .setChargesAndRegen("boomerang", 3, 20 * 10)
                                .applyCastSpeedToCooldown(), "Boomerang",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.chaining))
                .manualDesc("Strike enemies with a projectile that deals " + SpellCalcs.BOOMERANG.getLocDmgTooltip(Elements.Physical))

                .onCast(PartBuilder.playSound(SoundEvents.ALLAY_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.BOOMERANG.get(), 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 50D, false)
                        .put(MapField.CHAIN_COUNT, 5D)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 10D, 0.01D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.BOOMERANG, Elements.Physical, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CRIT, 50D, 0.5D))
                .onExpire(PartBuilder.playSound(SoundEvents.TRIDENT_HIT, 1D, 1D))
                .build();


        SpellBuilder.of(HUNTER_POTION, PlayStyle.DEX, SpellConfiguration.Builder.instant(0, 60 * 20 * 3), "Hunter's Potion",
                        Arrays.asList(SpellTag.heal)
                )
                .manualDesc("Drink a potion, healing you for " + SpellCalcs.HUNTER_POTION_HEAL.getLocDmgTooltip() + " health")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
                .onCast(PartBuilder.healCaster(SpellCalcs.HUNTER_POTION_HEAL))
                .build();

        trap(FROST_TRAP, "Frost Trap", ParticleTypes.ITEM_SNOWBALL, SpellCalcs.RANGER_TRAP, Elements.Cold).build();
        trap(POISON_TRAP, "Poison Trap", ParticleTypes.ITEM_SLIME, SpellCalcs.RANGER_TRAP, Elements.Chaos).build();
        trap(FIRE_TRAP, "Fire Trap", ParticleTypes.FLAME, SpellCalcs.RANGER_TRAP, Elements.Fire).build();

        SpellBuilder.of(SMOKE_BOMB, PlayStyle.DEX, SpellConfiguration.Builder.instant(7, 20 * 60), "Smoke Bomb",
                        Arrays.asList())
                .manualDesc("Throw out a smoke bomb, blinding enemies and reducing threat.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SPLASH_POTION_BREAK, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.SMOKE_BOMB, AggroAction.Type.DE_AGGRO))
                        .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.BLIND.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 5))
                        .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 200D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 3D))
                .build();


        SpellBuilder.of(DASH_ID, PlayStyle.DEX, SpellConfiguration.Builder.instant(10, 15)

                                .setChargesAndRegen("dash", 3, 20 * 30)
                        , "Dash",
                        Arrays.asList(SpellTag.movement, SpellTag.technique))
                .manualDesc(
                        "Dash in your direction and gain slowfall.")
                .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.CREEPER_PRIMED, 1D, 1.6D)
                        .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
                .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1.6D))

                .teleportForward()

                .build();

        SpellBuilder.of(CHARGED_BOLT, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(8, 20 * 15), "Charged Bolt",
                        Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))

                .manualDesc(
                        "Shoot a charged arrow that goes through enemies and deals "
                                + SpellCalcs.CHARGED_BOLT.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " in radius and slows.")

                .weaponReq(CastingWeapon.RANGED)
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)
                        .put(MapField.PROJECTILE_SPEED, 1D)
                        .put(MapField.EXPIRE_ON_ENTITY_HIT, false)
                        .put(MapField.GRAVITY, false)))

                .onHit(PartBuilder.aoeParticles(ParticleTypes.CRIT, 100D, 1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damageInAoe(SpellCalcs.CHARGED_BOLT, Elements.Physical, 2D)
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 40D))))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ENCHANTED_HIT, 4D, 0.1D))
                .build();


        SpellBuilder.of("quickdraw", PlayStyle.DEX, SpellConfiguration.Builder.instant(5, 60 * 20)
                        .setChargesAndRegen("quickdraw", 3, 20 * 60), "Quickdraw", Arrays.asList())
                .manualDesc("Your bow now fires instantly, and you gain a stack of arrows if you don't have infinity.")

                .onCast(PartBuilder.playSound(SoundEvents.WOLF_HOWL, 1D, 1D))
                .onCast(PartBuilder.giveSelfEffect(SlashPotions.INSTANT_ARROWS.get(), 20D * 10, 16D))
                .onCast(PartBuilder.giveSelfEffect(MobEffects.MOVEMENT_SPEED, 20D * 3))
                .onCast(PartBuilder.justAction(SpellAction.GIVE_ARROWS.create()))
                .build();


        // todo does this do anything?
        SpellBuilder.of(ARROW_STORM, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(20, 20 * 25), "Arrow Storm",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))
                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Shoot out arrows in an arc, dealing " + SpellCalcs.ARROW_STORM.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
                .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Physical))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
                .build();

        SpellBuilder.of("gale_wind", PlayStyle.DEX, SpellConfiguration.Builder.multiCast(20, 20 * 10, 10, 3), "Gale Wind",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))

                .manualDesc("Summons multiple clouds, knocking back and dealing " + SpellCalcs.GALE_WIND.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.EGG_THROW, 1D, 0.5D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 5D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 40D, false)))

                .onTick(PartBuilder.particleOnTick(2D, ParticleTypes.CLOUD, 3D, 0.1D))

                .onHit(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 2D))
                .onHit(PartBuilder.knockback(5D))
                .onHit(PartBuilder.damage(SpellCalcs.GALE_WIND, Elements.Physical))

                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 5D, 1D))

                .build();

        SpellBuilder.of(BARRAGE, PlayStyle.DEX, SpellConfiguration.Builder.multiCast(20, 20, 20, 5)
                                .setChargesAndRegen(BARRAGE, 3, 20 * 10), "Arrow Barrage",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))

                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Shoot out many arrows in a single line, dealing " + SpellCalcs.ARROW_STORM.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onHit(PartBuilder.particleOnTick(3D, ParticleTypes.CLOUD, 3D, 0.1D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onHit(PartBuilder.damage(SpellCalcs.ARROW_STORM, Elements.Physical))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D))
                .build();


        SpellBuilder.of(EXPLOSIVE_ARROW_ID, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Explosive Arrow",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))
                .weaponReq(CastingWeapon.RANGED)
                .manualDesc("Shoot an arrow that does " + SpellCalcs.EXPLOSIVE_ARROW.getLocDmgTooltip(Elements.Physical) + " around it")
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 0.1D))
                .onExpire(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.EXPLOSIVE_ARROW, Elements.Physical, 2D)
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 40D))))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.CRIT, 4D, 0.1D))
                .build();

        SpellBuilder.of(RECOIL_SHOT, PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(10, 20 * 10), "Recoil Shot",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))
                .weaponReq(CastingWeapon.RANGED)
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D)))
                .onHit(PartBuilder.damage(SpellCalcs.DIRECT_ARROW_HIT, Elements.Physical))
                .onCast(PartBuilder.pushCaster(DashUtils.Way.BACKWARDS, DashUtils.Strength.MEDIUM_DISTANCE))
                .onHit(PartBuilder.addExileEffectToEnemiesInAoe(NegativeEffects.WOUNDS.resourcePath, 1D, 20 * 20D))
                .onHit(PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onTick(PartBuilder.particleOnTick(5D, ParticleTypes.CRIT, 5D, 0.1D)
                )
                .build();

        SpellBuilder.of("meteor_arrow", PlayStyle.DEX, SpellConfiguration.Builder.arrowSpell(15, 10)
                                .setChargesAndRegen("meteor_arrow", 3, 20 * 10), "Meteor Arrow",
                        Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage))
                .weaponReq(CastingWeapon.RANGED)
                .onCast(PartBuilder.playSound(SoundEvents.ARROW_SHOOT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(1D).put(MapField.ENTITY_NAME, "arrow")))

                .onHit("arrow", PartBuilder.damage(SpellCalcs.METEOR, Elements.Physical))
                .onHit("arrow", PartBuilder.playSound(SoundEvents.ARROW_HIT, 1D, 1D))
                .onTick("arrow", PartBuilder.particleOnTick(5D, ParticleTypes.LAVA, 5D, 0.1D))

                .onExpire("arrow", PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 0D, 7D)
                        .put(MapField.ENTITY_NAME, "height_en")))

                .onExpire("height_en", PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                        .put(MapField.ENTITY_NAME, "meteor")
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                        .put(MapField.IS_BLOCK_FALLING, true)))

                .onTick("meteor", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
                .onExpire("meteor", PartBuilder.damageInAoe(SpellCalcs.METEOR, Elements.Fire, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
                .onExpire("meteor", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .build();


    }

    static SpellBuilder trap(String id, String name, SimpleParticleType particle, ValueCalculation dmg, Elements element) {

        return SpellBuilder.of(id, PlayStyle.DEX, SpellConfiguration.Builder.instant(7, 5)
                                .setChargesAndRegen(id, 3, 20 * 30)
                                .setSwingArm(), name,
                        Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.trap))
                .manualDesc(
                        "Throw out a trap that stays on the ground and activates when an enemy approaches to deal "
                                + dmg.getLocDmgTooltip() + element.getIconNameDmg() + " in area around itself."
                )
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.IRON_INGOT, 1D, 0.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 100D, true)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.TRAP.get(), 30 * 20D)
                        .put(MapField.ENTITY_NAME, "trap")
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("trap", PartBuilder.aoeParticles(particle, 5D, 1D)
                        .addCondition(EffectCondition.IS_ENTITY_IN_RADIUS.enemiesInRadius(1D))
                        .addActions(SpellAction.EXPIRE.create())
                        .addActions(SpellAction.SPECIFIC_ACTION.create("expire"))
                        .tickRequirement(2D))

                .addSpecificAction("expire", PartBuilder.damageInAoe(dmg, element, 3D))
                .addSpecificAction("expire", PartBuilder.aoeParticles(particle, 30D, 3D))
                .addSpecificAction("expire", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D));

    }

}
