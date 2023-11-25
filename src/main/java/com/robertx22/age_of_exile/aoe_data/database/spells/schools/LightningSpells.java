package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class LightningSpells implements ExileRegistryInit {
    public static String LIGHTNING_NOVA = "lightning_nova";
    public static String LIGHTNING_SPEAR = "lightning_spear";
    public static String CHAIN_LIGHTNING = "chain_lightning";
    public static String LIGHTNING_TOTEM = "lightning_totem";

    @Override
    public void registerAll() {


        SpellBuilder.of(CHAIN_LIGHTNING, PlayStyle.INT, SpellConfiguration.Builder.instant(25, 20)
                                .setChargesAndRegen("chain_lightning", 3, 20 * 10)
                                .applyCastSpeedToCooldown(), "Chain Lightning",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.chaining))
                .manualDesc(
                        "Strike enemies with chaining lightning that deals " + SpellCalcs.LIGHTNING_SPEAR.getLocDmgTooltip()
                                + " " + Elements.Lightning.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ALLAY_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.LIGHTNING.get(), 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 12D, true)
                        .put(MapField.CHAIN_COUNT, 5D)
                        .put(MapField.GRAVITY, false)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ELECTRIC_SPARK, 10D, 0.01D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.LIGHTNING_SPEAR, Elements.Lightning, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ELECTRIC_SPARK, 50D, 0.5D))
                .onExpire(PartBuilder.playSound(SoundEvents.TRIDENT_HIT, 1D, 1D))
                .levelReq(20)
                .build();


        SpellBuilder.of(LIGHTNING_SPEAR, PlayStyle.INT, SpellConfiguration.Builder.instant(5, 20 * 5)
                                .setSwingArm()
                                .setChargesAndRegen("lightning_spear", 3, 20 * 5)
                                .applyCastSpeedToCooldown(), "Lightning Spear",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage))
                .manualDesc(
                        "Throw out an electric spear, dealing " + SpellCalcs.LIGHTNING_SPEAR.getLocDmgTooltip()
                                + " " + Elements.Lightning.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.TRIDENT_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.SLIMEBALL.get(), 1D, 3D, SlashEntities.SIMPLE_TRIDENT.get(), 12D, true)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ELECTRIC_SPARK, 1D, 0.15D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ELECTRIC_SPARK, 10D, 0.2D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.LIGHTNING_SPEAR, Elements.Lightning, 2D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ELECTRIC_SPARK, 100D, 2D))
                .onExpire(PartBuilder.playSound(SoundEvents.TRIDENT_HIT, 1D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ELECTRIC_SPARK, 25D, 1D))

                .levelReq(1)
                .build();


        SpellBuilder.of(LIGHTNING_NOVA, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 25 * 10), "Lightning Nova",
                        Arrays.asList(SpellTag.area, SpellTag.damage))
                .manualDesc(
                        "Deal " + SpellCalcs.LIGHNING_NOVA.getLocDmgTooltip()
                                + " " + Elements.Lightning.getIconNameDmg() + " to nearby enemies.")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.TRIDENT_THUNDER, 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 200D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 300D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 250D, 4D, 0.5D))
                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 0.5D, 1D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.LIGHNING_NOVA, Elements.Lightning, 4D)
                        .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D)))
                .levelReq(10)
                .build();

        SpellBuilder.of(LIGHTNING_TOTEM, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 20 * 60)
                                .setSwingArm(), "Lightning Totem",
                        Arrays.asList(SpellTag.damage, SpellTag.area, SpellTag.totem))
                .manualDesc("Summon a lightning totem that deals "
                        + SpellCalcs.LIGHTNING_TOTEM.getLocDmgTooltip(Elements.Lightning) + " in an area every second.")

                .onCast(PartBuilder.playSound(SoundEvents.GRASS_PLACE, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.GUARD_TOTEM.get(), 20D * 8)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.ELECTRIC_SPARK, 20D, 1.5D, 0.2D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.POOF, 5D, 1.5D, 0.2D))
                .onTick("block", PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 0.75D, 1D).tickRequirement(20D))

                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.LIGHTNING_TOTEM, Elements.Lightning, 6D).disableKnockback().tickRequirement(20D)
                        .disableKnockback()
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.ELECTRIC_SPARK, 75D, 1.5D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.ELECTRIC_SPARK, 50D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.ELECTRIC_SPARK, 100D, 2D, 0.1D))
                )

                .levelReq(20)
                .build();


    }
}
