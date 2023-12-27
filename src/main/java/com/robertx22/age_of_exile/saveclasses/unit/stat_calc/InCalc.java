package com.robertx22.age_of_exile.saveclasses.unit.stat_calc;

import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStatsInCalc;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class InCalc {

    Unit unit;

    public InCalc(Unit unit) {
        this.unit = unit;
    }

    public void addVanillaHpToStats(LivingEntity entity) {
        if (entity instanceof Player) {

            float maxhp = Mth.clamp(entity.getMaxHealth(), 0, 500);
            // all increases after this would just reduce enviro damage

            unit.getStats().getStatInCalculation(Health.getInstance()).addAlreadyScaledFlat(maxhp);

            // add vanila hp to extra hp
        }
    }

    public void modify(Unit data) {
        // apply stats that add to others
        unit.getStats().modifyInCalc(calcStat -> {
            if (calcStat.GetStat() instanceof IAffectsStatsInCalc aff) {
                aff.affectStats(data, calcStat);
            }
        });
        // todo these should probably be after stats are out of calculation..? or they should be another phase
        // apply transfer stats
        unit.getStats().modifyInCalc(calcStat -> {
            if (calcStat.GetStat() instanceof ITransferToOtherStats transfer) {
                transfer.transferStats(data, calcStat);
            }
        });
        // apply core stats
        unit.getStats().modifyInCalc(calcStat -> {
            if (calcStat.GetStat() instanceof ICoreStat core) {
                core.addToOtherStats(data, calcStat);
            }
        });
    }
}
