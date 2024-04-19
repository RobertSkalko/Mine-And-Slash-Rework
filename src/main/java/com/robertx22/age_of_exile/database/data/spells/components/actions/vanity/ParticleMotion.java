package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import net.minecraft.world.phys.Vec3;

public enum ParticleMotion {
    CasterLook {
        @Override
        public Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos) {
            Vec3 rot = casterAngle;
            return rot;
        }
    },
    Upwards {
        @Override
        public Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos) {
            return new Vec3(0, 1F, 0);
        }
    },
    Downwards {
        @Override
        public Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos) {
            return new Vec3(0, -1F, 0);
        }
    },
    OutwardMotion {
        @Override
        public Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos) {

            Vec3 c = sourcePos;
            c = new Vec3(c.x, 0, c.z);

            Vec3 p = particlePos;
            p = new Vec3(p.x, 0, p.z);

            return p.subtract(c);
        }
    },
    None {
        @Override
        public Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos) {
            return Vec3.ZERO;
        }
    };

    public abstract Vec3 getMotion(Vec3 particlePos, Vec3 casterAngle, Vec3 sourcePos);


}