package com.robertx22.mine_and_slash.database.data.currency.map;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.currency.base.Currency;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;

public class MapRarityIncrease extends Currency implements IShapedRecipe {

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this.getCurrencyItem(), 3)
                .define('X', Items.DIAMOND)
                .define('Y', Items.REDSTONE)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public WorksOnBlock.ItemType usedOn() {
        return WorksOnBlock.ItemType.MAP;
    }

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        MapItemData data = StackSaving.MAP.loadFrom(stack);

        MapBlueprint b = new MapBlueprint(LootInfo.ofLevel(data.lvl));
        b.rarity.set(data.getRarity().getHigherRarity());

        var newdata = b.createData();

        newdata.uber = data.uber;
        newdata.lvl = data.lvl;


        SoundUtils.ding(ctx.player.level(), ctx.player.blockPosition());
        SoundUtils.playSound(ctx.player.level(), ctx.player.blockPosition(), SoundEvents.ANVIL_USE, 1, 1);

        newdata.saveToStack(stack);
        return stack;
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().setLevelReq(ServerContainer.get().MIN_LEVEL_MAP_DROPS.get()).build();
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades Map Rarity. Maps with higher rarities can drop better and higher rarity gear but are also more difficult to beat.";
    }

    @Override
    public String locNameForLangFile() {
        return "Map Rarity Upgrade Orb";
    }

    @Override
    public String GUID() {
        return "map_rarity_upgrade";
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public ExplainedResult canItemBeModified(LocReqContext context) {
        MapItemData data = StackSaving.MAP.loadFrom(context.stack);
        if (data == null) {
            return ExplainedResult.failure(Chats.NOT_MAP.locName());
        }
        var can = canBeModified(data);
        if (!can.can) {
            return can;
        }
        return super.canItemBeModified(context);
    }


    public ExplainedResult canBeModified(MapItemData data) {

        if (data.rar.equals(IRarity.MYTHIC_ID)) {
            return ExplainedResult.failure(Chats.MAX_MAP_RARITY.locName());

        }

        if ((data.getRarity().hasHigherRarity() && data.lvl >= data.getRarity().getHigherRarity().min_lvl) == false) {
            return ExplainedResult.failure(Chats.MAX_MAP_RARITY_FOR_LVL.locName());
        }

        return ExplainedResult.success();
    }


}
