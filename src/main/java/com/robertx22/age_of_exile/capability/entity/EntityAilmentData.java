package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Math;

import java.util.*;

public class EntityAilmentData {

    // todo freeze and electrify

    public HashMap<Elements, List<DotData>> dotMap = new HashMap<Elements, List<DotData>>();
    public HashMap<String, Float> strMap = new HashMap<String, Float>();
    public HashMap<String, Float> dmgMap = new HashMap<String, Float>();

    String lastAttacker = "";


    // todo have to call this with stats or ways that do it. I'll have stats that have chance to do it etc.
    public void shatterAccumulated(LivingEntity caster, LivingEntity target, Ailment ailment) {

        float dmg = dmgMap.getOrDefault(ailment.GUID(), 0F);
        dmgMap.put(ailment.GUID(), 0F);

        if (dmg > 0) {
            EventBuilder.ofDamage(caster, target, dmg).setupDamage(AttackType.dot, WeaponTypes.none, PlayStyle.magic).set(x -> {
                        x.setElement(ailment.element);
                    }).build()
                    .Activate();
        }
    }

    public void onAilmentCausingDamage(LivingEntity caster, LivingEntity target, Ailment ailment, float dmg) {

        AilmentDuration dur = new AilmentDuration(ailment);
        AilmentResistance res = new AilmentResistance(ailment);
        AilmentEffectStat eff = new AilmentEffectStat(ailment);

        lastAttacker = caster.getStringUUID();

        dmg = dmg * ailment.damageEffectivenessMulti; // make sure this isnt done multiple times
        dmg *= Load.Unit(caster).getUnit().getCalculatedStat(eff).getMultiplier();
        dmg *= Load.Unit(target).getUnit().getCalculatedStat(res).getMultiplier();


        if (ailment.isDot) {
            int ticks = ailment.durationTicks;
            ticks *= Load.Unit(caster).getUnit().getCalculatedStat(dur).getMultiplier();

            if (!dotMap.containsKey(ailment.element)) {
                dotMap.put(ailment.element, new ArrayList<>());
            }
            dotMap.get(ailment.element).add(new DotData(ticks, dmg));
        } else {


            float strength = 0;

            float max = Load.Unit(target).getUnit().healthData().getValue() + Load.Unit(target).getUnit().magicShieldData().getValue();

            float forFull = max * ailment.percentHealthRequiredForFullStrength;

            if (!strMap.containsKey(ailment.GUID())) {
                strMap.put(ailment.GUID(), 0F);
            }
            if (!dmgMap.containsKey(ailment.GUID())) {
                dmgMap.put(ailment.GUID(), 0F);
            }
            dmgMap.put(ailment.GUID(), dmgMap.get(ailment.GUID()) + dmg);

            float add = dmg / forFull;
            strength = Math.clamp(strMap.get(ailment.GUID()) + (add), 0, 1);
            strength *= Load.Unit(caster).getUnit().getCalculatedStat(eff).getMultiplier();
            strength *= Load.Unit(target).getUnit().getCalculatedStat(res).getMultiplier();

            strMap.put(ailment.GUID(), strength);
        }

    }


    public void onTick(LivingEntity en) {

        for (Map.Entry<Elements, List<DotData>> e : dotMap.entrySet()) {
            for (DotData d : e.getValue()) {
                d.ticks--;
            }
        }

        if (en.tickCount % 20 == 0) { // make sure the needed ticks is divisible by 20 for this reason, this is so this isn't calculated every tick
            for (Map.Entry<String, Float> e : strMap.entrySet()) {

                if (e.getValue() > 0) {
                    Ailment ail = ExileDB.Ailments().get(e.getKey());

                    int ticks = ail.lostOccursEverySeconds;

                    if (en.tickCount % ticks == 0) {
                        e.setValue(e.getValue() - ail.percentLostEveryXSeconds);
                    }
                }
            }
        }


        if (en.tickCount % 20 == 0) {

            UUID id = null;
            try {
                id = UUID.fromString(lastAttacker);
            } catch (Exception e) {
                //throw new RuntimeException(e);
            }
            
            if (id != null) {
                LivingEntity caster = en.level().getPlayerByUUID(id);


                if (caster != null) {
                    for (Map.Entry<Elements, List<DotData>> e : dotMap.entrySet()) {
                        float dmg = 0;

                        for (DotData d : e.getValue()) {
                            dmg += d.dmg;
                        }
                        // todo why is this sometimes 0?
                        if (dmg > 1) {
                            // todo will probably have to tweak this
                            EventBuilder.ofDamage(caster, en, dmg).setupDamage(AttackType.dot, WeaponTypes.none, PlayStyle.magic).set(x -> {
                                        x.setElement(e.getKey());
                                    }).build()
                                    .Activate();
                        }
                    }
                }

            }
        }

        for (List<DotData> l : this.dotMap.values()) {
            l.removeIf(x -> x.ticks < 1);
        }


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
