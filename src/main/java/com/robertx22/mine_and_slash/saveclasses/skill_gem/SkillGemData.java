package com.robertx22.mine_and_slash.saveclasses.skill_gem;

import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.wrappers.ExileText;
import com.robertx22.mine_and_slash.database.data.aura.AuraGem;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.support_gem.SupportGem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.*;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.DropLevelBlock;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SkillGemsItems;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillGemData implements ICommonDataItem<GearRarity> {


    @Override
    public void BuildTooltip(TooltipContext ctx) {

    }


    @Override
    public List<ItemStack> getSalvageResult(ExileStack stack) {
        if (!isSalvagable(stack)) {
            return Arrays.asList();
        }
        int amount = 1; // todo
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.getOrDefault(getRarity().GUID(), RarityItems.RARITY_STONE.get(IRarity.COMMON_ID)).get(), amount));
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.SPELL;
    }

    @Override
    public String getSalvageConfigurationId() {
        return id;
    }


    @Override
    public int getLevel() {
        return 1; // wait what
    }

    @Override
    public ItemstackDataSaver<SkillGemData> getStackSaver() {
        return StackSaving.SKILL_GEM;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }


    public String id = "";
    public SkillGemType type = SkillGemType.SKILL;
    public int perc = 0;
    public String rar = IRarity.COMMON_ID;
    private int links = 1;


    public void setLinks(int t) {
        this.links = t;
    }

    public int getFlatLinks() {
        return links;
    }

    public MaxLinks getMaxLinks(Player p) {
        int total = GameBalanceConfig.get().getTotalLinks(links, p);
        boolean cappedBySpelllvl = GameBalanceConfig.get().getTotalLinks(total + 1, p) > total;
        boolean cappedByLevel = !cappedBySpelllvl && total < 5;

        return new MaxLinks(total, cappedByLevel, cappedBySpelllvl);
    }

    public enum SkillGemType {
        SKILL(), SUPPORT(), AURA();
    }


    @Override
    public String getRarityId() {
        return rar;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public int getStatPercent() {
        return perc;
    }

    public Spell getSpell() {
        if (type == SkillGemType.SKILL) {
            return ExileDB.Spells().get(id);
        }
        return null;
    }

    public boolean canPlayerWear(Player p) {

        int req = getGeneric().getRequiredLevel();

        if (Load.Unit(p).getLevel() < req) {
            return false;
        }

        return true;
    }

    public AuraGem getAura() {
        if (type == SkillGemType.AURA) {
            return ExileDB.AuraGems().get(id);
        }
        return null;
    }

    public SupportGem getSupport() {
        if (type == SkillGemType.SUPPORT) {
            return ExileDB.SupportGems().get(id);
        }
        return null;
    }


    public ISkillGem getGeneric() {
        if (getSupport() != null) {
            return getSupport();
        }
        if (getSpell() != null) {
            return getSpell();
        }
        if (getAura() != null) {
            return getAura();
        }
        return null;
    }

    public PlayStyle getStyle() {
        return getGeneric().getStyle();
    }


    private MutableComponent stars(Player p) {

        int slots = GameBalanceConfig.get().getTotalLinks(links, p);

        var text = Gui.AVAILABLE_SUPPORT_SLOTS.locName(slots).withStyle(ChatFormatting.DARK_PURPLE);

        if (links > slots) {
            int next = GameBalanceConfig.get().getNextLinkUpgradeLevel(Load.Unit(p).getLevel());
            text = text.append(Words.CAPPED_BECAUSE_PLAYER_LEVEL.locName(next));
        }
        return text;

    }


    public List<Component> getTooltip(Player p) {

        List<Component> list = new ArrayList<>();

        GearRarity rar = getRarity();

        int req = getGeneric().getRequiredLevel();

        if (this.type == SkillGemType.SKILL) {
            Spell spell = getSpell();

            if (spell == null) {
                return list;
            }
            for (Component c : spell.GetTooltipString(new StatRangeInfo(ModRange.of(rar.stat_percents)))) {
                list.add(c);
            }
            // mahj to-do need to change to current level rather than the "minimum" level
            //list.add(ExileText.emptyLine().get());
            //if (req > 0) { 
            //    list.add(TooltipUtils.level(req));

            //}

            list.add(ExileText.emptyLine().get());

            list.add(stars(p));


            return list;
        }

        ISkillGem generic = getGeneric();


        list.add(generic.locName().withStyle(rar.textFormatting()));
        list.add(ExileText.emptyLine().get());


        ExileTooltips tip = new ExileTooltips();


        if (this.type == SkillGemType.SUPPORT) {
            SupportGem supp = getSupport();
            List<MutableComponent> stats = new ArrayList<>();
            for (ExactStatData ex : supp.GetAllStats(Load.Unit(p), this)) {
                stats.addAll(ex.GetTooltipString());
            }
            tip.accept(new StatBlock() {
                @Override
                public List<? extends Component> getAvailableComponents() {
                    return stats;
                }
            });
            tip.accept(new AdditionalBlock(Itemtips.SUPPORT_GEM_COST.locName((int) (supp.manaMulti * 100)).withStyle(ChatFormatting.RED)));
            if (supp.isOneOfAKind()) {
                tip.accept(new AdditionalBlock(Itemtips.SUPPORT_GEM_ONLY_ONE.locName().append(supp.one_of_a_kind + "")));
            }
            tip.accept(new AdditionalBlock(Itemtips.SUPPORT_GEM_EXPLANATION.locName().withStyle(ChatFormatting.AQUA)));
            tip.accept(new DropLevelBlock(supp.min_lvl, GameBalanceConfig.get().MAX_LEVEL));
            tip.accept(new ClickToOpenGuiBlock());

        }

        if (this.type == SkillGemType.AURA) {
            AuraGem aura = getAura();
            List<MutableComponent> stats = new ArrayList<>();

            for (ExactStatData ex : aura.GetAllStats(Load.Unit(p), this)) {
                stats.addAll(ex.GetTooltipString());
            }
            tip.accept(new StatBlock() {
                @Override
                public List<? extends Component> getAvailableComponents() {
                    return stats;
                }
            });
            tip.accept(new AdditionalBlock(Itemtips.AURA_RESERVATION.locName().append((int) (aura.reservation * 100) + "").withStyle(ChatFormatting.RED)));

            int spiritLeft = (int) Load.player(p).getSkillGemInventory().getRemainingSpirit(p);
            tip.accept(new AdditionalBlock(Itemtips.REMAINING_AURA_CAPACITY.locName().append(spiritLeft + "").withStyle(ChatFormatting.AQUA)));
            tip.accept(new DropLevelBlock(aura.min_lvl, GameBalanceConfig.get().MAX_LEVEL));
            tip.accept(new AdditionalBlock(Itemtips.AUGMENT_EXPLANATION.locName().withStyle(ChatFormatting.AQUA)));

            tip.accept(new ClickToOpenGuiBlock());

        }

        if (req > 0) {
            tip.accept(new RequirementBlock().setLevelRequirement(req));
        }
        tip.accept(new RarityBlock(rar));

        list.addAll(tip.release());

        return list;


    }

    public Item getItem() {
        return SkillGemsItems.get(this);
    }

}
