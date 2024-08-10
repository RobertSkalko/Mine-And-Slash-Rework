package com.robertx22.mine_and_slash.database.data.stats.types.defense;

import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;

public class DodgeRating extends Stat implements IUsableStat {

    public static String GUID = "dodge";

    public static DodgeRating getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to ignore attack damage";
    }

    private DodgeRating() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.statEffect = new Effect();

        this.icon = UNICODE.STAR;
        this.format = ChatFormatting.DARK_GREEN.getName();

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Dodge Rating";
    }

    @Override
    public float getMaxMulti() {
        return 0.8F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 100;
    }

    private static class Effect extends BaseDamageEffect {

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
            effect.data.setHitAvoided(EventData.IS_DODGED);
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {

            if (effect.GetElement() != Elements.Physical) {
                return false;
            }
            if (effect.isSpell() && effect.getSpell().config.tags.contains(SpellTags.magic)) {
                return false;
            }

            DodgeRating dodge = (DodgeRating) stat;

            float totalDodge = Mth.clamp(data.getValue() - effect.data.getNumber(EventData.ACCURACY).number, 0, Integer.MAX_VALUE);

            float chance = dodge.getUsableValue(effect.targetData.getUnit(), (int) totalDodge, effect.sourceData.getLevel()) * 100;

            return effect.getAttackType().isAttack() && RandomUtils.roll(chance);
        }
    }

    private static class SingletonHolder {
        private static final DodgeRating INSTANCE = new DodgeRating();
    }
}
