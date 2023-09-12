package com.robertx22.age_of_exile.gui.wiki.all;

import com.robertx22.age_of_exile.gui.wiki.BestiaryEntry;
import com.robertx22.age_of_exile.gui.wiki.BestiaryGroup;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DBItemEntry<T> extends BestiaryGroup<T> {

    ExileRegistryType type;
    Words word;
    String id;
    public Function<T, BestiaryEntry> maker;

    public DBItemEntry(ExileRegistryType type, Words word, String id, Function<T, BestiaryEntry> maker) {
        this.type = type;
        this.word = word;
        this.id = id;
        this.maker = maker;
    }

    @Override
    public List<BestiaryEntry> getAll(int lvl) {
        List<BestiaryEntry> list = new ArrayList<>();
        for (Object o : Database.getRegistry(type).getList()) {
            T t = (T) o;
            list.add(maker.apply(t));
        }
        return list;
    }

    @Override
    public Component getName() {
        return word.locName();
    }

    
    @Override
    public String texName() {
        return id;
    }


}
