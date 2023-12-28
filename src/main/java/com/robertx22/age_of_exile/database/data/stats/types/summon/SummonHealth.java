package com.robertx22.age_of_exile.database.data.stats.types.summon;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.SummonStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class SummonHealth extends SummonStat {

    public static String GUID = "summon_health";

    public static SummonHealth getInstance() {
        return SummonHealth.SingletonHolder.INSTANCE;

    }

    public SummonHealth() {
        this.is_perc = true;
        this.scaling = StatScaling.NONE;

        this.statEffect = new Effect();
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public Stat getStatToGiveToSummon() {
        return Health.getInstance();
    }

    @Override
    public ModType getModType() {
        return ModType.MORE;
    }

    @Override
    public String locDescForLangFile() {
        return "Gives more hp to summons.";
    }

    @Override
    public String locNameForLangFile() {
        return "Summon Health";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    private static class SingletonHolder {
        private static final SummonHealth INSTANCE = new SummonHealth();
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.isSpell() && effect.getSpell().config.tags.contains(SpellTags.minion_explode);
        }
    }
}
