package com.robertx22.age_of_exile.database.data.spells.components.selectors;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;

public class TargetSelector extends BaseTargetSelector {

    public TargetSelector() {
        super(Arrays.asList());
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, Vec3 pos, MapHolder data) {
        return Arrays.asList(target);
    }

    @Override
    public String GUID() {
        return "target";
    }

    public MapHolder create() {
        MapHolder c = new MapHolder();
        c.type = GUID();
        return c;
    }
}

