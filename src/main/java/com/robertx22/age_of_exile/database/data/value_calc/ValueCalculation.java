package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class ValueCalculation implements JsonExileRegistry<ValueCalculation>, IAutoGson<ValueCalculation> {

    public static ValueCalculation SERIALIZER = new ValueCalculation();

    public ValueCalculation() {

    }

    public List<ScalingCalc> getAllScalingValues() {
        return new ArrayList<>(stat_scalings);
    }

    public List<ScalingCalc> stat_scalings = new ArrayList<>();

    public String id = "";
    public StatScaling base_scaling_type = StatScaling.NORMAL;
    public LeveledValue attack_scaling = new LeveledValue(0, 0);
    public LeveledValue base = new LeveledValue(0, 0);

    public int getCalculatedBaseValue(LevelProvider provider) {

        if (base_scaling_type == null) {
            MMORPG.logError("base scaling type null");
            return 0;
        }

        return (int) base_scaling_type.scale(base.getValue(provider), provider.getCasterLevel());
    }

    public String getLocDmgTooltip(Elements element) {
        return "[calc:" + id + "]" + " " + element.getIconNameDmg();
    }

    public String getLocDmgTooltip() {
        return "[calc:" + id + "]";
    }

    private int getCalculatedScalingValue(LevelProvider provider) {

        float amount = 0;
        if (attack_scaling.getValue(provider) > 0) {
            for (Stat stat : new BonusAttackDamage(Elements.Chaos).generateAllPossibleStatVariations()) {
                amount += provider.getCasterData()
                        .getUnit()
                        .getCalculatedStat(stat.GUID())
                        .getValue() * attack_scaling.getValue(provider);
            }
        }
        amount += getAllScalingValues().stream()
                .mapToInt(x -> x.getCalculatedValue(provider))
                .sum();

        return (int) amount;
    }

    public int getCalculatedValue(LevelProvider provider) {
        int val = getCalculatedScalingValue(provider);
        val += getCalculatedBaseValue(provider);
        return val;

    }

    public Component getShortTooltip(LevelProvider provider) {
        MutableComponent text = Component.literal("");

        int val = getCalculatedValue(provider);

        
        text.append(val + "");

        if (attack_scaling.getValue(provider) > 0) {
            if (val < 1 || Screen.hasShiftDown()) {
                text.append(" (" + (int) (attack_scaling.getValue(provider) * 100) + "% Weapon Damage)")
                        .withStyle(ChatFormatting.YELLOW);
            }
        }


        stat_scalings.forEach(x -> {
            text.append(" ").append(x.GetTooltipString(provider));
        });

        return text;

    }

    @Override
    public Class<ValueCalculation> getClassForSerialization() {
        return ValueCalculation.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.VALUE_CALC;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
