package com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.Currency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.runes.Rune;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.StatBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.DropChanceBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.DropLevelBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.loot.blueprints.bases.RunePart;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.LuckyRandom;
import com.robertx22.mine_and_slash.vanilla_mc.packets.proxies.OpenGuiWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;


public class RuneItem extends Item implements IGUID, IAutoModel, IAutoLocName, IWeighted, IItemAsCurrency {

    public int weight = 1000;

    @Override
    public Currency currencyEffect(ItemStack stack) {
        return new RuneCurrency();
    }


    public class RuneCurrency extends GearCurrency {

        @Override
        public List<GearOutcome> getOutcomes() {
            return Arrays.asList(
                    new GearOutcome() {
                        @Override
                        public Words getName() {
                            return Words.Rune;
                        }

                        @Override
                        public OutcomeType getOutcomeType() {
                            return OutcomeType.GOOD;
                        }

                        @Override
                        public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {

                            //todo actually make this based on gear rarities
                            var rune = new SocketData();
                            boolean add = true;
                            var opt = gear.sockets.getSocketed().stream().filter(x -> x.isRune() && x.getRune().GUID().equals(RuneItem.this.type.id)).findAny();
                            if (opt.isPresent()) {
                                rune = opt.get();
                                add = false;
                            }

                            rune.g = RuneItem.this.type.id;

                            int val = LuckyRandom.randomInt(0, 100, LuckyRandom.LuckyUnlucky.UNLUCKY, 2);

                            if (!rune.getRune().uses_unlucky_ran) {
                                val = new MinMax(0, 100).random();
                            }

                            if (val > rune.p) {
                                rune.p = val;
                            }

                            if (add) {
                                gear.sockets.getSocketed().add(rune);
                            }


                            if (gear.getRarity().can_have_runewords) {
                                var list = ExileDB.RuneWords().getFilterWrapped(x -> x.canApplyOnItem(stack) && x.hasMatchingRunesToCreate(gear)).list;
                                if (!list.isEmpty()) {
                                    var biggest = list.stream().max(Comparator.comparingInt(x -> x.runes.size())).get();

                                    var current = gear.sockets.getRuneWord();

                                    int currentSize = 0;

                                    if (current != null && !current.isEmpty()) {
                                        currentSize = current.runes.size();
                                    }
                                    if (biggest.runes.size() > currentSize) {
                                        gear.sockets.setRuneword(biggest);
                                    }
                                }
                            }

                            gear.saveToStack(stack);
                            return stack;
                        }

                        @Override
                        public int Weight() {
                            return 1000;
                        }
                    }
            );
        }

        @Override
        public int getPotentialLoss() {
            return 0;
        }

        @Override
        public ExplainedResult canBeModified(GearItemData data) {

            if (data.uniqueStats != null && data.isUnique() && !data.uniqueStats.getUnique(data).runable) {
                return ExplainedResult.failure(Chats.CANT_RUNE_THIS_UNIQUE.locName());
            }

            if (!data.rar.equals(IRarity.RUNEWORD_ID)) {
                int runes = (int) data.sockets.getSocketed().stream().filter(x -> x.isRune()).count();
                int gems = (int) data.sockets.getSocketed().stream().filter(x -> x.isGem()).count();
                runes++;
                if (gems > runes) {
                    return ExplainedResult.failure(Chats.CANT_HAVE_MORE_RUNES_THAN_GEMS_IN_NON_RUNED.locName());
                }
            }

            Rune rune = ExileDB.Runes().get(RuneItem.this.type.id);

            if (rune.getFor(data.GetBaseGearType().family()).isEmpty()) {
                return ExplainedResult.failure(Chats.NOT_FAMILY.locName());
            }

            var opt = data.sockets.getSocketed().stream().filter(x -> x.isRune() && x.getRune().GUID().equals(RuneItem.this.type.id)).findAny();
            if (opt.isPresent()) {
                if (opt.get().p >= 100) {
                    return ExplainedResult.failure(Chats.RUNE_IS_ALREADY_MAXED.locName());
                }
            }


            int samerunes = (int) data.sockets.getSocketed().stream().filter(x -> x.isRune() && x.getRune().GUID().equals(RuneItem.this.type.id)).count();
            var can = data.getEmptySockets() > 0 || samerunes == 1;

            if (!can) {
                return ExplainedResult.failure(Chats.NEEDS_EMPTY_OR_RUNE.locName());
            }
            return ExplainedResult.success();
        }

        @Override
        public String locDescForLangFile() {
            return "";
        }

        @Override
        public String locNameForLangFile() {
            return "";
        }

        @Override
        public String GUID() {
            return "rune";
        }

        @Override
        public int Weight() {
            return 0;
        }
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (pLevel.isClientSide) {
            OpenGuiWrapper.openWikiRunewords();
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId()).withStyle(ChatFormatting.GOLD);
    }

    @Override
    public String locNameLangFileGUID() {
        return VanillaUTIL.REGISTRY.items().getKey(this)
                .toString();
    }

    @Override
    public String locNameForLangFile() {
        return type.locName + " Rune";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public RuneType type;

    public RuneItem(RuneType type) {
        super(new Properties().stacksTo(64));
        this.type = type;

        this.weight = type.weight;
    }

    @Override
    public String GUID() {
        return "runes/" + type.id;
    }


    @Override
    public int Weight() {
        return weight;
    }

    public Rune getRune() {
        return ExileDB.Runes()
                .get(type.id);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        try {

            ExileTooltips t = new ExileTooltips();

            t.accept(new StatBlock() {
                @Override
                public List<? extends Component> getAvailableComponents() {
                    return statsTooltip();
                }
            });

            t.accept(WorksOnBlock.usableOn(WorksOnBlock.ItemType.GEAR));
            t.accept(new OperationTipBlock().setAlt().setShift());
            Rune rune = this.getRune();

            if (Screen.hasShiftDown()) {
                t.accept(new UsageBlock(splitLongText(Itemtips.RUNE_ITEM_USAGE.locName().withStyle(ChatFormatting.BLUE))));
                if (rune.Weight() > 0) {
                    var lvl = Load.Unit(ClientOnly.getPlayer()).getLevel();
                    t.accept(new DropLevelBlock(rune.getReqLevelToDrop(), GameBalanceConfig.get().MAX_LEVEL));
                    t.accept(new DropChanceBlock(RunePart.droppableAtLevel(lvl).getDropChance(rune)));
                } else {
                    t.accept(new AdditionalBlock(Itemtips.NOT_A_RANDOM_MNS_DROP_CHECK_MODPACK.locName().withStyle(ChatFormatting.BLUE)));
                }
            }

            tooltip.addAll(t.release());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Component> statsTooltip() {
        List<Component> tooltip = new ArrayList<>();

        int lvl = Load.Unit(ClientOnly.getPlayer()).getLevel();

        Rune gem = this.getRune();


        // TooltipInfo info = new TooltipInfo();

        List<StatMod> wep = gem.getFor(SlotFamily.Weapon);
        if (!wep.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Words.WEAPON.locName().withStyle(ChatFormatting.RED));
            for (StatMod x : wep) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }

        List<StatMod> armor = gem.getFor(SlotFamily.Armor);
        if (!armor.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Words.ARMOR.locName().withStyle(ChatFormatting.BLUE));
            for (StatMod x : armor) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }
        List<StatMod> jewelry = gem.getFor(SlotFamily.Jewelry);
        if (!jewelry.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Words.JEWERLY.locName().withStyle(ChatFormatting.LIGHT_PURPLE));
            for (StatMod x : jewelry) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }
        tooltip.add(Component.literal(""));


        return tooltip;
    }
}
