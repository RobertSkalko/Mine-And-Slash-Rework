package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;

public class StackKeys {

    public static StackKey<MapItemData> MAP = of(StackSaving.MAP);
    public static StackKey<GearItemData> GEAR = of(StackSaving.GEARS);
    public static StackKey<JewelItemData> JEWEL = of(StackSaving.JEWEL);
    public static StackKey<PotentialData> POTENTIAL = of(StackSaving.POTENTIAL);
    public static StackKey<OmenData> OMEN = of(StackSaving.OMEN);
    public static StackKey<ProfessionToolData> TOOL = of(StackSaving.TOOL);
    public static StackKey<CustomItemData> CUSTOM = of(StackSaving.CUSTOM_DATA);


    private static <T> StackKey<T> of(ItemstackDataSaver<T> saver) {
        return new StackKey<>(saver.GUID(), x -> new StackData<>(x, saver));
    }


}
