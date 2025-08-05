package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.Collection;

public class ExpireAction extends SpellAction {

    public ExpireAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        Entity entityToDiscard = ctx.getPositionEntity();
        if (entityToDiscard != null) {
            if (!entityToDiscard.isRemoved()) {
                if (!(entityToDiscard instanceof Player)) {
                    if (entityToDiscard instanceof SummonEntity summonEntity) {
                        Load.Unit(summonEntity).summonedPetData.discard((LivingEntity) entityToDiscard);
                    } else {
                        entityToDiscard.discard(); // this can cause infi loops and even calling expire spell multiple times
                    }
                }
            }
        }
    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "expire";
    }

}

