package com.robertx22.age_of_exile.capability.player.data;

import java.util.HashMap;

public class PlayerProfessionsData {

    private HashMap<String, Data> map = new HashMap();

    public int getLevel(String id) {
        return map.getOrDefault(id, new Data()).lvl;
    }

    public void addExp(String id, int exp) {
        if (!map.containsKey(id)) {
            map.put(id, new Data());
        }
        map.get(id).exp += exp;
    }

    private static class Data {

        public int lvl = 1;
        public int exp = 0;
    }
}
