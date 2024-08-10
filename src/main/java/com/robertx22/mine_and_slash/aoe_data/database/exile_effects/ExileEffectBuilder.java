package com.robertx22.mine_and_slash.aoe_data.database.exile_effects;

import com.robertx22.mine_and_slash.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.exile_effects.EffectType;
import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.data.exile_effects.VanillaStatData;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.tags.imp.EffectTag;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class ExileEffectBuilder {

    private ExileEffect effect = new ExileEffect();

    public static ExileEffectBuilder of(EffectCtx ctx) {
        ExileEffectBuilder b = new ExileEffectBuilder();
        b.effect.type = ctx.type;
        b.effect.id = ctx.resourcePath;
        b.effect.locName = ctx.locname;

        if (ctx.type == EffectType.beneficial) {
            b.addTags(EffectTags.positive);
        }
        if (ctx.type == EffectType.negative) {
            b.addTags(EffectTags.negative);
        }
        return b;
    }

    public static ExileEffectBuilder food(EffectCtx ctx) {
        ExileEffectBuilder b = of(ctx);
        b.addTags(EffectTags.food);
        b.maxStacks(1);
        return b;
    }

    public ExileEffectBuilder addTags(EffectTag... tags) {
        for (EffectTag tag : tags) {
            if (!effect.tags.contains(tag.GUID())) {
                this.effect.tags.add(tag);
            }
        }
        return this;
    }

    public ExileEffectBuilder addSpellTags(SpellTag... tags) {
        for (SpellTag tag : tags) {
            if (!effect.spell_tags.contains(tag.GUID())) {
                this.effect.spell_tags.add(tag);
            }
        }
        return this;
    }

    public ExileEffectBuilder oneOfAKind(String kind) {
        this.effect.one_of_a_kind_id = kind;
        return this;
    }

    public ExileEffectBuilder removeOnSpellCastWithTag(SpellTag tag) {
        this.effect.remove_on_spell_cast = tag;
        return this;
    }

    public ExileEffectBuilder stat(StatMod stat) {
        this.effect.stats.add(stat);
        return this;
    }

    public ExileEffectBuilder vanillaStat(VanillaStatData stat) {
        this.effect.mc_stats.add(stat);
        return this;
    }

    public ExileEffectBuilder maxStacks(int stacks) {
        this.effect.max_stacks = stacks;
        return this;
    }

    public ExileEffectBuilder spell(Spell stat) {
        this.effect.spell = stat.getAttached();
        return this;
    }

    public ExileEffectBuilder disableStackingStatBuff() {
        this.effect.stacks_affect_stats = false;
        return this;
    }

    public ExileEffectBuilder stat(float first, float second, Stat stat, ModType type) {
        StatMod data = new StatMod(first, second, stat, type);
        this.effect.stats.add(data);
        return this;
    }

    public ExileEffectBuilder stat(float first, float second, Stat stat) {
        return stat(first, second, stat, ModType.FLAT);
    }

    public ExileEffect build() {
        effect.addToSerializables();
        return effect;
    }

}
