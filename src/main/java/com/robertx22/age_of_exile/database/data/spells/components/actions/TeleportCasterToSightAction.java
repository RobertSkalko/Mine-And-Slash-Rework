package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;

public class TeleportCasterToSightAction extends SpellAction {

    public TeleportCasterToSightAction() {
        super(Arrays.asList(MapField.DISTANCE));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.world.isClientSide) {
            return;
        }
        Double distance = data.getOrDefault(MapField.DISTANCE, 10D);

        HitResult ray = ctx.caster.pick(distance, 0.0F, false);
        Vec3 pos = ray.getLocation();

        EntityUtils.setLoc(ctx.caster, new MyPosition(pos).asVector3D(), ctx.caster.getYRot(), ctx.caster.getXRot());

    }

    public MapHolder create(Double distance) {
        MapHolder c = new MapHolder();
        c.put(MapField.DISTANCE, distance);
        c.type = GUID();
        this.validate(c);
        return c;
    }

    @Override
    public String GUID() {
        return "tp_caster_in_dir";
    }

}
