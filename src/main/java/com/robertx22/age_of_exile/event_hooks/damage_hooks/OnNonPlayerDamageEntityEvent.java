package com.robertx22.age_of_exile.event_hooks.damage_hooks;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.mixin_methods.OnHurtEvent;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.UnstuckMobs;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
                    int num = (int) Load.Unit(caster)
                            .getUnit()
                            .getCalculatedStat(WeaponDamage.getInstance())
                            .getValue();

                    float dmgMulti = 0.5F;

                    num *= dmgMulti; // gotta nerf summons a bit i think or they would be op for every build?
                    // to make them more viable more summoners, ill make summon damage stats bigger


                    Spell spell = ExileDB.Spells().get(Load.Unit(summon).summonedPetData.spell);

                    if (spell != null) {

                        float cost = Energy.getInstance().scale(ModType.FLAT, 5, Load.Unit(caster).getLevel()) * dmgMulti;

                        SpendResourceEvent spendEnergy = new SpendResourceEvent(caster, ResourceType.energy, cost);
                        spendEnergy.calculateEffects();

                        if (spendEnergy.data.getNumber() <= Load.Unit(caster).getResources().getEnergy()) {

                            event.damage = 0;
                            event.canceled = true;

                            spendEnergy.Activate();

                            DamageEvent dmg = EventBuilder.ofSpellDamage(caster, event.mob, num, spell)
                                    .setupDamage(AttackType.hit, WeaponTypes.none, PlayStyle.INT)
                                    .setIsBasicAttack()
                                    .set(x -> {
                                        x.petEntity = summon;
                                        x.data.setBoolean(EventData.IS_SUMMON_ATTACK, true);
                                    })
                                    .build();

                            //the knockback makes them op
                            dmg.data.setBoolean(EventData.DISABLE_KNOCKBACK, true);

                            dmg.Activate();
                        }
                    }
                }
            } else {
                OnHurtEvent.onHurtEvent(new AttackInformation(event, AttackInformation.Mitigation.PRE, event.mob, event.source, event.damage));
            }
        }
    }
}
