package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ScalingCalc {

    public String stat;
    public LeveledValue multi;

    public Stat getStat() {
        return ExileDB.Stats()
                .get(stat);
    }

    public ScalingCalc() {

    }

    public ScalingCalc(Stat stat, LeveledValue multi) {
        super();
        this.stat = stat.GUID();
        this.multi = multi;
    }

    public LeveledValue getMulti() {
        return multi;
    }

    public int getMultiAsPercent(LivingEntity en, MaxLevelProvider provider) {
        return (int) (multi.getValue(en, provider) * 100);
    }

    public Component GetTooltipString(LivingEntity en, MaxLevelProvider provider) {
        return Gui.SPELL_DAMAGE_PROPORTION.locName(getMultiAsPercent(en, provider), getStat().getMutableIconNameFormat());
    }

    public List<Component> getTooltipFor(float multi, float value, MutableComponent statname, Elements el) {
        List<Component> list = new ArrayList<>();
        String eleStr = "";

        if (el != null) {
            eleStr = el.format + el.icon;
        }


        if (statname != null) {
            list.add(Component.literal(
                            ChatFormatting.RED + "Scales with " + (int) (multi * 100F) + "% " + eleStr + " ").append(
                            statname)
                    .append(" (" + value + ")"));
        }

        return list;
    }

    public int getCalculatedValue(LivingEntity en, MaxLevelProvider provider) {
        float multi = (getMulti().getValue(en, provider));

        int val = (int) (multi * Load.Unit(en)
                .getUnit()
                .getCalculatedStat(stat)
                .getValue());

        if (getStat() == WeaponDamage.getInstance() && en instanceof Player == false) {
            val += Load.Unit(en).getMobBaseDamage(); // todo what?
        }
        return val;

    }
}
