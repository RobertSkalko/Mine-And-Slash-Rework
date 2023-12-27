package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class SummonedPetData {

    public String spell = "";
    public int ticks = 0;

    public void setup(Spell spell, int ticks) {
        this.spell = spell.GUID();
        this.ticks = ticks;
    }

    public boolean isEmpty() {
        return spell.isEmpty();
    }

    public Spell getSourceSpell() {
        return ExileDB.Spells().get(spell);
    }


    public void tick(LivingEntity en) {
        if (!en.level().isClientSide) {
            if (en.tickCount > ticks) {
                SoundUtils.playSound(en, SoundEvents.GENERIC_DEATH);
                en.discard();
            }
        }
    }

}
