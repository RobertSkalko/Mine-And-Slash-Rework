package com.robertx22.age_of_exile.aoe_data.database.stat_effects;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.ProcSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.actions.PositionSource;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayers;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.*;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberModifier;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class StatEffects implements ExileRegistryInit {

    // todo LOCALIZE THIS!!!!

    public static class Layers {

        public static ModifyStatLayerEffect ADDITIVE_FLAT_DAMAGE_FROM_MANA = new ModifyStatLayerEffect(StatLayers.Offensive.FLAT_DAMAGE, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofPercentOfStat(Mana.getInstance().GUID()), new NumberModifier(NumberModifier.ModifierType.SPELL_DAMAGE_EFFECTIVENESS_MULTI));
        public static ModifyStatLayerEffect ADDITIVE_FLAT_DAMAGE = new ModifyStatLayerEffect(StatLayers.Offensive.FLAT_DAMAGE, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofStatData(), new NumberModifier(NumberModifier.ModifierType.SPELL_DAMAGE_EFFECTIVENESS_MULTI));
        public static ModifyStatLayerEffect ADDITIVE_DAMAGE_PERCENT = new ModifyStatLayerEffect(StatLayers.Offensive.ADDITIVE_DMG, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofStatData());
        public static ModifyStatLayerEffect CRIT_DAMAGE = new ModifyStatLayerEffect(StatLayers.Offensive.CRIT_DAMAGE, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofStatData());
        public static ModifyStatLayerEffect DOUBLE_DAMAGE = new ModifyStatLayerEffect(StatLayers.Offensive.DOUBLE_DAMAGE, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofStatData());
        public static ModifyStatLayerEffect ADDITIVE_FLAT_MANA_FROM_MANA = new ModifyStatLayerEffect(StatLayers.Offensive.FLAT_DAMAGE, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, NumberProvider.ofPercentOfStat(Mana.getInstance().GUID()));


        // defensive
        public static ModifyStatLayerEffect DAMAGE_REDUCTION = new ModifyStatLayerEffect(StatLayers.Defensive.DAMAGE_REDUCTION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.REDUCE, NumberProvider.ofStatData());
        public static ModifyStatLayerEffect DAMAGE_REDUCTION_50 = new ModifyStatLayerEffect(StatLayers.Defensive.DAMAGE_REDUCTION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.REDUCE, NumberProvider.specificNumber(50));


        // public static ModifyStatLayerEffect ELEMENTAL_RESIST = new ModifyStatLayerEffect(StatLayers.Defensive.ELEMENTAL_MITIGATION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.REDUCE, ModifyStatLayerEffect.CalculationType.JUST_STAT_NUMBER);
        //public static ModifyStatLayerEffect ELEMENTAL_PENETRATION = new ModifyStatLayerEffect(StatLayers.Defensive.ELEMENTAL_MITIGATION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, ModifyStatLayerEffect.CalculationType.JUST_STAT_NUMBER);

        //public static ModifyStatLayerEffect ARMOR = new ModifyStatLayerEffect(StatLayers.Defensive.PHYS_MITIGATION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.REDUCE, ModifyStatLayerEffect.CalculationType.EFFECTIVE_ARMOR);
        //public static ModifyStatLayerEffect ARMOR_PENETRATION = new ModifyStatLayerEffect(StatLayers.Defensive.PHYS_MITIGATION, EventData.NUMBER, ModifyStatLayerEffect.ModificationType.ADD, ModifyStatLayerEffect.CalculationType.EFFECTIVE_ARMOR);

        public static void init() {

        }
    }

    public static DataHolder<EffectCtx, StatEffect> GIVE_SELF_EFFECT_30_SEC = new DataHolder<>(
            Arrays.asList(
                    ModEffects.TAUNT_STANCE,
                    ModEffects.MISSILE_BARRAGE,
                    ModEffects.CURSE_AGONY,
                    ModEffects.DESPAIR,
                    ModEffects.CURSE_WEAKNESS,
                    ModEffects.SLOW,
                    ModEffects.WOUNDS
            ),
            x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Source, 30)
    );


    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_IN_AOE = new DataHolder<>(
            Arrays.asList(
                    ModEffects.REJUVENATE
            ),
            x -> new GiveExileStatusInRadius("give_" + x.id + "_to_allies_in_radius", AllyOrEnemy.allies, 10, x.resourcePath)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA = new DataHolder<>(
            ResourceType.values()
            , x -> new RestoreResourceAction("restore_" + x.id + "_per_stat_data", NumberProvider.ofStatData(), x, RestoreType.leech)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE = new DataHolder<>(
            ResourceType.values()
            , x -> new RestoreResourceAction("leech_" + x.id, NumberProvider.ofPercentOfDataNumber(EventData.NUMBER), x, RestoreType.leech)
    );

    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_TO_SOURCE_30_SEC = new DataHolder<>(
            Arrays.asList(
                    ModEffects.ENDURANCE_CHARGE,
                    ModEffects.POWER_CHARGE,
                    ModEffects.FRENZY_CHARGE,

                    ModEffects.ESSENCE_OF_FROST,
                    ModEffects.ICE_GOLEM_BUFF,
                    ModEffects.FIRE_GOLEM_BUFF,
                    ModEffects.LIGHTNING_GOLEM_BUFF
            )
            , x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Source, 30));

    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_TO_TARGET = new DataHolder<>(
            Arrays.asList(
                    ModEffects.STUN,
                    ModEffects.BLIND,
                    ModEffects.TAUNT_STANCE,
                    ModEffects.MISSILE_BARRAGE,
                    ModEffects.CURSE_AGONY,
                    ModEffects.DESPAIR,
                    ModEffects.CURSE_WEAKNESS,
                    ModEffects.SLOW,
                    ModEffects.WOUNDS
            )
            , x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Target, 15));

    public static DataHolder<EffectCtx, StatEffect> REMOVE_EFFECT_FROM_TARGET = new DataHolder<>(
            Arrays.asList(
                    ModEffects.BONE_CHILL
            )
            , x -> new RemoveExileEffectAction(x.resourcePath, EffectSides.Target));


    public static StatEffect SET_IS_CRIT = new SetBooleanEffect(EventData.CRIT);
    public static StatEffect INC_VALUE_PER_CURSE_ON_TARGET = new IncreaseNumberPerCurseOnTarget();
    public static StatEffect SET_PIERCE = new SetBooleanEffect(EventData.PIERCE);
    public static StatEffect SET_BARRAGE = new SetBooleanEffect(EventData.BARRAGE);

    /*
    // todo ..
    public static StatEffect INCREASE_VALUE = new IncreaseNumberByPercentEffect(EventData.NUMBER);
    public static StatEffect MULTIPLY_VALUE = new MultiplyNumberByPercentEffect(EventData.NUMBER);
    public static StatEffect DECREASE_VALUE = new DecreaseNumberByPercentEffect(EventData.NUMBER);


     */
    public static StatEffect INCREASE_EFFECT_DURATION = new IncreaseNumberByPercentEffect(EventData.EFFECT_DURATION_TICKS);
    public static StatEffect INCREASE_SECONDS = new IncreaseNumberByPercentEffect(EventData.SECONDS);
    public static StatEffect SET_ACCURACY = new SetDataNumberAction(EventData.ACCURACY);
    public static StatEffect ADD_STAT_DATA_TO_NUMBER = new AddToNumberEffect("add_stat_data_to_num", EventData.NUMBER, NumberProvider.ofStatData());
    //public static StatEffect ADD_TO_MAX_SUMMONS = new AddToNumberEffect("add_max_summons", EventData.BONUS_MAX_SUMMONS, NumberProvider.ofStatData());
    public static StatEffect ADD_TOTAL_SUMMONS = new AddToNumberEffect("add_total_summons", EventData.BONUS_TOTAL_SUMMONS, NumberProvider.ofStatData());

    public static StatEffect DECREASE_COOLDOWN = new DecreaseNumberByPercentEffect(EventData.COOLDOWN_TICKS);
    public static StatEffect DECREASE_COOLDOWN_BY_X_TICKS = new AddToNumberEffect("reduce_cd_by_ticks", EventData.COOLDOWN_TICKS, NumberProvider.ofStatData());
    public static StatEffect INCREASE_MANA_COST = new IncreaseNumberByPercentEffect(EventData.MANA_COST);
    public static StatEffect INCREASE_PROJ_SPEED = new IncreaseNumberByPercentEffect(EventData.PROJECTILE_SPEED_MULTI);
    public static StatEffect PROJECTILE_COUNT = new AddToNumberEffect("proj_count", EventData.BONUS_PROJECTILES, NumberProvider.ofStatData());
    public static StatEffect DURATION_INCREASE = new IncreaseNumberByPercentEffect(EventData.DURATION_MULTI);
    public static StatEffect AGGRO_INCREASE = new IncreaseNumberByPercentEffect(EventData.AGGRO_RADIUS);
    public static StatEffect DECREASE_CAST_TIME = new DecreaseNumberByPercentEffect(EventData.CAST_TICKS);
    public static StatEffect INCREASE_AREA = new IncreaseNumberByPercentEffect(EventData.AREA_MULTI);
    public static StatEffect APPLY_CAST_SPEED_TO_CD = new ApplyCooldownAsCastTimeEffect();
    public static StatEffect CANCEL_EVENT = new CancelEvent();


    public static DataHolder<SetCooldownEffect.Data, StatEffect> SET_COOLDOWN = new DataHolder<>(
            Arrays.asList(
                    SetCooldownEffect.MISSILE_BARRAGE
            )
            , x -> new SetCooldownEffect(x));


    public static StatEffect PROC_SHATTER = new ProcSpellEffect(WaterSpells.BONE_SHATTER_PROC, PositionSource.TARGET);
    public static StatEffect PROC_PROFANE_EXPLOSION = new ProcSpellEffect(ProcSpells.PROFANE_EXPLOSION, PositionSource.TARGET);


    public static void addSerializers() {

        Layers.init();
    }

    @Override
    public void registerAll() {

        for (StatEffect v : StatEffect.ALL_SERIAZABLE) {
            if (!v.id.isEmpty()) {
                v.addToSerializables();
            }
        }
    }
}
