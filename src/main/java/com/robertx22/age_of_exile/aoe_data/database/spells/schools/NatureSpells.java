package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class NatureSpells implements ExileRegistryInit {

    public static String REFRESH = "refresh";
    public static String REJUVENATION = "rejuvenation";
    public static String ENTANGLE_SEED = "entangling_seed";
    public static String POISON_CLOUD = "poison_cloud";
    public static String THORN_BUSH = "thorn_bush";
    public static String CHAOS_TOTEM = "chaos_totem";
    public static String CIRCLE_OF_HEALING = "circle_of_healing";
    public static String GARDEN_OF_THORNS = "garden_of_thorns";

    @Override
    public void registerAll() {

        SpellBuilder.of(CHAOS_TOTEM, PlayStyle.STR, SpellConfiguration.Builder.instant(40, 20 * 60), "Chaos Totem",
                        Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.totem))

                .manualDesc("Summons a totem that spawns chaos meteors, dealing " + SpellCalcs.CHAOS_TOTEM.getLocDmgTooltip(Elements.Chaos)
                        + " in an area.")

                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.PROJECTILE_TOTEM.get(), 20D * 7.5D)
                        .put(MapField.ENTITY_NAME, "totem")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))


                .onTick("totem", PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 100D, 3D, 0.5D).tickRequirement(2D))

                .onTick("totem", PartBuilder.aoeSelectEnemies(10D, 50D).tickRequirement(20D)
                        .addPerEntityHit(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.PURPLE_GLAZED_TERRACOTTA, 200D)
                                .put(MapField.ENTITY_NAME, "meteor")
                                .put(MapField.BLOCK_FALL_SPEED, -0.05D)
                                .put(MapField.HEIGHT, 4D)
                                .put(MapField.FIND_NEAREST_SURFACE, false)
                                .put(MapField.IS_BLOCK_FALLING, true))))

                .onTick("meteor", PartBuilder.particleOnTick(1D, ParticleTypes.WITCH, 20D, 0.5D))
                .onExpire("meteor", PartBuilder.damageInAoe(SpellCalcs.CHAOS_TOTEM, Elements.Chaos, 2D))

                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.WITCH, 100D, 2D))
                .onExpire("meteor", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
                .onExpire("meteor", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .levelReq(20)

                .build();


        SpellBuilder.of(THORN_BUSH, PlayStyle.INT, SpellConfiguration.Builder.instant(15, 20 * 6)
                                .setSwingArm(), "Thorn Bush",
                        Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.totem, SpellTag.thorns))
                .manualDesc("Summon a thorny bush that deals "
                        + SpellCalcs.THORN_BUSH.getLocDmgTooltip()
                        + Elements.Physical.getIconNameDmg() + " in an area every second and applies Thorned. " +
                        "Thorned enemies consume the stack of thorns every time they are basic attacked to deal "
                        + SpellCalcs.THORN_CONSUME.getLocDmgTooltip(Elements.Physical))

                .onCast(PartBuilder.playSound(SoundEvents.GRASS_PLACE, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.THORN_BUSH.get(), 20D * 5)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))


                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SNEEZE, 40D, 3D, 1D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.ITEM_SLIME, 40D, 3D, 1D))
                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.THORN_BUSH, Elements.Physical, 3D).tickRequirement(20D).disableKnockback())
                .onTick("block", PartBuilder.addExileEffectToEnemiesInAoe(ModEffects.THORN.resourcePath, 3D, 20 * 8D).tickRequirement(20D))
                .onTick("block", PartBuilder.playSound(SoundEvents.GRASS_BREAK, 1D, 1D).tickRequirement(20D))
                .levelReq(20)
                .build();

        SpellBuilder.of(POISON_CLOUD, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 20 * 45), "Poison Cloud",
                        Arrays.asList(SpellTag.area, SpellTag.damage))
                .manualDesc(
                        "Erupt with poisonous gas, dealing " + SpellCalcs.POISON_CLOUD.getLocDmgTooltip()
                                + " " + Elements.Chaos.getIconNameDmg() + " to nearby enemies.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 0.5D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.SNEEZE, 300D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 200D, 5D, 0.2D))

                .onCast(PartBuilder.damageInAoe(SpellCalcs.POISON_CLOUD, Elements.Chaos, 5D)
                        .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D)))
                .levelReq(20)
                .build();


        SpellBuilder.of(REFRESH, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(40, 20 * 60 * 3, 20)
                        , "Refresh",
                        Arrays.asList())

                .manualDesc(
                        "Refreshes all your cooldowns by 1 minute.")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SlashSounds.REFRESH.get(), 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.REFRESH_COOLDOWNS_BY_X_TICKS.create(20 * 60D))
                        .addTarget(TargetSelector.CASTER.create()))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_FX_HOLDER.createFXHolder(60D, "refresh")
                        .put(MapField.IS_PLAYER_EFFECT, true)
                ))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.FALLING_WATER, 100D, 1.5D).hideInFX())
                .onCast(PartBuilder.aoeParticles(ParticleTypes.DRIPPING_WATER, 50D, 1.5D).hideInFX())
                .onCast(PartBuilder.aoeParticles(ParticleTypes.EFFECT, 50D, 1.5D).hideInFX())
                .levelReq(30)
                .build();


        SpellBuilder.of(REJUVENATION, PlayStyle.INT, SpellConfiguration.Builder.instant(15, 60 * 20)
                        , "Rejuvenation",
                        Arrays.asList(SpellTag.heal, SpellTag.rejuvenate))
                .manualDesc("Gives buff that heals nearby allies for " + SpellCalcs.REJUVENATION.getLocDmgTooltip() + " every second.")
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.giveExileEffectToAlliesInRadius(8D, ModEffects.REJUVENATE.resourcePath, 20 * 15D))
                .levelReq(1)
                .build();

        SpellBuilder.of(CIRCLE_OF_HEALING, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 10)
                                .setChargesAndRegen(CIRCLE_OF_HEALING, 3, 20 * 30), "Circle of Healing",
                        Arrays.asList(SpellTag.heal, SpellTag.rejuvenate))
                .manualDesc("Rejuvenate allies around you for " + SpellCalcs.CIRCLE_OF_HEALING.getLocDmgTooltip() + " health.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
                .onCast(PartBuilder.healInAoe(SpellCalcs.CIRCLE_OF_HEALING, 5D))
                .levelReq(20)
                .build();


        SpellBuilder.of(ENTANGLE_SEED, PlayStyle.INT, SpellConfiguration.Builder.instant(15, 60 * 20)
                                .setSwingArm(), "Entangling Seed",
                        Arrays.asList(SpellTag.area))
                .manualDesc("Throw out a seed that explodes and petrifies enemies.")

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.BEETROOT_SEEDS, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 40D)))

                .onExpire(PartBuilder.justAction(SpellAction.EXILE_EFFECT.giveSeconds(ModEffects.PETRIFY, 5))
                        .enemiesInRadius(3D))
                .onExpire(PartBuilder.groundParticles(ParticleTypes.LARGE_SMOKE, 50D, 3D, 0.25D))
                .onExpire(PartBuilder.groundParticles(ParticleTypes.ITEM_SLIME, 100D, 3D, 0.25D))
                .onExpire(PartBuilder.playSound(SlashSounds.STONE_CRACK.get(), 1D, 1D))
                .levelReq(10)
                .build();


        SpellBuilder.of(GARDEN_OF_THORNS, PlayStyle.INT, SpellConfiguration.Builder.multiCast(15, 20, 20, 5)
                                .setChargesAndRegen(GARDEN_OF_THORNS, 3, 20 * 30), "Garden of Thorns",
                        Arrays.asList(SpellTag.area, SpellTag.damage, SpellTag.thorns)
                )
                .manualDesc("Deals " + SpellCalcs.THORN_CONSUME.getLocDmgTooltip(Elements.Physical) +
                        " in the area and applies Thorns. Gives you Inner Calm which restores " + SpellCalcs.INNER_CALM.getLocDmgTooltip() + " Mana/s.")

                .onCast(PartBuilder.playSound(SoundEvents.GRASS_PLACE, 1D, 1D))

                .onCast(PartBuilder.giveSelfExileEffect(ModEffects.INNER_CALM, 20 * 5D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 7D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.CACTUS, 200D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.BLOCK_FALL_SPEED, -0.02D)
                        .put(MapField.IS_BLOCK_FALLING, true)))
                .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.THORN_CONSUME, Elements.Physical, 3D).disableKnockback())
                .onExpire("block", PartBuilder.addExileEffectToEnemiesInAoe(ModEffects.THORN.resourcePath, 3D, 20 * 10D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 150D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.SNEEZE, 40D, 3D))
                .onExpire("block", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .levelReq(20)
                .build();


    }
}
