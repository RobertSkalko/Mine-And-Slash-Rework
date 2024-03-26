package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class BasicAttackSpells implements ExileRegistryInit {
    public static String POISONBALL_ID = "poison_ball";
    public static String FIREBALL_ID = "fireball";
    public static String FROSTBALL_ID = "frostball";


    @Override
    public void registerAll() {
        int cd = 10;
        int mana = 3;
        double RADIUS = 1.5D;

        SpellBuilder.of(FROSTBALL_ID, PlayStyle.INT, SpellConfiguration.Builder.instant(mana, cd)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Ice Shard",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.COLD))
                .manualDesc(
                        "Throw out a shard of ice, dealing " + SpellCalcs.ICEBALL.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + " and applying a stack of Bone Chill.")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.SNOWBALL.get(), 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SNOWBALL, 2D, 0.15D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SNOWFLAKE, 7D, 0.3D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.ICEBALL, Elements.Cold, RADIUS))
                .onExpire(PartBuilder.addExileEffectToEnemiesInAoe(ModEffects.BONE_CHILL.resourcePath, RADIUS, 20 * 6D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SNOWFLAKE, 15D, 0.5D))
                .levelReq(1)
                .build();

        SpellBuilder.of(FIREBALL_ID, PlayStyle.INT, SpellConfiguration.Builder.instant(mana, cd)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Fire Ball",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.FIRE))
                .manualDesc(
                        "Throw out a ball of fire, dealing " + SpellCalcs.FIREBALL.getLocDmgTooltip()
                                + " " + Elements.Fire.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)

                .onCast(PartBuilder.playSound(SoundEvents.BLAZE_SHOOT, 1D, 0.6D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.FIREBALL.get(), 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FLAME, 1D, 0.1D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.FALLING_LAVA, 1D, 0.5D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SMOKE, 1D, 0.01D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.FIREBALL, Elements.Fire, RADIUS))
                .onExpire(PartBuilder.playSound(SoundEvents.GENERIC_BURN, 1D, 2D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 3D, 1D))
                .levelReq(1)
                .build();


        SpellBuilder.of(POISONBALL_ID, PlayStyle.INT, SpellConfiguration.Builder.instant(mana, cd)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Poison Blast",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.CHAOS))
                .manualDesc(
                        "Blast out a ball of poison, dealing " + SpellCalcs.POISON_BALL.getLocDmgTooltip()
                                + " " + Elements.Shadow.getIconNameDmg())
                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SNOWBALL_THROW, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(SlashItems.SLIMEBALL.get(), 1D, 2.5D, SlashEntities.SIMPLE_PROJECTILE.get(), 8D, false)
                ))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.SNEEZE, 1D, 0.15D))
                .onTick(PartBuilder.particleOnTick(1D, ParticleTypes.ITEM_SLIME, 10D, 0.15D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.POISON_BALL, Elements.Shadow, RADIUS))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 100D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SNEEZE, 25D, 1D))
                .levelReq(10)

                .build();
    }
}
