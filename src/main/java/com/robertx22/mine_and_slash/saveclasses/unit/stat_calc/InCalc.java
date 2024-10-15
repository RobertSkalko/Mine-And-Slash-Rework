package com.robertx22.mine_and_slash.saveclasses.unit.stat_calc;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class InCalc {

    Unit unit;

    public InCalc(Unit unit) {
        this.unit = unit;
    }

    public void addVanillaHpToStats(LivingEntity entity, InCalcStatContainer calc) {
        if (entity instanceof Player) {

            float maxhp = Mth.clamp(entity.getMaxHealth(), 0, 500);
            // all increases after this would just reduce enviro damage

            calc.getStatInCalculation(Health.getInstance()).addAlreadyScaledFlat(maxhp);

            // add vanila hp to extra hp
        }
    }

    public void modify(EntityData data, InCalcStatContainer calc) {

        // todo these should probably be after stats are out of calculation..? or they should be another phase
        // apply transfer stats
        calc.modifyInCalc(calcStat -> {
            if (calcStat.GetStat() instanceof ITransferToOtherStats transfer) {
                transfer.transferStats(calc, calcStat);
            }
        });
        // apply core stats
        /*
        calc.modifyInCalc(calcStat -> {
            if (calcStat.GetStat() instanceof ICoreStat core) {
                core.addToOtherStats(data, calc, calcStat);
            }
        });

         */
    }
}
