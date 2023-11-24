package com.robertx22.age_of_exile.event_hooks.damage_hooks;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.mixin_methods.OnHurtEvent;
import com.robertx22.age_of_exile.uncommon.UnstuckMobs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnNonPlayerDamageEntityEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        if (event.mob.level().isClientSide) {
            return;
        }
        if (event.source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return;
        }
        if (event.mob instanceof Player == false) {
            if (WorldUtils.isMapWorldClass(event.mob.level())) {
                if (event.source.is(DamageTypes.IN_WALL)) {
                    UnstuckMobs.unstuckFromWalls(event.mob);
                    return;
                }
            }
        }

        if (DmgSourceUtils.isMyDmgSource(event.source)) {
            return;
        }
        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            if (event.mob instanceof Player == false) {
                if (WorldUtils.isMapWorldClass(event.mob.level())) {
                    event.damage = 0;
                    event.canceled = true;
                }
            }
            return;
        }

        if (!(event.source.getEntity() instanceof Player)) {

            if (event.source.getEntity() instanceof SummonEntity summon) {
                LivingEntity caster = summon.getOwner();


                if (caster != null) {

                    event.damage = 0;
                    event.canceled = true;

                    Spell spell = ExileDB.Spells().get(Load.Unit(summon).summonedPetData.spell);

                    if (spell != null) {


                        Spell basic = spell.getConfig().getSummonBasicSpell();

                        var ctx = new SpellCastContext(caster, 0, basic);


                        boolean cancast = false;
                        if (caster instanceof Player p) {
                            if (Load.player(p).spellCastingData.canCast(basic, p)) {
                                cancast = true;
                            } else {
                                cancast = false;
                            }
                        }

                        if (cancast) {
                            basic.spendResources(ctx);
                            basic.attached.onCast(SpellCtx.onCast(caster, ctx.calcData));
                            basic.attached.tryActivate(Spell.DEFAULT_EN_NAME, SpellCtx.onHit(caster, summon, event.mob, ctx.calcData));
                        }

                    }
                } else {
                    summon.kill();
                }
            } else {
                OnHurtEvent.onHurtEvent(new AttackInformation(event, AttackInformation.Mitigation.PRE, event.mob, event.source, event.damage));
            }
        }
    }
}
