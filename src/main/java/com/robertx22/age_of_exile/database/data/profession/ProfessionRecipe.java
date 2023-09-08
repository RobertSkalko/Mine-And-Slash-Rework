package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.all.ProfessionMatItems;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProfessionRecipe implements JsonExileRegistry<ProfessionRecipe>, IAutoGson<ProfessionRecipe> {
    public static ProfessionRecipe SERIALIZER = new ProfessionRecipe();

    public String id = "";
    public String profession = "";

    private List<CraftingMaterial> mats = new ArrayList<>();
    public String result = "";
    private int result_num = 1;
    private int exp = 100;

    public String power = "";
    public int tier = 0;

    public int getLevelRequirement() {
        return getTier().levelRange.getMinLevel();
    }

    public List<Component> getTooltipJEI() {

        List<Component> list = new ArrayList<>();

        var prof = ExileDB.Professions().get(profession);
        
        list.add(prof.locName().append(" ").append(TooltipUtils.level(getLevelRequirement())));

        return list;
    }

    public CraftedItemPower getPower() {
        return CraftedItemPower.ofId(power);
    }

    public SkillItemTier getTier() {
        return SkillItemTier.of(tier);
    }


    public int getExpReward(int skilLvl, List<ItemStack> mats) {
        int req = getTier().levelRange.getMinLevel();

        RecipeDifficulty diff = RecipeDifficulty.get(skilLvl, req);

        return (int) (req * diff.xpMulti * this.exp);
    }

    public ItemStack toResultStackForJei() {
        ItemStack stack = new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(result)), result_num);

        if (true) { // todo not all items might need tiers
            LeveledItem.setTier(stack, tier);
        }

        return stack;
    }

    public enum RecipeDifficulty {
        EASY(ChatFormatting.WHITE, Words.Easy, 0, 25),
        MEDIUM(ChatFormatting.GREEN, Words.Medium, 0.25F, 10),
        HARD(ChatFormatting.YELLOW, Words.Hard, 0.75F, 5),
        VERY_HARD(ChatFormatting.RED, Words.VERY_HARD, 1, 0);

        public ChatFormatting color;
        public Words word;
        public float xpMulti;
        public int doubleDropChance;

        RecipeDifficulty(ChatFormatting color, Words word, float xpMulti, int doubleDropChance) {
            this.color = color;
            this.word = word;
            this.xpMulti = xpMulti;
            this.doubleDropChance = doubleDropChance;
        }

        public static RecipeDifficulty get(int skilllvl, int recipelvl) {

            int diff = Math.abs(skilllvl - recipelvl);
            float diffMulti = LevelUtils.getMaxLevelMultiplier(diff);

            if (diffMulti < 0.1F) {
                return VERY_HARD;
            }
            if (diffMulti < 0.2F) {
                return HARD;
            }
            if (diffMulti < 0.3F) {
                return MEDIUM;
            }
            if (diffMulti < 0.5F) {
                return EASY;
            }

            return VERY_HARD;
        }
    }

    private static class CraftingMaterial {

        public String id = "";
        public int num = 1;
        public Type type = Type.ITEM;

        public boolean matches(ItemStack stack) {
            if (type == Type.ITEM) {
                return VanillaUTIL.REGISTRY.items().getKey(stack.getItem()).toString().equals(id) && stack.getCount() >= num;
            }
            return false;
        }

        public ItemStack toStackForJei() {
            return new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(id)), num);
        }


        public void spend(ItemStack stack) {
            stack.shrink(num);
        }

        public static CraftingMaterial item(String id, int num) {
            CraftingMaterial c = new CraftingMaterial();
            c.id = id;
            c.num = num;
            return c;
        }

        public enum Type {
            ITEM, TAG, FOOD, MEAT // etc
        }


    }

    public void spendMaterials(List<ItemStack> stacks) {
        for (CraftingMaterial mat : this.mats) {
            for (ItemStack stack : stacks) {
                if (mat.matches(stack)) {
                    mat.spend(stack);
                }
            }
        }
    }

    public boolean canCraft(List<ItemStack> stacks) {
        return mats.stream().allMatch(x -> stacks.stream().anyMatch(e -> x.matches(e)));
    }

    public List<ItemStack> craft(Player p, List<ItemStack> stacks) {
        List<ItemStack> list = new ArrayList<>();

        ItemStack stack = new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(result)), result_num);
        LeveledItem.setTier(stack, tier);
        list.add(stack);

        ProfessionRecipe.RecipeDifficulty diff = ProfessionRecipe.RecipeDifficulty.get(Load.player(p).professions.getLevel(this.profession), getLevelRequirement());


        if (RandomUtils.roll(diff.doubleDropChance)) {
            if (stack.getMaxStackSize() > 1) {
                stack.setCount(stack.getCount() * 2);
            } else {
                list.add(stack.copy());
            }
        }
        return list;
    }


    public List<ItemStack> getMaterials() {
        return this.mats.stream().map(x -> x.toStackForJei()).collect(Collectors.toList());
    }


    // public String craftActions todo if i need different craft actions like determine lvl etc

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RECIPE;
    }

    @Override
    public Class<ProfessionRecipe> getClassForSerialization() {
        return ProfessionRecipe.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }


    public static class Data {

        public SkillItemTier tier;
        public CraftedItemPower power;
        public ProfessionRecipe recipe;


        public Data(SkillItemTier tier, CraftedItemPower power, ProfessionRecipe recipe) {
            this.tier = tier;
            this.power = power;
            this.recipe = recipe;
        }
    }

    public static class TierBuilder {

        List<Consumer<Data>> actions = new ArrayList<>();

        public static TierBuilder of(Function<SkillItemTier, Item> item, String proff, int num) {
            TierBuilder b = new TierBuilder();
            b.actions.add((data) -> {
                var id = VanillaUTIL.REGISTRY.items().getKey(item.apply(data.tier));
                data.recipe.id = id.getPath().replaceAll("/", "_") + data.tier.tier;
                data.recipe.result = id.toString();
                data.recipe.profession = proff;
                data.recipe.result_num = num;
                data.recipe.tier = data.tier.tier;
            });
            return b;
        }

        public TierBuilder exp(int xp) {
            this.actions.add(x -> x.recipe.exp = xp);
            return this;
        }

        public TierBuilder onlyOnTier(Function<SkillItemTier, ItemStack> tier) {
            this.actions.add(data -> {

                ItemStack stack = tier.apply(data.tier);

                if (!stack.isEmpty()) {

                    var id = VanillaUTIL.REGISTRY.items().getKey(stack.getItem());

                    if (stack.getCount() > stack.getMaxStackSize()) {
                        throw new RuntimeException(id.toString() + " has more than max stack size");
                    }

                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), stack.getCount()));


                }
            });
            return this;
        }

        public TierBuilder forRarityPower(String rar, HashMap<CraftedItemPower, RegObj<Item>> map) {
            CraftedItemPower power = CraftedItemPower.ofRarity(rar);
            this.actions.add(data -> {
                if (data.power.perc >= power.perc) {
                    var id = VanillaUTIL.REGISTRY.items().getKey(map.get(power).get());
                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), 1));
                }
            });
            return this;
        }


        public TierBuilder onTierOrAbove(SkillItemTier tier, Item item, int num) {
            this.actions.add(data -> {
                if (data.tier.tier >= tier.tier) {
                    var id = VanillaUTIL.REGISTRY.items().getKey(item);
                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), num));
                }
            });
            return this;
        }


        public void buildEachTier() {
            for (SkillItemTier tier : SkillItemTier.values()) {
                ProfessionRecipe r = new ProfessionRecipe();
                Data data = new Data(tier, CraftedItemPower.GREATER, r);
                r.power = data.power.id;
                for (Consumer<Data> action : this.actions) {
                    action.accept(data);
                }
                r.addToSerializables();
            }
        }
    }

    public static class TierPowerBuilder {
        SkillItemTier lowest;

        List<Consumer<Data>> actions = new ArrayList<>();

        public static TierPowerBuilder of(CraftedItemHolder hold, SkillItemTier lowestTier, String proff, int num) {
            TierPowerBuilder b = new TierPowerBuilder();
            b.lowest = lowestTier;
            b.actions.add((data) -> {
                var id = VanillaUTIL.REGISTRY.items().getKey(hold.get(data.tier, data.power).getItem());
                data.recipe.id = id.getPath().replaceAll("/", "_") + data.tier.tier; // todo test
                data.recipe.result = id.toString();
                data.recipe.profession = proff;
                data.recipe.result_num = num;
                data.recipe.tier = data.tier.tier;
            });
            return b;
        }


        public TierPowerBuilder coreMaterials(String prof) {
            materialItems(ProfessionMatItems.TIERED_MAIN_MATS.get(prof));
            return this;
        }

        private TierPowerBuilder materialItems(HashMap<SkillItemTier, RegObj<Item>>... items) {
            this.actions.add(data -> {
                for (HashMap<SkillItemTier, RegObj<Item>> item : items) {
                    var id = VanillaUTIL.REGISTRY.items().getKey(item.get(data.tier).get());
                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), data.power.matItems));
                }
            });
            return this;
        }

        public TierPowerBuilder lesser(Item item, int num) {
            return material(CraftedItemPower.LESSER, item, num);
        }

        public TierPowerBuilder medium(Item item, int num) {
            return material(CraftedItemPower.MEDIUM, item, num);
        }

        public TierPowerBuilder greater(Item item, int num) {
            return material(CraftedItemPower.GREATER, item, num);
        }

        public TierPowerBuilder forPowers(HashMap<CraftedItemPower, RegObj<Item>> map, int num) {
            for (CraftedItemPower p : CraftedItemPower.values()) {
                material(CraftedItemPower.MEDIUM, map.get(p).get(), num);
            }
            return this;
        }

        private TierPowerBuilder material(CraftedItemPower forPower, Item item, int num) {
            this.actions.add(data -> {
                if (data.power.perc >= forPower.perc) {
                    var id = VanillaUTIL.REGISTRY.items().getKey(item);
                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), num));
                }
            });
            return this;
        }

        public TierPowerBuilder exp(int xp) {
            this.actions.add(x -> x.recipe.exp = xp);
            return this;
        }

        public void buildEachTierAndPower() {
            for (SkillItemTier tier : SkillItemTier.values()) {
                if (tier.tier >= lowest.tier) {
                    for (CraftedItemPower power : CraftedItemPower.values()) {
                        ProfessionRecipe r = new ProfessionRecipe();
                        Data data = new Data(tier, power, r);
                        r.power = power.id;
                        for (Consumer<Data> action : this.actions) {
                            action.accept(data);
                        }
                        r.addToSerializables();
                    }
                }
            }
        }

    }
}
