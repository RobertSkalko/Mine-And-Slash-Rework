package com.robertx22.age_of_exile.database.data.runewords;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class RuneWord implements IAutoGson<RuneWord>, JsonExileRegistry<RuneWord>, IAutoLocName {
    public static RuneWord SERIALIZER = new RuneWord();

    public String id = "";
    public transient String name = "";
    public List<StatMod> stats = new ArrayList<>();
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


    @Override
    public Class<RuneWord> getClassForSerialization() {
        return RuneWord.class;
    }

    // todo
    public static String join(Iterator<?> iterator, String separator) {
        if (separator == null) {
            separator = "";
        }
        StringBuilder buf = new StringBuilder(256); // Java default is 16, probably too small
        while (iterator.hasNext()) {
            buf.append(iterator.next());
            if (iterator.hasNext()) {
                buf.append(separator);
            }
        }
        return buf.toString();
    }

    public boolean hasMatchingRunesToCreate(GearItemData gear) {

        var list = gear.sockets.getSocketed().stream().map(x -> x.g).collect(Collectors.toList());

        String reqString = join(runes.listIterator(), "");
        String testString = join(list.listIterator(), "");

        return testString.contains(reqString);

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


    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Rune_Words;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".runeword." + id;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }
}
