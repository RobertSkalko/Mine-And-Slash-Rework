package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonFXHolderAction extends SpellAction {

    public SummonFXHolderAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.PROJECTILE_COUNT, MapField.PROJECTILE_SPEED, MapField.LIFESPAN_TICKS));
    }


    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.world.isClientSide) {
            return;
        }

        Optional<EntityType<?>> projectile = EntityType.byString(data.get(MapField.PROJECTILE_ENTITY));

        Vec3 pos = ctx.caster.position();
        Double addY = data.getOrDefault(MapField.HEIGHT, 0D);

        Vec3 finalPos = new Vec3(pos.x, pos.y + addY, pos.z);

        Level world = ctx.caster.level();
        AbstractArrow en = (AbstractArrow) projectile.get().create(world);
        en.setPos(finalPos);
        if(data.has(MapField.SKILL_FX)){
            FX fx = FXHelper.getFX(data.getSkillFXResourceLocation());
            new EntityEffect(fx, ctx.world, en).start();
        }

        //en.shoot(pos.x, pos.y, pos.z, 0F, 1F);
        //SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);

    }



    public MapHolder createFXHolder(Double lifespan, String txt){
        MapHolder c = createBase(1D, 0D, lifespan, false);
        c.put(MapField.SKILL_FX, txt);
        c.put(MapField.PROJECTILE_ENTITY, EntityType.getKey(SlashEntities.SIMPLE_PROJECTILE.get()).toString());
        return c;
    }

    private MapHolder createBase(Double projCount, Double speed, Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.PROJECTILE_COUNT, projCount);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.PROJECTILE_SPEED, speed);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.ITEM, VanillaUTIL.REGISTRY.items().getKey(Items.AIR)
                .toString());
        c.put(MapField.GRAVITY, gravity);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "fx_holder";
    }

}
