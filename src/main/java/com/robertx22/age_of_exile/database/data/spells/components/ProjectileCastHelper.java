package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ProjectileCastHelper {

    LivingEntity caster;

    public boolean silent = false;
    public float apart = 3;
    public float shootSpeed = 1;
    public int projectilesAmount = 1;
    public boolean gravity = true;
    EntityType projectile;
    EntitySavedSpellData data;
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

    public ProjectileCastHelper(SpellCtx ctx, Vec3 pos, MapHolder holder, LivingEntity caster, EntityType projectile, EntitySavedSpellData data) {
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
            if (this.castType == CastType.SPREAD_OUT_HORIZONTAL) { // TODO
                if (projectilesAmount > 1) {
                    if (i < projectilesAmount / 2) {

                    } else if (i == projectilesAmount / 2) {
                        posAdd = Vec3.ZERO;
                    } else if (i > projectilesAmount / 2) {

                    }
                }
            }

            AbstractArrow en = (AbstractArrow) projectile.create(world);
            SpellUtils.shootProjectile(pos.add(posAdd), en, caster, shootSpeed, pitch, yaw + addYaw);
            SpellUtils.initSpellEntity(en, caster, data, holder);


            if (fallDown) {
                en.setDeltaMovement(0, -1, 0);
            }

            en.setSilent(silent);

            if (ctx.isCastedFromTotem) {

                BlockPos pos = en.blockPosition();

                EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
                        .finder(EntityFinder.SelectionType.RADIUS)
                        .searchFor(AllyOrEnemy.enemies)
                        .radius(15);

                boolean hastarget = false;
                for (LivingEntity target : finder.build()) {
                    Vec3 vel = positionToVelocity(new MyPosition(en.position()), new MyPosition(target.getEyePosition()));
                    vel = vel.multiply(shootSpeed, shootSpeed, shootSpeed);
                    en.setDeltaMovement(vel);
                    hastarget = true;
                    caster.level().addFreshEntity(en);
                    break;
                    // todo test this
                }

            } else {
                caster.level().addFreshEntity(en);
            }
        }

    }

    public Vec3 positionToVelocity(MyPosition current, MyPosition destination) {
        return destination.subtract(current).normalize();
    }

}

