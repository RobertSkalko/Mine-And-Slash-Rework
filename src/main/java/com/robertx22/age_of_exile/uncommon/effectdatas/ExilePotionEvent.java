package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ExilePotionEvent extends EffectEvent {

    public static String ID = "on_exile_effect";

    public String spellid = "";

    @Override
    public String getName() {
        return "MNS Effect Event";
    }

    @Override
    public String GUID() {
        return ID;
    }

    int lvl;

    CalculatedSpellData calc;

    public ExilePotionEvent(CalculatedSpellData calc, int lvl, ExileEffect effect, GiveOrTake giveOrTake, LivingEntity caster, LivingEntity target, int tickDuration) {
        super(1, caster, target);
        this.lvl = lvl;
        this.calc = calc;

        this.data.setupNumber(EventData.STACKS, 1);
        this.data.setString(EventData.GIVE_OR_TAKE, giveOrTake.name());
        this.data.setString(EventData.EXILE_EFFECT, effect.GUID());
        this.data.setupNumber(EventData.EFFECT_DURATION_TICKS, tickDuration);

    }

    @Override
    protected void activate() {

        if (source.level().isClientSide) {
            return;
        }
        if (this.data.isCanceled()) {
            return;
        }

        int stacks = (int) data.getNumber(EventData.STACKS).number;

        GiveOrTake action = data.getGiveOrTake();
        ExileEffect effect = data.getExileEffect();

        ExileEffectInstanceData extraData = Load.Unit(target).getStatusEffectsData().getOrCreate(effect);

        boolean applied = extraData.stacks == 0;


        if (action == GiveOrTake.take) {

            extraData.stacks -= stacks;
            extraData.stacks = Mth.clamp(extraData.stacks, 0, effect.max_stacks);
            extraData.str_multi = data.getNumber();

            Load.Unit(target).setEquipsChanged(true);
            Load.Unit(target).setShouldSync();
        } else {


            extraData.stacks++;
            extraData.stacks = Mth.clamp(extraData.stacks, 1, effect.max_stacks);

        }


        extraData.caster_uuid = source.getStringUUID();
        extraData.spell_id = this.spellid;
        extraData.str_multi = data.getNumber();
        extraData.calcSpell = this.calc;
        extraData.ticks_left = (int) data.getNumber(EventData.EFFECT_DURATION_TICKS).number;

        if (extraData.stacks < 1) {
            Load.Unit(target).getStatusEffectsData().delete(effect);
        }

        if (applied) {
            effect.onApply(target);
        }

        Load.Unit(target).setEquipsChanged(true);
        Load.Unit(target).tryRecalculateStats();
        Load.Unit(target).setShouldSync();
    }


}
