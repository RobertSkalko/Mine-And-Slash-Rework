package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.rarities.MapRarityRewardData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueTeleportBlock;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class BossTpItem extends AutoItem {

    public BossTpItem() {
        super(new Properties());

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {


            if (WorldUtils.isMapWorldClass(pLevel)) {
                if (!EntityFinder.start(p, Mob.class, p.blockPosition()).radius(4).searchFor(AllyOrEnemy.enemies).build().isEmpty()) {
                    p.sendSystemMessage(Chats.ENEMY_TOO_CLOSE.locName());
                    return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
                }
                if (!canTeleportToArena(p)) {
                    p.sendSystemMessage(Chats.CANT_TP_TO_BOSS.locName());

                    return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
                }

                itemstack.shrink(1);

                LeagueTeleportBlock.teleportToLeague(p, p.blockPosition(), LeagueMechanics.MAP_BOSS_ID);

                return InteractionResultHolder.success(p.getItemInHand(pUsedHand));
            }
        }
        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));
    }

    public static boolean canTeleportToArena(Player p) {
        var map = Load.mapAt(p.level(), p.blockPosition());

        if (map == null) {
            return false;
        }
        int perc = MapRarityRewardData.getMapCompletePercent(map);

        if (perc > 50) {
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.addAll(splitLongText(Itemtips.BOSS_TELEPORT_ITEM.locName().withStyle(ChatFormatting.RED)));
    }


    @Override
    public String locNameForLangFile() {
        return "Boss Arena Teleport Scroll";
    }


    @Override
    public String GUID() {
        return "arena_teleport";
    }
}
