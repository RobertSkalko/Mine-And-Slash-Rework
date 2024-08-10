package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.effectdatas.builders.DamageEventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class EventBuilder<T extends EffectEvent> {

    protected T event;

    public static EventBuilder<ExilePotionEvent> ofEffect(CalculatedSpellData calc, LivingEntity caster, LivingEntity target, int lvl, ExileEffect effect, GiveOrTake2 giveOrTake, int ticks) {
        ExilePotionEvent event = new ExilePotionEvent(calc, lvl, effect, giveOrTake, caster, target, ticks);
        EventBuilder<ExilePotionEvent> b = new EventBuilder();
        b.event = event;
        return b;
    }

    public static DamageEventBuilder ofSpellDamage(LivingEntity source, LivingEntity target, int dmg, Spell spell) {
        DamageEvent event = new DamageEvent(null, source, target, dmg);
        DamageEventBuilder b = new DamageEventBuilder();
        b.event = event;
        b.setupDamage(AttackType.hit, spell.getWeapon(source), spell.getConfig().getStyle());
        b.setSpell(spell);
        return b;
    }

    public static DamageEventBuilder ofDamage(AttackInformation data, LivingEntity source, LivingEntity target, float dmg) {
        DamageEvent event = new DamageEvent(data, source, target, dmg);
        DamageEventBuilder b = new DamageEventBuilder();
        b.event = event;
        return b;
    }

    public static DamageEventBuilder ofDamage(LivingEntity source, LivingEntity target, float dmg) {
        DamageEvent event = new DamageEvent(null, source, target, dmg);
        DamageEventBuilder b = new DamageEventBuilder();
        b.event = event;
        return b;
    }

    public static EventBuilder<RestoreResourceEvent> ofRestore(LivingEntity source, LivingEntity target, ResourceType resource, RestoreType restoreType, float amount) {
        RestoreResourceEvent event = new RestoreResourceEvent(amount, source, target);
        EventBuilder<RestoreResourceEvent> b = new EventBuilder();
        b.event = event;
        event.data.setString(EventData.RESOURCE_TYPE, resource.name());
        event.data.setString(EventData.RESTORE_TYPE, restoreType.name());
        return b;
    }

    public EventBuilder<T> setSpell(Spell spell) {
        this.event.data.setString(EventData.SPELL, spell.GUID());
        return this;
    }

    public EventBuilder<T> setSpell(String spell) {
        this.event.data.setString(EventData.SPELL, spell);
        return this;
    }

    public EventBuilder<T> set(Consumer<T> s) {
        s.accept(event);
        return this;
    }

    protected void buildCheck() {

    }

    public T build() {
        buildCheck();
        return event;
    }

}
