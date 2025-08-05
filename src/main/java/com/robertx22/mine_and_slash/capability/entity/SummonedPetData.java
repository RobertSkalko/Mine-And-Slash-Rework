package com.robertx22.mine_and_slash.capability.entity;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SummonPetAction;
import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SummonedPetData {

    public String spell = "";
    public int ticks = 0;
    public int aggro_radius = 10;
    public boolean counts_towards_max_summons;

    public void setup(Spell spell, int ticks, int aggro_radius, boolean counts_towards_max_summons) {
        this.spell = spell.GUID();
        this.ticks = ticks;
        this.aggro_radius = aggro_radius;
        this.counts_towards_max_summons = counts_towards_max_summons;
    }

    public boolean isEmpty() {
        return spell.isEmpty();
    }

    public Spell getSourceSpell() {
        return ExileDB.Spells().get(spell);
    }


    public void tick(LivingEntity en) {
        if (!en.level().isClientSide) {
            if (ticks == SummonPetAction.INFINITE_DURATION) {
                return;
            }

            if (ticks-- < 1) {
                SoundUtils.playSound(en, SoundEvents.GENERIC_DEATH);
                discard(en);
            }
        }
    }

    public void discard(LivingEntity en) {
        en.discard();

        if (!(en instanceof SummonEntity summonEntity)) {
            return;
        }

        onDeath(summonEntity);
    }

    public void onDeath(SummonEntity summonEntity) {
        if (summonEntity.getOwner() == null || !(summonEntity.getOwner() instanceof Player player)) {
            return;
        }

        Load.player(player).addSummonedType(spell, -1);
    }
}
