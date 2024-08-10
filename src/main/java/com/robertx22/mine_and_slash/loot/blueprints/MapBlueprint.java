package com.robertx22.mine_and_slash.loot.blueprints;


import com.robertx22.mine_and_slash.content.ubers.UberBossArena;
import com.robertx22.mine_and_slash.content.ubers.UberBossTier;
import com.robertx22.mine_and_slash.database.data.map_affix.MapAffix;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.maps.MapAffixData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MapBlueprint extends RarityItemBlueprint {


    public MapBlueprint(LootInfo info) {
        super(info);
    }

    boolean uberMap = false;
    UberBossTier uberTier = UberBossTier.T1;
    UberBossArena uber;

    public void setUberBoss(UberBossArena boss, UberBossTier tier) {

        this.uber = boss;
        this.uberTier = tier;
        this.uberMap = true;
    }

    @Override
    public ItemStack generate() {


        MapItemData data = createData();


        ItemStack stack = new ItemStack(SlashItems.MAP.get());

        if (this.uberMap) {
            stack.setHoverName(uber.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        }

        StackSaving.MAP.saveTo(stack, data);

        return stack;

    }

    public MapItemData createData() {
        MapItemData data = new MapItemData();

        GearRarity rarity = (GearRarity) this.rarity.get();

        data.rar = rarity.GUID();

        data.tier = rarity.getPossibleMapTiers().random();

        data.lvl = level.get();


        if (this.uberMap) {
            data.uber = this.uber.GUID();
            data.uber_tier = this.uberTier.tier;
        }

        genAffixes(data, rarity);

        return data;
    }

    private MapItemData genAffixes(MapItemData map, GearRarity rarity) {

        int amount = rarity.getAffixAmount() + 2;

        List<String> affixes = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {

            MapAffix affix = ExileDB.MapAffixes().getFilterWrapped(x -> x.req.isEmpty()).random();

            while (affixes.contains(affix.GUID()) /*|| affix.isBeneficial()*/) {
                affix = ExileDB.MapAffixes().getFilterWrapped(x -> x.req.isEmpty()).random();
            }
            int percent = rarity.stat_percents.random();
            map.affixes.add(new MapAffixData(affix, percent));
            affixes.add(affix.GUID());

        }

        return map;
    }

}
