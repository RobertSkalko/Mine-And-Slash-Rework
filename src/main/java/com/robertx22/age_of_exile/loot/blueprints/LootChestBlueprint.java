package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.LootChestPart;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

public class LootChestBlueprint extends RarityItemBlueprint implements ITypeBlueprint {

    public LootChestBlueprint(LootInfo info) {
        super(info);
        this.rarity.chanceForHigherRarity = 75;
    }

    public LootChestPart type = new LootChestPart(this);

    @Override
    public ItemStack generate() {


        LootChestData data = createData();

        ItemStack stack = data.getLootChest().getChestItem(data).getDefaultInstance();

        StackSaving.LOOT_CHEST.saveTo(stack, data);

        return stack;

    }

    public LootChestData createData() {
        LootChestData data = new LootChestData();


        data.num = RandomUtils.RandomRange(4, 8);
        data.lvl = info.level;
        data.rar = this.rarity.get().GUID();
        data.id = this.type.get().GUID();

        return data;
    }


    @Override
    public void setType(String type) {
        this.type.set(ExileDB.LootChests().get(type));
    }
}
