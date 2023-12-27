package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipStatsAligner;
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

    public static int MAX_TIER = 100;

    public int lvl = 1;
    public int tier = 0;
    public String rar = IRarity.COMMON_ID;

    public List<MapAffixData> affixes = new ArrayList<MapAffixData>();

    public List<String> mechs = new ArrayList<>();


    public List<LeagueMechanic> getLeagueMechanics() {
        return mechs.stream().map(x -> ExileDB.LeagueMechanics().get(x)).collect(Collectors.toList());
    }

    public List<ExactStatData> getTierStats() {
        float mob_stat_multi = (float) (GameBalanceConfig.get().HP_DMG_MOB_BONUS_PER_MAP_TIER * tier);

        List<Stat> to = new ArrayList<>();
        to.add(Health.getInstance());
        to.add(Stats.TOTAL_DAMAGE.get());

        List<ExactStatData> stats = new ArrayList<>();

        for (Stat stat : to) {
            stats.add(ExactStatData.noScaling(mob_stat_multi * 100F, ModType.MORE, stat.GUID()));
        }
        return stats;
    }


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

        float tierBonus = tier * 0.02F;

        return (1 + tierBonus);
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

    // we just need tier bonus i think
    /*
    private float getAffixMulti() {

        float total = 0;
        for (MapAffixData affix : affixes) {
            total += affix.getBonusLootMultiplier();
        }
        return total;
    }

     */

    public List<MapAffixData> getAllAffixesThatAffect(AffectedEntities aff) {
        return affixes.stream().filter(x -> x.getAffix().affected == aff).collect(Collectors.toList());
    }


    public List<Component> getTooltip() {
        List<Component> tooltip = new ArrayList<>();

        GearRarity rarity = getRarity();

        tooltip.add(TooltipUtils.level(this.lvl));
        TooltipUtils.addEmpty(tooltip);


        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Mobs);
        this.getTierStats().forEach(x -> tooltip.addAll(x.GetTooltipString(new TooltipInfo())));

        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Players);
        addAffixTypeToTooltip(this, tooltip, AffectedEntities.All);

        TooltipUtils.addEmpty(tooltip);


        TooltipUtils.addEmpty(tooltip);

        MutableComponent comp = TooltipUtils.rarityShort(rarity)
                .append(ChatFormatting.GRAY + ", ")
                .withStyle(ChatFormatting.GREEN);

        boolean addedExp = false;
        if (getBonusExpAmountInPercent() > 0) {
            comp.append(Itemtips.Exp.locName(this.getBonusExpAmountInPercent()));
            addedExp = true;
        }

        if (getBonusLootAmountInPercent() > 0) {
            if (addedExp) {
                comp.append(ChatFormatting.GRAY + ", ");
            }

            comp.append(Itemtips.Loot.locName(this.getBonusLootAmountInPercent()).withStyle(ChatFormatting.YELLOW));
        }
        comp.append(ChatFormatting.GRAY + ", ")
                .append(Itemtips.TIER_TIP.locName().withStyle(ChatFormatting.GOLD)
                        .append(this.tier + ""));

        tooltip.add(comp);

        tooltip.add(ExileText.emptyLine().get());


        for (LeagueMechanic league : this.getLeagueMechanics()) {
            tooltip.add(Itemtips.MAP_LEAGUE_SPAWN.locName().append(league.GUID()));
        }

        TooltipUtils.removeDoubleBlankLines(tooltip);

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


    private static void addAffixTypeToTooltip(MapItemData data, List<Component> tooltip, AffectedEntities affected) {

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

        List<Component> preList = new ArrayList<>();

        for (MapAffixData affix : affixes) {

            for (ExactStatData statmod : affix.getAffix().getStats(affix.p, data.getLevel())) {

                TooltipInfo info = new TooltipInfo();
                preList.addAll(statmod.GetTooltipString(info));
            }

        }
        List<Component> finalList = new TooltipStatsAligner(preList).buildNewTooltipsStats();
        tooltip.addAll(finalList);

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