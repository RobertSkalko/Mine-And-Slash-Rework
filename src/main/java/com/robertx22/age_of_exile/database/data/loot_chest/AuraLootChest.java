package com.robertx22.age_of_exile.database.data.loot_chest;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AuraLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {
        SkillGemBlueprint b = new SkillGemBlueprint(LootInfo.ofLevel(data.lvl), SkillGemData.SkillGemType.AURA);
        b.rarity.set(data.getRarity());
        return b.createStack();
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().build();
    }

    @Override
    public Item getKey() {
        return null;
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return RarityItems.GEAR_CHESTS.get(data.getRarity().GUID()).get();
    }

    @Override
    public String GUID() {
        return "aura";
    }

    @Override
    public int Weight() {
        return (int) (ServerContainer.get().AURA_GEM_DROPRATE.get() * 100);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Lootboxes;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".chest_type." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return GUID().substring(0, 1).toUpperCase() + GUID().substring(1);
    }
}
