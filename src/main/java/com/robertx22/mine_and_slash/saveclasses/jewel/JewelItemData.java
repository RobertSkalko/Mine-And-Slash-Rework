package com.robertx22.mine_and_slash.saveclasses.jewel;

import com.google.common.collect.ImmutableList;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.*;
import com.robertx22.mine_and_slash.gui.texts.textblocks.affixdatablocks.SimpleItemStatBlock;
import com.robertx22.mine_and_slash.itemstack.CommonTooltips;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JewelItemData implements ICommonDataItem<GearRarity>, IStatCtx {


    public CraftedUniqueJewelData uniq = new CraftedUniqueJewelData();


    public List<AffixData> cor = new ArrayList<>();

    public List<AffixData> affixes = new ArrayList<>();

    public List<StatsWhileUnderAuraData> auraStats = new ArrayList<>();

    public String style = PlayStyle.STR.id;

    public int lvl = 1;

    public String rar = IRarity.COMMON_ID;


    public void corrupt() {
        if (cor.isEmpty()) {
            int num = RandomUtils.roll(10) ? 2 : 1;

            for (int i = 0; i < num; i++) {
                Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                    return x.type == Affix.AffixSlot.jewel_corruption;
                }).random();
                var data = new AffixData(Affix.AffixSlot.jewel_corruption);
                data.randomizeTier(getRarity());
                data.p = data.getMinMax().random();
                data.id = affix.guid;
                cor.add(data);
            }
        }
    }

    public void generateAffixes() {

        int num = 1 + getRarity().min_affixes / 2;

        affixes.clear();

        for (int i = 0; i < num; i++) {
            Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                return x.type == Affix.AffixSlot.jewel && x.getAllTagReq().contains(SlotTags.any_jewel.GUID()) || x.getAllTagReq().contains(getStyle().getJewelAffixTag().GUID());
            }).random();

            var data = new AffixData(Affix.AffixSlot.jewel);
            data.randomizeTier(getRarity());
            data.p = data.getMinMax().random();
            data.id = affix.guid;
            affixes.add(data);
        }


    }


    @Override
    public void BuildTooltip(TooltipContext ctx) {


        ExileStack ex = ExileStack.of(ctx.stack);

        ctx.tooltip.clear();


        StatRangeInfo info = new StatRangeInfo(ModRange.of(this.getRarity().stat_percents));

        var tip = new ExileTooltips();

        tip.accept(new NameBlock(Collections.singletonList(ctx.stack.getHoverName())));
        tip.accept(new RarityBlock(getRarity()));


        if (this.auraStats.isEmpty()) {
            tip.accept(new SimpleItemStatBlock(info)
                    .acceptIf(Itemtips.JEWEL_STATS.locName().withStyle(ChatFormatting.BLUE),
                            affixes.stream().flatMap(x -> x.getAllStatsWithCtx(lvl, this.getRarity()).stream()).toList(),
                            this.auraStats.isEmpty())
            );
        } else {
            tip.accept(new StatBlock() {
                @Override
                public List<? extends Component> getAvailableComponents() {
                    List<MutableComponent> list = new ArrayList<>();
                    for (StatsWhileUnderAuraData aura : auraStats) {
                        list.addAll(aura.getTooltip());
                        list.add(Component.empty());
                    }
                    return list;
                }
            });
        }
        if (!this.cor.isEmpty()) {
            tip.accept(new StatBlock() {
                @Override
                public List<? extends Component> getAvailableComponents() {
                    List<MutableComponent> list = new ArrayList<>();
                    list.add(Itemtips.COR_STATS.locName().withStyle(ChatFormatting.RED));
                    for (TooltipStatWithContext c : cor.stream().flatMap(x -> x.getAllStatsWithCtx(lvl, getRarity()).stream()).toList()) {
                        list.addAll(c.GetTooltipString());
                    }
                    return list;
                }
            });
        }
        tip.accept(new RequirementBlock(this.lvl))
                .accept(new SalvageBlock(this))
                .accept(new AdditionalBlock(() -> {
                    var up = uniq.getCraftedTier().upgradeStack.get();
                    return ImmutableList.of(
                            Itemtips.JEWEL_UPGRADE_1.locName(up.getCount(), up.getHoverName()).withStyle(ChatFormatting.AQUA),
                            Itemtips.JEWEL_UPGRADE_2.locName(up.getCount(), up.getHoverName()).withStyle(ChatFormatting.AQUA)
                    );

                }).showWhen(() -> this.auraStats.isEmpty() && uniq.isUnique() && uniq.isCraftableUnique() && uniq.getCraftedTier().canUpgradeMore()))

                .accept(new OperationTipBlock().setShift().setAlt());

        tip.accept(CommonTooltips.potentialCorruptionAndQuality(ex));

        tip.accept(new ClickToOpenGuiBlock());

        ctx.tooltip.addAll(tip.release());
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
        if (!auraStats.isEmpty()) {
            return SlashItems.WATCHER_EYE_JEWEL.get();
        }
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
    public List<ItemStack> getSalvageResult(ExileStack stack) {
        int amount = 1;

        if (RarityItems.RARITY_STONE.containsKey(getRarity().GUID())) {
            return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.get(getRarity().GUID()).get(), amount));
        }
        return Arrays.asList(new ItemStack(RandomUtils.randomFromList(RarityItems.RARITY_STONE.values().stream().toList()).get(), RandomUtils.RandomRange(1, 5)));
    }


    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> list = new ArrayList<>();
        for (AffixData affix : this.affixes) {
            list.addAll(affix.GetAllStats(lvl));
        }
        for (AffixData affix : this.cor) {
            list.addAll(affix.GetAllStats(lvl));
        }

        List<StatContext> ctx = new ArrayList<>();

        if (en instanceof Player p) {
            var data = Load.player(p);

            for (StatsWhileUnderAuraData aura : auraStats) {
                if (data.aurasOn.contains(aura.getAura().id)) {
                    ctx.addAll(aura.getStatAndContext(en));
                }
            }
        }

        ctx.add(new SimpleStatCtx(StatContext.StatCtxType.JEWEL, list));

        return ctx;
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.JEWEL;
    }
}
