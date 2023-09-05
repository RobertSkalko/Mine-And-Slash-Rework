package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ProfessionBlockEntity extends BlockEntity {

    static MergedContainer.Inventory INPUTS = new MergedContainer.Inventory("INPUTS", 9, Direction.UP);
    static MergedContainer.Inventory OUTPUTS = new MergedContainer.Inventory("OUTPUTS", 9, Direction.DOWN);

    public MergedContainer inventory = new MergedContainer(Arrays.asList(INPUTS, OUTPUTS));


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

                updateOwner(level);

                if (tryRecipe(true) || trySalvage(true)) {
                    craftingTicks += 20;

                    if (craftingTicks > 60) {
                        craftingTicks = 0;
                        tryRecipe(false);
                        trySalvage(false);

                        SoundUtils.playSound(level, getBlockPos(), SoundEvents.ANVIL_DESTROY);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setChanged(); // todo will this cause any problems to have it perma on?
    }

    public boolean tryRecipe(boolean justCheck) {

        var recipe = getCurrentRecipe(level);

        if (recipe != null && this.inventory.getInventory(OUTPUTS).isEmpty()) {

            if (justCheck) {
                return true;
            }


            this.addExp(recipe.getExpReward(this.ownerLvl, getMats()) * recipe.getTier().levelRange.getMinLevel());

            for (ItemStack stack : recipe.craft(getMats())) {
                inventory.addStack(OUTPUTS, stack);
            }

            recipe.spendMaterials(getMats());
            return true;
        } else {
            return false;
        }


    }

    public boolean trySalvage(boolean justCheck) {

        if (getProfession().GUID().equals(Professions.SALVAGING)) {
            if (this.inventory.getInventory(OUTPUTS).isEmpty()) {
                for (ItemStack stack : this.getMats()) {

                    ICommonDataItem data = ICommonDataItem.load(stack);
                    ISalvagable sal = ISalvagable.load(stack);
                    if (data != null && sal != null) {
                        if (sal.isSalvagable()) {

                            if (justCheck) {
                                return true;
                            }
                            for (ItemStack res : sal.getSalvageResult(stack)) {
                                this.inventory.addStack(OUTPUTS, res);
                            }

                            float multi = data.getRarity().item_value_multi;

                            stack.shrink(1);
                            for (ItemStack randomDrop : getProfession().getAllDrops(ownerLvl, data.getLevel(), multi)) {
                                this.inventory.addStack(OUTPUTS, randomDrop);
                            }

                            return true;
                        }
                    }
                }

            }
        }
        return false;
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
        return inventory.getAllStacks(INPUTS);

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

        this.inventory.fromTag(pTag.getList("inv", 10));

        this.owner = pTag.getString("owner");
        this.ownerLvl = pTag.getInt("owner_lvl");
        this.savedXP = pTag.getInt("saved_exp");

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("inv", this.inventory.createTag());

        pTag.putString("owner", owner);

        pTag.putInt("owner_lvl", ownerLvl);
        pTag.putInt("saved_exp", savedXP);

    }

}

