package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.FXEntity;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class SummonFXHolderAction extends SpellAction {

    public SummonFXHolderAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.FX_ENTITY, MapField.LIFESPAN_TICKS));
    }


    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        Boolean FXEnable = ClientConfigs.getConfig().ENABLE_PHOTON_FX.get();

        if (!ctx.world.isClientSide) {
            Optional<EntityType<?>> entity = EntityType.byString(data.get(MapField.FX_ENTITY));

            Vec3 pos = ctx.caster.position();
            Double addY = data.getOrDefault(MapField.HEIGHT, 0D);

            Vec3 finalPos = new Vec3(pos.x, pos.y + addY, pos.z);

            Level world = ctx.caster.level();

            FXEntity en = new FXEntity(world);
            SpellUtils.initSpellEntity(en, ctx.caster, ctx.calculatedSpellData, data);
                en.setPos(finalPos);

            if(FXEnable && data.has(MapField.SKILL_FX)){
                FX fx = FXHelper.getFX(data.getSkillFXResourceLocation());
                new EntityEffect(fx, ctx.world, en).start();
            }

            ctx.world.addFreshEntity(en);

        }

    }



    public MapHolder createFXHolder(Double lifespan, String fxName){
        MapHolder c = createBase(lifespan, false);
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.SKILL_FX, fxName);
        c.put(MapField.FX_ENTITY, EntityType.getKey(SlashEntities.FX_ENTITY.get()).toString());
        return c;
    }

    public MapHolder createFXHolder(Double lifespan, String fxName, String entityName){
        MapHolder c = createBase(lifespan, false);
        c.put(MapField.ENTITY_NAME, entityName);
        c.put(MapField.SKILL_FX, fxName);
        c.put(MapField.FX_ENTITY, EntityType.getKey(SlashEntities.FX_ENTITY.get()).toString());
        return c;
    }

    private MapHolder createBase(Double lifespan, boolean gravity) {
        MapHolder c = new MapHolder();
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.put(MapField.GRAVITY, gravity);
        c.type = GUID();
        return c;
    }


    @Override
    public String GUID() {
        return "fx_holder";
    }

}
