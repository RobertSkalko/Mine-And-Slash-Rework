package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.database.data.prophecy.ProphecyStart;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ProphecyData {

    public String uuid = "";
    public String start = "";
    public int amount = 1;
    public int cost = 0;
    public List<ProphecyModifierData> mods = new ArrayList<>();


    public List<ItemStack> generateRewards(Player p) {
        List<ItemStack> list = new ArrayList<>();

        var data = Load.player(p).prophecy;

        int lvl = data.getAverageLevel();
        int tier = data.getAverageTier();

        for (int i = 0; i < amount; i++) {

            var blueprint = this.getStart().create(lvl, tier);

            for (ProphecyModifierData mod : this.mods) {
                mod.get().modifier_type.set(blueprint, mod.get().data);
            }

            ItemStack stack = blueprint.createStack();

            list.add(stack);

        }

        return list;

    }

    public List<MutableComponent> getTooltip() {

        List<MutableComponent> list = new ArrayList<>();


        var start = getStart();

        list.add(start.locName().withStyle(ChatFormatting.LIGHT_PURPLE));

        list.add(Component.literal(""));

        for (ProphecyModifierData mod : mods) {
            list.add(mod.get().modifier_type.getTooltip(mod.get().data));
        }

        list.add(Words.X_ITEMS.locName(this.amount));


        list.add(Words.COSTS_FAVOR.locName(this.cost));


        return list;


    }

    public ProphecyStart getStart() {
        return ExileDB.ProphecyStarts().get(start);
    }

}
