package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;

import java.util.ArrayList;
import java.util.List;

public abstract class StatContext {


    public final StatCtxType type;
    public final String gear_slot;

    public final List<ExactStatData> stats;

    public List<ExactStatData> getPercentOfStats(float multi) {
        List<ExactStatData> modified = new ArrayList<>();
        stats.forEach(x -> {
            var stat = ExactStatData.copy(x);
            stat.multiplyBy(multi);
            modified.add(stat);
        });
        return modified;
    }

    public StatContext(StatCtxType type, List<ExactStatData> stats) {
        this.type = type;
        this.stats = stats;
        this.gear_slot = "";
    }

    public StatContext(StatCtxType type, String gear_slot, List<ExactStatData> stats) {
        this.type = type;
        this.gear_slot = gear_slot;
        this.stats = stats;
    }

    public enum StatCtxType {
        GEAR, INNATE_SPELL, SUPPORT_GEM, BASE_STAT, ENCHANT_COMPAT, PROPHECY_CURSE, NEWBIE_RESISTS, JEWEL, VANILLA_STAT_COMPAT,
        BONUS_XP_PER_CHARACTER, COMMAND_EXACT_STATS, STAT_POINTS, TALENT, ASCENDANCY, POTION_EFFECT, AURA, MISC, MOB_AFFIX, FOOD_BUFF, TOOL,
        STAT_CTX_MODIFIER_BONUS
    }
}
