package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.MutableComponent;

public class RunewordSlotFilter extends GroupFilterEntry {

    GearSlot slot;

    public RunewordSlotFilter(GearSlot slot) {
        this.slot = slot;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof RuneWord u && u.slots.contains(slot.GUID());
    }

    @Override
    public MutableComponent getName() {
        return slot.locName();
    }
}
