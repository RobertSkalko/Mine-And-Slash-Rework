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

import java.util.*;

public class PlayerBuffData implements IStatCtx {

    public HashMap<Type, Buff> map = new HashMap<>();


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        for (Buff buff : map.values()) {
            stats.addAll(buff.stats);
        }

        return Arrays.asList(new MiscStatCtx(stats));
    }

    public enum Type {
        POTION("potion", "Potion", 20 * 60 * 30),
        ELIXIR("elixir", "Elixir", 20 * 60 * 15),
        MEAL("meal", "Meal", 20 * 60 * 60),
        FISH("fish", "Seafood", 20 * 60 * 60);

        public String id;
        public String name;
        public int durationTicks;

        Type(String id, String name, int durationTicks) {
            this.id = id;
            this.name = name;
            this.durationTicks = durationTicks;
        }

        public boolean isFood() {
            return this == MEAL || this == FISH;
        }
    }

    public void onTick(Player p) {


        for (Buff buff : map.values()) {
            buff.ticks--;
        }

        for (Map.Entry<Type, Buff> en : new HashMap<>(map).entrySet()) {
            if (en.getValue().ticks < 1) {
                map.remove(en.getKey());
            }
        }
    }

    public boolean tryAdd(Player p, StatBuff buff, int lvl, int perc, Type type, int ticks) {

        if (lvl > Load.Unit(p).getLevel()) {
            p.sendSystemMessage(Chats.TOO_LOW_LEVEL.locName());
            return false;
        }

        List<ExactStatData> stats = buff.getStats(lvl, perc);
        Buff data = new Buff(buff.GUID(), stats, ticks);
        map.put(type, data);

        return true;
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