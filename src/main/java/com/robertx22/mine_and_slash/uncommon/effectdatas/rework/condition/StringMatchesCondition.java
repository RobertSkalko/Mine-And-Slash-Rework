package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class StringMatchesCondition extends StatCondition {

    public String string_id = "";
    public String string_key = "";

    public StringMatchesCondition(String key, String id) {
        super(key + "_is_" + id, "string_matches");
        this.string_id = id;
        this.string_key = key;


    }

    public StringMatchesCondition() {
        super("", "string_matches");
    }


    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        String s = event.data.getString(string_key);
        Boolean is = s.equals(string_id);
        return is;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return StringMatchesCondition.class;
    }

}
