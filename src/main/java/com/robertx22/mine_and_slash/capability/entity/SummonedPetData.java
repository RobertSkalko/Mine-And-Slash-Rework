package com.robertx22.mine_and_slash.capability.entity;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class SummonedPetData {

    public String spell = "";
    public int ticks = 0;
    public int aggro_radius = 10;

    public void setup(Spell spell, int ticks, int aggro_radius) {
        this.spell = spell.GUID();
        this.ticks = ticks;
        this.aggro_radius = aggro_radius;
    }

    public boolean isEmpty() {
        return spell.isEmpty();
    }

    public Spell getSourceSpell() {
        return ExileDB.Spells().get(spell);
    }


    public void tick(LivingEntity en) {
        if (!en.level().isClientSide) {
            if (ticks-- < 1) {
                SoundUtils.playSound(en, SoundEvents.GENERIC_DEATH);
                en.discard();
            }
        }
    }

}
