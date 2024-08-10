package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.GiveOrTake2;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class GiveExileStatusEffect extends StatEffect {

    public EffectSides give_to;
    public int seconds = 10;
    public String effect = "";

    public GiveExileStatusEffect(String effect, EffectSides give_to, int sec) {
        super("give_" + effect + "_to_" + give_to.id, "give_exile_effect");
        this.give_to = give_to;
        this.seconds = sec;
        this.effect = effect;
    }

    public GiveExileStatusEffect() {
        super("", "give_exile_effect");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        ExilePotionEvent potionEvent = EventBuilder.ofEffect(new CalculatedSpellData(null), event.getSide(statSource), event.getSide(give_to), Load.Unit(event.getSide(statSource))
                        .getLevel(), ExileDB.ExileEffects()
                        .get(effect), GiveOrTake2.give, seconds * 20)
                .set(x -> {
                    if (event.isSpell()) {
                        x.data.setString(EventData.SPELL, event.getSpell().GUID());
                    }
                })
                .build();
        potionEvent.Activate();

    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return GiveExileStatusEffect.class;
    }

}