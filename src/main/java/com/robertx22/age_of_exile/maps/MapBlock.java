package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MapBlock extends BaseEntityBlock {
    public MapBlock() {
        super(BlockBehaviour.Properties.of().strength(2));

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MapBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {


        if (!level.isClientSide) {

            MapItemData data = StackSaving.MAP.loadFrom(p.getItemInHand(pHand));


            if (WorldUtils.isDungeonWorld(level)) {
                Load.playerRPGData(p).map.teleportBack(p);

            } else {

                if (data != null) {
                    Load.worldData(level).map.startNewMap(p, data);
                    SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.mapId = p.getStringUUID();
                    return InteractionResult.SUCCESS;
                }
                if (p.getItemInHand(pHand).is(SlashItems.MAP_SETTER.get())) {
                    MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);
                    be.mapId = p.getStringUUID();
                    return InteractionResult.SUCCESS;
                }

                MapBlockEntity be = (MapBlockEntity) level.getBlockEntity(pPos);

                var map = Load.worldData(level).map.getMapFromPlayerID(be.mapId);

                map.ifPresent(x -> {
                    x.teleportToMap(p);
                });

            }

        }

        return InteractionResult.SUCCESS;
    }
}
