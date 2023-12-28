package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.augment.AugmentData;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class AugmentBlueprint extends RarityItemBlueprint {
    public AugmentBlueprint(LootInfo info) {
        super(info);
    }

    @Override
    ItemStack generate() {

        ItemStack stack = new ItemStack(SlashItems.AUGMENT.get());

        AugmentData data = new AugmentData();

        var rar = this.rarity.get();

        data.rar = rar.GUID();

        data.dur_sec = (int) (5 + (rar.stat_percents.random() * 10D));

        var pd = Load.player(info.player);

        var pos = ExileDB.Spells().getFilterWrapped(x -> pd.spellCastingData.getSpellData(x.GUID()).rank > 0);

        // i didn't know anon objects like this were possible
        var ref = new Object() {
            Spell spell = ExileDB.Spells().random();
        };
        if (!pos.list.isEmpty()) {
            ref.spell = pos.random();
        }
        // todo make sure only spells that player has can

        data.spell = ref.spell.GUID();

        // now to make sure effect can't benefit the spell itself

        var effect = ExileDB.ExileEffects().getFilterWrapped(x -> {
            if (!x.tags.contains(EffectTags.augment)) {
                return false;
            }
            if (ref.spell.getConfig().tags.tags.stream().anyMatch(e -> x.spell_tags.contains(e))) {
                return false;
            }
            return true;
        }).random();

        data.effect = effect.GUID();


        StackSaving.AUGMENT.saveTo(stack, data);

        return stack;
    }
}
