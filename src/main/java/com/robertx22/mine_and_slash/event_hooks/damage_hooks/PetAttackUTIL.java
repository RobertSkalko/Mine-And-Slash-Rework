package com.robertx22.mine_and_slash.event_hooks.damage_hooks;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PetAttackUTIL {

    public static void tryAttack(LivingEntity summon, LivingEntity caster, LivingEntity target) {

        if (caster != null) {

            Spell spell = ExileDB.Spells().get(Load.Unit(summon).summonedPetData.spell);

            if (spell != null) {


                Spell basic = spell.getConfig().getSummonBasicSpell();

                var ctx = new SpellCastContext(caster, 0, basic);

                var originctx = new SpellCastContext(caster, 0, spell);


                boolean cancast = false;
                if (caster instanceof Player p) {
                    if (Load.player(p).spellCastingData.canCast(basic, p).can) {
                        cancast = true;
                    } else {
                        cancast = false;
                    }
                }

                if (cancast) {
                    basic.spendResources(ctx);
                    basic.attached.onCast(SpellCtx.onCast(caster, originctx.calcData));
                    basic.attached.tryActivate(Spell.DEFAULT_EN_NAME, SpellCtx.onHit(caster, summon, target, originctx.calcData)); // todo this should be reworked.
                    // pet ability should gain the stats of the pet used to summon it, this is a nasty hack
                }

            }
        } else {
            summon.kill();
        }
    }
}
