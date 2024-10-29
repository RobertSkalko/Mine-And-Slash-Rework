package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.content.ubers.UberBossTier;
import com.robertx22.mine_and_slash.loot.LootInfo;
import net.minecraft.world.item.ItemStack;

public class WatcherEyeBlueprint extends ItemBlueprint {

    public WatcherEyeBlueprint(LootInfo info) {
        super(info);
    }

    public UberBossTier tier = UberBossTier.getTierFromMap(info.level);

    @Override
    protected ItemStack generate() {
        JewelBlueprint b = new JewelBlueprint(info);

        b.isEye = true;
        b.auraAffixes = tier.watcherEyeAffixes;
        b.level.set(info.level);

        return b.createStack();
    }
}
