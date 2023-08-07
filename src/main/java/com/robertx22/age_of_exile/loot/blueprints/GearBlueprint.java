package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearItemSlotPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.age_of_exile.loot.generators.GearSoulLootGen;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GearBlueprint extends ItemBlueprint {

    public Item item = Items.AIR;

    public GearBlueprint(int lvl) {
        super(lvl);
    }

    public GearBlueprint(LootInfo info) {
        super(info);

        this.info = info;
        this.rarity.setupChances(info);
    }

    public GearBlueprint(int lvl, int tier) {
        super(lvl, tier);

    }

    public GearItemSlotPart gearItemSlot = new GearItemSlotPart(this);
    public GearRarityPart rarity = new GearRarityPart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);


    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {

        this.item = rarity.get().getLootableItem(this.gearItemSlot.get().getGearSlot());

        // todo need to make this drop normal items not souls, or make souls rare?

        if (item == Items.AIR) {
            return GearSoulLootGen.createSoulBasedOnGear(this);
        } else {
            return GearCreationUtils.CreateStack(createData(), item); // todo need to find item
        }
    }

}
