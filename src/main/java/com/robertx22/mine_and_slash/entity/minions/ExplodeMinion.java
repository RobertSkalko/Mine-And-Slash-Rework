package com.robertx22.mine_and_slash.entity.minions;

import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.BossSpells;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle3d;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class ExplodeMinion extends Minion {

    public ExplodeMinion(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }


    @Override
    public void tick() {
        try {
            super.tick();
            if (!this.level().isClientSide) {

                if (!this.isDeadOrDying()) {
                    if (tickCount == (20 * 5)) {
                        SoundUtils.playSound(this, SoundEvents.CREEPER_PRIMED, 0.5F, 1);
                    }
                    if (tickCount == (20 * 10)) {
                        SoundUtils.playSound(this, SoundEvents.CREEPER_PRIMED, 1, 1);
                    }


                    if (this.tickCount > (20 * 15)) {
                        Circle3d c = new Circle3d(new MyPosition(position().add(0, 0.2F, 0)), 1);
                        c.doXTimes((int) (50 * c.radius), x -> {
                            c.spawnParticle(level(), c.getRandomPos(), ParticleTypes.EXPLOSION);
                        });

                        // todo why did this do so little dmg, balance problem or bug?

                        Spell spell = ExileDB.Spells().get(BossSpells.MINION_EXPLOSION);
                        spell.cast(new SpellCastContext(this, 0, spell));
                        this.discard();
                    }
                }

            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
    }
}
