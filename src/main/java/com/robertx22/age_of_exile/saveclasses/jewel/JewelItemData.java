package com.robertx22.age_of_exile.saveclasses.jewel;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JewelItemData implements ICommonDataItem<GearRarity>, IStatCtx {


    public UniqueJewelData uniq = new UniqueJewelData();

    public List<AffixData> affixes = new ArrayList<>();

    public String style = PlayStyle.STR.id;

    public int lvl = 1;

    public String rar = IRarity.COMMON_ID;


    public void generateAffixes() {

        int num = 1 + getRarity().min_affixes / 2;

        affixes.clear();

        for (int i = 0; i < num; i++) {
            Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                return x.getAllTagReq().contains(BaseGearType.SlotTag.any_jewel.getTagId()) || x.getAllTagReq().contains(getStyle().getJewelAffixTag().getTagId());
            }).random();

            var data = new AffixData(Affix.Type.jewel);
            data.randomizeTier(getRarity());
            data.p = data.getMinMax().random();
            data.id = affix.guid;
            affixes.add(data);
        }


    }


    @Override
    public void BuildTooltip(TooltipContext ctx) {

        ctx.tooltip.clear();

        ctx.tooltip.add(Component.literal("").append(ctx.stack.getHoverName()).withStyle(getRarity().textFormatting()));

        TooltipInfo info = new TooltipInfo(ctx.data);

        for (AffixData affix : affixes) {
            for (Component c : affix.GetTooltipString(info, lvl)) {
                ctx.tooltip.add(c);
            }
        }

        ctx.tooltip.add(Component.literal(""));

        ctx.tooltip.add(TooltipUtils.gearRarity(getRarity()));

        if (uniq.isUnique()) {
            if (uniq.isCraftableUnique()) {
                if (uniq.getCraftedTier().canUpgradeMore()) {
                    var up = uniq.getCraftedTier().upgradeStack.get();
                    ctx.tooltip.add(Component.literal("To Upgrade needs: " + up.getCount() + "x ").append(up.getHoverName()));
                    ctx.tooltip.add(Component.literal("[Click the Jewel with the Stone]"));
                }

            }
        }


        ctx.tooltip.add(TooltipUtils.level(lvl));
    }

    public boolean canWear(EntityData data) {
        return data.getLevel() >= lvl;
    }

    @Override
    public int getLevel() {
        return lvl;
    }

    @Override
    public ItemstackDataSaver<JewelItemData> getStackSaver() {
        return StackSaving.JEWEL;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }

    @Override
    public String getRarityId() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }


    public Item getItem() {
        var s = getStyle();

        if (s == PlayStyle.DEX) {
            return SlashItems.DEX_JEWEL.get();
        }
        if (s == PlayStyle.INT) {
            return SlashItems.INT_JEWEL.get();
        }

        return SlashItems.STR_JEWEL.get();

    }

    public PlayStyle getStyle() {
        return PlayStyle.fromID(this.style);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        int amount = 1;
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.get(getRarity().GUID()).get(), amount)); // todo fix this

    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> list = new ArrayList<>();
        for (AffixData affix : this.affixes) {
            list.addAll(affix.GetAllStats(lvl));
        }

        return Arrays.asList(new MiscStatCtx(list));
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.JEWEL;
    }
}
