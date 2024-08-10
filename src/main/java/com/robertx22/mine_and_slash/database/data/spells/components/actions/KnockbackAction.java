package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;


public class KnockbackAction extends SpellAction {

    public KnockbackAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        float str = data.getOrDefault(MapField.PUSH_STRENGTH, 1D).floatValue();

        targets.forEach(x -> {
            knockbackTarget(ctx.caster, x, str);
        });

    }

    private void knockbackTarget(Entity caster, LivingEntity entity, float strength) {
        float rot = caster.getYRot();
        if (entity != null) {
            entity.knockback(strength, Mth.sin((float) (rot * Math.PI / 180.0F)), -Mth.cos((float) (rot * (Math.PI / 180.0F))));
        }
    }


    public MapHolder create(Double str) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(MapField.PUSH_STRENGTH, str);
        return d;
    }

    @Override
    public String GUID() {
        return "knockback";
    }
}

