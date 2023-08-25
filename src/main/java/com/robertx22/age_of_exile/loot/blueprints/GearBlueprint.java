package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearTypePart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.age_of_exile.loot.generators.SoulLootGen;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GearBlueprint extends RarityItemBlueprint implements ITypeBlueprint {

    public Item item = Items.AIR;


    public GearBlueprint(LootInfo info) {
        super(info);

        this.rarity.setupChances(info);
    }


    public GearTypePart gearItemSlot = new GearTypePart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);


    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {
        this.item = rarity.get().getLootableItem(this.gearItemSlot.get().getGearSlot());

        if (item == Items.AIR) {
            return SoulLootGen.createSoulBasedOnGear(this);
        } else {
            return GearCreationUtils.CreateStack(createData(), item); // todo need to find item
        }
    }

    @Override
    public void setType(String type) {
        this.gearItemSlot.set(type);
    }
}
