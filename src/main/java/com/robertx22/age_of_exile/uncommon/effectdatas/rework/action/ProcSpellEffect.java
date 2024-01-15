package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.spells.components.actions.PositionSource;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.player.Player;

public class ProcSpellEffect extends StatEffect {

    String spellId = "";
    PositionSource pos = PositionSource.TARGET;

    public ProcSpellEffect(String spellId, PositionSource pos) {
        super("proc_spell_" + spellId, "proc_spell");
        this.spellId = spellId;
        this.pos = pos;
    }

    ProcSpellEffect() {
        super("", "proc_spell");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        // be careful not to make it proc itself
        var spell = ExileDB.Spells().get(spellId);

        var ctx = new SpellCastContext(event.source, 0, spell);

        var c = SpellCtx.onCast(event.source, ctx.calcData);

        c.setPositionSource(pos);
        c.target = event.target;

        spell.attached.onCast(c);

        if (event.source instanceof Player p) {
            Load.player(p).spellCastingData.setCooldownOnCasted(ctx);
        }


    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ProcSpellEffect.class;
    }

}
