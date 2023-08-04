package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GemItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CreativeTabs {

    public static ItemGroup MyModTab = new ItemGroup("mmorpg_main_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SlashItems.GearItems.NECKLACES.get(VanillaMaterial.DIAMOND)
                    .get());
        }
    };

    public static ItemGroup GearSouls = new ItemGroup("mmorpg_gear_soul_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.SOUL_LANTERN);
        }
    };

    public static ItemGroup GemRuneCurrency = new ItemGroup("mmorpg_currency") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CurrencyItems.ORB_OF_TRANSMUTATION.get());
        }
    };
    public static ItemGroup Gems = new ItemGroup("mmorpg_gem") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GemItems.MAP.get(GemItem.GemType.GARNET)
                    .get(GemItem.GemRank.GLORIOUS)
                    .get());
        }
    };

   
}
