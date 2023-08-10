package com.robertx22.age_of_exile.aoe_data.database.boss_spell;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.geometry.Circle2d;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;

public abstract class BossCastSpell extends BossSpell {


    public abstract String spellId();

    @Override
    public void onTick(LivingEntity en, int tick) {

        Circle2d c = new Circle2d(new MyPosition(en.position().add(0, 0.2F, 0)), 1);
 
        c.doXTimes((int) (50 * c.radius), x -> {
            c.spawnParticle(en.level(), c.getEdgePos(x.multi), ParticleTypes.WITCH);
        });
        c.radius = 2;
        c.doXTimes((int) (50 * c.radius), x -> {
            c.spawnParticle(en.level(), c.getEdgePos(x.multi), ParticleTypes.WITCH);

        });
        c.radius = 3;
        c.doXTimes((int) (50 * c.radius), x -> {
            c.spawnParticle(en.level(), c.getEdgePos(x.multi), ParticleTypes.WITCH);
        });

    }

    @Override
    public void onStartOverride(LivingEntity en) {


    }

    @Override
    public void onFinish(LivingEntity en) {
        getSpell().cast(new SpellCastContext(en, castTicks(), getSpell()));
    }


    private Spell getSpell() {
        return ExileDB.Spells().get(spellId());
    }

}
