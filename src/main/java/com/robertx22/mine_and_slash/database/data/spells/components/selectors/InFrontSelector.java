package com.robertx22.mine_and_slash.database.data.spells.components.selectors;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField.*;

public class InFrontSelector extends BaseTargetSelector {

    public InFrontSelector() {
        super(Arrays.asList(DISTANCE, WIDTH, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, Vec3 pos, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        float distance = data.get(DISTANCE)
                .floatValue();
        float width = data.get(WIDTH)
                .floatValue();

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
                .finder(EntityFinder.SelectionType.IN_FRONT)
                .searchFor(predicate)
                .radius(Math.min(width, distance))
                .distance(distance);


        return finder.build().stream().filter(x -> AoeSelector.canHit(ctx.getPos(), x)).collect(Collectors.toList());
    }

    public MapHolder create(Double distance, Double width, AllyOrEnemy pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(DISTANCE, distance);
        d.put(WIDTH, width);
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "in_front";
    }
}
