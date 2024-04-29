package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.mmorpg.ModErrors;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UberBossAltarBlock extends Block {

    public UberBossAltarBlock() {
        super(BlockBehaviour.Properties.of().strength(200).noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {


        if (!level.isClientSide) {

            try {
                if (WorldUtils.isDungeonWorld(level)) {
                    var uber = Load.mapAt(level, pPos).map.getUber();

                    var type = uber.getRandomBoss();


                    LivingEntity en = (LivingEntity) type.create(level);
                    en.setPos(new MyPosition(pPos).add(0, 1, 0));
                    //OnMobSpawn.setupNewMob(en, Load.Unit(en), null);
                    //Load.Unit(en).setRarity(IRarity.UBER);

                    level.addFreshEntity(en);
                    Load.Unit(en).setRarity(IRarity.UBER); // todo does this work

                    p.sendSystemMessage(uber.locDesc().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

                    SoundUtils.playSound(p, SoundEvents.WITHER_SPAWN);

                    level.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);

                }
            } catch (Exception e) {
                ModErrors.print(e);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
