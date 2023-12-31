package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellUtils;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.vanilla_mc.packets.SpellEntityInitPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static com.robertx22.age_of_exile.uncommon.utilityclasses.ServerOnly.getPlayerWithinRange;

public class SummonBlockAction extends SpellAction {

    public SummonBlockAction() {
        super(Arrays.asList(MapField.ENTITY_NAME, MapField.BLOCK));
    }

    static int SEARCH = 10;


    static boolean isSolid(Level level, BlockPos pos) {
        return level.getBlockState(pos).isSolid();
    }


    static float getRandomOffset(MapHolder data, MapField<Double> f) {
        float off = data.getOrDefault(f, 0D).floatValue();
        if (off != 0) {
            int te = (int) (off * 100F);
            float num = RandomUtils.RandomRange(0, te) / 100F;
            return RandomUtils.roll(50) ? num : -num;
        }
        return 0;
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {


        if (!ctx.world.isClientSide) {
            //HitResult ray = ctx.caster.rayTrace(5D, 0.0F, false);
            MyPosition pos = new MyPosition(ctx.getBlockPos());

            float yoff = getRandomOffset(data, MapField.RANDOM_Y_OFFSET);
            float xoff = getRandomOffset(data, MapField.RANDOM_X_OFFSET);
            float zoff = getRandomOffset(data, MapField.RANDOM_Z_OFFSET);


            pos = new MyPosition(pos.x() + xoff,
                    pos.y() + data.getOrDefault(MapField.HEIGHT, 0D).intValue() + yoff,
                    pos.z() + zoff);

            boolean found = true;


            if (data.getOrDefault(MapField.FIND_NEAREST_SURFACE, true)) {

                found = false;

                int times = 0;

                while (!found && pos.y() > 1 && SEARCH > times) {
                    times++;
                    if (!isSolid(ctx.world, pos.asBlockPos()) && isSolid(ctx.world, pos.asBlockPos().below())) {
                        found = true;
                    } else {
                        pos = new MyPosition(pos.x, pos.y - 1, pos.z);
                    }
                }
                if (!found) {
                    pos = new MyPosition(ctx.getBlockPos());
                    times = 0;
                    while (!found && pos.y() < ctx.world.getMaxBuildHeight() && SEARCH > times) {
                        times++;
                        if (!isSolid(ctx.world, pos.asBlockPos()) && isSolid(ctx.world, pos.asBlockPos().below())) {
                            found = true;
                        } else {
                            pos = new MyPosition(pos.x, pos.y + 1, pos.z);
                        }
                    }
                }
            }
            Block block = data.getBlock();
            Objects.requireNonNull(block);

            if (found) {
                StationaryFallingBlockEntity be = new StationaryFallingBlockEntity(ctx.world, pos.asBlockPos(), block.defaultBlockState());
                be.getEntityData().set(StationaryFallingBlockEntity.IS_FALLING, data.getOrDefault(MapField.IS_BLOCK_FALLING, false));
                SpellUtils.initSpellEntity(be, ctx.caster, ctx.calculatedSpellData, data);

                if(data.has(MapField.SKILL_FX)){
                    var finalPosAdd = pos.asBlockPos();
                    getPlayerWithinRange(finalPosAdd.getCenter(), ctx.world, 128.0D)
                            .stream()
                            .filter(OnLogin::readFXConfigValue)
                            .toList()
                            .forEach(serverPlayer ->
                                    Packets.sendToClient(serverPlayer, new SpellEntityInitPacket(be.getUUID(), new Vec3(finalPosAdd.getX(), finalPosAdd.getY(), finalPosAdd.getZ()), data.get(MapField.SKILL_FX))));

                }


                ctx.world.addFreshEntity(be);
            }

        }


    }

    public MapHolder create(Block block, Double lifespan) {
        MapHolder c = new MapHolder();
        c.put(MapField.BLOCK, VanillaUTIL.REGISTRY.blocks().getKey(block)
                .toString());
        c.put(MapField.ENTITY_NAME, Spell.DEFAULT_EN_NAME);
        c.put(MapField.LIFESPAN_TICKS, lifespan);
        c.type = GUID();
        return c;
    }

    @Override
    public String GUID() {
        return "summon_block";
    }
}

