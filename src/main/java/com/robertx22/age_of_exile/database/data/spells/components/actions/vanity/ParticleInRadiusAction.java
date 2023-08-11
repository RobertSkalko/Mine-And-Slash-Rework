package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle2d;
import com.robertx22.library_of_exile.utils.geometry.Circle3d;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.library_of_exile.utils.geometry.ShapeHelper;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class ParticleInRadiusAction extends SpellAction {

    public enum Shape {
        CIRCLE, CIRCLE_EDGE, CIRCLE_2D, CIRCLE_2D_EDGE;

    }

    public ParticleInRadiusAction() {
        super(Arrays.asList(PARTICLE_TYPE, RADIUS, PARTICLE_COUNT));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (ctx.world.isClientSide) {

            Shape shape = data.getParticleShape();

            SimpleParticleType particle = data.getParticle();

            float radius = data.get(RADIUS)
                    .floatValue();

            radius *= ctx.calculatedSpellData.data.getNumber(EventData.AREA_MULTI, 1F).number;

            float height = data.getOrDefault(HEIGHT, 0D)
                    .floatValue();
            int amount = data.get(PARTICLE_COUNT)
                    .intValue();

            amount *= ctx.calculatedSpellData.data.getNumber(EventData.AREA_MULTI, 1F).number;

            ParticleMotion motion = null;

            try {
                motion = ParticleMotion.valueOf(data.get(MOTION));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                motion = ParticleMotion.None;
            }

            float yrand = data.getOrDefault(Y_RANDOM, 0D)
                    .floatValue();

            float motionMulti = data.getOrDefault(MOTION_MULTI, 1D)
                    .floatValue();

            Vec3 pos = ctx.vecPos;
            Vec3 vel = ctx.sourceEntity.getDeltaMovement();

            ShapeHelper c = new Circle3d(new MyPosition(pos), radius);

            float finalRadius = radius;
            ParticleMotion finalMotion1 = motion;
            c.doXTimes(amount, x -> {
                MyPosition sp = null;
                float yRandom = (int) RandomUtils.RandomRange(0, yrand);

                if (shape == Shape.CIRCLE) {
                    sp = new MyPosition(new Circle3d(new MyPosition(pos), finalRadius).getRandomPos());
                }
                if (shape == Shape.CIRCLE_EDGE) {
                    sp = new MyPosition(new Circle3d(new MyPosition(pos), finalRadius).getRandomEdgePos());
                }
                if (shape == Shape.CIRCLE_2D) {
                    sp = new MyPosition(new Circle2d(new MyPosition(pos), finalRadius).getRandomPos());
                }
                if (shape == Shape.CIRCLE_2D_EDGE) {
                    sp = new MyPosition(new Circle2d(new MyPosition(pos), finalRadius).getEdgePos(x.multi));
                }

                sp = new MyPosition(sp.x - vel.x / 2F, sp.y - vel.y / 2 + height, sp.z - vel.z / 2);

                Vec3 v = finalMotion1.getMotion(new Vec3(sp.x, sp.y + yRandom, sp.z), ctx).multiply(motionMulti, motionMulti, motionMulti);

                // todo this could be buggy

                c.spawnParticle(ctx.world, sp.asVector3D(), particle, new MyPosition(v).asVector3D());
            });


        }
    }


    public MapHolder create(SimpleParticleType particle, Double count, Double radius) {
        return create(particle, count, radius, ParticleMotion.None);
    }

    public MapHolder create(SimpleParticleType particle, Double count, Double radius, ParticleMotion motion) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(RADIUS, radius);
        dmg.put(PARTICLE_COUNT, count);
        dmg.put(PARTICLE_TYPE, BuiltInRegistries.PARTICLE_TYPE.getKey(particle)
                .toString());
        dmg.put(MOTION, motion.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "particles_in_radius";
    }
}
