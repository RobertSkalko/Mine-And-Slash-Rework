package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatEffect;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayer;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayerData;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.base.EffectWithCtx;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class EffectEvent implements IGUID {

    public EntityData sourceData;
    public EntityData targetData;

    public LivingEntity source;
    public LivingEntity target;

    private boolean effectsCalculated = false;

    public EventData data = new EventData();

    public abstract String getName();

    private boolean activated = false;

    private HashMap<String, StatLayerData> layers = new HashMap<>(); // todo use this later

    private List<MoreMultiData> moreMultis = new ArrayList<>(); // todo use this later


    public void addMoreMulti(String number, float multi) {
        moreMultis.add(new MoreMultiData(multi, number));
    }

    public static class MoreMultiData {

        public float multi = 1;
        public String numberid = "";

        public MoreMultiData(float multi, String numberid) {
            this.multi = multi;
            this.numberid = numberid;
        }
    }

    public StatLayerData getLayer(StatLayer layer, String number) {
        String id = layer.GUID() + "_" + number;
        if (!layers.containsKey(id)) {
            layers.put(id, new StatLayerData(layer.GUID(), number));
        }
        return layers.get(id);

    }

    public EffectEvent(float num, LivingEntity source, LivingEntity target) {
        this(source, target);

        data.setupNumber(EventData.NUMBER, num);

    }

    public EffectEvent(LivingEntity source, LivingEntity target) {

        this.source = source;
        this.target = target;

        if (target != null && source != null) {
            this.targetData = Load.Unit(target);
            this.sourceData = Load.Unit(source);
        } else {
            this.data.setBoolean(EventData.CANCELED, true);
        }
    }

    public boolean isSpell() {
        return data.isSpellEffect();
    }

    public Spell getSpell() {
        return ExileDB.Spells().get(data.getString(EventData.SPELL));
    }

    public void initBeforeActivating() {

    }


    public boolean canWorkOnDeadTarget() {
        return false;
    }

    public void increaseByPercent(float perc) {
        increaseByPercent(EventData.NUMBER, perc);
    }

    public void increaseByPercent(String num, float perc) {
        data.getNumber(num).number += data.getOriginalNumber(num).number * perc / 100F;
    }

    public void Activate() {
        if (!activated) {

            if (this.source.isDeadOrDying() || target.isDeadOrDying()) {
                if (!canWorkOnDeadTarget()) {
                    this.activated = true;
                    return;
                }
            }


            //Watch watch = new Watch();
            //watch.min = 500;

            initBeforeActivating();
            calculateEffects();
            data.freeze();

            if (!data.isCanceled()) {

                activate();
                this.activated = true;
            }

            // watch.print("stat events " + GUID() + " ");
        }

    }

    // todo this shouldnt be calculated at the end.. stats like magic shield depend on it
    private void calculateStatLayersAndMoreMultis() {
        List<StatLayerData> all = new ArrayList<>();
        all.addAll(layers.values());
        all.sort(Comparator.comparingInt(x -> x.getLayer().priority)); // todo check if this is correct order

        for (StatLayerData layer : all) {
            layer.getLayer().action.apply(this, layer, layer.numberID);
        }

        for (MoreMultiData more : this.moreMultis) {
            this.data.getNumber(more.numberid).number *= more.multi;
        }
    }

    public void calculateEffects() {
        if (source.level().isClientSide) {
            return; // todo is this fine? spell calc seems to be called on client every tick!
        }
        if (!effectsCalculated) {
            effectsCalculated = true;
            if (target == null || data.isCanceled() || sourceData == null || targetData == null) {
                return;
            }


            TryApplyEffects(source, sourceData, EffectSides.Source);
            TryApplyEffects(target, targetData, EffectSides.Target);


        }

    }

    protected abstract void activate();

    protected void TryApplyEffects(LivingEntity en, EntityData data, EffectSides side) {


        if (this.data.isCanceled()) {
            return;
        }

        List<EffectWithCtx> effectsWithCtx = new ArrayList<>();

        effectsWithCtx = AddEffects(effectsWithCtx, data, en, side);

        effectsWithCtx.sort(EffectWithCtx.COMPARATOR);

        for (EffectWithCtx item : effectsWithCtx) {
            if (item.stat.isNotZero()) {
                if (item.effect.Side().equals(side)) {
                    item.effect.TryModifyEffect(this, item.statSource, item.stat, item.stat.GetStat());
                } else {
                    System.out.print("ERORR Stat at wrong side should never be added in the first place! ");
                }
            } else {
                System.out.print("ERORR cant be zero! ");
            }
        }

    }

    public LivingEntity getSide(EffectSides side) {
        if (side == EffectSides.Source) {
            return source;
        } else {
            return target;
        }
    }

    private static EffectWithCtx CALC_LAYERS_EFFECT = new EffectWithCtx(new IStatEffect() {
        @Override
        public boolean worksOnEvent(EffectEvent ev) {
            return true;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return StatPriority.Damage.CALC_DAMAGE_LAYERS.priority;
        }

        @Override
        public void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {
            effect.calculateStatLayersAndMoreMultis();
        }
    }, EffectSides.Source, new StatData());


    private List<EffectWithCtx> AddEffects(List<EffectWithCtx> effects, EntityData enData, LivingEntity en, EffectSides side) {

        if (enData == null) {
            return effects;
        }


        Unit un = enData.getUnit();


        if (side == EffectSides.Source) {
            effects.add(CALC_LAYERS_EFFECT);

            if (isSpell()) {
                if (en instanceof Player p) {
                    if (getSpell() != null) {
                        un = Load.player(p).getSpellUnitStats(p, getSpell());
                    }
                }
            }
        }

        List<StatData> list = new ArrayList<>();

        un.getStats().stats
                .values()
                .forEach(data -> {
                    if (data.isNotZero()) {
                        Stat stat = data.GetStat();

                        IStatEffect effect = null;

                        if (stat.statEffect != null) {
                            if (stat.statEffect.Side().equals(side)) {
                                if (stat.statEffect.worksOnEvent(this)) {
                                    effect = stat.statEffect;
                                }
                            }
                        }
                        if (effect != null) {
                            effects.add(new EffectWithCtx(effect, side, data));
                            list.add(data);
                        }


                        if (stat instanceof DatapackStat) {
                            DatapackStat d = (DatapackStat) stat;
                            for (DataPackStatEffect eff : d.effect) {
                                if (eff.worksOnEvent(this)) {
                                    if (eff.Side().equals(side)) {
                                        effects.add(new EffectWithCtx(eff, side, data));
                                        list.add(data);
                                    }
                                }
                            }

                        }


                    }
                });


        if (MMORPG.deepCombatLogEnabled()) {
            if (this instanceof DamageEvent) { // todo allow config
                MutableComponent merged = Component.literal("");
                for (StatData s : list) {
                    merged.append(Component.literal(s.getId() + ":" + s.getValue() + ", "));
                }
                /// todo

                Player p = null;
                if (this.source instanceof Player px) {
                    p = px;
                    p.sendSystemMessage(Component.literal(getName()).withStyle(ChatFormatting.RED)
                            .append(Component.literal(", " + side.id).withStyle(ChatFormatting.YELLOW)
                                    .append(Component.literal(", Used Stats: ").withStyle(ChatFormatting.WHITE)).append(merged).withStyle(ChatFormatting.GREEN)))
                    ;
                }
                if (this.target instanceof Player px) {
                    p = px;
                    p.sendSystemMessage(Component.literal(getName()).withStyle(ChatFormatting.RED)
                            .append(Component.literal(", " + side.id).withStyle(ChatFormatting.YELLOW)
                                    .append(Component.literal(", Used Stats: ").withStyle(ChatFormatting.WHITE)).append(merged).withStyle(ChatFormatting.GREEN)))
                    ;
                }

            }
        }
        return effects;
    }

}