package com.robertx22.age_of_exile.maps;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.content.ubers.UberBossArena;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.IgnoreNullList;
import com.robertx22.age_of_exile.gui.texts.textblocks.*;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.robertx22.age_of_exile.gui.texts.ExileTooltips.EMPTY_LINE;

public class MapItemData implements ICommonDataItem<GearRarity> {

    public static int MAX_TIER = 100;
    private static MapItemData empty;
    public int uber_tier = 0;
    public String uber = "";

    public int lvl = 1;
    public int tier = 0;
    public String rar = IRarity.COMMON_ID;

    public List<MapAffixData> affixes = new ArrayList<MapAffixData>();


    public String uuid = UUID.randomUUID().toString();

    public MapItemData() {

    }

    public static MapItemData empty() {
        if (empty == null) {
            empty = new MapItemData();
        }
        return empty;

    }


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
        MapItemData thisMapItemData = this;
        TooltipInfo tooltipInfo = new TooltipInfo();
        var tip = new ExileTooltips()
                .accept(new NameBlock(Collections.singletonList(Component.translatable("item.mmorpg.map"))))
                .accept(new RequirementBlock()
                        .setLevelRequirement(this.lvl)
                        .setStatRequirement(getStatReq()))
                .accept(new RarityBlock(this.getRarity()))
                .accept(new StatBlock() {
                    @Nonnull
                    private final MapItemData mapItemData = thisMapItemData;

                    private final ImmutableMap<AffectedEntities, MutableComponent> map = ImmutableMap.of(
                            AffectedEntities.Mobs, Words.Mob_Affixes.locName(),
                            AffectedEntities.Players, Words.Player_Affixes.locName(),
                            AffectedEntities.All, Words.Affixes_Affecting_All.locName()
                    );

                    @Override
                    public List<? extends Component> getAvailableComponents() {

                        IgnoreNullList<Component> list = new IgnoreNullList<>();
                        TooltipInfo info = new TooltipInfo();

                        Stream.of(AffectedEntities.Mobs, AffectedEntities.Players, AffectedEntities.All).forEachOrdered(x -> getAffectedStatList(list, info, x));

                        list.add(Itemtips.TIER_INFLUENCE.locName().withStyle(ChatFormatting.BLUE));
                        mapItemData.getTierStats().forEach(exactStatData -> list.addAll(exactStatData.GetTooltipString(info)));

                        return list;
                    }

                    private void getAffectedStatList(IgnoreNullList<Component> list, TooltipInfo info, AffectedEntities target) {
                        List<MutableComponent> list1 = Optional.of(target)
                                .map(mapItemData::getAllAffixesThatAffect)
                                .filter(x -> !x.isEmpty())
                                .stream()
                                .flatMap(Collection::stream)
                                .map(x -> x.getAffix().getStats(x.p, mapItemData.getLevel()))
                                .flatMap(x -> x.stream()
                                        .map(y -> y.GetTooltipString(info))
                                        .flatMap(Collection::stream)
                                )
                                .sorted((s1, s2) -> {
                                    //sort long stat
                                    Boolean s1IfLong = s1.getString().contains("\u25C6");
                                    Boolean s2IfLong = s2.getString().contains("\u25C6");
                                    return s1IfLong.compareTo(s2IfLong);
                                })
                                .filter(x -> Objects.nonNull(x) && !x.getString().isBlank())
                                .toList();
                        if (!list1.isEmpty()) {
                            list.add(map.get(target).withStyle(ChatFormatting.BLUE));
                            list.addAll(list1);
                            list.add(EMPTY_LINE);
                        }

                    }
                })
                .accept(new AdditionalBlock(() -> !tooltipInfo.shouldShowDescriptions() ?
                        ImmutableList.of(
                                Itemtips.Exp.locName(this.getBonusExpAmountInPercent()).withStyle(ChatFormatting.GOLD),
                                Itemtips.Loot.locName(this.getBonusLootAmountInPercent()).withStyle(ChatFormatting.GOLD),
                                TooltipUtils.tier(this.tier).withStyle(ChatFormatting.GOLD))
                        :
                        ImmutableList.of(
                                Itemtips.Exp.locName(this.getBonusExpAmountInPercent()).withStyle(ChatFormatting.GOLD),
                                Itemtips.Loot.locName(this.getBonusLootAmountInPercent()).withStyle(ChatFormatting.GOLD),
                                TooltipUtils.tier(this.tier).withStyle(ChatFormatting.GOLD),
                                Component.literal("[" + Itemtips.SOUL_TIER_TIP.locName().getString() + "]").withStyle(ChatFormatting.BLUE)
                        )))
                //handle possibleRarities
                .accept(new AdditionalBlock(() -> {

                    RarityRegistryContainer<GearRarity> gearRarityRarityRegistryContainer = ExileDB.GearRarities();
                    Set<GearRarity> possibleRarities = new HashSet<>(gearRarityRarityRegistryContainer.getFilterWrapped(
                            x -> this.tier >= ExileDB.GearRarities().get(x.min_map_rarity_to_drop).map_tiers.min
                    ).list);


                    List<GearRarity> allRarities = gearRarityRarityRegistryContainer.getList();
                    allRarities.sort(Comparator.comparingInt(x -> x.item_tier));
                    if (!tooltipInfo.shouldShowDescriptions()) {
                        MutableComponent starter = Component.literal("");
                        String block = "\u25A0";
                        allRarities
                                .forEach(x -> {
                                    if (possibleRarities.contains(x)) {
                                        starter.append(Component.literal(block).withStyle(x.textFormatting()));
                                    } else {
                                        starter.append(Component.literal(block).withStyle(ChatFormatting.DARK_GRAY));
                                    }
                                });
                        return Collections.singletonList(starter);
                    } else {
                        List<MutableComponent> list = allRarities
                                .stream().map(x -> {
                                    if (possibleRarities.contains(x)) {
                                        return x.locName().withStyle(x.textFormatting());
                                    } else {
                                        return x.locName().withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC, ChatFormatting.STRIKETHROUGH);
                                    }
                                })
                                .toList();

                        return ImmutableList.of(Words.POSSIBLE_DROPS.locName(),
                                TooltipUtils.joinMutableComps(list.iterator(), Gui.COMMA_SEPARATOR.locName()));
                    }

                }))
                .accept(new SalvageBlock(this));
        if (this.isUber()) {
            tip.accept(new AdditionalBlock(Collections.singletonList(Words.AreaContains.locName().withStyle(ChatFormatting.RED))));
        }
        tip.accept(new OperationTipBlock().setAlt());

        return tip.release();

    }


    @Override
    public void BuildTooltip(TooltipContext ctx) {
        if (ctx.data != null) {
            ctx.tooltip.clear();
            ctx.tooltip.addAll(getTooltip());

        }
    }

    private int getBonusLootAmountInPercent() {
        return (int) ((this.getBonusLootMulti() - 1) * 100);
    }

    private int getBonusExpAmountInPercent() {
        return (int) ((this.getExpMulti() - 1) * 100);
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