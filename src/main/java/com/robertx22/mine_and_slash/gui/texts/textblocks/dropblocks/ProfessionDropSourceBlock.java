package com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks;

import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class ProfessionDropSourceBlock extends AbstractTextBlock {

    public Profession prof;

    boolean crafting = false;

    public ProfessionDropSourceBlock(String pro) {
        this.prof = ExileDB.Professions().get(pro);
        this.crafting = Professions.STATION_PROFESSIONS.contains(pro);
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        if (crafting) {
            return Arrays.asList(Chats.CRAFT_PROF_MAT_SOURCE.locName(prof.locName().withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GREEN));
        }
        return Arrays.asList(Chats.PROF_MAT_SOURCE.locName(prof.locName().withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.ADDITIONAL;
    }
}