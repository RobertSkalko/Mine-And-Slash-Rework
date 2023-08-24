package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DeathTicketItem extends AutoItem implements IShapedRecipe {

    String rar;

    public DeathTicketItem(String rar) {
        super(new Properties().stacksTo(64));
        this.rar = rar;
    }

    public static GearRarity getRarityNeeded(Player p) {
        return ExileDB.GearRarities().get(Load.playerRPGData(p).map.rar);
    }

    @Override
    public String locNameForLangFile() {
        return getRarity().textFormatting() + StringUTIL.capitalise(rar) + " Death Ticket";
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {

        return shaped(this, 3)
                .define('r', RarityStoneItem.of(rar))
                .define('c', Items.DIAMOND)
                .define('b', Items.PAPER)
                .pattern("rcr")
                .pattern("rbr")
                .pattern("rrr")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Removes off " + getSeconds() + "s of Map Teleportation Penalty."));
    }

    public int getSeconds() {
        return (int) (ExileDB.GearRarities().get(rar).item_tier_power * ServerContainer.get().MAP_DEATH_COOLDOWN.get() / 20); // todo not the best
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }
}
