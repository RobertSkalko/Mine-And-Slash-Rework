package com.robertx22.age_of_exile.saveclasses.jewel;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.*;
import com.robertx22.age_of_exile.gui.texts.textblocks.affixdatablocks.SimpleItemStatBlock;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatCtx;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
                    return x.type == Affix.Type.jewel_corruption;
                }).random();
                var data = new AffixData(Affix.Type.jewel_corruption);
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
                return x.getAllTagReq().contains(SlotTags.any_jewel.GUID()) || x.getAllTagReq().contains(getStyle().getJewelAffixTag().GUID());
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
        TooltipInfo info = new TooltipInfo(ctx.data);

        ctx.tooltip.addAll(new ExileTooltips()
                .accept(new NameBlock(Collections.singletonList(ctx.stack.getHoverName())))
                .accept(new RarityBlock(getRarity()))
                .accept(new SimpleItemStatBlock(info)

                        .acceptIf(Itemtips.JEWEL_STATS.locName().withStyle(ChatFormatting.BLUE),
                                affixes.stream().flatMap(x -> x.getAllStatsWithCtx(lvl, info).stream()).toList(),
                                this.auraStats.isEmpty())

                        .acceptIf(Itemtips.UNIQUE_STATS.locName().withStyle(ChatFormatting.YELLOW),
                                this.auraStats.stream().flatMap(x -> x.getStatAndContext(ClientOnly.getPlayer()).stream().flatMap(statContext -> statContext.stats.stream())).toList(),
                                !this.auraStats.isEmpty())

                        .accept(Itemtips.COR_STATS.locName().withStyle(ChatFormatting.RED), cor.stream().flatMap(x -> x.getAllStatsWithCtx(lvl, info).stream()).toList())
                )
                .accept(new RequirementBlock(this.lvl))
                .accept(new OperationTipBlock().setShift().setAlt())
                .release());



/*        if (!cor.isEmpty()) {
            ctx.tooltip.add(Itemtips.COR_STATS.locName().withStyle(ChatFormatting.RED));
            for (AffixData affix : cor) {
                ctx.tooltip.addAll(affix.GetTooltipString(info, lvl));
            }
        }

        ctx.tooltip.add(Component.empty());

        if (this.auraStats.isEmpty()) {
            ctx.tooltip.add(Itemtips.JEWEL_STATS.locName().withStyle(ChatFormatting.GREEN));

            List<Component> preList = new ArrayList<>();
            for (AffixData affix : affixes) {
                preList.addAll(affix.GetTooltipString(info, lvl));
            }
            List<Component> finalList = new TooltipStatsAligner(preList).buildNewTooltipsStats();
            ctx.tooltip.addAll(finalList);
            ctx.tooltip.add(Component.literal(""));
//todo is this still working?
            if (uniq.isUnique()) {
                if (uniq.isCraftableUnique()) {
                    if (uniq.getCraftedTier().canUpgradeMore()) {
                        var up = uniq.getCraftedTier().upgradeStack.get();
                        ctx.tooltip.add(Component.literal("To Upgrade needs: " + up.getCount() + "x ").append(up.getHoverName()));
                        ctx.tooltip.add(Component.literal("[Click the Jewel with the Stone]"));
                    }

                }
            }
        } else {
            ctx.tooltip.add(Itemtips.UNIQUE_STATS.locName().withStyle(ChatFormatting.YELLOW));

            for (StatsWhileUnderAuraData aura : this.auraStats) {
                ctx.tooltip.addAll(aura.getTooltip());
                ctx.tooltip.add(Component.empty());
            }
        }*/

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
