package com.robertx22.age_of_exile.gui.wiki.all;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.wiki.BestiaryEntry;
import com.robertx22.age_of_exile.gui.wiki.BestiaryGroup;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyBestiary extends BestiaryGroup<Currency> {
    @Override
    public List<BestiaryEntry> getAll(int lvl) {
        return ExileDB.CurrencyItems().getList().stream().map(x -> new BestiaryEntry.Item(x.getCurrencyItem().getDefaultInstance())).collect(Collectors.toList());
    }

    @Override
    public Component getName() {
        return Words.Currency.locName();
    }

    @Override
    public String texName() {
        return "currency";
    }

}
