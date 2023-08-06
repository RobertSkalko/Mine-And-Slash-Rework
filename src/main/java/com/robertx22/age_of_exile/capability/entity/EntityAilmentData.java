package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Math;

import java.util.HashMap;
import java.util.List;

public class EntityAilmentData {


    public HashMap<Elements, List<DotData>> dotMap = new HashMap<Elements, List<DotData>>();
    public HashMap<String, Float> strMap = new HashMap<String, Float>();


    public void onAilmentCausingDamage(LivingEntity caster, LivingEntity target, Ailment ailment, float dmg) {

        dmg = dmg * ailment.damageEffectivenessMulti; // make sure this isnt done multiple times

        if (ailment.isDot) {
            dotMap.get(ailment.element).add(new DotData(ailment.durationTicks, dmg)); // todo make sure this damage isnt increased multiple times
        } else {
            
            float strength = 0;

            float max = Load.Unit(target).getUnit().healthData().getValue() + Load.Unit(target).getUnit().magicShieldData().getValue();

            float forFull = max * ailment.percentHealthRequiredForFullStrength;

            if (!strMap.containsKey(ailment.GUID())) {
                strMap.put(ailment.GUID(), 0F);
            }
            float add = dmg / forFull;
            strength = Math.clamp(strMap.get(ailment.GUID()) + (add), 0, 1);

            strMap.put(ailment.GUID(), strength);
        }

    }


    public void onTick(LivingEntity en) {


    }


    public class DotData {
        public int ticks;
        public float dmg;

        public DotData(int ticks, float dmg) {
            this.ticks = ticks;
            this.dmg = dmg;
        }
    }
}
