package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class OperationTipBlock extends AbstractTextBlock {

    private boolean alt = false;

    private boolean shift = false;

    private boolean ctrl = false;

    private final StatRangeInfo info = new StatRangeInfo(ModRange.hide());

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

    public OperationTipBlock addOperationTipAbove(List<Component> components) {
        this.additionalOperation = components;
        return this;
    }


    @Override
    public List<? extends Component> getAvailableComponents() {
        ArrayList<Component> components = new ArrayList<>();
        MutableComponent component = Component.literal("");
        if (this.additionalOperation != null) {
            components.addAll(this.additionalOperation);
        }
        if (this.shift && !info.hasShiftDown && !info.hasAltDown) {
            component.append(Itemtips.SHIFT_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.ctrl) {
            component.append(Itemtips.CTRL_TIP.locName()).withStyle(ChatFormatting.BLUE).append(" ");
        }
        if (this.alt && !info.hasAltDown && !info.hasShiftDown) {
            component.append(Itemtips.ALT_TIP.locName()).withStyle(ChatFormatting.BLUE);
        }
        if (!component.equals(Component.literal(""))) {
            components.add(component);
        }

        return components;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.OPERATION;
    }


}
