package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class PlayerProfessionsData {

    private HashMap<String, Data> map = new HashMap();

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
        var data = map.get(id);
        data.exp += exp;

        if (data.canLvl()) {
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
}
