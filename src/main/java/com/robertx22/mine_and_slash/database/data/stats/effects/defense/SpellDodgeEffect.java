package com.robertx22.mine_and_slash.database.data.stats.effects.defense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.SpellDodge;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.util.Mth;

public class SpellDodgeEffect extends BaseDamageEffect {

    protected SpellDodgeEffect() {
    }

    public static SpellDodgeEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatPriority GetPriority() {
        return StatPriority.Damage.HIT_PREVENTION;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
        SpellDodge dodge = (SpellDodge) stat;

        float totalDodge = Mth.clamp(data.getValue() - effect.data.getNumber(EventData.ACCURACY).number, 0, Integer.MAX_VALUE);

        float chance = dodge.getUsableValue(effect.targetData.getUnit(), (int) totalDodge, effect.sourceData.getLevel()) * 100;

        if (RandomUtils.roll(chance)) {
            effect.data.setHitAvoided(EventData.IS_DODGED);
        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        if (effect.isSpell() && effect.getSpell().config.tags.contains(SpellTags.magic)) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final SpellDodgeEffect INSTANCE = new SpellDodgeEffect();
    }
}
