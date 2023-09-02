package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionBlockEntity extends BlockEntity {

    CraftingContainer mats = new CraftingContainer(9);
    CraftingContainer output = new CraftingContainer(1);


    public ProfessionBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlashBlockEntities.PROFESSION.get(), pPos, pBlockState);
    }

    public Profession getProfession() {
        var id = ((ProfessionBlock) getBlockState().getBlock()).profession;
        return ExileDB.Professions().get(id);
    }

    public RecipeType getRecipeType() {
        return ((ProfessionBlock) getBlockState().getBlock()).recipeType.get();
    }

    int ticks = 0;

    public void tick(Level level) {

        ticks++;

        if (ticks % 20 == 0) {

            var recipe = getCurrentRecipe(level);

            if (recipe != null && this.output.isEmpty()) {

                int tier = getAverageTierOfMats();

                ItemStack resultStack = recipe.assemble(mats, level.registryAccess());

                LeveledItem.setTier(resultStack, tier);

                this.output.setItem(0, resultStack.copy());
            }

        }

    }

    public int getAverageTierOfMats() {

        List<Integer> tiers = new ArrayList<>();

        for (int i = 0; i < mats.getContainerSize(); i++) {
            ItemStack mat = mats.getItem(i);
            if (!mat.isEmpty()) {
                tiers.add(LevelUtils.levelToTier(LeveledItem.getLevel(mat)));
            }
        }

        return tiers.stream().mapToInt(x -> x.intValue()).sum() / tiers.size();

    }

    public CraftingRecipe getCurrentRecipe(Level level) {
        Optional<CraftingRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(getRecipeType(), mats, level);
        var rec = optional.get();
        return rec;

    }


    public class CraftingContainer extends SimpleContainer implements net.minecraft.world.inventory.CraftingContainer {

        public CraftingContainer(int pSize) {
            super(pSize);
        }

        public CraftingContainer(ItemStack... pItems) {
            super(pItems);
        }

        @Override
        public int getWidth() {
            return 3;
        }

        @Override
        public int getHeight() {
            return 3;
        }

        @Override
        public List<ItemStack> getItems() {
            var list = new ArrayList<ItemStack>();

            for (int i = 0; i < this.getContainerSize(); i++) {
                list.add(getItem(i));
            }
            return list;
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.mats.fromTag(pTag.getList("mats", 10));
        this.output.fromTag(pTag.getList("result", 10));

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("mats", this.mats.createTag());
        pTag.put("result", this.output.createTag());

    }


}
