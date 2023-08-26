package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.entities.IDatapackSpellEntity;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;

public class SpellUtils {

    public static void summonLightningStrike(Entity entity) {

        LightningBolt lightningboltentity = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());  //boolean true means it's only an effect!'

        lightningboltentity.setPosRaw((double) entity.getX() + 0.5D,
                (double) entity.getY(),
                (double) entity.getZ() + 0.5D);

        lightningboltentity.setVisualOnly(true);

        SoundUtils.playSound(entity, SoundEvents.LIGHTNING_BOLT_IMPACT, 1, 1);

        addLightningBolt(((ServerLevel) entity.level()), lightningboltentity);

    }

    public static void addLightningBolt(ServerLevel world, LightningBolt entityIn) {
        world.getServer()
                .getPlayerList()
                .broadcast((Player) null, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 50, world.dimension()
                        , new ClientboundAddEntityPacket(entityIn));
    }

    public static void shootProjectile(Vec3 pos, AbstractArrow projectile, Entity caster, float speed,
                                       float pitch, float yaw) {

        ((Entity) projectile).setPos(pos.x, caster.getEyeY() - 0.1F, pos.z);

        projectile.shootFromRotation(caster, pitch, yaw, 0, speed, 1F);

    }

    public static void initSpellEntity(Entity spellEntity, LivingEntity caster, CalculatedSpellData data, MapHolder holder) {

        IDatapackSpellEntity se = (IDatapackSpellEntity) spellEntity;
        se.init(caster, data, holder);
    }

}
