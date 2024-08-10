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

public class RemoveExileEffectAction extends StatEffect {

    public EffectSides remove_from;
    public String effect = "";
    public int stacks = 1;

    public RemoveExileEffectAction(String effect, EffectSides remove_from) {
        super("remove_" + effect + "_from_" + remove_from.id, "remove_exile_effect");
        this.remove_from = remove_from;
        this.effect = effect;
    }

    public RemoveExileEffectAction() {
        super("", "remove_exile_effect");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        ExilePotionEvent potionEvent = EventBuilder.ofEffect(new CalculatedSpellData(null), event.getSide(statSource), event.getSide(remove_from), Load.Unit(event.getSide(statSource))
                        .getLevel(), ExileDB.ExileEffects()
                        .get(effect), GiveOrTake2.take, 1)
                .set(x -> x.data.getNumber(EventData.STACKS).number = stacks)
                .build();
        potionEvent.Activate();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return RemoveExileEffectAction.class;
    }
}
