package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CurrencyItem extends Item implements IItemAsCurrency, IAutoLocName, IAutoModel, IAutoLocDesc {

    Currency effect;

    public CurrencyItem(Currency effect) {
        super(new Item.Properties());
        this.effect = effect;
        

    }

    /*
    // todo test
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.level().isClientSide) {
            Load.backpacks(pPlayer).getBackpacks().openBackpack(Backpacks.BackpackType.GEARS, pPlayer);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }
    
     */


    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip,
                                TooltipFlag flagIn) {

        tooltip.add(locDesc().withStyle(ChatFormatting.YELLOW));

        if (effect instanceof GearCurrency g) {
            for (GearOutcome o : g.getOutcomes()) {
                tooltip.add(o.getTooltip());
            }
        }


        TooltipUtils.addEmpty(tooltip);


        tooltip.add(TooltipUtils.dragOntoGearToUse());

        TooltipUtils.addEmpty(tooltip);

    }

    @Override
    public Currency currencyEffect() {
        return effect;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return effect.locNameLangFileGUID();
    }

    @Override
    public String locNameForLangFile() {
        return effect.locNameForLangFile();
    }

    @Override
    public String GUID() {
        return effect.GUID();
    }

    @Override
    public AutoLocGroup locDescGroup() {

        return effect.locDescGroup();
    }

    @Override
    public String locDescLangFileGUID() {
        return effect.locDescLangFileGUID();
    }

    @Override
    public String locDescForLangFile() {
        return effect.locDescForLangFile();
    }
}
