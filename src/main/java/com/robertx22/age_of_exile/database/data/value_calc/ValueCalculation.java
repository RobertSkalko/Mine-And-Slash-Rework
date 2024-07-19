package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

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
    public LeveledValue base = new LeveledValue(0, 0);

    public ScalingCalc dmg_effectiveness = new ScalingCalc(Health.getInstance(), new LeveledValue(1, 1));

    public float cap_to_wep_dmg = 1000; // by default doesnt cap

    public int getCalculatedBaseValue(LivingEntity en, MaxLevelProvider provider) {
        if (base_scaling_type == null) {
            MMORPG.logError("base scaling type null");
            return 0;
        }

        return (int) base_scaling_type.scale(base.getValue(en, provider), Load.Unit(en).getLevel());
    }

    public float getDamageEffectiveness(LivingEntity en, MaxLevelProvider provider) {
        return dmg_effectiveness.getMulti().getValue(en, provider);
    }

    public String getLocDmgTooltip(Elements element) {
        return "[calc:" + id + "]" + " " + element.getIconNameDmg();
    }

    public String getLocDmgTooltip() {
        return "[calc:" + id + "]";
    }

    private int getCalculatedScalingValue(LivingEntity en, MaxLevelProvider provider) {

        var opt = getAllScalingValues().stream().filter(x -> x.getStat() == WeaponDamage.getInstance()).findFirst();

        float dmg = 0;
        if (opt.isPresent()) {
            dmg = opt.get().getCalculatedValue(en, provider);
        }


        float other = 0;

        other += getAllScalingValues().stream().filter(x -> x.getStat() != WeaponDamage.getInstance())
                .mapToInt(x -> x.getCalculatedValue(en, provider))
                .sum();

        if (this.capsToWeaponDamage()) {
            float maxotherscaling = dmg * this.cap_to_wep_dmg;
            if (other > maxotherscaling) {
                other = maxotherscaling;
            }
        }

        float amount = other + dmg;

        return (int) amount;
    }

    public int getCalculatedValue(LivingEntity en, MaxLevelProvider provider) {
        int val = getCalculatedScalingValue(en, provider);
        val += getCalculatedBaseValue(en, provider);
        return val;

    }

    public Component getShortTooltip(LivingEntity en, MaxLevelProvider provider) {
        MutableComponent text = Component.literal("");

        int val = getCalculatedValue(en, provider);


        text.append( "" + ChatFormatting.GREEN + val + ChatFormatting.GRAY);


        stat_scalings.forEach(x -> {
            text.append(" ").append(x.GetTooltipString(en, provider));
        });

        if (capsToWeaponDamage()) {

            var opt = getAllScalingValues().stream().filter(x -> x.getStat() == WeaponDamage.getInstance()).findFirst();

            if (opt.isPresent()) {
                int num = (int) (opt.get().getMulti().getValue(en, provider) * 100F * this.cap_to_wep_dmg);
                text.append(Words.CAPPED_TO_WEP_DMG.locName(num));
            }
        }

        return text;

    }

    public boolean capsToWeaponDamage() {
        return this.cap_to_wep_dmg < 50;

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
