package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;

public class StatRangeInfo implements Cloneable {


    public StatRangeInfo(ModRange minmax) {
        this.hasAltDown = Screen.hasAltDown();
        this.hasShiftDown = Screen.hasShiftDown();

        this.player = ClientOnly.getPlayer();
        this.unitdata = Load.Unit(player);

        this.minmax = minmax;
    }

    public Player player;
    public EntityData unitdata;
    public ModRange minmax = ModRange.of(new MinMax(0, 100));
    public StatTooltipType statTooltipType = StatTooltipType.NORMAL;

    public boolean hasAltDown = false;
    public boolean hasShiftDown = false;

    public boolean shouldShowDescriptions() {
        return hasAltDown && !hasShiftDown;
    }

    public boolean useInDepthStats() {
        return !hasAltDown && hasShiftDown;
    }

}
