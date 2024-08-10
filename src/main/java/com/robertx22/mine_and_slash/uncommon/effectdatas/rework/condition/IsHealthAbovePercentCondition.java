package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.LivingEntity;

public class IsHealthAbovePercentCondition extends StatCondition {

    EffectSides side;
    int perc;

    public IsHealthAbovePercentCondition(String id, int percent, EffectSides side) {
        super(id, "is_hp_above");
        this.side = side;
        this.perc = percent;
    }

    IsHealthAbovePercentCondition() {
        super("", "is_hp_above");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        LivingEntity en = event.getSide(side);
        return perc < en.getHealth() / en.getMaxHealth() * 100;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsHealthAbovePercentCondition.class;
    }

}
