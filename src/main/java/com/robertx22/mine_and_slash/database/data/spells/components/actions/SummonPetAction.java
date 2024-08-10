package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.aoe_data.database.spells.SummonType;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.map_fields.MapField;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
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

            TamableAnimal en = (TamableAnimal) type.get().create(ctx.world);

            en.finalizeSpawn((ServerLevel) ctx.world, ctx.world.getCurrentDifficultyAt(ctx.getBlockPos()), MobSpawnType.MOB_SUMMONED, null, null);

            en.tame((Player) ctx.caster);

            var pos = ctx.caster.blockPosition(); // todo

            en.setPos(pos.getX(), pos.getY(), pos.getZ());

            int duration = data.get(MapField.LIFESPAN_TICKS).intValue();
            duration *= ctx.calculatedSpellData.data.getNumber(EventData.DURATION_MULTI, 1).number;

            int aggroRadius = data.get(MapField.LIFESPAN_TICKS).intValue();
            aggroRadius *= ctx.calculatedSpellData.data.getNumber(EventData.AGGRO_RADIUS, 1).number;


            Load.Unit(en).summonedPetData.setup(ctx.calculatedSpellData.getSpell(), duration, aggroRadius);


            Load.Unit(en).SetMobLevelAtSpawn((Player) ctx.caster);

            Load.Unit(en).setLevel(Load.Unit(ctx.caster).getLevel());

            Load.Unit(en).setRarity(IRarity.SUMMON_ID);


            ctx.world.addFreshEntity(en);


            boolean counts = data.getOrDefault(MapField.COUNTS_TOWARDS_MAX_SUMMONS, true);
            SummonType summonType = data.getSummonType();

            if (counts) {
                int maxTotal = (int) ctx.calculatedSpellData.data.getNumber(EventData.BONUS_TOTAL_SUMMONS, 0).number;
                despawnIfExceededMaximumSummons(ctx.caster, maxTotal);
            }
        }
    }

    public static void despawnIfExceededMaximumSummons(LivingEntity caster, int max) {


        int current = 0;

        List<SummonEntity> list = new ArrayList<>();

        for (SummonEntity en : EntityFinder.start(caster, SummonEntity.class, caster.blockPosition()).searchFor(AllyOrEnemy.all).radius(100).build()) {
            if (en.getOwner() == caster) {
                current++;
                list.add(en);
            }
        }

        list.sort(Comparator.comparingInt(x -> -x.tickCount)); // todo this needs to be from highest to lowest age

        int excess = current - max;

        if (excess > 0) {
            for (int i = 0; i < excess; i++) {
                list.get(i).discard();
            }
        }
    }

    public MapHolder create(EntityType type, int lifespan, int amount, SummonType st, boolean counts) {
        MapHolder c = new MapHolder();
        c.put(MapField.SUMMON_TYPE, st.name());
        c.put(MapField.SUMMONED_PET_ID, EntityType.getKey(type).toString());
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, (double) lifespan);
        c.put(MapField.COUNT, (double) amount);
        c.put(MapField.COUNTS_TOWARDS_MAX_SUMMONS, counts);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_pet";
    }
}
