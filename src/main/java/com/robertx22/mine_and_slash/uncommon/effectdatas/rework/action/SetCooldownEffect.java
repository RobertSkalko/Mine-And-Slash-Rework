package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.aoe_data.database.stats.base.AutoHashClass;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.Objects;

public class SetCooldownEffect extends StatEffect {

    //public static SetCooldownEffect.Data MISSILE_BARRAGE = new Data("missile_barrage", 20);

    public Data data = new Data("", 0);

    public static class Data extends AutoHashClass {
        public String cd_id = "";
        int num = 20;

        public Data(String cd_id, int num) {
            this.cd_id = cd_id;
            this.num = num;
        }

        @Override
        public int hashCode() {
            return Objects.hash(cd_id + num);
        }

        @Override
        public String GUID() {
            return cd_id + "_" + num;
        }
    }

    public SetCooldownEffect(Data data) {
        super("set_cooldown_" + data.cd_id + data.num, "set_cooldown");
        this.data = data;
    }

    SetCooldownEffect() {
        super("", "set_cooldown");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.sourceData.getCooldowns().setOnCooldown(this.data.cd_id, this.data.num);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return SetCooldownEffect.class;
    }
}
