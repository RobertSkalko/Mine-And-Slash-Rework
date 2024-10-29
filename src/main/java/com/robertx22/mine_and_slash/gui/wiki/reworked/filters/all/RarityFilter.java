package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.network.chat.MutableComponent;

public class RarityFilter extends GroupFilterEntry {
    GearRarity rar;

    public RarityFilter(GearRarity rar) {
        this.rar = rar;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof IRarity u && u.getRarity().GUID().equals(rar.GUID());
    }

    @Override
    public MutableComponent getName() {
        return rar.locName();
    }
}
