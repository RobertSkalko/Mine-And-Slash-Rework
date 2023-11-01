package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.BaseGem;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class GemItem extends BaseGemItem implements IGUID, IAutoModel, IAutoLocName, IItemAsCurrency, IWeighted {

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }


    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item." + locNameLangFileGUID(), this.gemRank.locName, this.gemType.locName).withStyle(gemType.format);
    }

    @Override
    public int Weight() {
        return this.weight;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".gem_item_name";
    }

    @Override
    public String locNameForLangFile() {
        return "%1$s" + " " + "%2$s";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player p, InteractionHand hand) {


        ItemStack stack = p.getItemInHand(hand);

        if (!world.isClientSide) {


            if (!getGem().hasHigherTierGem()) {
                p.displayClientMessage(Component.literal(ChatFormatting.RED + "These gems are already maximum rank."), false);
                return InteractionResultHolder.success(stack);
            }
            if (stack.getCount() < 3) {
                p.displayClientMessage(Component.literal(ChatFormatting.RED + "You need 3 gems to attempt upgrade."), false);
                return InteractionResultHolder.success(stack);
            }

            Gem gem = getGem();

            if (stack.getCount() > 2) {
                if (gem.hasHigherTierGem()) {
                    boolean success = RandomUtils.roll(gem.perc_upgrade_chance);

                    ItemStack newstack = new ItemStack(gem.getHigherTierGem().getItem());

                    if (!newstack.isEmpty()) {
                        Item old = stack.getItem();


                        if (success) {
                            p.displayClientMessage(Component.literal(ChatFormatting.GREEN + "").append(new ItemStack(old).getHoverName())
                                    .append(" has been upgraded to ")
                                    .append(newstack.getHoverName()), false);

                            p.spawnAtLocation(newstack.copy());

                            SoundUtils.playSound(p, SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);

                            //Packets.sendToClient(p, new TotemAnimationPacket(new ItemStack(newstack.getItem())));

                        } else {
                            SoundUtils.playSound(p, SoundEvents.VILLAGER_NO, 1, 1);

                            p.displayClientMessage(Component.literal(ChatFormatting.RED + "").append(new ItemStack(old).getHoverName())
                                    .append(" has failed the upgrade and was destroyed."), false);
                        }

                        stack.shrink(3);

                    }
                }
            }

        }
        return InteractionResultHolder.success(stack);
    }


    static float MIN_WEP_DMG = 2;
    static float MAX_WEP_DMG = 15;
    static float MIN_RES = 5;
    static float MAX_RES = 15;
    static float MIN_ELE_DMG = 2;
    static float MAX_ELE_DMG = 10;

    @Override
    public BaseGem getBaseRuneGem() {
        return getGem();
    }

    @Override
    public float getStatValueMulti() {
        return this.gemRank.statmulti;
    }

    @Override
    public List<StatMod> getStatModsForSerialization(SlotFamily family) {
        return gemType.stats.getFor(family);
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
                                return Words.None;
                            }

                            @Override
                            public OutcomeType getOutcomeType() {
                                return OutcomeType.GOOD;
                            }

                            @Override
                            public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                                GemItem gitem = (GemItem) ctx.Currency.getItem();
                                Gem gem = gitem.getGem();

                                SocketData socket = new SocketData();
                                socket.g = gem.identifier;

                                gear.sockets.getSocketed().add(socket);

                                ctx.player.displayClientMessage(Component.literal("Gem Socketed"), false);


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

            @Override
            public ExplainedResult canBeModified(GearItemData data) {
                if (data.getEmptySockets() > 0) {
                    return ExplainedResult.success();
                }
                return ExplainedResult.failure(Chats.NEED_EMPTY_SOCKET.locName());
            }


            @Override
            public String locDescForLangFile() {
                return "Sockets the gem";
            }

            @Override
            public String locNameForLangFile() {
                return locNameForLangFile();
            }

            @Override
            public String GUID() {
                return GUID();
            }

            @Override
            public int Weight() {
                return weight;
            }
        };
    }


    public static class EleGem extends GemStatPerTypes {
        public Elements ele;

        public EleGem(Elements ele) {
            this.ele = ele;
        }

        @Override
        public List<StatMod> onArmor() {
            return Arrays.asList(new StatMod(MIN_RES, MAX_RES, new ElementalResist(ele), ModType.FLAT));
        }

        @Override
        public List<StatMod> onJewelry() {
            return Arrays.asList(new StatMod(MIN_ELE_DMG, MAX_ELE_DMG, Stats.ELEMENTAL_SPELL_DAMAGE.get(ele)));
        }

        @Override
        public List<StatMod> onWeapons() {
            return Arrays.asList(new StatMod(MIN_WEP_DMG, MAX_WEP_DMG, Stats.ELEMENTAL_DAMAGE.get(ele), ModType.FLAT));
        }
    }

    public enum GemType implements IAutoLocName {

        TOURMALINE("tourmaline", "Tourmaline", ChatFormatting.LIGHT_PURPLE, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.STR));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(2, 15, HealthRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(1, 5, Stats.LIFESTEAL.get()));
            }
        }),
        AZURITE("azurite", "Azurite", ChatFormatting.AQUA, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.INT));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(2, 15, ManaRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit))));
            }
        }),

        GARNET("garnet", "Garnet", ChatFormatting.GREEN, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.DEX));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(2, 15, EnergyRegen.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(2, 8, Stats.CRIT_CHANCE.get()));
            }
        }),
        OPAL("opal", "Opal", ChatFormatting.GOLD, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.STR));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(1, 5, Stats.CRIT_CHANCE.get()));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(3, 12, Stats.CRIT_DAMAGE.get()));
            }
        }),
        TOPAZ("topaz", "Topaz", ChatFormatting.YELLOW, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(MIN_RES, MAX_RES, new ElementalResist(Elements.Lightning)));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(2, 15, Energy.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit))));
            }
        }),
        AMETHYST("amethyst", "Amethyst", ChatFormatting.DARK_PURPLE, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.INT));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(1, 6, SkillDamage.getInstance(), ModType.FLAT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(5, 25, Stats.CRIT_DAMAGE.get()));
            }
        }),
        RUBY("ruby", "Ruby", ChatFormatting.RED, new EleGem(Elements.Fire)),
        EMERALD("emerald", "Emerald", ChatFormatting.GREEN, new EleGem(Elements.Chaos)),
        SAPPHIRE("sapphire", "Sapphire", ChatFormatting.BLUE, new EleGem(Elements.Cold));

        public String locName;
        public String id;
        public ChatFormatting format;
        public GemStatPerTypes stats;

        GemType(String id, String locName, ChatFormatting format, GemStatPerTypes stats) {
            this.locName = locName;
            this.id = id;
            this.format = format;
            this.stats = stats;
        }

        @Override
        public AutoLocGroup locNameGroup() {
            return AutoLocGroup.Misc;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".gem_type." + GUID();
        }

        @Override
        public String locNameForLangFile() {
            return locName;
        }

        @Override
        public String GUID() {
            return id;
        }
    }


    public enum GemRank implements IAutoLocName {
        CRACKED("Cracked", 0, 0.1F, 100, 100999, 0F),
        CHIPPED("Chipped", 1, 0.2F, 75, 25999, 0.1F),
        FLAWED("Flawed", 2, 0.3F, 50, 5000, 0.2F),
        REGULAR("Regular", 3, 0.4F, 25, 1000, 0.5F),
        GRAND("Grand", 4, 0.6F, 10, 200, 0.75F),
        GLORIOUS("Glorious", 5, 0.8F, 5, 25, 0.9F),
        DIVINE("Divine", 6, 1F, 0, 1, 0.95F);

        public String locName;
        public int tier;
        public float statmulti;
        public int upgradeChance;
        public int weight;
        public float lvlToDropmulti;

        GemRank(String locName, int tier, float statmulti, int upgradeChance, int weight, float lvlToDropmulti) {
            this.locName = locName;
            this.weight = weight;
            this.lvlToDropmulti = lvlToDropmulti;
            this.tier = tier;
            this.statmulti = statmulti;
            this.upgradeChance = upgradeChance;
        }

        public static GemRank ofTier(int tier) {

            for (GemRank gr : GemRank.values()) {
                if (gr.tier == tier) {
                    return gr;
                }
            }

            return GemRank.CHIPPED;

        }

        @Override
        public AutoLocGroup locNameGroup() {
            return AutoLocGroup.Misc;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".gem_rank." + GUID();
        }

        @Override
        public String locNameForLangFile() {
            return locName;
        }

        @Override
        public String GUID() {
            return this.locName.toLowerCase(Locale.ROOT);
        }
    }

    public GemItem(GemType type, GemRank gemRank) {
        super(new Properties()
                .stacksTo(16));

        this.gemType = type;
        this.gemRank = gemRank;

        this.weight = gemRank.weight;
        this.levelToStartDrop = gemRank.lvlToDropmulti;

    }

    public GemType gemType;
    public GemRank gemRank;
    public float levelToStartDrop;

    @Override
    public String GUID() {
        return "gems/" + gemType.id + "/" + gemRank.tier;
    }

    public Gem getGem() {
        String id = VanillaUTIL.REGISTRY.items().getKey(this).toString();

        Optional<Gem> opt = ExileDB.Gems()
                .getList()
                .stream()
                .filter(x -> id.equals(x.item_id))
                .findFirst();

        return opt.orElse(new Gem());
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        try {

            tooltip.addAll(getBaseTooltip());

            tooltip.add(Component.literal(""));

            if (getGem().hasHigherTierGem()) {
                tooltip.add(Component.literal("Click with 3 gems to attempt upgrade"));
                tooltip.add(Component.literal("Upgrade chance: " + getGem().perc_upgrade_chance + "%"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
