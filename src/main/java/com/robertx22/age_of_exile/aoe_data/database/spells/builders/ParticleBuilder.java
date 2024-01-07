package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleBuilder {


    private float radius;
    private float randomY = 0;
    private SimpleParticleType type;
    private float height = 0;
    private int amount = 1;
    private ParticleMotion motion = ParticleMotion.None;
    private int everyXticks = 1;

    public static ParticleBuilder of(SimpleParticleType type, Float radius) {
        ParticleBuilder b = new ParticleBuilder();
        b.radius = radius;
        b.type = type;
        return b;
    }

    public ParticleShape shape = ParticleShape.CIRCLE;


    public ParticleBuilder shape(ParticleShape shape) {
        this.shape = shape;
        return this;
    }

    public ParticleBuilder motion(ParticleMotion motion) {
        this.motion = motion;
        return this;
    }


    public ParticleBuilder randomY(float randomY) {
        this.randomY = randomY;
        return this;
    }


    public ParticleBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ParticleBuilder height(float height) {
        this.height = height;
        return this;
    }

    public ParticleBuilder tickReq(int ticks) {
        this.everyXticks = everyXticks;
        return this;
    }


    public ComponentPart build() {
        ComponentPart c = new ComponentPart();
        c.acts.add(SpellAction.PARTICLES_IN_RADIUS.create(type, (double) amount, (double) radius)
                .put(MapField.PARTICLE_SHAPE, shape.name())
                .put(MapField.MOTION, motion.name())
                .put(MapField.HEIGHT, (double) height)
                .put(MapField.Y_RANDOM, (double) randomY));

        if (everyXticks > 1) {
            c.tickRequirement((double) everyXticks);
        }
        return c;
    }

}
