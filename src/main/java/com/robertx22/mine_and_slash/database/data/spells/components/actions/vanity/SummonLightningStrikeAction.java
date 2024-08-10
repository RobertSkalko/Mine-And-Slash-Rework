package com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellUtils;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;

public class SummonLightningStrikeAction extends SpellAction {

    public SummonLightningStrikeAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClientSide) {
            targets.forEach(t -> {

                SpellUtils.summonLightningStrike(t);
            });
        }
    }

    public MapHolder create() {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        return dmg;
    }

    @Override
    public String GUID() {
        return "summon_lightning_strike";
    }
}

