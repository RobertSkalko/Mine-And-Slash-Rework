package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public abstract class LootChest implements ExileRegistry<LootChest>, IAutoLocName {

    public abstract ItemStack generateOne(LootChestData data);


    public int minLevelDrop() {
        return 0;
    }

    public abstract DropRequirement getDropReq();

    public abstract Item getKey();


    public List<ItemStack> generateAll(LootChestData data) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < data.num; i++) {
            list.add(generateOne(data));
        }
        return list;
    }

    public boolean isLocked() {
        return getKey() != null && getKey() != Items.AIR;
    }

    public abstract Item getChestItem(LootChestData data);

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LOOT_CHEST;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Lootboxes;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".chest_content." + GUID();
    }
}
