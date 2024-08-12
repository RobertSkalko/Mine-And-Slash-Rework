package com.robertx22.mine_and_slash.database.data.profession;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.database.data.profession.stat.ProfExp;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProfessionRecipe implements JsonExileRegistry<ProfessionRecipe>, IAutoGson<ProfessionRecipe> {
    public static ProfessionRecipe SERIALIZER = new ProfessionRecipe();

    public String id = "";
    public String profession = "";

    public boolean set_tier_nbt = true;

    private List<CraftingMaterial> mats = new ArrayList<>();
    public String result = "";
    private int result_num = 1;
    private int exp = 100;

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
  

    public SkillItemTier getTier() {
        return SkillItemTier.of(tier);
    }


    public int getExpReward(Player player, int skilLvl, List<ItemStack> mats) {
        int expLvl = MathHelper.clamp(skilLvl, getTier().levelRange.getMinLevel(), getTier().levelRange.getMaxLevel());

        int req = getTier().levelRange.getMinLevel();

        RecipeDifficulty diff = RecipeDifficulty.get(skilLvl, req);

        float expbonusmulti = Load.Unit(player).getUnit().getCalculatedStat(new ProfExp(this.profession)).getMultiplier();


        return (int) (expLvl * diff.xpMulti * this.exp * expbonusmulti);
    }

    public ItemStack toResultStackForJei() {
        ItemStack stack = new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(result)), result_num);

        if (set_tier_nbt) {
            LeveledItem.setTier(stack, tier);
        }

        return stack;
    }

    public enum RecipeDifficulty {
        EASY(ChatFormatting.WHITE, 30, Words.Easy, 0.25F, 25),
        MEDIUM(ChatFormatting.GREEN, 20, Words.Medium, 0.5F, 10),
        HARD(ChatFormatting.YELLOW, 10, Words.Hard, 0.75F, 5),
        VERY_HARD(ChatFormatting.RED, 0, Words.VERY_HARD, 1, 0);

        public ChatFormatting color;
        public Words word;
        public float xpMulti;
        public int doubleDropChance;
        public int masteryLvls;

        RecipeDifficulty(ChatFormatting color, int mastery, Words word, float xpMulti, int doubleDropChance) {
            this.color = color;
            this.masteryLvls = mastery;
            this.word = word;
            this.xpMulti = xpMulti;
            this.doubleDropChance = doubleDropChance;
        }

        public static RecipeDifficulty get(int skilllvl, int recipelvl) {
            if (recipelvl > skilllvl) {
                return VERY_HARD;
            } else {
                int diff = Math.abs(skilllvl - recipelvl);
                return Arrays.stream(RecipeDifficulty.values()).filter(x -> x.masteryLvls >= diff).max(Comparator.comparing(x -> -x.doubleDropChance)).orElse(VERY_HARD);
            }
        }
    }

    public static class CraftingMaterial {

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
                    break; // don't spend too many
                }
            }
        }
    }

    public List<CraftingMaterial> getMissingMaterials(List<ItemStack> stacks) {
        List<CraftingMaterial> list = new ArrayList<>();
        for (CraftingMaterial mat : this.mats) {
            boolean has = false;
            for (ItemStack stack : stacks) {
                if (mat.matches(stack)) {
                    has = true;
                }
            }
            if (!has) {
                list.add(mat);
            }
        }
        return list;
    }


    public boolean isMadeWithPrimaryMats(Item tier, Item rar) {
        ItemStack v1 = new ItemStack(tier, 64);
        ItemStack v2 = new ItemStack(rar, 64);
        return this.mats.stream().anyMatch(x -> x.matches(v1)) && mats.stream().anyMatch(x -> x.matches(v2));
    }

    public boolean canCraft(List<ItemStack> stacks) {
        return mats.stream().allMatch(x -> stacks.stream().anyMatch(e -> x.matches(e)));
    }

    public List<ItemStack> craft(Player p, List<ItemStack> stacks) {
        List<ItemStack> list = new ArrayList<>();

        ItemStack stack = new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(result)), result_num);
        if (set_tier_nbt) {
            LeveledItem.setTier(stack, tier);
        }
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
        public ProfessionRecipe recipe;


        public Data(SkillItemTier tier, ProfessionRecipe recipe) {
            this.tier = tier;
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

        public TierBuilder onTierOrAbove(SkillItemTier tier, Item item, int num) {
            this.actions.add(data -> {
                if (data.tier.tier >= tier.tier) {
                    var id = VanillaUTIL.REGISTRY.items().getKey(item);
                    data.recipe.mats.add(CraftingMaterial.item(id.toString(), num));
                }
            });
            return this;
        }

        public TierBuilder custom(Consumer<Data> con) {
            this.actions.add(con);
            return this;
        }


        public void buildEachTier() {
            for (SkillItemTier tier : SkillItemTier.values()) {
                ProfessionRecipe r = new ProfessionRecipe();
                Data data = new Data(tier, r);
                for (Consumer<Data> action : this.actions) {
                    action.accept(data);
                }
                r.addToSerializables();
            }
        }
    }


}
