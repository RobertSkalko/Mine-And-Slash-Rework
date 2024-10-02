package com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.mine_and_slash.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.currency.IItemAsCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearCurrency;
import com.robertx22.mine_and_slash.database.data.currency.base.GearOutcome;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapelessRecipe;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.GemItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Formatter;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class GemItem extends BaseGemItem implements IGUID, IAutoModel, IItemAsCurrency, IWeighted, IShapelessRecipe {


    @Override
    public Component getName(ItemStack stack) {
        return Formatter.GEM_ITEM_NAME.locName(this.gemRank.locName(), this.gemType.locName()).withStyle(gemType.format);
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {

        if (this.gemRank.lower() == null) {
            return null;
        }
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, GemItems.MAP.get(gemType)
                        .get(gemRank)
                        .get())
                .requires(GemItems.MAP.get(gemType)
                        .get(gemRank.lower())
                        .get(), 3)
                .unlockedBy("player_level", trigger());
    }

    @Override
    public int Weight() {
        return this.weight;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }


    static float MIN_WEP_DMG = 2;
    static float MAX_WEP_DMG = 15;
    static float MIN_RES = 5;
    static float MAX_RES = 15;
    static float MIN_ELE_DMG = 2;
    static float MAX_ELE_DMG = 10;

    @Override
    public Gem getBaseGem() {
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
    public CodeCurrency currencyEffect(ItemStack stack) {
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
                            public void modify(LocReqContext ctx) {

                                ctx.stack.GEAR.edit(gear -> {
                                    GemItem gitem = (GemItem) ctx.Currency.getItem();
                                    Gem gem = gitem.getGem();

                                    SocketData socket = new SocketData();
                                    socket.g = gem.identifier;

                                    gear.sockets.getSocketed().add(socket);

                                    ctx.player.displayClientMessage(Chats.GEM_SOCKETED.locName(), false);

                                });
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
            public ExplainedResult canBeModified(ExileStack stack) {
                var data = stack.GEAR.get();
                
                if (data.getEmptySockets() < 1) {
                    return ExplainedResult.failure(Chats.NEED_EMPTY_SOCKET.locName());
                }
                if (data.rar.equals(IRarity.RUNEWORD_ID)) {
                    int runes = (int) data.sockets.getSocketed().stream().filter(x -> x.isRune()).count();
                    int gems = (int) data.sockets.getSocketed().stream().filter(x -> x.isGem()).count();
                    gems++;
                    if (gems > runes) {
                        return ExplainedResult.failure(Chats.CANT_HAVE_MORE_GEMS_THAN_RUNES_IN_RUNEWORD.locName());
                    }
                }

                return ExplainedResult.success();
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
            return Arrays.asList(new StatMod(MIN_ELE_DMG, MAX_ELE_DMG, OffenseStats.ELEMENTAL_SPELL_DAMAGE.get(ele)));
        }

        @Override
        public List<StatMod> onWeapons() {
            return Arrays.asList(new StatMod(5, 25, OffenseStats.ELEMENTAL_DAMAGE.get(ele), ModType.FLAT));
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
                return Arrays.asList(new StatMod(1, 5, ResourceStats.LIFESTEAL.get()));
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
                return Arrays.asList(new StatMod(1, 3, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit))));
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
                return Arrays.asList(new StatMod(2, 8, OffenseStats.CRIT_CHANCE.get()));
            }
        }),
        OPAL("opal", "Opal", ChatFormatting.GOLD, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.STR));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(1, 4, OffenseStats.CRIT_CHANCE.get()));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(3, 15, OffenseStats.CRIT_DAMAGE.get()));
            }
        }),
        TOPAZ("topaz", "Topaz", ChatFormatting.YELLOW, new GemStatPerTypes() {
            @Override
            public List<StatMod> onArmor() {
                return Arrays.asList(new StatMod(1, 5, DatapackStats.INT));
            }

            @Override
            public List<StatMod> onJewelry() {
                return Arrays.asList(new StatMod(2, 15, Energy.getInstance(), ModType.PERCENT));
            }

            @Override
            public List<StatMod> onWeapons() {
                return Arrays.asList(new StatMod(3, 15, OffenseStats.CRIT_DAMAGE.get()));
            }
        }),

        AMETHYST("amethyst", "Amethyst", ChatFormatting.DARK_PURPLE, new EleGem(Elements.Shadow)),
        RUBY("ruby", "Ruby", ChatFormatting.RED, new EleGem(Elements.Fire)),
        EMERALD("emerald", "Emerald", ChatFormatting.GREEN, new EleGem(Elements.Shadow)),
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
        CRACKED("Cracked", 0, 0.1F, 100, 100999, 0F, IRarity.COMMON_ID),
        CHIPPED("Chipped", 1, 0.2F, 75, 25999, 0.1F, IRarity.COMMON_ID),
        FLAWED("Flawed", 2, 0.3F, 50, 5000, 0.2F, IRarity.UNCOMMON),
        REGULAR("Regular", 3, 0.4F, 25, 1000, 0.5F, IRarity.RARE_ID),
        GRAND("Grand", 4, 0.6F, 10, 200, 0.75F, IRarity.EPIC_ID),
        GLORIOUS("Glorious", 5, 0.8F, 5, 25, 0.9F, IRarity.LEGENDARY_ID),
        DIVINE("Divine", 6, 1F, 0, 1, 0.95F, IRarity.MYTHIC_ID);

        public String rar;
        public String locName;
        public int tier;
        public float statmulti;
        public int upgradeChance;
        public int weight;
        public float lvlToDropmulti;

        GemRank(String locName, int tier, float statmulti, int upgradeChance, int weight, float lvlToDropmulti, String rar) {
            this.locName = locName;
            this.rar = rar;
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

        public GemRank lower() {
            for (GemRank gr : GemRank.values()) {
                if (gr.tier == (tier - 1)) {
                    return gr;
                }
            }
            return null;
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


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
