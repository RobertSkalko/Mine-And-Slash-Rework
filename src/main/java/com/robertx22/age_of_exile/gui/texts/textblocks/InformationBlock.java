package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class InformationBlock extends AbstractTextBlock {

    private boolean alt = false;

    private boolean shift = false;

    private boolean ctrl = false;

    private final TooltipInfo info = new TooltipInfo();

    public InformationBlock() {
    }

    public InformationBlock setAlt() {
        this.alt = true;
        return this;
    }

    public InformationBlock setShift() {
        this.shift = true;
        return this;
    }

    public InformationBlock setCtrl() {
        this.ctrl = true;
        return this;
    }

    public InformationBlock setAll() {
        this.ctrl = true;
        this.alt = true;
        this.shift = true;
        return this;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        MutableComponent component = Component.literal("");
        if (this.shift && !info.hasShiftDown && !info.hasAltDown){
            component.append(Itemtips.SHIFT_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.ctrl){
            component.append(Itemtips.CTRL_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.alt && !info.hasAltDown && !info.hasShiftDown){
            component.append(Itemtips.ALT_TIP.locName()).withStyle(ChatFormatting.BLUE);
        }
        return component.equals(Component.literal(""))? EMPTY_LIST : Collections.singletonList(component);
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.INFORMATION;
    }

}
