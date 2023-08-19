package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class LootChestData implements ICommonDataItem<GearRarity> {

    public int num = 1;
    public String rar = "";
    public int lvl = 1;
    public String id = "";


    public LootChest getLootChest() {
        return ExileDB.LootChests().get(id);
    }

    @Override
    public String getRarityId() {
        return null;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public boolean isLocked() {
        return getLootChest().getKey() != null;
    }

    public Item getKeyItem() {
        return getLootChest().getKey();
    }

    public boolean canOpen(Player p) {

        if (!isLocked()) {
            return true;
        }
        return p.getInventory().countItem(getKeyItem()) > 0;
    }

    public void spendKey(Player p) {
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack stack = p.getInventory().getItem(i);
            if (stack.is(getKeyItem())) {
                stack.shrink(1);
                return;
            }
        }
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

        var tip = ctx.tooltip;

        tip.clear();

        GearRarity rar = getRarity();

        tip.add(Component.literal(rar.textFormatting() + "").withStyle(rar.textFormatting()).append(ctx.stack.getHoverName()).withStyle(rar.textFormatting()));

        tip.add(Component.empty());

        tip.add(Component.literal("Right Click to Open Loot Chest!"));

        if (getLootChest().isLocked()) {
            tip.add(Component.literal("Needs Key: ").append(getLootChest().getKey().getDefaultInstance().getDisplayName()));
        }

        tip.add(Component.empty());

        tip.add(TooltipUtils.level(lvl));
        tip.add(TooltipUtils.rarity(rar));

    }

    @Override
    public ItemstackDataSaver<LootChestData> getStackSaver() {
        return StackSaving.LOOT_CHEST;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        return Arrays.asList();
    }

    @Override
    public boolean isSalvagable() {
        return false;
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.GEAR;
    }
}
