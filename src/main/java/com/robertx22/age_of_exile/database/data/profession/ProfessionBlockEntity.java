package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.ModErrors;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISalvagable;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
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

    public SimpleContainer show = new SimpleContainer(1);

    public String owner = "";
    public UUID ownerUUID = null;


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

        if (ownerUUID == null) {
            if (!owner.isEmpty()) {
                try {
                    ownerUUID = UUID.fromString(owner);
                } catch (Exception e) {
                    ModErrors.print(e);
                }
            }
        }
        
        if (ownerUUID != null) {
            return l.getPlayerByUUID(ownerUUID);
        }

        return null;
    }

    int cooldown = 0;

    public void tick(Level level) {

        try {
            ticks++;

            int tickRate = 5;

            if (ticks % tickRate == 0) {

                Player p = getOwner(level);


                if (p != null && p.isAlive()) {

                    if (this.inventory.getInventory(INPUTS).isEmpty()) {
                        return;
                    }

                    cooldown -= tickRate;

                    if (getProfession().GUID().equals(Professions.SALVAGING)) {
                        if (trySalvage(p, true).can) {
                            craftingTicks += tickRate;

                            if (craftingTicks > 100) {
                                craftingTicks = 0;
                                trySalvage(p, false);
                                SoundUtils.playSound(level, getBlockPos(), SoundEvents.ANVIL_DESTROY);
                            }
                        } else {
                            show.clearContent();
                        }
                    } else {
                        var rec = tryRecipe(p, true);

                        if (!rec.can) {
                            if (cooldown < 1) {
                                if (p.containerMenu instanceof CraftingStationMenu && p.blockPosition().distManhattan(getBlockPos()) < 5) {
                                    p.sendSystemMessage(rec.answer);
                                    cooldown = 20 * 10;
                                }
                            }
                        }
                        if (rec.can) {
                            craftingTicks += tickRate;

                            if (craftingTicks > 100) {
                                craftingTicks = 0;
                                tryRecipe(p, false);
                                SoundUtils.playSound(level, getBlockPos(), SoundEvents.ANVIL_DESTROY);
                            }
                        } else {
                            show.clearContent();
                        }
                    }
                }

            }

        } catch (Exception e) {
            ModErrors.print(e);
        }

        this.setChanged(); // todo will this cause any problems to have it perma on?
    }

    public ExplainedResult tryRecipe(Player p, boolean justCheck) {


        int ownerLvl = Load.player(p).professions.getLevel(getProfession().GUID());

        var recipe = getCurrentRecipe(level);

        if (recipe == null) {
            return ExplainedResult.failure(Component.literal("Recipe not found"));
        }
        if (!this.inventory.getInventory(OUTPUTS).isEmpty()) {
            return ExplainedResult.failure(Component.literal("Output slots are not empty."));
        }
        if (recipe.getLevelRequirement() > ownerLvl) {
            return ExplainedResult.failure(Component.literal("Not high enough level to craft."));
        }

        if (justCheck) {
            var showstack = recipe.toResultStackForJei();
            showstack.setCount(1);
            this.show.setItem(0, showstack);
            return ExplainedResult.success();
        }


        float expMulti = 1;

        boolean destroyOuput = false;

        if (this.inventory.getInventory(INPUTS).countItem(SlashItems.DESTROY_OUTPUT.get()) > 0) {
            expMulti = 3;
            destroyOuput = true;
        }

        int expGive = (int) (recipe.getExpReward(p, ownerLvl, getMats()) * expMulti);

        this.addExp(expGive);

        if (destroyOuput) {
            SoundUtils.playSound(level, getBlockPos(), SoundEvents.FIRE_EXTINGUISH);
            SoundUtils.playSound(level, getBlockPos(), SoundEvents.EXPERIENCE_ORB_PICKUP);
        } else {
            for (ItemStack stack : recipe.craft(p, getMats())) {
                inventory.addStack(OUTPUTS, stack);
            }
        }


        recipe.spendMaterials(getMats());
        return ExplainedResult.success();

    }

    public ExplainedResult trySalvage(Player p, boolean justCheck) {

        int ownerLvl = Load.player(p).professions.getLevel(getProfession().GUID());

        if (getProfession().GUID().equals(Professions.SALVAGING)) {
            if (this.inventory.getInventory(OUTPUTS).isEmpty()) {
                for (ItemStack stack : this.getMats()) {

                    ICommonDataItem data = ICommonDataItem.load(stack);
                    ISalvagable sal = ISalvagable.load(stack);
                    if (data != null && sal != null) {
                        if (sal.isSalvagable()) {

                            if (justCheck) {
                                return ExplainedResult.success();
                            }
                            for (ItemStack res : sal.getSalvageResult(stack)) {
                                this.inventory.addStack(OUTPUTS, res);
                            }
                            this.addExp(data.getSalvageExpReward());

                            float multi = data.getRarity().item_value_multi;

                            stack.shrink(1);
                            for (ItemStack randomDrop : getProfession().getAllDrops(p, ownerLvl, data.getLevel(), multi)) {
                                this.inventory.addStack(OUTPUTS, randomDrop);
                            }

                            return ExplainedResult.success();
                        }
                    }
                }

            }
        }
        return ExplainedResult.failure(Component.literal(""));
    }

    public void addExp(int xp) {

        Player p = getOwner(level);

        if (p != null) {
            Load.player(p).professions.addExp(p, getProfession().GUID(), xp);
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

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("inv", this.inventory.createTag());

        pTag.putString("owner", owner);


    }

}

