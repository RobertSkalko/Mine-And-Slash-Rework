package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
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
        if (ctx.getPositionEntity() != null) {
            if (!ctx.getPositionEntity().isRemoved()) {

                // SAFETY: Avoid recursively discarding inside an ON_EXPIRE activation, which can
                // trigger entities' remove() -> onExpire -> ExpireAction -> discard() loops.
                // This can hang the tick thread if multiple components cascade removals.
                if (ctx.activation == com.robertx22.mine_and_slash.database.data.spells.components.EntityActivation.ON_EXPIRE) {
                    return;
                }

                // Only discard non-player sources as intended by original logic.
                if (ctx.getPositionEntity() instanceof Player == false) {
                    ctx.getPositionEntity().discard();
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

