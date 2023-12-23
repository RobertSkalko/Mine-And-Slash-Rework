package com.robertx22.age_of_exile.database.data.stats.types.summon;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.golems.GolemSummon;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;

public class GolemSpellChance extends Stat {

    private GolemSpellChance() {
        this.format = ChatFormatting.AQUA.getName();

        this.statEffect = new GolemSpellEffect();
    }

    public static GolemSpellChance getInstance() {
        return GolemSpellChance.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Bonus Chance for golems to cast their AOE nova spells";
    }

    @Override
    public String GUID() {
        return "golem_spell_chance";
    }

    @Override
    public String locNameForLangFile() {
        return "Golem Spell Chance";
    }

    static class GolemSpellEffect implements IStatEffect {

        @Override
        public boolean worksOnEvent(EffectEvent ev) {
            return ev instanceof DamageEvent;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public int GetPriority() {
            return 0;
        }

        @Override
        public void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {

            if (effect instanceof DamageEvent dmg) {

                if (dmg.petEntity instanceof GolemSummon sum) {
                    if (!Load.Unit(sum).getCooldowns().isOnCooldown("golem_spell")) {
                        Load.Unit(sum).getCooldowns().setOnCooldown("golem_spell", 20);
                        int chance = (int) effect.sourceData.getUnit().getCalculatedStat(GolemSpellChance.getInstance()).getValue();

                        if (RandomUtils.roll(chance)) {
                            var spell = ExileDB.Spells().get(sum.aoeSpell());
                            // todo this doesnt affect summon damage.. hm
                            var c = (new SpellCastContext(effect.source, 0, spell));
                            spell.getAttached().onCast(SpellCtx.onCast(effect.source, c.calcData).setSourceEntity(sum));

                        }
                    }
                }
            }
        }
    }

    private static class SingletonHolder {
        private static final GolemSpellChance INSTANCE = new GolemSpellChance();
    }
}
