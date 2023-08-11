package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

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

            en.finalizeSpawn((ServerLevel) ctx.world, ctx.world.getCurrentDifficultyAt(ctx.pos), MobSpawnType.MOB_SUMMONED, null, null);

            en.tame((Player) ctx.caster);


            var pos = ctx.caster.blockPosition(); // todo

            en.setPos(pos.getX(), pos.getY(), pos.getZ());

            int duration = data.get(MapField.LIFESPAN_TICKS).intValue();

            duration *= ctx.calculatedSpellData.data.getNumber(EventData.SUMMON_DURATION_MULTI, 1).number;

            Load.Unit(en).summonedPetData.setup(ctx.calculatedSpellData.getSpell(), duration);


            MobRarity rar = ExileDB.MobRarities().get(IRarity.ELITE_ID); // todo

            Load.Unit(en).SetMobLevelAtSpawn((Player) ctx.caster);


            Load.Unit(en).setLevel(Load.Unit(ctx.caster).getLevel());

            Load.Unit(en).setRarity(rar.GUID());


            ctx.world.addFreshEntity(en);
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
