package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;

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
