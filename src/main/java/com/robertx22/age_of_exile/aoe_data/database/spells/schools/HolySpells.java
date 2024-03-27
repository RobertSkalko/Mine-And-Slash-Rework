package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.DamageBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ParticleBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

import java.util.Arrays;

public class HolySpells implements ExileRegistryInit {

    public static String HEALING_AURA_ID = "healing_aura";
    public static String WISH = "wish";
    public static String UNDYING_WILL = "undying_will";
    public static String BANISH = "banish";
    public static String INSPIRATION = "awaken_mana";
    public static String SHOOTING_STAR = "shooting_star";

    public static String CHARGE_ID = "charge";
    public static String GONG_STRIKE_ID = "gong_strike";
    public static String WHIRLWIND = "whirlwind";
    public static String TAUNT = "taunt";
    public static String SHOUT_WARN = "shout_warn";
    public static String PULL = "pull";

    public static String HOLY_MISSILES = "holy_missiles";

    public static String HYMN_OF_VALOR = "song_of_valor";
    public static String HYMN_OF_PERSERVANCE = "song_of_perseverance";
    public static String HYMN_OF_VIGOR = "song_of_vigor";

    @Override
    public void registerAll() {

        SpellBuilder.of(HOLY_MISSILES, PlayStyle.INT, SpellConfiguration.Builder.multiCast(30, 10, 30, 5)
                                .setSwingArm().setTrackingRadius(2), "Holy Missiles",
                        Arrays.asList(SpellTags.projectile, SpellTags.damage, SpellTags.HOLY, SpellTags.MISSILE))
                .manualDesc(
                        "Fire off Missiles that slowly move towards enemies and deal " + SpellCalcs.ICEBALL.getLocDmgTooltip()
                                + " " + Elements.Cold.getIconNameDmg() + ".")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.WITCH_THROW, 1D, 2D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.AIR, 1D, 0.3D, SlashEntities.SIMPLE_PROJECTILE.get(), 20 * 10D, false)
                        .put(MapField.TRACKS_ENEMIES, true)
                        .put(MapField.EXPIRE_ON_ENTITY_HIT, true)
                ))
                .onTick(ParticleBuilder.of(ParticleTypes.END_ROD, 0.03F).amount(3).build())
                .onTick(ParticleBuilder.of(ParticleTypes.ENCHANT, 0.03F).amount(3).build())

                .onHit(DamageBuilder.target(Elements.Holy, SpellCalcs.HOLY_MISSILES).build())

                .build();


        song(HYMN_OF_VALOR, "Hymn of Valor", ModEffects.VALOR);
        song(HYMN_OF_PERSERVANCE, "Hymn of Perseverance", ModEffects.PERSEVERANCE);
        song(HYMN_OF_VIGOR, "Hymn of Vigor", ModEffects.VIGOR);

        SpellBuilder.of(WHIRLWIND, PlayStyle.STR, SpellConfiguration.Builder.multiCast(10, 0, 100, 10)
                                .setSwingArm(), "Whirlwind",
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.PHYSICAL))
                .manualDesc("Spin and continuously strike enemies around you for " + SpellCalcs.WHIRLWIND.getLocDmgTooltip(Elements.Physical))

                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.giveSelfEffect(SlashPotions.KNOCKBACK_RESISTANCE.get(), 100D))
                .onCast(PartBuilder.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1D, 1D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 2D, 0.5D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.WHIRLWIND, Elements.Physical, 1.5D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 50D, 0.5D, 0.1D))
                )
                .levelReq(30)
                .build();

        SpellBuilder.of(CHARGE_ID, PlayStyle.STR, SpellConfiguration.Builder.multiCast(10, 20 * 10, 60, 60)
                        , "Charge",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.movement, SpellTags.PHYSICAL))
                .manualDesc(
                        "Charge in a direction, stopping upon first enemy hit to deal "
                                + SpellCalcs.CHARGE.getLocDmgTooltip() + " " + Elements.Physical.getIconNameDmg() + " in an area."

                )
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.ANCIENT_DEBRIS_STEP, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 0.4D, ParticleMotion.CasterLook)
                                .put(MapField.IGNORE_Y, true))
                        .addTarget(TargetSelector.CASTER.create()))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 20D, 1D, 0.5D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 1D, 0.5D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.CHARGE, Elements.Physical, 1.75D)
                        .addPerEntityHit(PartBuilder.playSound(SoundEvents.ANVIL_LAND, 1D, 1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.EFFECT, 100D, 0.5D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 100D, 0.5D, 0.1D))
                        .addPerEntityHit(PartBuilder.cancelSpell())
                )
                .levelReq(1)
                .build();

        SpellBuilder.of(TAUNT, PlayStyle.STR, SpellConfiguration.Builder.instant(0, 20 * 30)
                                .setSwingArm(), "Taunt",
                        Arrays.asList(SpellTags.area, SpellTags.BUFF))
                .manualDesc(
                        "Shout, making nearby enemies want to attack you. " +
                                "Generates " + SpellCalcs.TAUNT.getLocDmgTooltip() + " threat."
                )
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.SHIELD_BLOCK, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                        .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 20D, 3D))
                .levelReq(10)
                .build();


        SpellBuilder.of(PULL, PlayStyle.STR, SpellConfiguration.Builder.instant(5, 60 * 20), "Pull",
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.PHYSICAL))
                .manualDesc(
                        "Pull enemies in area to you, dealing " +
                                SpellCalcs.PULL.getLocDmgTooltip() + " " +
                                Elements.Physical.getIconNameDmg() + " and slowing them."
                )
                .onCast(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 1D))
                .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addActions(SpellAction.POTION.createGive(MobEffects.MOVEMENT_SLOWDOWN, 20D * 5))
                        .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL, Elements.Physical))
                        .addActions(SpellAction.EXILE_EFFECT.create(ModEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                        .addTarget(TargetSelector.AOE.create(8D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
                .levelReq(20)
                .build();

        SpellBuilder.of(GONG_STRIKE_ID, PlayStyle.STR, SpellConfiguration.Builder.instant(8, 20 * 10)
                                .setSwingArm(), "Gong Strike",
                        Arrays.asList(SpellTags.weapon_skill, SpellTags.area, SpellTags.damage, SpellTags.PHYSICAL))
                .manualDesc("Bash enemies around you for " +
                        SpellCalcs.GONG_STRIKE.getLocDmgTooltip(Elements.Physical))

                .weaponReq(CastingWeapon.MELEE_WEAPON)

                .onCast(PartBuilder.playSound(SoundEvents.ANVIL_PLACE, 1D, 1D))
                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .onCast(PartBuilder.damageInFront(SpellCalcs.GONG_STRIKE, Elements.Physical, 2D, 3D))
                .onCast(PartBuilder.addExileEffectToEnemiesInFront(ModEffects.STUN.resourcePath, 2D, 2D, 20D * 3))

                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CLOUD, 300D, 2D, 0.1D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 5D, 2D, 0.1D))

                .levelReq(1)
                .build();

        SpellBuilder.of(UNDYING_WILL, PlayStyle.STR, SpellConfiguration.Builder.instant(7, 20 * 60)
                        , "Undying Will",
                        Arrays.asList(SpellTags.BUFF))
                .manualDesc("Gives buff to self.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.RAVAGER_ROAR, 1D, 1D))
                .onCast(PartBuilder.giveSelfExileEffect(ModEffects.UNDYING_WILL, 20D * 10))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANTED_HIT, 50D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 50D, 1D))
                .levelReq(30)
                .build();

        SpellBuilder.of(INSPIRATION, PlayStyle.INT, SpellConfiguration.Builder.instant(0, 300 * 20), "Inspiration",
                        Arrays.asList(SpellTags.heal)
                )
                .manualDesc("Restores " + SpellCalcs.AWAKEN_MANA.getLocDmgTooltip() + " mana.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
                .onCast(PartBuilder.restoreManaToCaster(SpellCalcs.AWAKEN_MANA))
                .levelReq(10)
                .build();

        SpellBuilder.of(SHOOTING_STAR, PlayStyle.INT, SpellConfiguration.Builder.instant(10, 20)
                                .setSwingArm()
                                .applyCastSpeedToCooldown(), "Shooting Star",
                        Arrays.asList(SpellTags.projectile, SpellTags.heal))
                .manualDesc("Shoots a star that heals allies for " + SpellCalcs.SHOOTING_STAR.getLocDmgTooltip() + " health on hit.")

                .weaponReq(CastingWeapon.MAGE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.BEACON_ACTIVATE, 1D, 1.7D))
                .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                        .put(MapField.HITS_ALLIES, true)))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 3D, 0.5D).tick(1D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SOUL_FIRE_FLAME, 5D, 0.5D).tick(1D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 1D, 0.7D).tick(1D))
                .onExpire(PartBuilder.healInAoe(SpellCalcs.SHOOTING_STAR, 2D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SOUL_FIRE_FLAME, 10D, 1D))
                .levelReq(10)
                .build();

        SpellBuilder.of(HEALING_AURA_ID, PlayStyle.INT, SpellConfiguration.Builder.multiCast(15, 20 * 30, 60, 3), "Healing Aria",
                        Arrays.asList(SpellTags.area, SpellTags.heal, SpellTags.song))
                .manualDesc(
                        "Heal allies around you for " + SpellCalcs.HEALING_AURA.getLocDmgTooltip() +
                                " health")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
                .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_AURA, 2D))
                .levelReq(1)
                .build();

        SpellBuilder.of(WISH, PlayStyle.INT, SpellConfiguration.Builder.instant(20, 10)
                                .setChargesAndRegen(WISH, 3, 20 * 30), "Wish",
                        Arrays.asList(SpellTags.heal))
                .manualDesc(
                        "Heal allies around you for " + SpellCalcs.WISH.getLocDmgTooltip() +
                                " health")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 5D, 0.2D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 50D, 5D, 0.2D))
                .onCast(PartBuilder.healInAoe(SpellCalcs.WISH, 5D))
                .levelReq(20)
                .build();

    }

    static void song(String id, String name, EffectCtx effect) {

        SpellBuilder.of(id, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(10, 20 * 10, 10)
                        , name,
                        Arrays.asList(SpellTags.area, SpellTags.song, SpellTags.BUFF))
                .manualDesc(
                        "Give a stack of " + effect.locname + " to all allies around you."
                )
                .onCast(PartBuilder.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1D, 1D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.NOTE, 50D, 3D))
                .onCast(PartBuilder.giveExileEffectToAlliesInRadius(10D, effect.resourcePath, 20 * 30D))
                .levelReq(10)
                .build();
    }
}
