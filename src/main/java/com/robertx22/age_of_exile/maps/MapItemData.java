package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapItemData implements ICommonDataItem<GearRarity> {

    public MapItemData() {

    }

    public static int MAX_TIER = 10;

    // todo minify these
    public int lvl = 1;
    public int tier = 0;
    public String rar = IRarity.COMMON_ID;
    //public String mapUUID = UUID.randomUUID().toString();

    public List<MapAffixData> affixes = new ArrayList<MapAffixData>();


    private static MapItemData empty;

    public static MapItemData empty() {
        if (empty == null) {
            empty = new MapItemData();
        }
        return empty;

    }

    public boolean isEmpty() {
        return this.equals(empty());
    }


    public float getBonusLootMulti() {
        return bonusFormula();

    }

    public float bonusFormula() {
        return (1 + getAffixMulti());
    }

    public float getBonusExpMulti() {

        return (bonusFormula());
    }


    public boolean canIncreaseTier() {
        return tier < MAX_TIER;
    }

    public boolean increaseTier(int i) {

        int tier = this.tier + i;

        if (!canIncreaseTier()) {
            return false;
        }
        this.tier = tier;
        return true;
    }

    private float getAffixMulti() {

        float total = 0;
        for (MapAffixData affix : affixes) {
            total += affix.getBonusLootMultiplier();
        }
        return total;
    }

    public List<MapAffixData> getAllAffixesThatAffect(AffectedEntities aff) {
        return affixes.stream().filter(x -> x.getAffix().affected == aff).collect(Collectors.toList());
    }


    public List<Component> getTooltip() {
        List<Component> tooltip = new ArrayList<>();

        GearRarity rarity = getRarity();

        tooltip.add(TooltipUtils.level(this.lvl));
        TooltipUtils.addEmpty(tooltip);


        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Mobs);
        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Players);
        addAffixTypeToTooltip(this, tooltip, AffectedEntities.All);

        TooltipUtils.addEmpty(tooltip);


        TooltipUtils.addEmpty(tooltip);

        MutableComponent comp = TooltipUtils.rarityShort(rarity)
                .append(ChatFormatting.GRAY + ", ")
                .withStyle(ChatFormatting.GREEN);

        boolean addedExp = false;
        if (getBonusExpAmountInPercent() > 0) {
            comp.append(Words.Exp.locName()
                    .append(
                            ": +" + this.getBonusExpAmountInPercent() + "%"));
            addedExp = true;
        }

        if (getBonusLootAmountInPercent() > 0) {
            if (addedExp) {
                comp.append(ChatFormatting.GRAY + ", ");
            }

            comp.append(Words.Loot.locName()
                    .append(ChatFormatting.YELLOW +
                            ": +" + this.getBonusLootAmountInPercent() + "%"));
        }
        comp.append(ChatFormatting.GRAY + ", ")
                .append(Words.Tier.locName().withStyle(ChatFormatting.GOLD)
                        .append(": " + this.tier));

        tooltip.add(comp);

        tooltip.add(ExileText.emptyLine().get());


        TooltipUtils.removeDoubleBlankLines(tooltip, 20);

        return tooltip;

    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {
        if (ctx.data != null) {
            ctx.tooltip.addAll(getTooltip());
        }
    }

    private int getBonusLootAmountInPercent() {
        return (int) ((this.getBonusLootMulti() - 1) * 100);
    }

    private int getBonusExpAmountInPercent() {
        return (int) ((this.getBonusExpMulti() - 1) * 100);
    }


    private static void addAffixTypeToTooltip(MapItemData data, List<Component> tooltip,
                                              AffectedEntities affected) {

        List<MapAffixData> affixes = new ArrayList<>(data.getAllAffixesThatAffect(affected));

        if (affixes.size() == 0) {
            return;
        }

        MutableComponent str = ExileText.ofText("").get();

        if (affected.equals(AffectedEntities.Players)) {
            str.append(Words.Player_Affixes.locName());
        } else if (affected.equals(AffectedEntities.Mobs)) {
            str.append(Words.Mob_Affixes.locName());
        } else {
            str.append(Words.Affixes_Affecting_All.locName());
        }

        tooltip.add(str.withStyle(ChatFormatting.AQUA));

        for (MapAffixData affix : affixes) {

            for (ExactStatData statmod : affix.getAffix().getStats(affix.p, data.getLevel())) {

                TooltipInfo info = new TooltipInfo();
                tooltip.addAll(statmod.GetTooltipString(info));
            }

        }
    }


    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        try {
            for (MapAffixData affix : this.getAllAffixesThatAffect(AffectedEntities.of(en))) {
                stats.addAll(affix.getAffix().getStats(affix.p, getLevel()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.MOB_AFFIX, stats));
    }

    @Override
    public String getRarityId() {
        return null;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }


    public int getTier() {
        return this.tier;
    }


    public int getLevel() {
        return this.lvl;
    }


    @Override
    public ItemstackDataSaver<? extends ICommonDataItem> getStackSaver() {
        return StackSaving.MAP;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        StackSaving.MAP.saveTo(stack, this);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        return Arrays.asList();
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return null; // todo
    }
}