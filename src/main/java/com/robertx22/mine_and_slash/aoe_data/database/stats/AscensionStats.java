package com.robertx22.mine_and_slash.aoe_data.database.stats;

public class AscensionStats {

    // todo whats the best way to make it so after i cast a spell, it removes a stack of buff?

    /*
    public static DataPackStatAccessor<EmptyAccessor> BARRAGE_SELF_BUFF_ON_SPELL_CRIT = DatapackStatBuilder
            .ofSingle("chance_of_barrage_on_spell_crit", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.IS_BOOLEAN.get(EventData.CRIT))
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.SPELL_NOT_HAVE_TAG.get(SpellTags.MISSILE))
            .addCondition(StatConditions.IS_NOT_ON_COOLDOWN.get(SetCooldownEffect.MISSILE_BARRAGE.cd_id))
            .addEffect(StatEffects.GIVE_SELF_EFFECT_30_SEC.get(ModEffects.MISSILE_BARRAGE))
            .addEffect(StatEffects.SET_COOLDOWN.get(SetCooldownEffect.MISSILE_BARRAGE))
            .setLocName(x -> Stat.format(
                    "Your non Missile Spell Criticals have " + Stat.VAL1 + "% chance of giving you " + ModEffects.MISSILE_BARRAGE.locname
            ))
            .setLocDesc(x -> "Chance to give effect")
            .modifyAfterDone(x -> {
                x.setChanceBasedStatDefaults();
            })
            .build();


     */
    public static void init() {


    }
}
