package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.MutableComponent;

public class UniqueSlotFilter extends GroupFilterEntry {

    GearSlot slot;

    public UniqueSlotFilter(GearSlot slot) {
        this.slot = slot;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof UniqueGear u && u.getSlot().GUID().equals(slot.GUID());
    }

    @Override
    public MutableComponent getName() {
        return slot.locName();
    }
}
