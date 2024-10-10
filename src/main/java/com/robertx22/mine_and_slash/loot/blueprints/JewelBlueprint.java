package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.saveclasses.jewel.CraftedUniqueJewelData;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.saveclasses.jewel.StatsWhileUnderAuraData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class JewelBlueprint extends RarityItemBlueprint {


    public JewelBlueprint(LootInfo info) {
        super(info);
        this.rarity.chanceForHigherRarity = 50;
        this.rarity.canRollUnique = false;
    }

    public boolean isEye = false;
    public int auraAffixes = 0;

    @Override
    public ItemStack generate() {

        JewelItemData data = createData();

        ItemStack stack = data.getItem().getDefaultInstance();


        StackSaving.JEWEL.saveTo(stack, data);


        ExileStack ex = ExileStack.of(stack);

        ex.get(StackKeys.POTENTIAL).edit(x -> x.potential = 50);

        return ex.getStack();

    }

    public JewelItemData createData() {
        JewelItemData data = new JewelItemData();

        PlayStyle style = RandomUtils.randomFromList(Arrays.stream(PlayStyle.values()).toList());

        if (isEye) {
            data.style = PlayStyle.INT.id;
            data.rar = IRarity.UNIQUE_ID;

            data.uniq = new CraftedUniqueJewelData();
            data.uniq.id = CraftedUniqueJewelData.WATCHER_EYE;

            while (data.auraStats.size() < this.auraAffixes) {
                var affix = ExileDB.Affixes().getFilterWrapped(x -> x.type == Affix.AffixSlot.watcher_eye).random();

                if (data.auraStats.stream().noneMatch(x -> x.affix.equals(affix.GUID()))) {
                    data.auraStats.add(new StatsWhileUnderAuraData(affix, this.level.get()));
                }
            }

        } else {
            data.rar = this.rarity.get().GUID();
            data.style = style.id;
        }

        data.lvl = info.level;

        data.generateAffixes();

        return data;
    }


}
