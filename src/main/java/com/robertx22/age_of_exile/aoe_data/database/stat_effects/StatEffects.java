package com.robertx22.age_of_exile.aoe_data.database.stat_effects;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.actions.PositionSource;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.*;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class StatEffects implements ExileRegistryInit {

    public static DataHolder<EffectCtx, StatEffect> GIVE_SELF_EFFECT = new DataHolder<>(
            Arrays.asList(
                    ModEffects.TAUNT_STANCE
            ),
            x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Source, 10)
    );


    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_IN_AOE = new DataHolder<>(
            Arrays.asList(ModEffects.REJUVENATE
            ),
            x -> new GiveExileStatusInRadius("give_" + x.id + "_to_allies_in_radius", AllyOrEnemy.allies, 10, x.resourcePath)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA = new DataHolder<>(
            Arrays.asList(
                    ResourceType.mana,
                    ResourceType.energy,
                    ResourceType.magic_shield,
                    ResourceType.health
            )
            , x -> new RestoreResourceAction("restore_" + x.id + "_per_stat_data", NumberProvider.ofStatData(), x, RestoreType.leech)
    );

    public static DataHolder<ResourceType, StatEffect> LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE = new DataHolder<>(
            Arrays.asList(
                    ResourceType.mana,
                    ResourceType.energy,
                    ResourceType.magic_shield,
                    ResourceType.health
            )
            , x -> new RestoreResourceAction("leech_" + x.id, NumberProvider.ofPercentOfDataNumber(EventData.NUMBER), x, RestoreType.leech)
    );

    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_TO_SOURCE = new DataHolder<>(
            Arrays.asList(
            )
            , x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Source, 5));

    public static DataHolder<EffectCtx, StatEffect> GIVE_EFFECT_TO_TARGET = new DataHolder<>(
            Arrays.asList(
                    ModEffects.SLOW,
                    ModEffects.STUN,
                    ModEffects.BLIND
            )
            , x -> new GiveExileStatusEffect(x.resourcePath, EffectSides.Target, 5));

    public static DataHolder<EffectCtx, StatEffect> REMOVE_EFFECT_FROM_TARGET = new DataHolder<>(
            Arrays.asList(
                    ModEffects.BONE_CHILL
            )
            , x -> new RemoveExileEffectAction(x.resourcePath, EffectSides.Target));


    public static StatEffect SET_IS_CRIT = new SetBooleanEffect(EventData.CRIT);
    public static StatEffect INC_VALUE_PER_CURSE_ON_TARGET = new IncreaseNumberPerCurseOnTarget();
    public static StatEffect DOUBLE_DAMAGE = new DoubleDamageAction();
    public static StatEffect SET_PIERCE = new SetBooleanEffect(EventData.PIERCE);
    public static StatEffect SET_BARRAGE = new SetBooleanEffect(EventData.BARRAGE);
    public static StatEffect INCREASE_VALUE = new IncreaseNumberByPercentEffect(EventData.NUMBER);
    public static StatEffect MULTIPLY_VALUE = new MultiplyNumberByPercentEffect(EventData.NUMBER);
    public static StatEffect DECREASE_VALUE = new DecreaseNumberByPercentEffect(EventData.NUMBER);
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
    public static StatEffect DECREASE_CAST_TIME = new DecreaseNumberByPercentEffect(EventData.CAST_TICKS);
    public static StatEffect INCREASE_AREA = new IncreaseNumberByPercentEffect(EventData.AREA_MULTI);
    public static StatEffect APPLY_CAST_SPEED_TO_CD = new ApplyCooldownAsCastTimeEffect();

    public static DataHolder<String, StatEffect> ADD_PERC_OF_STAT_TO_NUMBER = new DataHolder<>(
            Arrays.asList(
                    Health.getInstance()
                            .GUID(),
                    Mana.getInstance()
                            .GUID()
            )
            , x -> new AddToNumberEffect("add_perc_of_" + x + "_to_num", EventData.NUMBER, NumberProvider.ofPercentOfStat(x)));


    public static StatEffect PROC_SHATTER = new ProcSpellEffect(WaterSpells.BONE_SHATTER_PROC, PositionSource.TARGET);


    public static void loadClass() {
    }

    @Override
    public void registerAll() {
        PROC_SHATTER.addToSerializables();
        REMOVE_EFFECT_FROM_TARGET.addToSerializables();

        ADD_TOTAL_SUMMONS.addToSerializables();
        DURATION_INCREASE.addToSerializables();
        SET_BARRAGE.addToSerializables();
        PROJECTILE_COUNT.addToSerializables();
        GIVE_SELF_EFFECT.addToSerializables();
        APPLY_CAST_SPEED_TO_CD.addToSerializables();
        DOUBLE_DAMAGE.addToSerializables();
        MULTIPLY_VALUE.addToSerializables();
        GIVE_EFFECT_TO_TARGET.addToSerializables();
        SET_IS_CRIT.addToSerializables();
        INCREASE_VALUE.addToSerializables();
        SET_ACCURACY.addToSerializables();
        LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.addToSerializables();
        ADD_PERC_OF_STAT_TO_NUMBER.addToSerializables();
        ADD_STAT_DATA_TO_NUMBER.addToSerializables();
        INCREASE_SECONDS.addToSerializables();
        DECREASE_COOLDOWN.addToSerializables();
        INCREASE_MANA_COST.addToSerializables();
        SET_PIERCE.addToSerializables();
        INCREASE_AREA.addToSerializables();
        INCREASE_PROJ_SPEED.addToSerializables();
        DECREASE_CAST_TIME.addToSerializables();
        LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.addToSerializables();
        GIVE_EFFECT_IN_AOE.addToSerializables();
        DECREASE_COOLDOWN_BY_X_TICKS.addToSerializables();
        DECREASE_VALUE.addToSerializables();
        INCREASE_EFFECT_DURATION.addToSerializables();

    }
}
