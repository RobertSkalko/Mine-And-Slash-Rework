package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.LuckyRandom;
import com.robertx22.age_of_exile.vanilla_mc.packets.proxies.OpenGuiWrapper;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.ChatFormatting;
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

import static com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils.splitLongText;


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

                            int val = LuckyRandom.randomInt(0, 100, LuckyRandom.LuckyUnlucky.UNLUCKY, 5);

                            if (val > rune.p) {
                                rune.p = val;
                            }

                            if (add) {
                                gear.sockets.getSocketed().add(rune);
                            }

                            var list = ExileDB.RuneWords().getFilterWrapped(x -> x.canApplyOnItem(stack) && x.hasMatchingRunesToCreate(gear)).list;
                            if (!list.isEmpty()) {
                                var biggest = list.stream().max(Comparator.comparingInt(x -> x.runes.size())).get();

                                var current = gear.sockets.getRuneWord();
                                if (current == null || biggest.runes.size() > current.runes.size()) {
                                    gear.sockets.setRuneword(biggest);
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

            Rune rune = ExileDB.Runes().get(RuneItem.this.type.id);

            if (rune.getFor(data.GetBaseGearType().family()).isEmpty()) {
                return ExplainedResult.failure(Chats.NOT_FAMILY.locName());
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

            tooltip.addAll(statsTooltip());

            tooltip.addAll(splitLongText(Itemtips.RUNE_ITEM_USAGE.locName().withStyle(ChatFormatting.RED)));


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
        tooltip.add(Words.PressAltForStatInfo.locName().withStyle(ChatFormatting.BLUE));

        return tooltip;
    }
}
