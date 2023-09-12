package com.robertx22.age_of_exile.gui.wiki.all;

import com.robertx22.age_of_exile.database.data.profession.ExpSources;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.wiki.BestiaryEntry;
import com.robertx22.age_of_exile.gui.wiki.BestiaryGroup;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfExpBestiary extends BestiaryGroup {
    @Override
    public List<BestiaryEntry> getAll(int lvl) {
        List<BestiaryEntry> list = new ArrayList<>();

        for (Profession p : ExileDB.Professions().getList()) {


            for (Map.Entry<Integer, List<ExpSources.ExpSource>> en : p.exp_sources.map.entrySet()) {
                for (ExpSources.ExpSource so : en.getValue()) {
                    List<Component> tip = new ArrayList<>();

                    tip.add(p.locName().withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));

                    tip.add(Component.literal(so.exp + " Exp"));
                    tip.add(TooltipUtils.tier(en.getKey()));
                    tip.add(Component.literal(so.id));

                    list.add(new BestiaryEntry.Tooltip(Items.PAPER.getDefaultInstance(), so.id, tip));
                }
            }

        }

        return list;
    }

    @Override
    public Component getName() {
        return Words.PROFESSIONS.locName();
    }


    @Override
    public String texName() {
        return "prof";
    }

}
