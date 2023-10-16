package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;

import java.util.Arrays;

public class SongSpells implements ExileRegistryInit {
    public static String POWER_CHORD = "power_chord";
    public static String RITARDANDO = "ritardando";
    public static String RESONANCE = "resonance";

    @Override
    public void registerAll() {

        SpellBuilder.of(POWER_CHORD, PlayStyle.INT, SpellConfiguration.Builder.instant(7, 15)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Power Chord",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.song))
                .manualDesc(
                        "Throw out a ball of music, dealing " + SpellCalcs.POWER_CHORD.getLocDmgTooltip()
                                + " " + Elements.Physical.getIconNameDmg() + " and charming enemies, reducing their defenses.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.POWER_CHORD, Elements.Physical, 1.5D)
                        .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6)))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.NOTE, 20D, 1D))
                .build();

        SpellBuilder.of(RITARDANDO, PlayStyle.INT, SpellConfiguration.Builder.instant(7, 15)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Ritardando",
                        Arrays.asList(SpellTag.projectile, SpellTag.damage, SpellTag.song))
                .manualDesc(
                        "Throw out a slow wave of sound, exploding upon contact and dealing " + SpellCalcs.RITARDANDO.getLocDmgTooltip()
                                + " " + Elements.Physical.getIconNameDmg() + " and slowing enemies in an area.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 0.25D, SlashEntities.SIMPLE_PROJECTILE.get(), 80D, false)))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.RITARDANDO, Elements.Physical, 3D)
                        .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.SLOW, 3)))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 3D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.NOTE, 20D, 3D))
                .build();

        SpellBuilder.of(RESONANCE, PlayStyle.INT, SpellConfiguration.Builder.multiCast(7, 20, 10, 3)
                                .setSwingArm().setChargesAndRegen("resonance", 3, 20 * 30)
                                .applyCastSpeedToCooldown(), "Resonance",
                        Arrays.asList(SpellTag.projectile, SpellTag.area, SpellTag.damage, SpellTag.song))
                .manualDesc(
                        "Throw out a ball of music, dealing " + SpellCalcs.RESONANCE.getLocDmgTooltip()
                                + " " + Elements.Physical.getIconNameDmg() + " and has 10% chance of charming enemies, reducing their defenses. Hits against charmed enemies explode.")
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.NOTE, 2D, 0.15D))

                .onExpire(PartBuilder.damageInAoe(SpellCalcs.RESONANCE, Elements.Physical, 1.5D)
                        .addActions(SpellAction.EXILE_EFFECT.giveSeconds(NegativeEffects.CHARM, 6).put(MapField.CHANCE, 10D)))

                .onExpire(PartBuilder.damageInAoeIfCharmed(SpellCalcs.RESONANCE, Elements.Physical, 3D)
                        .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.NOTE, 100D, 1D))
                        .addPerEntityHit(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1d))
                )

                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 10D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.NOTE, 20D, 1D))
                .build();
    }
}
