package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;

import com.robertx22.age_of_exile.uncommon.interfaces.IBaseAutoLoc.AutoLocGroup;

public interface Rarity extends IWeighted, IAutoLocName, JsonExileRegistry<Rarity> {

    String GUID();

    default String Color() {
        return textFormatting().toString();
    }

    int Weight();

    ChatFormatting textFormatting();

    @Override
    public default AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rarities;
    }

}
