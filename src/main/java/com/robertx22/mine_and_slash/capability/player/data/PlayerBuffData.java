package com.robertx22.mine_and_slash.capability.player.data;

import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.buffs.StatBuff;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashPotions;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.function.Supplier;

public class PlayerBuffData implements IStatCtx {

    public HashMap<Type, Buff> map = new HashMap<>();


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();

        for (Buff buff : map.values()) {
            for (ExactStatData stat : buff.stats) {
                stats.add(ExactStatData.copy(stat));// this is modified so need to clone it here or make the modification code clone it. Probably best both
            }
        }

        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.FOOD_BUFF, stats));
    }

    public enum Type implements IAutoLocName {
        POTION("potion", "Potion", 20 * 60 * 30, () -> SlashPotions.POTION.get(), Professions.ALCHEMY),
        // ELIXIR("elixir", "Elixir", 20 * 60 * 15),
        MEAL("meal", "Meal", 20 * 60 * 60, () -> SlashPotions.MEAL.get(), Professions.COOKING),
        FISH("fish", "Seafood", 20 * 60 * 60, () -> SlashPotions.FISH.get(), Professions.COOKING);

        public String id;
        public String name;
        public int durationTicks;
        public Supplier<MobEffect> effect;
        public String profession;

        Type(String id, String name, int durationTicks, Supplier<MobEffect> sup, String prof) {
            this.id = id;
            this.profession = prof;
            this.name = name;
            this.effect = sup;
            this.durationTicks = durationTicks;

        }

        public boolean isFood() {
            return this == MEAL || this == FISH;
        }

        @Override
        public AutoLocGroup locNameGroup() {
            return AutoLocGroup.Foods;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".buff_consumption." + GUID();
        }

        @Override
        public String locNameForLangFile() {
            return name;
        }

        @Override
        public String GUID() {
            return this.name
                    .toLowerCase(Locale.ROOT);
        }

    }

    public void onTick(Player p, int ticks) {


        for (Buff buff : map.values()) {
            buff.ticks -= ticks;
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
