package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
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
            // todo this is confusing
            if (ctx.getPositionEntity() instanceof Player == false) {
                if (ctx.getPositionEntity() instanceof SimpleProjectileEntity pro) {
                    pro.scheduleRemoval();
                } else if (ctx.getPositionEntity() instanceof StationaryFallingBlockEntity fe) {
                    fe.scheduleRemoval();
                } else {
                    ctx.getPositionEntity().discard(); // this can cause infi loops and even calling expire spell multiple times
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

