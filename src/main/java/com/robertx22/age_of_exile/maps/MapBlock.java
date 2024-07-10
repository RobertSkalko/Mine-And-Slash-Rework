package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.database.data.profession.ProfessionBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle2d;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MapBlock extends BaseEntityBlock {
    public MapBlock() {
        super(BlockBehaviour.Properties.of().strength(2).noOcclusion());

    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {

        try {
            if (pLevel.isClientSide) {

                var particle = ParticleTypes.WITCH;

                Circle2d c = new Circle2d(pPos, 1.5F);

                net.minecraft.core.particles.SimpleParticleType finalParticle = particle;
                c.doXTimes(5, x -> {
                    c.spawnParticle(pLevel, c.getRandomEdgePos(), finalParticle);
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRemove(BlockState pState, Level level, BlockPos p, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(p);
            if (blockentity instanceof ProfessionBlockEntity be) {
                if (level instanceof ServerLevel) {
                    ItemEntity en = new ItemEntity(level, p.getX(), p.getY(), p.getZ(), asItem().getDefaultInstance());
                    level.addFreshEntity(en);
                }
            }
        }
        super.onRemove(pState, level, p, pNewState, pIsMoving);
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

            MapItemData data = StackSaving.MAP.loadFrom(p.getItemInHand(pHand));


            if (WorldUtils.isDungeonWorld(level)) {
                Load.player(p).map.teleportBack(p);

            } else {

                if (data != null) {
                    if (Load.Unit(p).getLevel() < (data.lvl - 5)) {
                        p.sendSystemMessage(Chats.TOO_LOW_LEVEL.locName().withStyle(ChatFormatting.RED));
                        return InteractionResult.FAIL;
                    }
                    if (!data.getStatReq().meetsReq(data.lvl, Load.Unit(p))) {
                        p.sendSystemMessage(Chats.RESISTS_TOO_LOW_FOR_MAP.locName().withStyle(ChatFormatting.RED));
                        List<Component> reqDifference = data.getStatReq().getReqDifference(data.lvl, Load.Unit(p));
                        if (!reqDifference.isEmpty()) {
                            p.sendSystemMessage(Chats.NOT_MEET_MAP_REQ_FIRST_LINE.locName());
                            reqDifference.forEach(p::sendSystemMessage);
                        }
                        return InteractionResult.FAIL;
                    }

                    Load.worldData(level).map.startNewMap(p, data);
                    SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.setMap(p.getStringUUID());

                    if (!p.isCreative()) {
                        p.getItemInHand(pHand).shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
                if (p.getItemInHand(pHand).is(SlashItems.MAP_SETTER.get())) {
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.setMap(p.getStringUUID());
                    return InteractionResult.SUCCESS;
                }

                MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);

                var map = Load.worldData(level).map.getMapFromPlayerID(be.getMapId());

                if (map.isPresent()) {
                    MapData mapData = map.get();

                    if (mapData.getLives(p) < 1) {
                        p.sendSystemMessage(Chats.NO_MORE_LIVES_REMAINING.locName().withStyle(ChatFormatting.RED));
                        return InteractionResult.FAIL;
                    }
                    MapItemData map1 = mapData.map;
                    if (!map1.getStatReq().meetsReq(map1.lvl, Load.Unit(p))) {
                        p.sendSystemMessage(Chats.RESISTS_TOO_LOW_FOR_MAP.locName().withStyle(ChatFormatting.RED));
                        List<Component> reqDifference = map1.getStatReq().getReqDifference(map1.lvl, Load.Unit(p));
                        if (!reqDifference.isEmpty()) {
                            p.sendSystemMessage(Chats.NOT_MEET_MAP_REQ_FIRST_LINE.locName());
                            reqDifference.forEach(p::sendSystemMessage);
                        }
                        return InteractionResult.FAIL;
                    }
                    if (Load.Unit(p).getLevel() < (map1.lvl - 5)) {
                        p.sendSystemMessage(Chats.TOO_LOW_LEVEL.locName().withStyle(ChatFormatting.RED));
                        return InteractionResult.FAIL;
                    }
                    if (p.getInventory().countItem(SlashItems.TP_BACK.get()) < 1) {
                        p.sendSystemMessage(Chats.NEED_PEARL.locName(SlashItems.TP_BACK.get().getDefaultInstance().getHoverName()));
                        return InteractionResult.SUCCESS;
                    }

                    mapData.teleportToMap(p);

                } else {

                    return InteractionResult.FAIL;
                }

            }

        }

        return InteractionResult.SUCCESS;
    }
}
