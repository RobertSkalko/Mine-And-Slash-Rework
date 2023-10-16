package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class SummonPetAction extends SpellAction {
    public SummonPetAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (ctx.world.isClientSide) {
            return;
        }


        int amount = data.getOrDefault(MapField.COUNT, 1D).intValue();


        for (int i = 0; i < amount; i++) {

            Optional<EntityType<?>> type = EntityType.byString(data.get(MapField.SUMMONED_PET_ID));

            SummonEntity en = (SummonEntity) type.get().create(ctx.world);

            en.finalizeSpawn((ServerLevel) ctx.world, ctx.world.getCurrentDifficultyAt(ctx.getBlockPos()), MobSpawnType.MOB_SUMMONED, null, null);

            en.tame((Player) ctx.caster);


            var pos = ctx.caster.blockPosition(); // todo

            en.setPos(pos.getX(), pos.getY(), pos.getZ());

            int duration = data.get(MapField.LIFESPAN_TICKS).intValue();

            duration *= ctx.calculatedSpellData.data.getNumber(EventData.DURATION_MULTI, 1).number;


            Load.Unit(en).summonedPetData.setup(ctx.calculatedSpellData.getSpell(), duration);

            String rarid = ctx.calculatedSpellData.getSpell().getSummonRarityPerLevel((Player) ctx.caster);

            GearRarity rar = ExileDB.GearRarities().get(rarid);

            Load.Unit(en).SetMobLevelAtSpawn((Player) ctx.caster);


            Load.Unit(en).setLevel(Load.Unit(ctx.caster).getLevel());

            Load.Unit(en).setRarity(rar.GUID());


            ctx.world.addFreshEntity(en);

            SummonType summonType = en.summonType();

            if (en.countsTowardsMaxSummons()) {
                int maxTotal = (int) ctx.calculatedSpellData.data.getNumber(EventData.BONUS_TOTAL_SUMMONS, 0).number;
                despawnIfExceededMaximumSummons(ctx.caster, maxTotal, summonType);
            }
        }
    }

    private void despawnIfExceededMaximumSummons(LivingEntity caster, int max, SummonType type) {

        int current = 0;

        List<SummonEntity> list = new ArrayList<>();

        for (SummonEntity en : EntityFinder.start(caster, SummonEntity.class, caster.blockPosition()).searchFor(AllyOrEnemy.all).radius(400).build()) {
            // if (en.summonType() == type) {
            if (en.getOwner() == caster) {
                current++;
                list.add(en);
            }
            //}
        }

        list.sort(Comparator.comparingInt(x -> -x.tickCount)); // todo this needs to be from highest to lowest age

        int excess = current - max;

        for (int i = 0; i < excess; i++) {
            list.get(i).discard();
        }
    }

    public MapHolder create(EntityType type, int lifespan, int amount) {
        MapHolder c = new MapHolder();
        c.put(MapField.SUMMONED_PET_ID, EntityType.getKey(type).toString());
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, (double) lifespan);
        c.put(MapField.COUNT, (double) amount);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_pet";
    }
}
