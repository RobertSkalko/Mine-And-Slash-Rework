package com.robertx22.age_of_exile.saveclasses.jewel;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import net.minecraft.world.item.ItemStack;

public class CraftedUniqueJewelData {
    public static String CRAFTED_UNIQUE_ID = "crafted_unique";

    private int t = 0;

    public String id = "";

    public int perc = 0;


    public boolean isUnique() {
        return !id.isEmpty();
    }

    public CraftedJewelTier getCraftedTier() {
        return CraftedJewelTier.fromTier(t);
    }

    public ItemStack getStackNeededForUpgrade() {
        return getCraftedTier().upgradeStack.get();
    }

    public void upgradeUnique(JewelItemData data) {
        t++;

        CraftedJewelTier tier = getCraftedTier();

        data.lvl = tier.lvl;

        if (tier.addsAffix) {// make all rarity upgrades add to affixes, whatever, if its too op, nerf the affixes

            Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                return x.getAllTagReq().contains(SlotTags.crafted_jewel_unique.GUID());
            }).random();

            var affixdata = new AffixData(Affix.Type.jewel);
            affixdata.randomizeTier(data.getRarity());
            affixdata.p = affixdata.getMinMax().random();
            affixdata.id = affix.guid;
            data.affixes.add(affixdata);

        }
    }


    public boolean isCraftableUnique() {
        return id.equals(CRAFTED_UNIQUE_ID);
    }

}
