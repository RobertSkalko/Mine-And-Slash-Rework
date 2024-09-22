package com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import net.minecraft.network.chat.MutableComponent;

public class AffixForBaseGearFilter extends GroupFilterEntry {

    BaseGearType type;

    public AffixForBaseGearFilter(BaseGearType slot) {
        this.type = slot;
    }

    @Override
    public boolean isValid(BestiaryEntry e) {
        return e.obj instanceof Affix u && u.requirements.satisfiesAllRequirements(new GearRequestedFor(type));
    }

    @Override
    public MutableComponent getName() {
        return type.locName();
    }
}
