package com.robertx22.age_of_exile.prophecy;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle2d;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ProphecyAltarBlock extends Block {

    public ProphecyAltarBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.LECTERN).noOcclusion());
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        Circle2d c = new Circle2d(pPos.above(), 0.3F);
        c.doXTimes(15, x -> {
            c.spawnParticle(pLevel, c.getRandomPos(), ParticleTypes.WITCH);
        });
    }


    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {

        if (!level.isClientSide) {

            if (!EntityFinder.start(p, Mob.class, p.blockPosition()).radius(8).searchFor(AllyOrEnemy.enemies).build().isEmpty()) {
                p.sendSystemMessage(Chats.ENEMY_TOO_CLOSE.locName());
                return InteractionResult.FAIL;
            }
            
            if (Load.player(p).prophecy.numMobAffixesCanAdd > 0) {
                p.sendSystemMessage(Chats.PROPHECY_PLEASE_SPEND.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
                return InteractionResult.SUCCESS;
            }

            Load.player(p).prophecy.numMobAffixesCanAdd++;

            Load.player(p).prophecy.regenerateNewOffers(p);

            Load.player(p).prophecy.regenAffixOffers();

            p.sendSystemMessage(Chats.PROPHECY_ALTAR_MSG.locName().withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));

            SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP);

            level.setBlock(pPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);

            Load.player(p).playerDataSync.setDirty();

            // nothing is stored in this block, instead by clicking the altar, the player gains the options

        } else {

            // need to delay this for 1 tick at least
            //ClientOnly.openProphecy();
        }

        return InteractionResult.SUCCESS;
    }

}
