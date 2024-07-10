package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.database.data.profession.ICreativeTabTiered;
import com.robertx22.age_of_exile.database.data.profession.LeveledItem;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.LeveledItemBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.NameBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.RarityBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.RequirementBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.GearEnchantData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CraftedEnchantItem extends AutoItem implements IItemAsCurrency, ICreativeTabTiered, IRarity {

    public SlotFamily fam;
    String rar;

    public CraftedEnchantItem(SlotFamily fam, String rar) {
        super(new Properties());
        this.rar = rar;
        this.fam = fam;

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> l, TooltipFlag pIsAdvanced) {
        var tier = LeveledItem.getTier(pStack);
        l.clear();
        l.addAll(new ExileTooltips()
                .accept(new NameBlock(Collections.singletonList(pStack.getHoverName())))
                .accept(new RarityBlock(getRarity()))
                .accept(new UsageBlock(() -> {
                    List<GearOutcome> outcomes = ((GearCurrency) this.currencyEffect(pStack)).getOutcomes();
                    return outcomes.stream()
                            .map(x -> x.getTooltip(outcomes.stream().mapToInt(IWeighted::Weight).sum()).withStyle(ChatFormatting.GRAY))
                            .toList();

                }))
                .accept(new RequirementBlock(Collections.singletonList(Itemtips.ENCHANTMENT_GEAR_LEVEL_RANGE.locName(tier.levelRange.getMinLevel(), tier.levelRange.getMaxLevel()))))
                .accept(new LeveledItemBlock(pStack))
                .release());
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(rar) + " " + fam.name() + " Enchantment";
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public Currency currencyEffect(ItemStack stack) {
        return new GearCurrency() {
            @Override
            public List<GearOutcome> getOutcomes() {
                return Arrays.asList(
                        new GearOutcome() {
                            @Override
                            public Words getName() {
                                return Words.UpgradeEnchant;
                            }

                            @Override
                            public OutcomeType getOutcomeType() {
                                return OutcomeType.GOOD;
                            }

                            @Override
                            public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {

                                GearEnchantData en = new GearEnchantData();

                                // todo test this
                                Affix affix = ExileDB.Affixes().getFilterWrapped(x -> {
                                    return x.type == Affix.Type.enchant && x.requirements.satisfiesAllRequirements(new GearRequestedFor(gear)) && x.getAllTagReq().contains(SlotTags.enchantment.GUID());
                                }).random();

                                en.en = affix.GUID();
                                en.rar = rar;

                                gear.ench = en;

                                int num = gear.data.get(GearItemData.KEYS.ENCHANT_TIMES) + 1;
                                gear.data.set(GearItemData.KEYS.ENCHANT_TIMES, num);

                                StackSaving.GEARS.saveTo(stack, gear);

                                return stack;
                            }

                            @Override
                            public int Weight() {
                                return 1000;
                            }

                        },
                        new GearOutcome() {
                            @Override
                            public Words getName() {
                                return Words.Nothing;
                            }

                            @Override
                            public OutcomeType getOutcomeType() {
                                return OutcomeType.BAD;
                            }

                            @Override
                            public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                                int num = gear.data.get(GearItemData.KEYS.ENCHANT_TIMES) + 1;
                                gear.data.set(GearItemData.KEYS.ENCHANT_TIMES, num);
                                StackSaving.GEARS.saveTo(stack, gear);

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

            // todo turn this into a result with chat messages why it doesnt work
            @Override
            public ExplainedResult canBeModified(GearItemData data) {

                SkillItemTier tier = LeveledItem.getTier(stack);

                if (!tier.levelRange.isLevelInRange(data.lvl)) {
                    return ExplainedResult.failure(Chats.NOT_CORRECT_TIER_LEVEL.locName());
                }

                if (data.GetBaseGearType().family() != fam) {
                    return ExplainedResult.failure(Chats.NOT_FAMILY.locName());
                }

                if (data.ench == null) {
                    if (!IRarity.COMMON_ID.equals(rar)) {
                        return ExplainedResult.failure(Chats.ENCHANT_UPGRADE_RARITY.locName());
                    }
                } else {
                    if (!data.ench.rar.equals(rar) && !data.ench.canUpgradeToRarity(rar)) {
                        return ExplainedResult.failure(Chats.ENCHANT_UPGRADE_RARITY.locName());
                    }
                }


                if (data.data.get(GearItemData.KEYS.ENCHANT_TIMES) > 9) {
                    return ExplainedResult.failure(Chats.THIS_ITEM_CANT_BE_USED_MORE_THAN_X_TIMES.locName(10));
                }

                return ExplainedResult.success();
            }

            @Override
            public String locDescForLangFile() {
                return "Tries to enchant the item, adding stats. Item can only attempt enchants 10 times.";
            }

            @Override
            public String locNameForLangFile() {
                return "";
            }

            @Override
            public String GUID() {
                return "";
            }

            @Override
            public int Weight() {
                return 0;
            }
        };
    }

    @Override
    public Item getThis() {
        return this;
    }

    @Override
    public String getRarityId() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }
}
