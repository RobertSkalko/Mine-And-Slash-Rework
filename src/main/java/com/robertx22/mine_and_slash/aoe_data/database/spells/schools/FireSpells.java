package com.robertx22.mine_and_slash.aoe_data.database.spells.schools;

import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashEntities;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class FireSpells implements ExileRegistryInit {
    public static String FIRE_NOVA_ID = "fire_nova";
    public static String FLAME_STRIKE_ID = "flame_strike";

    public static String METEOR = "meteor";

    public static String VAMP_BLOOD = "vamp_blood";
    public static String DRACONIC_BLOOD = "draconic_blood";
    public static String MAGMA_FLOWER = "magma_flower";

    @Override
    public void registerAll() {

        SpellBuilder.of(MAGMA_FLOWER, PlayStyle.INT, SpellConfiguration.Builder.instant(15, 20 * 30)
                                .setSwingArm(), "Magma Totem",
                        Arrays.asList(SpellTags.damage, SpellTags.area, SpellTags.totem, SpellTags.FIRE))
                .manualDesc("Summon a flaming totem that deals "
                        + SpellCalcs.MAGMA_FLOWER.getLocDmgTooltip() + " "
                        + Elements.Fire.getIconNameDmg() + " in an area every second.")

                .onCast(PartBuilder.playSound(SoundEvents.GRASS_PLACE, 1D, 1D))

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(SlashBlocks.MAGMA_FLOWER.get(), 20D * 7)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false)))

                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 40D, 3D, 1D).tick(20D))
                .onTick("block", PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 40D, 3D, 1D).tick(20D))

                .onTick("block", PartBuilder.damageInAoe(SpellCalcs.MAGMA_FLOWER, Elements.Fire, 3D).tick(20D).noKnock())
                .onTick("block", PartBuilder.playSound(SoundEvents.GENERIC_BURN, 1D, 1D).tick(20D))
                .levelReq(20)
                .build();

        SpellBuilder.of(FLAME_STRIKE_ID, PlayStyle.STR, SpellConfiguration.Builder.instant(8, 15)
                                .setSwingArm(), "Flame Strike",
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.FIRE))
                .manualDesc("Strike enemies in front for " +
                        SpellCalcs.FLAME_STRIKE.getLocDmgTooltip(Elements.Fire))
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.FIRE_EXTINGUISH, 1D, 1D))
                .onCast(PartBuilder.swordSweepParticles())
                .onCast(PartBuilder.damageInFront(SpellCalcs.FLAME_STRIKE, Elements.Fire, 2D, 3D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.FLAME, 45D, 1D, 0.1D)))
                .levelReq(10)
                .build();


        SpellBuilder.of(VAMP_BLOOD, PlayStyle.STR, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Vampiric Blood",
                        Arrays.asList(SpellTags.FIRE, SpellTags.BUFF))
                .manualDesc("Gives effect to nearby allies.")
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, ModEffects.VAMPIRIC_BLOOD.resourcePath, 20 * 60D))
                .levelReq(30)
                .build();

        SpellBuilder.of(DRACONIC_BLOOD, PlayStyle.STR, SpellConfiguration.Builder.nonInstant(10, 60 * 20 * 3, 30), "Draconic Blood",
                        Arrays.asList(SpellTags.FIRE, SpellTags.BUFF))
                .manualDesc("Gives effect to nearby allies.")
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.giveExileEffectToAlliesInRadius(5D, ModEffects.DRACONIC_BLOOD.resourcePath, 20 * 60D))
                .levelReq(30)
                .build();


        SpellBuilder.of(METEOR, PlayStyle.INT, SpellConfiguration.Builder.instant(18, 20).setChargesAndRegen(METEOR, 3, 20 * 20), "Meteor",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.FIRE)
                )
                .manualDesc("Summon a meteor that falls from the sky, dealing " +
                        SpellCalcs.METEOR.getLocDmgTooltip(Elements.Fire) + " in an area.")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ILLUSIONER_CAST_SPELL, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 7D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.MAGMA_BLOCK, 200D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.BLOCK_FALL_SPEED, -0.03D)
                        .put(MapField.IS_BLOCK_FALLING, true)))
                .onTick("block", PartBuilder.particleOnTick(2D, ParticleTypes.LAVA, 2D, 0.5D))
                .onExpire("block", PartBuilder.damageInAoe(SpellCalcs.METEOR, Elements.Fire, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.LAVA, 150D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.ASH, 25D, 3D))
                .onExpire("block", PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 15D, 3D))
                .onExpire("block", PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))
                .levelReq(20)
                .build();

        SpellBuilder.of(FIRE_NOVA_ID, PlayStyle.STR, SpellConfiguration.Builder.instant(20, 20 * 25), "Fire Nova",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.FIRE))
                .manualDesc(
                        "Engulf the area in flames, dealing " + SpellCalcs.FIRE_NOVA.getLocDmgTooltip()
                                + " " + Elements.Fire.getIconNameDmg() + " to nearby enemies.")

                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .onCast(PartBuilder.nova(ParticleTypes.FLAME, 200D, 5D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 3.5D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.FLAME, 100D, 1.5D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

                .onCast(PartBuilder.damageInAoe(SpellCalcs.FIRE_NOVA, Elements.Fire, 5D))
                .levelReq(1)
                .build();


    }
}
