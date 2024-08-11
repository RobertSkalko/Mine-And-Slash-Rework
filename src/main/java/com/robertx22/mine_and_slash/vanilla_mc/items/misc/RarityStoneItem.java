package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.items.MaterialItem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class RarityStoneItem extends Item implements IWeighted, IAutoModel {

    String name;
    public int rarTier;

    public RarityStoneItem(String name, int rar) {
        super(new Properties().stacksTo(64));
        this.name = name;
        this.rarTier = rar;

    }

    public int getTotalRepair() {
        return 50 + (50 * rarTier);
    }

    public static Item of(String rar) {
        return RarityItems.RARITY_STONE.get(rar).get(); // todo bad
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        var pro = ExileDB.Professions().get(Professions.SALVAGING);
        var tip = MaterialItem.makeTooltip(pro, null);
        tip.accept(new UsageBlock(Arrays.asList(Itemtips.STONE_REPAIRE_DURABILITY.locName(getTotalRepair()).withStyle(ChatFormatting.GREEN))));
        tip.accept(new OperationTipBlock().addDraggableTipAbove(OperationTipBlock.AvailableTarget.GEAR));
        l.addAll(tip.release());
    }


    @Override
    public int Weight() {
        return 100;
    }


    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }
}
