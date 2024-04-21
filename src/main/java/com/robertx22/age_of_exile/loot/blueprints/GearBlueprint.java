package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearTypePart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.age_of_exile.loot.generators.SoulLootGen;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.resources.ResourceLocation;
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

        this.item = this.gearItemSlot.get().getRandomItem(rarity.get());

        if (rarity.get().is_unique_item && uniquePart.get() != null) {
            this.item = uniquePart.get().getBaseGear().getRandomItem(rarity.get());

            // because uniquepart.get overrides the base gear type, we need to first check if it's unique item
            if (!uniquePart.get().force_item_id.isEmpty()) {
                item = VanillaUTIL.REGISTRY.items().get(new ResourceLocation(uniquePart.get().force_item_id));
            }
        }

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
