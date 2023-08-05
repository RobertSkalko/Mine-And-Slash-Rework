package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DashUtils {

    public enum Way {
        FORWARDS, BACKWARDS, UPWARDS;
    }

    public enum Strength {
        SHORT_DISTANCE(0.4F), MEDIUM_DISTANCE(0.8F), LARGE_DISTANCE(1.5F);

        Strength(float num) {
            this.num = num;
        }

        public float num;

    }

    public static void knockback(LivingEntity source, LivingEntity target) {

        target.knockback(0.5F, source.getX() - target.getX(), source.getZ() - target.getZ());

        if (target instanceof ServerPlayer) {
            ((ServerPlayer) target).connection.send(new ClientboundSetEntityMotionPacket(target));
            target.hurtMarked = false;
        }
    }

    // copied from knockback, without knockback resist
    public static void push(Entity en, float f, double d, double e) {

        if (f > 0.0F) {
            en.hasImpulse = true;
            Vec3 vec3d = en.getDeltaMovement();
            Vec3 vec3d2 = (new Vec3(d, 0.0D, e)).normalize()
                .scale((double) f);
            en.setDeltaMovement(vec3d.x / 2.0D - vec3d2.x, en.isOnGround() ? Math.min(0.4D, vec3d.y / 2.0D + (double) f) : vec3d.y, vec3d.z / 2.0D - vec3d2.z);
        }
    }

    public static void dash(LivingEntity entity, float str, Way way) {

        double x;
        double z;

        final float importantValue = 0.017453292f;

        if (way == Way.BACKWARDS) {
            x = (double) -Mth.sin(entity.yRot * importantValue);
            z = (double) (Mth.cos(entity.yRot * importantValue));
        }
        if (way == Way.UPWARDS) {
            entity.push(0, str, 0);
            return;
        } else {
            x = (double) Mth.sin(entity.yRot * importantValue);
            z = (double) -(Mth.cos(entity.yRot * importantValue));
        }

        push(entity, str, x, z);

        if (entity instanceof ServerPlayer) {
            ((ServerPlayer) entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
            entity.hurtMarked = false;
        }
    }

    public static void dash(LivingEntity entity, Strength str, Way way) {
        dash(entity, str.num, way);
    }

}
