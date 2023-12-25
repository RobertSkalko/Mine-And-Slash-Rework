package com.robertx22.age_of_exile.database.data.spells.components;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.AoeSelector;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;

public class ProjectileCastHelper {

    LivingEntity caster;

    public boolean silent = false;
    public float apart = 3;
    public float shootSpeed = 1;
    public int projectilesAmount = 1;
    public boolean gravity = true;
    EntityType projectile;
    CalculatedSpellData data;
    MapHolder holder;
    Vec3 pos;

    public CastType castType = CastType.SPREAD_OUT_IN_RADIUS;

    public enum CastType {
        SPREAD_OUT_IN_RADIUS, SPREAD_OUT_HORIZONTAL
    }

    public float pitch;

    public float yaw;

    public boolean fallDown = false;
    public boolean targetEnemy = false;

    SpellCtx ctx;

    public ProjectileCastHelper(SpellCtx ctx, Vec3 pos, MapHolder holder, LivingEntity caster, EntityType projectile, CalculatedSpellData data) {
        this.ctx = ctx;
        this.projectile = projectile;
        this.caster = caster;
        this.data = data;
        this.holder = holder;
        this.pos = pos;

        this.pitch = caster.getXRot();
        this.yaw = caster.getYRot();
    }

    public void cast() {

        if (data.data.getBoolean(EventData.BARRAGE)) {
            this.castType = CastType.SPREAD_OUT_HORIZONTAL;
        }

        Level world = caster.level();

        float addYaw = 0;

        Vec3 posAdd = new Vec3(0, 0, 0);

        for (int i = 0; i < projectilesAmount; i++) {

            if (this.castType == CastType.SPREAD_OUT_IN_RADIUS) {
                if (projectilesAmount > 1) {
                    if (i < projectilesAmount / 2) {
                        addYaw -= apart / projectilesAmount;
                    } else if (i == projectilesAmount / 2) {
                        addYaw = 0;
                    } else if (i > projectilesAmount / 2) {
                        addYaw += apart / projectilesAmount;
                    }
                }
            }

            if (this.castType == CastType.SPREAD_OUT_HORIZONTAL) { // this seems to work
                if (projectilesAmount > 1) {
                    int m = 1 + i;
                    int off = projectilesAmount / 2;
                    if (i < projectilesAmount / 2) {
                        posAdd = getSideVelocity(caster).multiply(m, m, m);
                    } else if (i == projectilesAmount / 2) {
                        posAdd = Vec3.ZERO;
                    } else if (i > projectilesAmount / 2) {
                        posAdd = geOppositeSideVelocity(caster).multiply(i - off, i - off, i - off);
                    }
                }
            }

            AbstractArrow en = (AbstractArrow) projectile.create(world);

            SpellUtils.shootProjectile(pos.add(posAdd), en, ctx.getPositionEntity(), shootSpeed, pitch, yaw + addYaw);
            SpellUtils.initSpellEntity(en, caster, data, holder);

            if(holder.has(MapField.SKILL_FX)){
                FX fx = FXHelper.getFX(holder.getSkillFXResourceLocation());
                new EntityEffect(fx, ctx.world, en).start();
            }

            if (fallDown) {
                en.setDeltaMovement(0, -1, 0);
            }

            en.setSilent(silent);

            if (targetEnemy) {

                BlockPos pos = en.blockPosition();

                EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
                        .finder(EntityFinder.SelectionType.RADIUS)
                        .searchFor(AllyOrEnemy.enemies)
                        .predicate(x -> AoeSelector.canHit(ctx.getPos(), x))
                        .radius(15);


                LivingEntity target = finder.getClosest();

                if (target != null) {
                    Vec3 vel = positionToVelocity(new MyPosition(en.position()), new MyPosition(target.getEyePosition()));
                    vel = vel.multiply(shootSpeed, shootSpeed, shootSpeed);
                    //en.setDeltaMovement(vel);


                    en.shoot(vel.x, vel.y, vel.z, 1, 0);

                    caster.level().addFreshEntity(en);
                    break;
                }


            } else {
                caster.level().addFreshEntity(en);
            }
        }

    }

    public static Vec3 positionToVelocity(MyPosition current, MyPosition destination) {
        return destination.subtract(current).normalize();
    }

    public Vec3 getSideVelocity(Entity shooter) {
        float yaw = shooter.getYRot() - 90;
        float x = -Math.sin(yaw * 0.017453292F) * Math.cos(shooter.getXRot() * 0.017453292F);
        float y = -Math.sin((shooter.getXRot()) * 0.017453292F);
        float z = Math.cos(yaw * 0.017453292F) * Math.cos(shooter.getXRot() * 0.017453292F);
        return (new Vec3(x, y, z)).normalize();
    }

    public Vec3 geOppositeSideVelocity(Entity shooter) {
        return getSideVelocity(shooter).multiply(-1, -1, -1);
    }

}

