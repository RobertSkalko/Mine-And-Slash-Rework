package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import net.minecraft.network.chat.MutableComponent;

public class SpellTagFilter extends GroupFilterEntry {

    SpellTag tag;

    public SpellTagFilter(SpellTag tag) {
        this.tag = tag;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof Spell u && u.config.tags.contains(tag);
    }

    @Override
    public MutableComponent getName() {
        return tag.locName();
    }
}
