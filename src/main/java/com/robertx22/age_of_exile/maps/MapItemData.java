package com.robertx22.age_of_exile.maps;


import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.content.ubers.UberBossArena;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipStatsAligner;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.wrappers.ExileText;
import joptsimple.internal.Strings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MapItemData implements ICommonDataItem<GearRarity> {

    public MapItemData() {

    }

    public static int MAX_TIER = 100;

    public int uber_tier = 0;
    public String uber = "";

    public int lvl = 1;
    public int tier = 0;
    public String rar = IRarity.COMMON_ID;

    public List<MapAffixData> affixes = new ArrayList<MapAffixData>();


    public String uuid = UUID.randomUUID().toString();

    public boolean isUber() {
        return ExileDB.UberBoss().isRegistered(uber);
    }

    public UberBossArena getUber() {
        return ExileDB.UberBoss().get(uber);
    }

    public StatRequirement getStatReq() {

        StatRequirement req = new StatRequirement();

        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                var stat = new ElementalResist(ele);

                int number = getRarity().map_resist_req;

                for (MapAffixData affix : this.affixes) {
                    if (affix.getAffix() != null && affix.getAffix().map_resist == ele) {
                        number += affix.getAffix().map_resist_bonus_needed;
                    }
                }

                if (number > 0) {
                    req.base_req.put(stat.GUID(), (float) number);
                }
            }
        }

        return req;
    }

    public List<ExactStatData> getTierStats() {


        List<ExactStatData> stats = new ArrayList<>();

        stats.add(ExactStatData.noScaling((float) (GameBalanceConfig.get().HP_MOB_BONUS_PER_MAP_TIER * tier * 100F), ModType.MORE, Health.getInstance().GUID()));
        stats.add(ExactStatData.noScaling((float) (GameBalanceConfig.get().DMG_MOB_BONUS_PER_MAP_TIER * tier * 100F), ModType.MORE, OffenseStats.TOTAL_DAMAGE.get().GUID()));

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

    public float getExpMulti() {
        return getRarity().map_xp_multi;
    }


    public List<MapAffixData> getAllAffixesThatAffect(AffectedEntities aff) {
        return affixes.stream().filter(x -> x.getAffix() != null && x.getAffix().affected == aff).collect(Collectors.toList());
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

        comp.append(Itemtips.Exp.locName(this.getBonusExpAmountInPercent()));


        if (getBonusLootAmountInPercent() > 0) {
            comp.append(ChatFormatting.GRAY + ", ");


            comp.append(Itemtips.Loot.locName(this.getBonusLootAmountInPercent()).withStyle(ChatFormatting.YELLOW));
        }
        comp.append(ChatFormatting.GRAY + ", ")
                .append(Itemtips.TIER_TIP.locName().withStyle(ChatFormatting.GOLD)
                        .append(this.tier + ""));

        tooltip.add(comp);

        tooltip.add(ExileText.emptyLine().get());


        List<GearRarity> possiblerarities = ExileDB.GearRarities().getFilterWrapped(x -> this.getRarity().item_tier >= ExileDB.GearRarities().get(x.min_map_rarity_to_drop).item_tier).list;

        possiblerarities.sort(Comparator.comparingInt(x -> x.item_tier));
        tooltip.add(Words.POSSIBLE_DROS.locName());
        //  tooltip.add(TextUTIL.mergeList(possiblerarities.stream().map(x -> x.locName().withStyle(x.textFormatting())).collect(Collectors.toList())));

        var list = possiblerarities.stream().map(x -> x.textFormatting() + x.locName().getString()).collect(Collectors.toList());

        tooltip.add(Component.literal(Strings.join(list, ", ")));


        TooltipUtils.addEmpty(tooltip);
        if (!getStatReq().isEmpty()) {
            tooltip.add(Words.Requirements.locName().append(": ").withStyle(ChatFormatting.GREEN));
            TooltipUtils.addRequirements(tooltip, this.lvl, getStatReq(), Load.Unit(ClientOnly.getPlayer()));
        }
        if (this.isUber()) {
            TooltipUtils.addEmpty(tooltip);
            tooltip.add(Words.AreaContains.locName().withStyle(ChatFormatting.RED));
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
        return (int) ((this.getExpMulti() - 1) * 100);
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
        int amount = 1;
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.getOrDefault(getRarity().GUID(), RarityItems.RARITY_STONE.get(IRarity.COMMON_ID)).get(), amount));
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return null; // todo
    }
}