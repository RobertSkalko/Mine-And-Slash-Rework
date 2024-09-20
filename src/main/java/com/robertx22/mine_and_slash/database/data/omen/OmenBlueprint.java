package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.RarityItemBlueprint;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public class OmenBlueprint extends RarityItemBlueprint {

    public OmenBlueprint(LootInfo info) {
        super(info);
    }


    @Override
    protected ItemStack generate() {
        OmenData data = new OmenData();

        var rar = this.rarity.get();

        var diff = RandomUtils.randomFromList(Arrays.stream(OmenDifficulty.values()).filter(x -> this.info.level >= GameBalanceConfig.get().MAX_LEVEL * x.lvl_req).collect(Collectors.toList()));

        var omen = ExileDB.Omens().getFilterWrapped(x -> this.info.level >= GameBalanceConfig.get().MAX_LEVEL * x.lvl_req).random();

        data.id = omen.GUID();

        data.rar = rar.GUID();

        int slots = diff.specificSlots.random();

        int affixes = diff.affixes.random();


        data.rarities.put(GearRarityType.NORMAL, diff.normal.random());
        data.rarities.put(GearRarityType.UNIQUE, diff.unique.random());
        data.rarities.put(GearRarityType.RUNED, diff.runed.random());


        while (data.slot_req.size() < slots) {
            var slot = omen.getRandomSlotReq();
            if (data.slot_req.stream().noneMatch(x -> x.slot.equals(slot.GUID()))) {
                data.slot_req.add(new OmenData.OmenSlotReq(slot.GUID(), omen.getRandomSlotReqRarity(data)));
            }
        }

        for (int i = 0; i < affixes; i++) {
            var affix = ExileDB.Affixes().getFilterWrapped(x -> omen.affix_types.contains(x.type)).of(x -> !x.requirements.tag_requirements.stream().allMatch(t -> t.included.contains(SlotTags.weapon_family.GUID()))).random();
            var adata = new AffixData(affix.type);
            adata.id = affix.GUID();
            adata.rar = rar.GUID();
            adata.p = OmenData.getStatPercent(data.rarities, data.slot_req, rar);
            data.aff.add(adata);
        }


        var stack = new ItemStack(SlashItems.OMEN.get());
        StackSaving.OMEN.saveTo(stack, data);
        return stack;
    }
}
