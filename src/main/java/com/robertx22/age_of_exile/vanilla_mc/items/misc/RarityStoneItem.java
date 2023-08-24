package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        //  tooltip.add(Words.CreatedInSalvageStation.locName());

        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Repairs " + getTotalRepair() + " durability."));

        tooltip.add(TooltipUtils.dragOntoGearToUse());

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
