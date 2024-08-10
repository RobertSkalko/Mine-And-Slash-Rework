package com.robertx22.mine_and_slash.database.data.spells.entities;

import com.robertx22.mine_and_slash.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class AutoAimingProj extends AbstractHurtingProjectile {
    public AutoAimingProj(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    public LivingEntity target;

    public float speed = 1;


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return true;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            if (getOwner() instanceof LivingEntity en) {
                Entity entity = pResult.getEntity();
                Entity entity1 = this.getOwner();
                boolean flag;
                if (entity1 instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity1;
                    flag = entity.hurt(this.damageSources().mobAttack(en), 8.0F);
                    if (flag) {
                        if (entity.isAlive()) {
                            this.doEnchantDamageEffects(livingentity, entity);
                        } else {
                            livingentity.heal(5.0F);
                        }
                    }
                } else {
                    flag = entity.hurt(this.damageSources().magic(), 5.0F);
                }

                if (flag && entity instanceof LivingEntity) {
                    LivingEntity livingentity1 = (LivingEntity) entity;
                    int i = 0;
                    if (this.level().getDifficulty() == Difficulty.NORMAL) {
                        i = 10;
                    } else if (this.level().getDifficulty() == Difficulty.HARD) {
                        i = 40;
                    }

                    if (i > 0) {
                        livingentity1.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * i, 1), this.getEffectSource());
                    }
                }

            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }

    }

    @Override
    public void tick() {
        super.tick();


        if (!level().isClientSide) {
            if (target == null || target.isDeadOrDying()) {

            } else {
                var move = ProjectileCastHelper.positionToVelocity(new MyPosition(this.position()), new MyPosition(target.getEyePosition()));
                move = move.normalize().multiply(speed, speed, speed);

                this.setDeltaMovement(move);
                this.hurtMarked = true;
            }

            if (this.tickCount > (20 * 15)) {
                this.discard();
            }
        }


    }
}
