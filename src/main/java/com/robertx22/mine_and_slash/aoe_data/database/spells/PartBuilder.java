package com.robertx22.mine_and_slash.aoe_data.database.spells;

import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.mine_and_slash.database.data.spells.components.ComponentPart;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.AggroAction;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.ExileEffectAction.GiveOrTake;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.mine_and_slash.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.mine_and_slash.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.value_calc.ValueCalculation;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.DashUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;

public class PartBuilder {

    public static ComponentPart empty() {
        ComponentPart c = new ComponentPart();
        return c;
    }

    public static ComponentPart damage(ValueCalculation calc, Elements ele) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }


    public static ComponentPart exileEffect(String effect, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart restoreManaInRadius(ValueCalculation calc, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_MANA.create(calc));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static ComponentPart restoreManaToCaster(ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_MANA.create(calc));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart aoeSelectEnemies(Double radius, Double chance) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.AGGRO.create(SpellCalcs.CHAOS_TOTEM, AggroAction.Type.AGGRO)); // todo
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies).put(MapField.SELECTION_CHANCE, chance));
        return c;
    }

    public static ComponentPart damageInAoe(ValueCalculation calc, Elements ele, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart damageInAoeIfCharmed(ValueCalculation calc, Elements ele, Double radius) {
        ComponentPart c = new ComponentPart();

        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
        c.en_preds.add(EffectCondition.HAS_MNS_EFFECT.create(ModEffects.CHARM)); // todo this doesnt work
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart damageInAoeIfBoneChilled(ValueCalculation calc, Elements ele, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
        c.en_preds.add(EffectCondition.HAS_MNS_EFFECT.create(ModEffects.BONE_CHILL)); // todo this doesnt work
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart damageInFront(ValueCalculation calc, Elements ele, Double distance, Double width) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, ele));
        c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, AllyOrEnemy.enemies));
        return c;
    }


    public static ComponentPart healInAoe(ValueCalculation calc, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }


    public static ComponentPart healCaster(ValueCalculation calc) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.RESTORE_HEALTH.create(calc));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart knockback(Double str) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.KNOCKBACK.create(str));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart pushCaster(DashUtils.Way way, DashUtils.Strength str) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PUSH.create((double) str.num, way));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart onTickAction(Double ticks, MapHolder action) {
        ComponentPart c = new ComponentPart();
        c.acts.add(action);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart justAction(MapHolder data) {
        ComponentPart c = new ComponentPart();
        c.acts.add(data);
        return c;
    }

    public static ComponentPart particleOnTick(Double ticks, SimpleParticleType particle, Double count, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart aoeParticles(SimpleParticleType particle, Double count, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius));
        return c;
    }

    public static ComponentPart tickCloudParticle(Double ticks, SimpleParticleType particle, Double count, Double radius) {
        return tickCloudParticle(ticks, particle, count, radius, 2.5D);
    }

    public static ComponentPart tickCloudParticle(Double ticks, SimpleParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = cloudParticles(particle, count, radius, randomY);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart onTickCleanseInRadius(Double ticks, MobEffect effect, Double radius) {
        ComponentPart c = cleanseInRadius(effect, radius);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart onTickRemoveNegativeEffectInRadius(Double ticks, Double radius) {
        ComponentPart c = removeNegativeEffectInRadius(radius);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart removeNegativeEffectInRadius(Double radius) {
        ComponentPart c = new ComponentPart();
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        c.acts.add(SpellAction.POTION.removeNegative(1D));
        return c;
    }

    public static ComponentPart cleanseInRadius(MobEffect effect, Double radius) {
        ComponentPart c = new ComponentPart();
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        c.acts.add(SpellAction.POTION.createRemove(effect));
        return c;
    }

    public static ComponentPart cloudParticles(SimpleParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D.name())
                .put(MapField.Y_RANDOM, randomY));
        return c;
    }

    public static ComponentPart tickGroundParticle(Double ticks, SimpleParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = groundParticles(particle, count, radius, randomY);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;

    }

    public static ComponentPart cancelSpell() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.CANCEL_CAST.create());
        return c;
    }

    public static ComponentPart groundEdgeParticles(SimpleParticleType particle, Double count, Double radius, Double randomY) {
        return groundEdgeParticles(particle, count, radius, randomY, ParticleMotion.None);
    }


    public static ComponentPart nova(SimpleParticleType particle, Double count, Double radius, Double motionMulti) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D.name())
                .put(MapField.Y_RANDOM, 0.2D)
                .put(MapField.HEIGHT, 0.5D)
                .put(MapField.MOTION, ParticleMotion.OutwardMotion.name())
                .put(MapField.MOTION_MULTI, motionMulti)
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D_EDGE.name()));
        return c;
    }

    public static ComponentPart groundEdgeParticles(SimpleParticleType particle, Double count, Double radius, Double randomY, ParticleMotion motion) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D)
                .put(MapField.MOTION, motion.name())
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D_EDGE.name()));
        return c;
    }

    public static ComponentPart groundParticles(SimpleParticleType particle, Double count, Double radius, Double randomY) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(particle, count, radius)
                .put(MapField.PARTICLE_SHAPE, ParticleShape.CIRCLE_2D.name())
                .put(MapField.Y_RANDOM, randomY)
                .put(MapField.HEIGHT, 0.5D));
        return c;
    }

    public static ComponentPart particleOnTick(Double ticks, MapHolder map) {
        ComponentPart c = new ComponentPart();
        c.acts.add(map);
        c.ifs.add(EffectCondition.EVERY_X_TICKS.create(ticks));
        return c;
    }

    public static ComponentPart playSound(SoundEvent sound, Double volume, Double pitch) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PLAY_SOUND.create(sound, volume, pitch));
        return c;
    }


    public static ComponentPart swordSweepParticles() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.SWORD_SWEEP_PARTICLES.create());
        return c;
    }

    public static ComponentPart giveSelfExileEffect(String effect, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart giveSelfExileEffect(EffectCtx ctx, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(ctx.resourcePath, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart giveSelfEffect(MobEffect effect, Double dura) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createGive(effect, dura));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart giveEffectToAlliesInRadius(MobEffect effect, Double dura, Double radius) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createGive(effect, dura));
        c.targets.add(BaseTargetSelector.AOE.alliesInRadius(radius));
        return c;
    }


    public static ComponentPart giveExileEffectToAlliesInRadius(Double radius, String effect, Double dura) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, dura));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static ComponentPart giveSelfEffect(MobEffect effect, Double dura, Double str) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createGive(effect, dura)
                .put(MapField.POTION_STRENGTH, str));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart removeSelfEffect(MobEffect effect) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createRemove(effect));
        c.targets.add(BaseTargetSelector.CASTER.create());
        return c;
    }

    public static ComponentPart giveToAlliesInRadius(String effect, Double radius, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies));
        return c;
    }

    public static ComponentPart addExileEffectToEnemiesInAoe(String effect, Double radius, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart removeExileEffectStacksToTarget(String effect) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.REMOVE_STACKS, 20D));
        c.targets.add(BaseTargetSelector.TARGET.create());
        return c;
    }

    public static ComponentPart addEffectToEnemiesInAoe(MobEffect effect, Double radius, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.POTION.createGive(effect, duration));
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart selectSummons(Double radius) {
        ComponentPart c = new ComponentPart();
        c.targets.add(BaseTargetSelector.AOE.create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.casters_summons));
        return c;
    }

    public static ComponentPart addExileEffectToEnemiesInFront(String effect, Double distance, Double width, Double duration) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.EXILE_EFFECT.create(effect, GiveOrTake.GIVE_STACKS, duration));
        c.targets.add(BaseTargetSelector.IN_FRONT.create(distance, width, AllyOrEnemy.enemies));
        return c;
    }

    public static ComponentPart playSoundPerTarget(SoundEvent sound, Double volume, Double pitch) {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PLAY_SOUND_PER_TARGET.create(sound, volume, pitch));
        return c;
    }

}
