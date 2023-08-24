package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.DeathTicketItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle2d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MapBlock extends BaseEntityBlock {
    public MapBlock() {
        super(BlockBehaviour.Properties.of().strength(2).noOcclusion());


    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {

        try {
            if (pLevel.isClientSide) {

                var particle = ParticleTypes.WITCH;

                if (Load.Unit(ClientOnly.getPlayer()).getCooldowns().isOnCooldown(CooldownsData.MAP_TP)) {
                    particle = ParticleTypes.SQUID_INK;
                }

                Circle2d c = new Circle2d(pPos, 1.5F);

                net.minecraft.core.particles.SimpleParticleType finalParticle = particle;
                c.doXTimes(5, x -> {
                    c.spawnParticle(pLevel, c.getRandomEdgePos(), finalParticle);
                });


            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MapBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {

        if (!level.isClientSide) {
            if (Load.Unit(p).getCooldowns().isOnCooldown(CooldownsData.MAP_TP)) {
                GearRarity need = DeathTicketItem.getRarityNeeded(p);

                if (p.getMainHandItem().getItem() instanceof DeathTicketItem ticket) {

                    if (ticket.getRarity().equals(need)) {
                        SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);
                        Load.Unit(p).getCooldowns().tickDownCooldown(CooldownsData.MAP_TP, ticket.getSeconds() * 20);
                        p.getMainHandItem().shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }

                int sec = Load.Unit(p).getCooldowns().getCooldownTicks(CooldownsData.MAP_TP) / 20;
                p.sendSystemMessage(Component.literal("Next teleport allowed in: " + sec + "s"));
                p.sendSystemMessage(Component.literal("Needs: ").append(RarityItems.DEATH_TICKETS.get(need.GUID()).get().getDefaultInstance().getHoverName()));

                return InteractionResult.SUCCESS;
            }

            MapItemData data = StackSaving.MAP.loadFrom(p.getItemInHand(pHand));


            if (WorldUtils.isDungeonWorld(level)) {
                Load.playerRPGData(p).map.teleportBack(p);

            } else {

                if (data != null) {
                    Load.worldData(level).map.startNewMap(p, data);
                    SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.setMap(p.getStringUUID());

                    if (!p.isCreative()) {
                        p.getItemInHand(pHand).shrink(1);
                    }

                    Load.playerRPGData(p).map.clearDeathTicketRarity();

                    return InteractionResult.SUCCESS;
                }
                if (p.getItemInHand(pHand).is(SlashItems.MAP_SETTER.get())) {
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.setMap(p.getStringUUID());
                    return InteractionResult.SUCCESS;
                }

                MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);

                var map = Load.worldData(level).map.getMapFromPlayerID(be.getMapId());

                map.ifPresent(x -> {
                    x.teleportToMap(p);
                });

            }

        }

        return InteractionResult.SUCCESS;
    }
}
