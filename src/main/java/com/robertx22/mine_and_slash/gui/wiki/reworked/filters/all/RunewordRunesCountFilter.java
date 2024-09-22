package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class RunewordRunesCountFilter extends GroupFilterEntry {

    int runes;

    public RunewordRunesCountFilter(int runes) {
        this.runes = runes;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof RuneWord u && u.runes.size() == runes;
    }

    @Override
    public MutableComponent getName() {
        return Component.literal(runes + "");
    }
}
