package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_school.AscendancyClass;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;


public class AscendancyClassesData {

    public HashMap<String, Integer> allocated_lvls = new HashMap<>();
    public String school = "";


    public void reset() {
        this.allocated_lvls = new HashMap<>();
        this.school = "";
    }

    public int getLevel(String id) {
        return allocated_lvls.getOrDefault(id, 0);
    }

    public int getFreeSpellPoints(LivingEntity entity) {
        return (int) (GameBalanceConfig.get().CLASS_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(Load.Unit(entity).getLevel())) - getSpentPoints();
    }

    public int getSpentPoints() {
        int total = 0;
        for (Integer x : allocated_lvls.values()) {
            total += x;
        }
        return total;
    }

    public boolean canLearn(LivingEntity en, AscendancyClass school, Perk perk) {
        if (getFreeSpellPoints(en) < 1) {
            return false;
        }
        if (!school.isLevelEnoughFor(en, perk)) {
            return false;
        }
        if (!this.school.isEmpty() && !perk.equals(school)) {
            return false;
        }
        if (allocated_lvls.getOrDefault(perk, 0) >= perk.getMaxLevel()) {
            return false;
        }

        return true;
    }

    public void learn(Perk perk, AscendancyClass school) {

        if (this.school.isEmpty()) {
            this.school = school.GUID();
        }
        int current = allocated_lvls.getOrDefault(perk.GUID(), 0);
        allocated_lvls.put(perk.GUID(), current + 1);

    }


}
