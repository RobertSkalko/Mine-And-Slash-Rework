package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityData data;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public SpellStatsCalculationEvent event;
    public EntitySavedSpellData calcData;
    public Unit unit;

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;
        this.spell = spell;
        this.data = Load.Unit(caster);

        Objects.requireNonNull(spell);

     
        this.calcData = EntitySavedSpellData.create(data.getLevel(), caster, spell);

        this.event = new SpellStatsCalculationEvent(this.calcData, caster, spell.GUID());
        event.Activate();

        int castTicks = (int) event.data.getNumber(EventData.CAST_TICKS).number;
        this.isLastCastTick = castTicks == ticksInUse;


        // todo need better ways
        if (caster instanceof Player p) {
            try {
                int slot = Load.playerRPGData(p).getSkillGemInventory().getSpellGem(this.spell).getHotbarSlot();
                if (slot != -1) {
                    this.unit = new Unit(); // todo test if this works
                    this.unit.recalculateStats(caster, data, null, slot);
                }
            } catch (Exception e) {
                this.unit = this.data.getUnit();
            }

        } else {
            this.unit = this.data.getUnit();
        }

    }
}
