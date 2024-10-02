package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.mine_and_slash.saveclasses.item_classes.rework.DataKey;
import com.robertx22.mine_and_slash.saveclasses.item_classes.rework.DataKeyHolder;
import com.robertx22.mine_and_slash.saveclasses.item_classes.rework.GenericDataHolder;

public class CustomItemData {
    public static KeyHolderClass KEYS = new KeyHolderClass();


    // with this custom data will be saved only when needed
    public static class KeyHolderClass extends DataKeyHolder {
        // public DataKey.RegistryKey<GearRarity> RARITY = of(new DataKey.RegistryKey<>("rar", ExileRegistryTypes.GEAR_RARITY));

        public DataKey.BooleanKey CORRUPT = of(new DataKey.BooleanKey("cr"));
        public DataKey.BooleanKey CRAFTED = of(new DataKey.BooleanKey("crafted"));
        public DataKey.BooleanKey SALVAGING_DISABLED = of(new DataKey.BooleanKey("sl"));
        // public DataKey.BooleanKey USED_SHARPENING_STONE = of(new DataKey.BooleanKey("us"));

        public DataKey.StringKey UNIQUE_ID = of(new DataKey.StringKey("uq"));

        public DataKey.IntKey QUALITY = of(new DataKey.IntKey("ql"));
        public DataKey.IntKey ENCHANT_TIMES = of(new DataKey.IntKey("et"));
        //public DataKey.IntKey LEVEL_TIMES = of(new DataKey.IntKey("lt"));

    }

    public GenericDataHolder data = new GenericDataHolder();


    public boolean isCorrupted() {
        return data.get(KEYS.CORRUPT);
    }
}
