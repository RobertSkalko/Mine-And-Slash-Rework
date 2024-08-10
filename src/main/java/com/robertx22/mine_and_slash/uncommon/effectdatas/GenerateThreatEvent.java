package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class GenerateThreatEvent extends EffectEvent {

    public static String ID = "on_gen_threat";

    @Override
    public String GUID() {
        return ID;
    }

    public GenerateThreatEvent(LivingEntity player, Mob mob, ThreatGenType threatGenType, float threat) {
        super(threat, player, mob);
        this.data.setString(EventData.THREAT_GEN_TYPE, threatGenType.name());
    }

    @Override
    public String getName() {
        return "Threat Event";
    }

    @Override
    protected void activate() {

        int threat = (int) data.getNumber();


        if (threat == 0) {
            return;
        }

        Load.Unit(target).getThreat().addThreat(source, (Mob) target, threat);
    }

}