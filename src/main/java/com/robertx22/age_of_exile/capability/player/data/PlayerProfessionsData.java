package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class PlayerProfessionsData {

    private HashMap<String, Data> map = new HashMap();


    public int getLevel(String id) {
        return map.getOrDefault(id, new Data()).lvl;
    }

    public void setLevel(String id, int num) {
        var data = map.getOrDefault(id, new Data());
        data.lvl = num;
        map.put(id, data);
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
            int needExp = getExpNeeded();
            while (exp > needExp){
                exp -= getExpNeeded();
                lvl++;
                needExp = getExpNeeded();
            }
        }
    }


}
