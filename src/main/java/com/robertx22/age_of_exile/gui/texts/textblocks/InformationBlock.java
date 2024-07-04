package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
public class InformationBlock extends AbstractTextBlock {

    private boolean onlyAlt = false;


    @Override
    public List<? extends Component> getAvailableComponents() {
        return onlyAlt ? Collections.singletonList(Words.PressAltForStatInfo.locName().withStyle(ChatFormatting.BLUE))
        : Collections.singletonList(Component.translatable(SlashRef.MODID + ".tooltip." + "press_shift_more_info").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.INFORMATION;
    }

    public InformationBlock onlyAlt(){
        this.onlyAlt = true;
        return this;
    }
}
