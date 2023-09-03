package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ProfessionBlock extends BaseEntityBlock {

    public String profession;

    public ProfessionBlock(String profession) {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
        this.profession = profession;

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide) {

            ProfessionBlockEntity be = (ProfessionBlockEntity) pLevel.getBlockEntity(pPos);

            if (be.owner.isEmpty()) {
                be.owner = pPlayer.getStringUUID();
            } else {
                if (!be.owner.equals(pPlayer.getStringUUID())) {
                    pPlayer.sendSystemMessage(Chats.NOT_OWNER.locName());
                    return InteractionResult.FAIL;
                }
            }

            pPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @org.jetbrains.annotations.Nullable
                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                    return new CraftingStationMenu(pContainerId, pPlayerInventory, be.mats, be.output);
                }
            });

        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ProfessionBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return createTickerHelper(type, SlashBlockEntities.PROFESSION.get(), pLevel.isClientSide ? null : ProfessionBlock::serverTick);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ProfessionBlockEntity be) {
        be.tick(level);
    }

}
