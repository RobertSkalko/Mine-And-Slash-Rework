package com.robertx22.mine_and_slash.uncommon.coins;

import com.robertx22.mine_and_slash.database.data.profession.ICreativeTabTiered;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.LeveledItemBlock;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CoinItem extends AutoItem implements ICreativeTabTiered {
    public Coin coin;

    public CoinItem(Coin coin) {
        super(new Properties().stacksTo(64));
        this.coin = coin;
    }

    @Override
    public String locNameForLangFile() {
        return coin.locname + " Coin";
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        try {
            // todo need info for each coin type
            //  pTooltipComponents.add(coin.validator.isCoinValid(ClientOnly.getPlayer(), pStack).answer);
            pTooltipComponents.addAll(new ExileTooltips().accept(new LeveledItemBlock(pStack)).release());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public Item getThis() {
        return this;
    }
}
