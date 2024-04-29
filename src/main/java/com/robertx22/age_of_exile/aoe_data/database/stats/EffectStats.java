package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.ModTag;
import com.robertx22.age_of_exile.tags.TagType;
import com.robertx22.age_of_exile.tags.imp.EffectTag;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.Arrays;

public class EffectStats {
    public static DataPackStatAccessor<ModTag> EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG = DatapackStatBuilder
            .<ModTag>of(x -> "inc_effect_of_" + x.GUID() + "_buff_given", x -> Elements.Physical)
            .addAllOfType(ModTag.MAP.get(TagType.Effect))
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
            .setLocName(x -> x.locNameForLangFile() + " Effect Strength")
            .setLocDesc(x -> "Increases the stat strength of effects you give")
            .modifyAfterDone(x -> {
                //  x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();
    public static DataPackStatAccessor<ModTag> EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG = DatapackStatBuilder
            .<ModTag>of(x -> "inc_effect_of_" + x.GUID() + "_buff_on_you", x -> Elements.Physical)
            .addAllOfType(ModTag.MAP.get(TagType.Effect))
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Target)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
            .setLocName(x -> Stat.VAL1 + "% to effectiveness of " + x.locNameForLangFile() + " buffs on you")
            .setLocDesc(x -> "Increases the stat strength of effects you receive")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> EFFECT_DURATION_YOU_CAST = DatapackStatBuilder
            .ofSingle("eff_dur_u_cast", Elements.Physical)
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addEffect(e -> StatEffects.INCREASE_EFFECT_DURATION)
            .setLocName(x -> "Effect Duration")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EffectTag> EFFECT_DURATION_YOU_CAST_PER_TAG = DatapackStatBuilder
            .<EffectTag>of(x -> x.GUID() + "_eff_dur_u_cast", x -> Elements.Physical)
            .addAllOfType(EffectTag.getAll())
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(e -> StatEffects.INCREASE_EFFECT_DURATION)
            .setLocName(x -> x.locNameForLangFile() + " Effect Duration")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EffectCtx> GIVE_EFFECT_TO_ALLIES_IN_RADIUS = DatapackStatBuilder
            .<EffectCtx>of(x -> "give_" + x.id + "_to_allies_in_aoe", x -> x.element)
            .addAllOfType(Arrays.asList(
                    ModEffects.REJUVENATE
            ))
            .worksWithEvent(RestoreResourceEvent.ID) // todo should be tick event, BUT LAG
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addCondition(StatConditions.IS_IN_COMBAT)
            .addEffect(e -> StatEffects.GIVE_EFFECT_IN_AOE.get(e))
            .setLocName(x -> Stat.format(
                    "Give " + x.locname + " to allies in Radius."
            ))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 1;
                x.is_long = true;
            })
            .build();
    public static DataPackStatAccessor<EffectCtx> GIVE_EFFECT_TO_SELF_ON_TICK = DatapackStatBuilder
            .<EffectCtx>of(x -> "give_" + x.id + "_to_self_on_tick", x -> x.element)
            .addAllOfType(Arrays.asList(
                    ModEffects.TAUNT_STANCE
            ))
            .worksWithEvent(RestoreResourceEvent.ID) // todo should be tick event, BUT LAG
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addEffect(e -> StatEffects.GIVE_SELF_EFFECT_30_SEC.get(e))
            .setLocName(x -> Stat.format(
                    "Give " + x.locname + " to self"
            ))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 1;
                x.is_long = true;
            })
            .build();
    public static DataPackStatAccessor<EffectCtx> CHANCE_OF_APPLYING_EFFECT = DatapackStatBuilder
            .<EffectCtx>of(x -> "chance_of_" + x.id, x -> x.element)
            .addAllOfType(Arrays.asList(
                            ModEffects.BLIND,
                            ModEffects.SLOW
                    )
            )
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.FINAL_DAMAGE)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
            .addEffect(x -> StatEffects.GIVE_EFFECT_TO_TARGET.get(x))
            .setLocName(x -> Stat.format(
                    "Your " + x.element.getIconNameFormat() + " Attacks have " + Stat.VAL1 + "% chance of applying " + x.locname
            ))
            .setLocDesc(x -> "Chance to give effect")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 100;
                x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static void init() {

    }


}
