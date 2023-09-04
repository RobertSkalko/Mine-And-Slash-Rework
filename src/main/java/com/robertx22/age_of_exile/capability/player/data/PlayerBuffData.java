package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.profession.buffs.StatBuff;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerBuffData implements IStatCtx {

    public List<Buff> potions = new ArrayList<>();
    public List<Buff> food = new ArrayList<>();

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        for (Buff data : potions) {
            stats.addAll(data.stats);
        }
        for (Buff data : food) {
            stats.addAll(data.stats);
        }
        
        return Arrays.asList(new MiscStatCtx(stats));
    }

    public enum Type {
        POTION, FOOD;
    }

    public void onTick(Player p) {

        for (Buff buff : this.food) {
            buff.ticks--;
        }
        for (Buff buff : this.potions) {
            buff.ticks--;
        }

        potions.removeIf(x -> x.ticks < 1);

        food.removeIf(x -> x.ticks < 1);

    }

    public boolean tryAdd(Player p, StatBuff buff, int lvl, int perc, Type type, int ticks) {
        var list = potions;
        int max = 3;
        if (type == Type.FOOD) {
            list = food;
            max = 1;
        }

        if (lvl > Load.Unit(p).getLevel()) {
            p.sendSystemMessage(Chats.TOO_LOW_LEVEL.locName());
            return false;
        }

        if (list.size() >= max) {
            p.sendSystemMessage(Chats.TOO_MANY_BUFFS.locName());
            return false;
        }
        if (list.stream().allMatch(x -> !x.getBuff().isSameStat(buff))) {
            List<ExactStatData> stats = buff.getStats(lvl, perc);
            Buff data = new Buff(buff.GUID(), stats, ticks);
            list.add(data);
            return true;
        } else {
            p.sendSystemMessage(Chats.CANT_SAME_BUFF.locName());
        }

        return false;
    }

    public static class Buff {
        public String id;
        public List<ExactStatData> stats;
        public int ticks = 0;

        public StatBuff getBuff() {
            return ExileDB.StatBuffs().get(id);
        }

        public Buff(String id, List<ExactStatData> stats, int ticks) {
            this.id = id;
            this.stats = stats;
            this.ticks = ticks;
        }
    }
}
