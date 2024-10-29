package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.MutableComponent;

public class ItemTypeTargetFilter extends GroupFilterEntry {

    WorksOnBlock.ItemType type;

    public ItemTypeTargetFilter(WorksOnBlock.ItemType type) {
        this.type = type;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof ExileCurrency u && u.item_type.contains(type);
    }

    @Override
    public MutableComponent getName() {
        return type.name.locName();
    }
}
