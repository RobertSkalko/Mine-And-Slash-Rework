package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerProfessionsData {

    private HashMap<String, Data> map = new HashMap();

    public DailyDropModifiers daily_drop_multis = new DailyDropModifiers();

    public int getLevel(String id) {
        return map.getOrDefault(id, new Data()).lvl;
    }

    public int getExp(String id) {
        return map.getOrDefault(id, new Data()).exp;
    }

    public int getMaxExp(String id) {
        return map.getOrDefault(id, new Data()).getExpNeeded();
    }

    public void addExp(Player p, String id, int exp) {
        if (!map.containsKey(id)) {
            map.put(id, new Data());
        }
        var rested = Load.player(p).rested_xp;

        rested.onGiveProfExp(exp);

        if (rested.bonusProfExp > 0) {
            int added = MathHelper.clamp(rested.bonusProfExp, 0, exp);
            rested.bonusProfExp -= added;
            exp += added;
        }

        var data = map.get(id);
        data.exp += exp;

        int perc = MathHelper.clamp((int) (map.get(id).exp / (float) map.get(id).getExpNeeded() * 100F), 0, 100);
        OnScreenMessageUtils.actionBar((ServerPlayer) p, Component.literal("+" + exp + " ").append(ExileDB.Professions().get(id).locName().append(" Exp (" + perc + "%)")).withStyle(ChatFormatting.GREEN));

        if (data.canLvl() && Load.Unit(p).getLevel() > data.lvl) {
            data.levelUp();

            Profession pro = ExileDB.Professions().get(id);
            p.sendSystemMessage(Chats.PROFESSION_LEVEL_UP.locName(pro.locName(), data.lvl));
        }
    }


    private static class Data {
        public int lvl = 1;
        public int exp = 0;

        public int getExpNeeded() {
            return LevelUtils.getExpRequiredForLevel(lvl + 1);
        }

        public boolean canLvl() {
            return exp >= getExpNeeded() && lvl < GameBalanceConfig.get().MAX_LEVEL;
        }

        public void levelUp() {
            exp -= getExpNeeded();
            lvl++;
        }
    }

    public static class DailyDropModifiers {

        private HashMap<String, DropModifier> map = new HashMap<>();

        boolean day = false;

        private static DropModifier NONE = new DropModifier(Profession.DropCategory.MAIN, 1);

        public float getMulti(Profession pro, Profession.DropCategory cat) {
            if (map.containsKey(pro.id) && map.get(pro.id).cat == cat) {
                return map.get(pro.id).multi;
            }
            return 1;
        }

        public List<Component> getTooltip() {

            List<Component> list = new ArrayList<>();

            for (Map.Entry<String, DropModifier> en : map.entrySet()) {
                Profession pro = ExileDB.Professions().get(en.getKey());

                var mod = en.getValue();

                // todo add loc name for category

                list.add(pro.locName().append(": ").append(mod.cat.name()).append(": " + mod.multi + "x Drop Rate!")
                        .withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));

            }

            return list;
        }

        public void checkDayPass(ServerPlayer player) {


            // todo it seems this can be cheesed by going to another dimension..
            if (true) {
                this.map = new HashMap<>();
                return;
            }

            if (!day) {
                boolean is = player.level().isDay();

                if (is) {

                    this.map = new HashMap<>();

                    player.sendSystemMessage(Chats.NEW_DAY.locName());

                    for (Profession p : ExileDB.Professions().getList()) {
                        if (!p.chance_drops.isEmpty()) {
                            var drop = RandomUtils.randomFromList(p.chance_drops);
                            float multi = RandomUtils.RandomRange(2, 5);
                            map.put(p.id, new DropModifier(drop.type, multi));
                        }
                    }

                    for (Component c : this.getTooltip()) {
                        player.sendSystemMessage(c);
                    }
                }
            }

            this.day = player.level().isDay();
        }

    }

    private static class DropModifier {
        public Profession.DropCategory cat = Profession.DropCategory.MAIN;
        public float multi;

        public DropModifier(Profession.DropCategory id, float multi) {
            this.cat = id;
            this.multi = multi;
        }
    }

}
