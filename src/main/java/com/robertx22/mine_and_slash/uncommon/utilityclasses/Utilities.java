package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public final class Utilities {

    public static LivingEntity getLivingEntityByUUID(Level world, UUID id) {

        Entity en = getEntityByUUID(world, id);

        if (en instanceof LivingEntity) {
            return (LivingEntity) en;
        } else {
            return null;
        }

    }

    public static Entity getEntityByUUID(Level world, UUID id) {

        if (id == null)
            return null;

        if (world.isClientSide) {
            return ClientOnly.getEntityByUUID(world, id);
        } else {
            return ServerOnly.getEntityByUUID(world, id);
        }

    }

    public static Vec3 getEndOfLook(Entity entity, double distance) {
        return entity.getEyePosition(0.5F)
                .add(entity.getLookAngle()
                        .scale(distance));
    }

    public static void spawnParticlesForTesting(AABB aabb, Level world) {
        if (!world.isClientSide) {
            if (aabb.getSize() < 10) {
                for (double x = aabb.minX; x < aabb.maxX; x += 0.3F) {
                    for (double y = aabb.minY; y < aabb.maxY; y += 1F) {
                        for (double z = aabb.minZ; z < aabb.maxZ; z += 0.3F) {

                            for (int i = 0; i < 1; i++) {
                                ((ServerLevel) world).sendParticles(
                                        ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, 0.0D, 0.0D, 0.0D, 0F);
                            }
                        }
                    }
                }
            }
        }
    }

}
