package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;

public class SummonedPetData {

    public String spell = "";
    public int ticks = 0;

    public void setup(Spell spell, int ticks) {
        this.spell = spell.GUID();
        this.ticks = ticks;
    }
    

    public void tick(SummonEntity en) {
        if (!en.level().isClientSide) {
            if (en.tickCount > ticks) {
                SoundUtils.playSound(en, SoundEvents.GENERIC_DEATH);
                en.discard();
            }
        }
    }

}
