package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mixin_methods.OnHurtEvent;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnNonPlayerDamageEntityEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {
        if (event.mob.level().isClientSide) {
            return;
        }

        if (DmgSourceUtils.isMyDmgSource(event.source)) {
            return;
        }
        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            //     OnDmgDisableEnviroDmg.accept(event);
            return;
        }
        if (!(event.source.getEntity() instanceof Player)) {
            Stats.reso

            if (event.source.getEntity() instanceof SummonEntity summon) {
                LivingEntity caster = summon.getOwner();
                if (caster != null) {
                    int num = (int) Load.Unit(caster)
                            .getUnit()
                            .getCalculatedStat(WeaponDamage.getInstance())
                            .getValue();

                    num *= 0.5F; // gotta nerf summons a bit i think or they would be op for every build?
                    // to make them more viable more summoners, ill make summon damage stats bigger

                    Spell spell = ExileDB.Spells().get(Load.Unit(summon).summonedPetData.spell);

                    if (spell != null) {
                        DamageEvent dmg = EventBuilder.ofSpellDamage(caster, event.mob, num, spell)
                                .setupDamage(AttackType.attack, WeaponTypes.none, PlayStyle.INT)
                                .setIsBasicAttack()
                                .set(x -> x.data.setBoolean(EventData.IS_SUMMON_ATTACK, true))
                                .build();

                        dmg.Activate();
                    }
                }
            } else {

                // todo, i'm not sure if i want to override vanila damage or keep using both...
                OnHurtEvent.onHurtEvent(new AttackInformation(event, AttackInformation.Mitigation.PRE, event.mob, event.source, event.damage));
            }
        }
    }
}
