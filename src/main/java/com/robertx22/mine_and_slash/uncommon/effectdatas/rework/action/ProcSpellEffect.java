package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.spells.components.actions.PositionSource;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.player.Player;

public class ProcSpellEffect extends StatEffect {

    String spellId = "";
    PositionSource pos = PositionSource.TARGET;
    boolean can_proc_from_procs = false;

    EffectSides source = EffectSides.Source;
    EffectSides target = EffectSides.Target;

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

        if (!can_proc_from_procs && event.data.getBoolean(EventData.IS_PROC)) {
            return;
        }

        // be careful not to make it proc itself
        var spell = ExileDB.Spells().get(spellId);

        var SO = event.getSide(source);
        var TA = event.getSide(target);

        var ctx = new SpellCastContext(SO, 0, spell);

        var c = SpellCtx.onCast(SO, ctx.calcData);
        c.isProc = true;

        c.setPositionSource(pos);
        c.target = TA;

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
