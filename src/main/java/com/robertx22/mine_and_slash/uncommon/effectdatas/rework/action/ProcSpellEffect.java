package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.spells.components.actions.PositionSource;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.world.entity.player.Player;

public class ProcSpellEffect extends StatEffect {

    String spellId = "";
    PositionSource pos = PositionSource.TARGET;

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
        var SO = event.getSide(source);
        var TA = event.getSide(target);

        // be careful not to make it proc itself
        var spell = ExileDB.Spells().get(spellId);
        var ctx = new SpellCastContext(SO, 0, spell);
        var c = SpellCtx.onCast(SO, ctx.calcData);
        c.setPositionSource(pos);
        c.target = TA;
        
        // ALWAYS CHECK THIS BEFORE ACTUALLY CASTING THE PROC SPELL
        if (Load.Unit(event.source).getCooldowns().isOnCooldown(spell.GUID())) {
            return;
        }
        if (event.source instanceof Player p) {
            // always set the cooldown first
            Load.player(p).spellCastingData.setCooldownOnCasted(ctx);
        } else {
            Load.Unit(event.source).getCooldowns().setOnCooldown(spell.GUID(), spell.getCooldownTicks(ctx));
        }

        // THIS GOES LAST!!! If it's above in might create a stackoverflow
        spell.attached.onCast(c);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ProcSpellEffect.class;
    }

}
