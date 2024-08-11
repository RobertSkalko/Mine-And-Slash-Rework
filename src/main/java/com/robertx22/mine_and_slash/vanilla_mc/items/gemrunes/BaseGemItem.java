package com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.StatBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.DropChanceBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.DropLevelBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.loot.generators.GemLootGen;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseGemItem extends Item {
    public BaseGemItem(Properties settings) {
        super(settings);
    }

    public int weight;

    public abstract Gem getBaseGem();

    public abstract float getStatValueMulti();

    public abstract List<StatMod> getStatModsForSerialization(SlotFamily family);

    public List<OptScaleExactStat> getStatsForSerialization(SlotFamily family) {

        List<OptScaleExactStat> list = new ArrayList<>();

        float multi = getStatValueMulti();

        this.getStatModsForSerialization(family)
                .forEach(x -> {
                    OptScaleExactStat stat = new OptScaleExactStat(x.max * multi, x.GetStat(), x.getModType());
                    stat.scale_to_lvl = true;
                    list.add(stat);

                });

        return list;
    }

    public List<Component> getBaseTooltip() {

        List<Component> tooltip = new ArrayList<>();

        if (ExileDB.Runes()
                .isEmpty() || ExileDB.Gems()
                .isEmpty() || getBaseGem() == null) {
            return tooltip; // datapacks didnt register yet
        }

        ExileTooltips t = new ExileTooltips();

        Gem gem = getBaseGem();


        t.accept(new StatBlock() {
            @Override
            public List<? extends Component> getAvailableComponents() {
                List<Component> stats = new ArrayList<>();

                StatRangeInfo info = new StatRangeInfo(ModRange.hide());
                stats.add(Component.literal(""));
                List<OptScaleExactStat> wep = gem.getFor(SlotFamily.Weapon);
                stats.add(Words.WEAPON.locName().withStyle(ChatFormatting.RED));
                for (OptScaleExactStat x : wep) {
                    stats.addAll(x.GetTooltipString(info));
                }

                stats.add(Component.literal(""));
                List<OptScaleExactStat> armor = gem.getFor(SlotFamily.Armor);
                stats.add(Words.ARMOR.locName().withStyle(ChatFormatting.BLUE));
                for (OptScaleExactStat x : armor) {
                    stats.addAll(x.GetTooltipString(info));
                }

                stats.add(Component.literal(""));
                List<OptScaleExactStat> jewelry = gem.getFor(SlotFamily.Jewelry);
                stats.add(Words.JEWERLY.locName().withStyle(ChatFormatting.LIGHT_PURPLE));
                for (OptScaleExactStat x : jewelry) {
                    stats.addAll(x.GetTooltipString(info));
                }

                return stats;
            }
        });
        t.accept(new UsageBlock(Arrays.asList(Itemtips.GEM_ITEM_USAGE.locName().withStyle(ChatFormatting.BLUE))));
        t.accept(new DropLevelBlock(gem.getReqLevelToDrop(), GameBalanceConfig.get().MAX_LEVEL));
        t.accept(new OperationTipBlock().setAlt().setShift());

        var lvl = Load.Unit(ClientOnly.getPlayer()).getLevel();
        t.accept(new DropChanceBlock(GemLootGen.droppableAtLevel(lvl).getDropChance(gem)));
        return t.release();
    }

}
