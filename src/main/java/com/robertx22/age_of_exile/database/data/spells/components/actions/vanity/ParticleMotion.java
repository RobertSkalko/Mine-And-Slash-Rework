package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.world.phys.Vec3;

public enum ParticleMotion {
    CasterLook {
        @Override
        public Vec3 getMotion(Vec3 particlePos, SpellCtx ctx) {
            Vec3 rot = ctx.caster.getLookAngle();
            return rot;
        }
    },
    Upwards {
        @Override
        public Vec3 getMotion(Vec3 particlePos, SpellCtx ctx) {
            return new Vec3(0, 1F, 0);
        }
    },
    Downwards {
        @Override
        public Vec3 getMotion(Vec3 particlePos, SpellCtx ctx) {
            return new Vec3(0, -1F, 0);
        }
    },
    OutwardMotion {
        @Override
        public Vec3 getMotion(Vec3 particlePos, SpellCtx ctx) {

            Vec3 c = ctx.vecPos;
            c = new Vec3(c.x, 0, c.z);

            Vec3 p = particlePos;
            p = new Vec3(p.x, 0, p.z);

            return p.subtract(c);
        }
    },
    None {
        @Override
        public Vec3 getMotion(Vec3 particlePos, SpellCtx ctx) {
            return Vec3.ZERO;
        }
    };

    public abstract Vec3 getMotion(Vec3 particlePos, SpellCtx ctx);

    public Vec3 getMotion2(Vec3 particlePos, SpellCtx ctx) {
        var c = getMotion(particlePos, ctx);
        return new Vec3(c.x, c.y, c.z);

    }

}