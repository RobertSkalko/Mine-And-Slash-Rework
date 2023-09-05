package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ProfessionBlockEntity extends BlockEntity implements WorldlyContainer {

    SimpleContainer mats = new SimpleContainer(9);
    SimpleContainer output = new SimpleContainer(9);

    public String owner = "";

    public int ownerLvl = 1;

    public int savedXP = 0;


    public ProfessionBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlashBlockEntities.PROFESSION.get(), pPos, pBlockState);
    }

    public Profession getProfession() {
        var id = ((ProfessionBlock) getBlockState().getBlock()).profession;
        return ExileDB.Professions().get(id);
    }


    int ticks = 0;
    int craftingTicks = 0;

    public Player getOwner(Level l) {
        try {
            var id = UUID.fromString(owner);
            return l.getPlayerByUUID(id);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }

        return null;
    }

    public void tick(Level level) {

        try {
            ticks++;

            if (ticks % 20 == 0) {

                var recipe = getCurrentRecipe(level);

                if (recipe != null && this.output.isEmpty()) {

                    craftingTicks += 20;

                    if (craftingTicks > 60) {
                        craftingTicks = 0;

                        float successchance = recipe.getCraftChance(ownerLvl);

                        if (RandomUtils.roll(successchance)) {
                            this.addExp(recipe.getExpReward(this.ownerLvl, getMats()) * recipe.getTier().levelRange.getMinLevel());

                            int i = 0;
                            for (ItemStack stack : recipe.craft(getMats())) {
                                output.setItem(i, stack.copy());
                            }
                        }
                        recipe.spendMaterials(getMats());
                    }
                }
            }

        } catch (Exception e) {
            // throw new RuntimeException(e);
        }

        this.setChanged(); // todo will this cause any problems to have it perma on?
    }


    public void addExp(int xp) {

        Player p = getOwner(level);

        if (p != null) {
            Load.player(p).professions.addExp(p, getProfession().GUID(), savedXP);
        } else {
            this.savedXP += xp;
        }

    }

    public void updateOwner(Level level) {

        Player p = getOwner(level);

        if (p != null) {
            this.ownerLvl = Load.player(p).professions.getLevel(getProfession().GUID());
        }
    }


    public void onOpen(Player p) {

        if (savedXP > 0) {
            Load.player(p).professions.addExp(p, getProfession().GUID(), savedXP);
        }

    }

    public List<ItemStack> getMats() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < mats.getContainerSize(); i++) {
            list.add(mats.getItem(i));
        }
        return list;

    }

    public ProfessionRecipe getCurrentRecipe(Level level) {

        var mats = getMats();


        if (mats.stream().anyMatch(x -> !x.isEmpty())) {

            String prof = getProfession().id;

            // this could be a bit laggy
            var list = ExileDB.Recipes().getFilterWrapped(x -> {
                return x.profession.equals(prof) && x.canCraft(mats);
            }).list;

            if (list.isEmpty()) {
                return null;
            }
            // higher power versions usually just require more materials, so to make sure it always uses the highest power recipe, we do this
            return list.stream().max(Comparator.comparingInt(x -> x.getPower().perc)).get();
        }
        return null;
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.mats.fromTag(pTag.getList("mats", 10));
        this.output.fromTag(pTag.getList("result", 10));

        this.owner = pTag.getString("owner");
        this.ownerLvl = pTag.getInt("owner_lvl");
        this.savedXP = pTag.getInt("saved_exp");

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("mats", this.mats.createTag());
        pTag.put("result", this.output.createTag());

        pTag.putString("owner", owner);

        pTag.putInt("owner_lvl", ownerLvl);
        pTag.putInt("saved_exp", savedXP);

    }


    @Override
    public int[] getSlotsForFace(Direction pSide) {
        int[] ar = new int[0];

        if (pSide == Direction.UP) {
            ar = new int[output.getContainerSize()];

            for (int i = 0; i < output.getContainerSize(); i++) {
                ar[i] = i;
            }
        }
        if (pSide == Direction.DOWN) {
            ar = new int[output.getContainerSize()];
            int size = output.getContainerSize();

            for (int i = size; i < size + size; i++) {
                ar[i] = i;
            }
        }
        return ar;
    }


    // hopper functionality
    public SimpleContainer getMergedContainer() {
        SimpleContainer me = new SimpleContainer(18);

        int i = 0;

        for (int x = 0; x < mats.getContainerSize(); x++) {
            me.setItem(i, mats.getItem(i));
        }
        for (int x = 0; x < output.getContainerSize(); x++) {
            me.setItem(i, output.getItem(i));
        }
        return me;
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return pDirection == Direction.UP && pIndex < 9 && getMergedContainer().getItem(pIndex).isEmpty();
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return !pStack.isEmpty() && pDirection == pDirection.DOWN;
    }

    @Override
    public int getContainerSize() {
        return getMergedContainer().getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return getMergedContainer().isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return getMergedContainer().getItem(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return getMergedContainer().removeItem(pAmount, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return getMergedContainer().removeItemNoUpdate(pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        getMergedContainer().setItem(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        getMergedContainer().clearContent();
    }
}
