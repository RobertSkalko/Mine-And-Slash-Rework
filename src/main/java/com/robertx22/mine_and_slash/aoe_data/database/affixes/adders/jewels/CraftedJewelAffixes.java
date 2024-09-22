package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewels;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class CraftedJewelAffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        String PREFIX = "crafted_jewel_";

        // jewels dont need names
        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "jewel_res")
                .add(Elements.Fire, "")
                .add(Elements.Nature, "")
                .add(Elements.Cold, "")
                .stats(x -> Arrays.asList(new StatMod(10, 25, new ElementalResist(x), ModType.FLAT)))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(5000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "aura_cost")
                .stat(AuraCapacity.getInstance().mod(4, 10))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "aura_eff")
                .stat(AuraEffect.getInstance().mod(4, 10))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "hp")
                .stat(Health.getInstance().mod(5, 15).percent())
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "ms")
                .stat(MagicShield.getInstance().mod(5, 15).percent())
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "armor")
                .stat(Armor.getInstance().mod(10, 25).percent())
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "dodge")
                .stat(DodgeRating.getInstance().mod(10, 25).percent())
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "cdr")
                .stat(SpellChangeStats.COOLDOWN_REDUCTION.get().mod(3, 15))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "crit")
                .stat(OffenseStats.CRIT_CHANCE.get().mod(5, 15))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        AffixBuilder.Normal(PREFIX + "critdmg")
                .stat(OffenseStats.CRIT_DAMAGE.get().mod(10, 25))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(1000)
                .craftedUniqueJewel()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> PREFIX + x.guidName + "_max_res")
                .add(Elements.Fire, "")
                .add(Elements.Nature, "")
                .add(Elements.Cold, "")
                .add(Elements.Shadow, "")
                .stats(x -> Arrays.asList(new StatMod(1, 5, new MaxElementalResist(x), ModType.FLAT)))
                .includesTags(SlotTags.crafted_jewel_unique)
                .Weight(500)
                .craftedUniqueJewel()
                .Build();

    }
}
