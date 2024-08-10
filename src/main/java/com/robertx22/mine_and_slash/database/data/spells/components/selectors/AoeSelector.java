package com.robertx22.mine_and_slash.database.data.spells.components.selectors;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField.*;

public class AoeSelector extends BaseTargetSelector {

    public AoeSelector() {
        super(Arrays.asList(RADIUS, SELECTION_TYPE, ENTITY_PREDICATE));
    }

    @Override
    public List<LivingEntity> get(SpellCtx ctx, LivingEntity caster, LivingEntity target, Vec3 pos, MapHolder data) {
        AllyOrEnemy predicate = data.getEntityPredicate();
        Double radius = data.get(RADIUS);

        radius *= ctx.calculatedSpellData.data.getNumber(EventData.AREA_MULTI, 1).number;

        Double chance = data.getOrDefault(SELECTION_CHANCE, 100D);


        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(caster, LivingEntity.class, pos)
                .finder(EntityFinder.SelectionType.RADIUS)
                .searchFor(predicate)
                .height(data.getOrDefault(HEIGHT, radius))
                .radius(radius);

        if (chance < 100) {
            return finder.build()
                    .stream()
                    .filter(x -> canHit(ctx.getPos(), x) && RandomUtils.roll(chance))
                    .collect(Collectors.toList());
        } else {
            var list = finder.build();
            list.removeIf(x -> !canHit(ctx.getPos(), x));
            return list;
        }

    }

    public static boolean canHit(Vec3 pos, Entity en) {


        float perc = Explosion.getSeenPercent(pos, en);

        if (perc >= 0.4) {
            return true;
        }
        // todo this doesnt work all the time..

        int height = 2;

        // we check if there's a ceiling
        if (en.level().clip(new ClipContext(pos, pos.add(0, height, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS) {
            for (int i = 1; i <= height; i++) {
                if (Explosion.getSeenPercent(pos.add(0, i, 0), en) >= 0.4) {
                    return true;
                }
            }
        }
        // check if there's floor
        if (en.level().clip(new ClipContext(pos, pos.add(0, -height, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS) {
            for (int i = -1; i >= -height; i--) {
                if (Explosion.getSeenPercent(pos.add(0, i, 0), en) >= 0.4) {
                    return true;
                }
            }
        }

        return false;
    }

    // i copy pasted this from Explosion.class in case it loses it in future updates, very important to make sure spells cant be cheesed through walls with aoe etc
    /*
    public static float getSeenPercent(Vec3 pExplosionVector, Entity pEntity) {
        AABB aabb = pEntity.getBoundingBox();
        double d0 = 1.0D / ((aabb.maxX - aabb.minX) * 2.0D + 1.0D);
        double d1 = 1.0D / ((aabb.maxY - aabb.minY) * 2.0D + 1.0D);
        double d2 = 1.0D / ((aabb.maxZ - aabb.minZ) * 2.0D + 1.0D);
        double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
        double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
        if (!(d0 < 0.0D) && !(d1 < 0.0D) && !(d2 < 0.0D)) {
            int i = 0;
            int j = 0;

            for(double d5 = 0.0D; d5 <= 1.0D; d5 += d0) {
                for(double d6 = 0.0D; d6 <= 1.0D; d6 += d1) {
                    for(double d7 = 0.0D; d7 <= 1.0D; d7 += d2) {
                        double d8 = Mth.lerp(d5, aabb.minX, aabb.maxX);
                        double d9 = Mth.lerp(d6, aabb.minY, aabb.maxY);
                        double d10 = Mth.lerp(d7, aabb.minZ, aabb.maxZ);
                        Vec3 vec3 = new Vec3(d8 + d3, d9, d10 + d4);
                        if (pEntity.level().clip(new ClipContext(vec3, pExplosionVector, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pEntity)).getType() == HitResult.Type.MISS) {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return (float)i / (float)j;
        } else {
            return 0.0F;
        }
    }

     */
    public MapHolder enemiesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies);
    }

    public MapHolder alliesInRadius(Double radius) {
        return create(radius, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.allies);
    }

    public MapHolder create(Double radius, EntityFinder.SelectionType type, AllyOrEnemy pred) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(RADIUS, radius);
        d.put(SELECTION_TYPE, type.name());
        d.put(ENTITY_PREDICATE, pred.name());
        validate(d);
        return d;
    }

    @Override
    public String GUID() {
        return "aoe";
    }
}
