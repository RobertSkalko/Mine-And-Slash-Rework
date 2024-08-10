package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LookUtils {

    public static Entity getEntityLookedAt(Entity e) {
        List<Entity> list = getEntityLookedAt(e, 32, true);
        return list.isEmpty() ? null : list.get(0);
    }

    public static List<LivingEntity> getLivingEntityLookedAt(Entity e, double distance, boolean onlyfirst) {
        return getEntityLookedAt(e, distance, onlyfirst).stream()
            .filter(x -> x instanceof LivingEntity)
            .map(x -> (LivingEntity) x)
            .collect(Collectors.toList());
    }

    public static List<Entity> getEntityLookedAt(Entity e, double distance, boolean onlyfirst) {
        Entity foundEntity = null;

        HitResult pos = raycast(e, distance);

        List<Entity> list = new ArrayList<>();

        Vec3 positionVector = e.position();
        if (e instanceof Player)
            positionVector = positionVector.add(0, e.getEyeHeight(), 0);

        if (pos != null)
            distance = pos.getLocation()
                .distanceTo(positionVector);

        Vec3 lookVector = e.getLookAngle();
        Vec3 reachVector = positionVector.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.getCommandSenderWorld()
            .getEntities(e, e.getBoundingBox()
                .inflate(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance)
                .expandTowards(1.2F, 1.2F, 1.2F));

        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.isPickable()) {
                float collisionBorderSize = entity.getPickRadius();
                AABB hitbox = entity.getBoundingBox()
                    .expandTowards(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Optional<Vec3> interceptPosition = hitbox.clip(positionVector, reachVector);
                Vec3 interceptVec = interceptPosition.orElse(null);

                if (hitbox.contains(positionVector)) {
                    if (0.0D < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = 0.0D;
                    }
                } else if (interceptVec != null) {
                    double distanceToEntity = positionVector.distanceTo(interceptVec);

                    if (distanceToEntity < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = distanceToEntity;
                    }
                }
            }

            if (lookedEntity != null && (minDistance < distance || pos == null))
                foundEntity = lookedEntity;
        }

        if (onlyfirst) {
            if (foundEntity != null) {
                list.add(foundEntity);
            }
        } else {
            list.addAll(entitiesInBoundingBox);
        }

        return list;
    }

    public static HitResult raycast(Entity e, double len) {
        Vec3 vec = new Vec3(e.getX(), e.getY(), e.getZ());
        if (e instanceof Player)
            vec = vec.add(new Vec3(0, e.getEyeHeight(), 0));

        Vec3 look = e.getLookAngle();
        if (look == null)
            return null;

        return raycast(e.getCommandSenderWorld(), vec, look, e, len);
    }

    public static HitResult raycast(Level world, Vec3 origin, Vec3 ray, Entity e,
                                         double len) {
        Vec3 end = origin.add(ray.normalize()
            .scale(len));
        HitResult pos = world.clip(new ClipContext(origin, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, e));
        return pos;
    }

}




