package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class AffixTypeFilter extends GroupFilterEntry {


    Affix.AffixSlot type;

    public AffixTypeFilter(Affix.AffixSlot type) {
        this.type = type;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {

        if (e.obj instanceof Affix u) {
            if (u.type == this.type) {
                return true;

            }

        }
        return false;
    }

    @Override
    public MutableComponent getName() {
        // todo loc
        return Component.literal(type.name());
    }
}
