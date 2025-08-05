package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.aoe_data.database.spells.SummonType;
import com.robertx22.mine_and_slash.capability.entity.SummonedPetData;
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

    public static int INFINITE_DURATION = -1;

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

            int duration = getDuration(ctx, data);

            float aggroRadius = ctx.calculatedSpellData.data.getNumber(EventData.AGGRO_RADIUS, 15).number;
            aggroRadius *= ctx.calculatedSpellData.data.getNumber(EventData.AGGRO_RADIUS_MULTI, 1).number;


            boolean counts = data.getOrDefault(MapField.COUNTS_TOWARDS_MAX_SUMMONS, true);
            Load.Unit(en).summonedPetData.setup(ctx.calculatedSpellData.getSpell(), duration, (int) aggroRadius, counts);


            Load.Unit(en).SetMobLevelAtSpawn((Player) ctx.caster);

            Load.Unit(en).setLevel(Load.Unit(ctx.caster).getLevel());

            Load.Unit(en).setRarity(IRarity.SUMMON_ID);


            ctx.world.addFreshEntity(en);
        }

        int totalSummons = (int) ctx.calculatedSpellData.data.getNumber(EventData.BONUS_TOTAL_SUMMONS, 0).number;
        updatePlayerSummons(ctx.caster, totalSummons, ctx.calculatedSpellData.spell_id);
    }

    private static int getDuration(SpellCtx ctx, MapHolder data) {
        int duration = data.get(MapField.LIFESPAN_TICKS).intValue();
        if (duration == INFINITE_DURATION) {
            return duration;
        }

        return (int) (duration * ctx.calculatedSpellData.data.getNumber(EventData.DURATION_MULTI, 1).number);
    }

    public static void updatePlayerSummons(LivingEntity caster, int totalSummons, String currentSummonSpell) {
        List<SummonToRemove> list = new ArrayList<>();
        Map<String, Integer> summonedTypes = new HashMap<>();

        for (SummonEntity en : EntityFinder.start(caster, SummonEntity.class, caster.blockPosition()).searchFor(AllyOrEnemy.all).radius(100).build()) {
            if (en.getOwner() != caster) {
                continue;
            }

            var data = Load.Unit(en).summonedPetData;
            summonedTypes.put(data.spell, summonedTypes.getOrDefault(data.spell, 0) + 1);

            if (!data.counts_towards_max_summons) {
                continue;
            }

            list.add(new SummonToRemove(en, data));
        }

        list.sort(Comparator.comparingInt(x -> -x.summon.tickCount)); // todo this needs to be from highest to lowest age
        int excess = list.size() - totalSummons;

        if (excess > 0) {
            for (int i = 0; i < excess; i++) {
                SummonToRemove summonToRemove = list.get(i);
                summonToRemove.data.discard(summonToRemove.summon);

                String spell = summonToRemove.data.spell;
                summonedTypes.put(spell, summonedTypes.get(spell) - 1);
            }
        }

        if (!(caster instanceof Player player)) {
            return;
        }

        Load.player(player).setSummonedData(summonedTypes);
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

    private static class SummonToRemove {
        public SummonEntity summon;
        public SummonedPetData data;

        public SummonToRemove(SummonEntity summon, SummonedPetData data) {
            this.summon = summon;
            this.data = data;
        }
    }
}
