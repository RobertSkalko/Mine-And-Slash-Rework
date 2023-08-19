package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RuneWord implements IAutoGson<RuneWord>, JsonExileRegistry<RuneWord> {
    public static RuneWord SERIALIZER = new RuneWord();

    public String id = "";
    public String uniq_id = "";
    public List<String> runes = new ArrayList<>();
    public List<String> slots = new ArrayList<>();

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNEWORDS;
    }

    @Override
    public String GUID() {
        return id;
    }

    public UniqueGear getUnique() {
        return ExileDB.UniqueGears().get(uniq_id);

    }

    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }


    public ItemStack createGearItem(int lvl) {

        GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(lvl));
        UniqueGear uniq = ExileDB.UniqueGears().get(uniq_id);
        b.uniquePart.set(uniq);
        b.level.set(lvl);
        b.rarity.set(uniq.getUniqueRarity());
        b.gearItemSlot.set(uniq.getBaseGear());

        ItemStack stack = b.createStack();

        return stack;
    }

    public boolean hasRunesToCraft(Player p) {

        List<String> playeRunes = new ArrayList<>();

        for (ItemStack stack : getCraftInventory(p)) {
            if (stack.getItem() instanceof RuneItem rune) {
                playeRunes.add(rune.type.id);
            }
        }


        return hasAllRunes(playeRunes);
    }

    public void spendRunesToCraft(Player p) {
        for (String rune : this.runes) {
            for (ItemStack stack : this.getCraftInventory(p)) {
                if (isRuneItem(rune, stack)) {
                    stack.shrink(1);
                    break;
                }
            }
        }

    }

    // todo  just add to this if needed
    public List<ItemStack> getCraftInventory(Player p) {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack stack = p.getInventory().getItem(i);
            list.add(stack);
        }

        // this doesnt work for now because backpack items arent sycnced to client
        /*
        var inv = Load.backpacks(p).getBackpacks().getInv(Backpacks.BackpackType.CURRENCY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            list.add(stack);
        }

         */

        return list;

    }


    public boolean hasAllRunes(List<String> list) {
        return new HashSet<>(list).containsAll(this.runes);
    }

    boolean isRuneItem(String id, ItemStack stack) {
        return stack.getItem() instanceof RuneItem rune && rune.type.id.equals(id);
    }

    public boolean canApplyOnItem(ItemStack stack) {

        if (slots.stream()
                .noneMatch(e -> {
                    return GearSlot.isItemOfThisSlot(ExileDB.GearSlots()
                            .get(e), stack.getItem());
                })) {
            return false;
        }

        return true;
    }

    public boolean runesCanActivateRuneWord(List<String> craftrunes, boolean requireExact) {

        List<String> copy = new ArrayList<>(craftrunes);

        boolean nope = false;
        for (String runeid : runes) {
            if (copy.contains(runeid)) {
                copy.remove(runeid);
            } else {
                nope = true;
            }
        }
        if (nope) {
            return false;
        }

        if (!requireExact) {
            return true;
        }

        if (copy.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
