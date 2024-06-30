package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.aoe_data.database.ailments.AilmentSpeed;
import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentEffectStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class EntityAilmentData {

    // todo freeze and electrify

    public HashMap<String, List<DotData>> dotMap = new HashMap<String, List<DotData>>();
    public HashMap<String, Float> strMap = new HashMap<String, Float>();
    public HashMap<String, Float> dmgMap = new HashMap<String, Float>();

    String lastAttacker = "";

    transient HashMap<String, UUID> idCache = new HashMap<>();


    // todo have to call this with stats or ways that do it. I'll have stats that have chance to do it etc.
    public void shatterAccumulated(LivingEntity caster, LivingEntity target, Ailment ailment, Spell spell) {

        float dmg = dmgMap.getOrDefault(ailment.GUID(), 0F);
        dmgMap.put(ailment.GUID(), 0F);

        if (dmg > 0) {
            EventBuilder.ofSpellDamage(caster, target, (int) dmg, spell).setupDamage(AttackType.dot, WeaponTypes.none, PlayStyle.INT).set(x -> {
                        x.calcSourceEffects = false;
                        x.calcTargetEffects = false;
                        x.setElement(ailment.element);
                        x.setisAilmentDamage(ailment);

                    }).build()
                    .Activate();
        }
    }

    public void onAilmentCausingDamage(LivingEntity caster, LivingEntity target, Ailment ailment, float dmg) {

        AilmentDuration dur = new AilmentDuration(ailment);
        AilmentResistance res = new AilmentResistance(ailment);
        AilmentEffectStat eff = new AilmentEffectStat(ailment);

        lastAttacker = caster.getStringUUID();

        float speed = Load.Unit(caster).getUnit().getCalculatedStat(AilmentSpeed.INSTANCE).getMultiplier();


        dmg = dmg * ailment.damageEffectivenessMulti; // make sure this isnt done multiple times
        dmg *= Load.Unit(caster).getUnit().getCalculatedStat(eff).getMultiplier();
        dmg *= Load.Unit(target).getUnit().getCalculatedStat(res).getMultiplier();

        if (ailment.isDot) {
            // otherwise dots will add whole damage EVERY tick
            float secmulti = 1F / ((float) ailment.durationTicks / 20F);
            dmg *= secmulti;
        }


        if (ailment.isDot) {
            dmg *= speed;


            int ticks = ailment.durationTicks;
            var stat = Load.Unit(caster).getUnit().getCalculatedStat(AilmentSpeed.INSTANCE).getMultiplier();
            ticks /= stat;
            ticks *= Load.Unit(caster).getUnit().getCalculatedStat(dur).getMultiplier();

            if (ticks < 21) {
                ticks = 21;
            }
            if (!dotMap.containsKey(ailment.GUID())) {
                dotMap.put(ailment.GUID(), new ArrayList<>());
            }
            dotMap.get(ailment.GUID()).add(new DotData(ticks, dmg));
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
            strength = MathHelper.clamp(strMap.get(ailment.GUID()) + (add), 0, 1);
            strength *= Load.Unit(caster).getUnit().getCalculatedStat(eff).getMultiplier();
            strength *= Load.Unit(target).getUnit().getCalculatedStat(res).getMultiplier();

            strMap.put(ailment.GUID(), strength);


        }

        if (ailment.GUID().equals(Ailments.FREEZE.GUID())) {
            float freeze = strMap.getOrDefault(Ailments.FREEZE.GUID(), 0F);
            if (freeze > 0) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 5, Ailments.FREEZE.getSlowTier(freeze)));
            }
        }

    }


    public void onTick(LivingEntity en) {


        for (Map.Entry<String, List<DotData>> e : dotMap.entrySet()) {
            for (DotData d : e.getValue()) {
                d.ticks -= 1;
            }
        }

        if (en.tickCount % 20 == 0) { // make sure the needed ticks is divisible by 20 for this reason, this is so this isn't calculated every tick
            for (Map.Entry<String, Float> e : strMap.entrySet()) {

                if (e.getValue() > 0) {
                    Ailment ail = ExileDB.Ailments().get(e.getKey());

                    e.setValue(e.getValue() - (e.getValue() * ail.percentLostEveryXSeconds));
                }
            }
        }


        if (en.tickCount % 20 == 0) {

            if (!this.dotMap.isEmpty()) {
                UUID id = null;
                try {
                    if (!idCache.containsKey(lastAttacker)) {
                        if (!lastAttacker.isEmpty()) {
                            idCache.put(lastAttacker, UUID.fromString(lastAttacker));
                        }
                    }
                    id = idCache.get(lastAttacker);

                } catch (Exception e) {
                    //throw new RuntimeException(e);
                }

                if (id != null) {
                    LivingEntity caster = en.level().getPlayerByUUID(id);


                    if (caster != null) {
                        for (Map.Entry<String, List<DotData>> e : dotMap.entrySet()) {
                            float dmg = 0;

                            for (DotData d : e.getValue()) {
                                dmg += d.dmg;
                            }

                            // todo why is this sometimes 0?
                            if (dmg > 1) {

                                Ailment ailment = ExileDB.Ailments().get(e.getKey());
                                // todo will probably have to tweak this
                                EventBuilder.ofDamage(caster, en, dmg).setupDamage(AttackType.dot, WeaponTypes.none, PlayStyle.INT).set(x -> {
                                            x.setElement(ailment.element);
                                            x.setisAilmentDamage(ailment);
                                            x.calcTargetEffects = false;
                                            x.calcSourceEffects = false;
                                        }).build()
                                        .Activate();
                            }
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
        public float ticks;
        public float dmg;

        public DotData(int ticks, float dmg) {
            this.ticks = ticks;
            this.dmg = dmg;
        }
    }
}
