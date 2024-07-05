package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;

public class OperationTipBlock extends AbstractTextBlock {

    private boolean alt = false;

    private boolean shift = false;

    private boolean ctrl = false;

    private final TooltipInfo info = new TooltipInfo();

    private List<Component> additionalOperation = new ArrayList<>();

    public OperationTipBlock() {
    }

    public OperationTipBlock setAlt() {
        this.alt = true;
        return this;
    }

    public OperationTipBlock setShift() {
        this.shift = true;
        return this;
    }

    public OperationTipBlock setCtrl() {
        this.ctrl = true;
        return this;
    }

    public OperationTipBlock setAll() {
        this.ctrl = true;
        this.alt = true;
        this.shift = true;
        return this;
    }

    public OperationTipBlock addOperationTipAbove(List<Component> components){
        this.additionalOperation = components;
        return this;
    }

    public OperationTipBlock addDraggableTipAbove(AvailableTarget target){
        this.additionalOperation.add(target.component);
        return this;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        ArrayList<Component> components = new ArrayList<>();
        MutableComponent component = Component.literal("");
        if (this.additionalOperation != null){
            components.addAll(this.additionalOperation);
        }
        if (this.shift && !info.hasShiftDown && !info.hasAltDown){
            component.append(Itemtips.SHIFT_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.ctrl){
            component.append(Itemtips.CTRL_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.alt && !info.hasAltDown && !info.hasShiftDown){
            component.append(Itemtips.ALT_TIP.locName()).withStyle(ChatFormatting.BLUE);
        }
        if (!component.equals(Component.literal(""))){
            components.add(component);
        }

        return components;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.OPERATION;
    }


    public enum AvailableTarget{
        GEAR_SOUL(Itemtips.SOUL_MODIFIER_USE_TIP.locName().withStyle(ChatFormatting.BLUE)),

        GEAR(Itemtips.GEAR_SOUL_USE_TIP.locName().withStyle(ChatFormatting.BLUE));

        public final Component component;

        AvailableTarget(Component component) {
            this.component = component;
        }
    }

}
