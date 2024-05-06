package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.OnMobKilledByDamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import static com.robertx22.age_of_exile.database.data.stats.Stat.VAL1;

public class ProcStats {

    public static DataPackStatAccessor<EmptyAccessor> PROFANE_EXPLOSION_ON_KILL = DatapackStatBuilder
            .ofSingle("proc_profane_explosion", Elements.Cold)
            .worksWithEvent(OnMobKilledByDamageEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.IS_TARGET_CURSED)
            .addEffect(e -> StatEffects.PROC_PROFANE_EXPLOSION)
            .setLocName(x -> Stat.format(VAL1 + "% Chance to cause a Profane Exposion on kiling a Cursed Enemy."))
            .setLocDesc(x -> "This has a short cooldown. You can see the whole spell stats in the Ingame Library TODO")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.max = 100;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> PROC_SHATTER_MAX_FROST_ESSENCE = DatapackStatBuilder
            .ofSingle("proc_shatter_max_frost", Elements.Cold)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.IS_SOURCE_MAX_CHARGES.get(ModEffects.ESSENCE_OF_FROST))
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.SHATTER))
            .addEffect(e -> StatEffects.PROC_SHATTER)
            .setLocName(x -> Stat.format(VAL1 + "% Chance to casts Bone Shatter while at max Essence of Frost charges. This only works on spells have the Shatter tag."))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.max = 100;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROC_SHATTER = DatapackStatBuilder
            .ofSingle("proc_shatter", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.IS_EVENT_AILMENT.get(Ailments.FREEZE))
            .addCondition(StatConditions.TARGET_HAS_EFFECT.get(ModEffects.BONE_CHILL))
            .addCondition(StatConditions.BONE_SHATTER_NO_CD)
            .addEffect(e -> StatEffects.PROC_SHATTER)
            .addEffect(e -> StatEffects.GIVE_EFFECT_TO_SOURCE_30_SEC.get(ModEffects.ESSENCE_OF_FROST))
            .addEffect(e -> StatEffects.REMOVE_EFFECT_FROM_TARGET.get(ModEffects.BONE_CHILL))
            .setLocName(x -> Stat.format(VAL1 + "% Chance to casts Bone Shatter when you shatter a bone-chilled enemy. Also gives you Essence of Frost"))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.max = 100;
            })
            .build();

    public static void init() {

    }
}
