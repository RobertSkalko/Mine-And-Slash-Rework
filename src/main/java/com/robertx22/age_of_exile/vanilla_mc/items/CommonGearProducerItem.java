package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;


public class CommonGearProducerItem extends AutoItem {

    public CommonGearProducerItem() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {

        if (world != null && !world.isClientSide) {

            ItemStack stack = user.getItemInHand(hand);

            stack.shrink(1);

            StatSoulData soul = new StatSoulData();
            soul.setCanBeOnAnySlot();
            soul.rar = ExileDB.GearRarities()
                    .getFilterWrapped(x -> x.item_tier == 0)
                    .random()
                    .GUID();

            soul.tier = LevelUtils.levelToTier(Load.Unit(user)
                    .getLevel());
            soul.can_sal = false;

            ItemStack soulstack = soul.toStack();

            PlayerUtils.giveItem(soulstack, user);
        }

        return InteractionResultHolder.success(user.getItemInHand(hand));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Itemtips.SOUL_STONE_TIP.locName());
    }

    @Override
    public String locNameForLangFile() {
        return "Common Gear Soul Stone";
    }

    @Override
    public String GUID() {
        return "";
    }
}
