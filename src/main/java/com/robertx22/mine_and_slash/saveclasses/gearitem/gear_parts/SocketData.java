package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.database.data.runes.Rune;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class SocketData implements IStatsContainer {


    // gem id
    public String g = "";
    public int p = 0;


    public SocketData() {
    }


    public boolean isEmpty() {
        return getGem() == null && getRune() == null;
    }

    public boolean isGem() {
        return getGem() != null;
    }

    public boolean isRune() {
        return getRune() != null;
    }

    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear, boolean addplaceholder) {
        List<Component> list = new ArrayList<Component>();
        GetAllStats(gear).forEach(x -> {
            String placeholder = addplaceholder ? "[SOCKET_PLACEHOLDER]" : " ";
            for (MutableComponent m : x.GetTooltipString()) {
                list.add(Component.literal(placeholder).append(m));
            }

        });
        if (list.isEmpty()) {
            list.add(Component.literal("Socket Display Error"));
        }
        return list;
    }


    public Gem getGem() {
        if (ExileDB.Gems().isRegistered(g)) {
            return ExileDB.Gems().get(g);
        }
        return null;
    }

    public Rune getRune() {
        if (ExileDB.Runes().isRegistered(g)) {
            return ExileDB.Runes().get(g);
        }
        return null;
    }

    public ItemStack getOriginalItemStack() {
        if (isGem()) {
            return getGem().getItem().getDefaultInstance();
        }
        if (isRune()) {
            return getRune().getItem().getDefaultInstance();
        }
        return ItemStack.EMPTY;

    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        SlotFamily fam = gear.GetBaseGearType()
                .family();

        List<ExactStatData> stats = new ArrayList<>();

        try {
            if (isGem()) {
                getGem().getFor(fam).forEach(x -> {
                    stats.add(x.toExactStat((int) gear.getLevel()));
                });
            }
            if (isRune()) {
                getRune().getFor(fam).forEach(x -> {
                    stats.add(x.ToExactStat(p, (int) gear.getLevel()));
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stats;

    }

}
