package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.Weighted;
import com.robertx22.age_of_exile.database.data.profession.stat.DoubleDropChance;
import com.robertx22.age_of_exile.database.data.profession.stat.ProfCategoryDropStat;
import com.robertx22.age_of_exile.database.data.profession.stat.TripleDropChance;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.LootChestBlueprint;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Profession implements JsonExileRegistry<Profession>, IAutoGson<Profession>, IAutoLocName {
    public static Profession SERIALIZER = new Profession();

    public String id = "";

    // blocks/entities that give exp

    public String tool_tag = "";

    public float misc_chance = 5;

    public ExpSources exp_sources = new ExpSources();

    // todo add separate for tiered ones, these will be for all tiers

    public HashMap<SkillItemTier, List<ChancedDrop>> tiered_drops = new HashMap<>();

    public List<ChancedDrop> chance_drops = new ArrayList<>();


    public ItemStack randomFishingLootCrate(int lvl) {
        LootChestBlueprint b = new LootChestBlueprint(LootInfo.ofLevel(lvl));
        b.useRarityKey = true;
        return b.createStack();
    }


    public List<ItemStack> getAllDrops(Player p, int lvl, int recipelvl, float dropChanceMulti) {

        var data = StackSaving.TOOL.loadFrom(p.getMainHandItem());
        if (data != null) {
            if (data.force_lvl > -1) {
                lvl = data.force_lvl;
            }
        }

        List<ItemStack> list = new ArrayList<>();

        ProfessionRecipe.RecipeDifficulty diff = ProfessionRecipe.RecipeDifficulty.get(lvl, recipelvl);

        float lvlmulti = LevelUtils.getMaxLevelMultiplier(lvl);

        SkillItemTier tier = SkillItemTier.fromLevel(lvl);

        List<ChancedDrop> ALLDROPS = new ArrayList<>();

        if (tiered_drops.containsKey(tier)) {
            ALLDROPS.addAll(tiered_drops.get(tier));
        }
        ALLDROPS.addAll(chance_drops);


        for (ChancedDrop chancedDrop : ALLDROPS) {

            //float dailyMulti = Load.player(p).professions.daily_drop_multis.getMulti(this, chancedDrop.type);
            float statMuti = Load.Unit(p).getUnit().getCalculatedStat(new ProfCategoryDropStat(chancedDrop.type, GUID())).getMultiplier();

            float chance = dropChanceMulti * chancedDrop.chance * statMuti;

            if (RandomUtils.roll(chance)) {
                Weighted<ProfessionDrop> drop = RandomUtils.weightedRandom(chancedDrop.drops.stream().filter(x -> lvlmulti >= x.min_lvl).map(x -> x.toWeighted(p, this)).collect(Collectors.toList()));
                if (drop != null) {
                    ItemStack stack = drop.obj.get();
                    list.add(stack);
                }
            }
        }


        float doubleDrop = diff.doubleDropChance + Load.Unit(p).getUnit().getCalculatedStat(new DoubleDropChance(GUID())).getValue();
        float tripleDrop = Load.Unit(p).getUnit().getCalculatedStat(new TripleDropChance(GUID())).getValue();

        if (RandomUtils.roll(doubleDrop)) {
            for (ItemStack stack : list) {
                stack.setCount(MathHelper.clamp(stack.getCount() * 2, 1, stack.getMaxStackSize()));
            }
        }
        if (RandomUtils.roll(tripleDrop)) {
            for (ItemStack stack : list) {
                stack.setCount(MathHelper.clamp(stack.getCount() * 3, 1, stack.getMaxStackSize()));
            }
        }


        return list;
    }

    public static enum DropCategory {
        MAIN("core", "Core"),
        LESSER("lesser", "Common"),
        MEDIUM("medium", "Rare"),
        GREATER("greater", "Epic"),
        MISC("misc", "Misc");

        public String id;
        public String locname;

        DropCategory(String id, String locname) {
            this.id = id;
            this.locname = locname;
        }
    }

    public static class ChancedDrop {


        public List<ProfessionDrop> drops = new ArrayList<>();
        public DropCategory type;
        public float chance = 0;

        public ChancedDrop(List<ProfessionDrop> drops, DropCategory type, float chance) {
            this.drops = drops;
            this.type = type;
            this.chance = chance;
        }
    }

    public static class ProfessionDrop {

        public String item_id = "";
        public int num = 1;
        private int weight = 1000;
        public float min_lvl = 0;

        public ProfessionDrop(String item_id, int num, int weight, float min_lvl) {
            this.item_id = item_id;
            this.num = num;
            this.weight = weight;
            this.min_lvl = min_lvl;
        }

        public ItemStack get() {
            return new ItemStack(VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id)), num);
        }

        private int getWeight(Player p, Profession pro) {
            return (int) (weight);
        }

        public Weighted<ProfessionDrop> toWeighted(Player p, Profession pro) {
            return new Weighted<>(this, getWeight(p, pro));
        }
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".profession." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        if (id.contains("_")) {
            var list = Arrays.stream(StringUTIL.split(id, "_")).map(x -> StringUTIL.capitalise(x)).iterator();
            return StringUTIL.join(list, " ");
        }
        return StringUTIL.capitalise(id);
    }

    public enum Type {
        BLOCK, ENTITY, OTHER
    }

    public List<ItemStack> onFish(Player p) {
        int lvl = Load.player(p).professions.getLevel(this.GUID());
        var tier = SkillItemTier.fromLevel(lvl);

        ExpSources.ExpData data = this.exp_sources.exp(250, tier.tier);

        if (data.exp > 0) {
            data.giveExp(p, this);
            float chance = data.getLootChanceMulti(p, this);
            var list = this.getAllDrops(p, Load.player(p).professions.getLevel(this.GUID()), data.getLevelOfMastery(), chance);

            if (RandomUtils.roll(misc_chance)) {
                list.add(this.randomFishingLootCrate(lvl));
            }

            return list;
        }

        return Arrays.asList();
    }

    public List<ItemStack> onBreedAnimal(Player p, Entity en) {

        ExpSources.ExpData data = this.exp_sources.getData(en.getType());

        if (data == null || data.exp == 0) {
            data = exp_sources.getDefaultExp();
        }

        if (data.exp > 0) {
            data.giveExp(p, this);
            float chance = data.getLootChanceMulti(p, this);
            return this.getAllDrops(p, Load.player(p).professions.getLevel(this.GUID()), data.getLevelOfMastery(), chance);
        }


        return Arrays.asList();
    }


    public List<ItemStack> onMineGetBonusDrops(Player p, List<ItemStack> drops, BlockState state) {
        var data = this.exp_sources.getData(state.getBlock());

        if (data == null) {
            for (TagKey<Block> drop : state.getTags().toList()) {
                if (exp_sources.getData(drop) != null) {
                    data = exp_sources.getData(drop);
                    break;
                }
            }
        }
        if (data == null) {
            for (ItemStack drop : drops) {
                if (exp_sources.getData(drop.getItem()) != null) {
                    data = exp_sources.getData(drop.getItem());
                    break;
                }
            }
        }
        // todo add default drop for modded ores

        if (data != null) {
            if (data.exp > 0) {
                data.giveExp(p, this);

                float chance = data.getLootChanceMulti(p, this);
                return this.getAllDrops(p, Load.player(p).professions.getLevel(this.GUID()), data.getLevelOfMastery(), chance);
            }
        }
        return Arrays.asList();

    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PROFESSION;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<Profession> getClassForSerialization() {
        return Profession.class;
    }
}
