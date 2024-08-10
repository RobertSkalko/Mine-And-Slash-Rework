package com.robertx22.mine_and_slash.database.data.prophecy;

import com.robertx22.mine_and_slash.database.data.prophecy.starts.GearProphecy;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.RarityItemBlueprint;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public enum ProphecyModifierType {

    GEAR_TYPE() {
        @Override
        public MutableComponent getTooltip(String data) {
            return ExileDB.GearTypes().get(data).locName().withStyle(ChatFormatting.WHITE);
        }

        @Override
        public float chanceToSpawn() {
            return 15;
        }

        @Override
        public void set(ItemBlueprint b, String data) {
            if (b instanceof GearBlueprint gb) {
                gb.gearItemSlot.set(data);
            }
        }

        @Override
        public boolean canApplyTo(ProphecyStart start, ItemBlueprint b) {
            return start instanceof GearProphecy;
        }
    },
    GEAR_RARITY() {
        @Override
        public MutableComponent getTooltip(String data) {
            var rar = ExileDB.GearRarities().get(data);
            return rar.locName().withStyle(rar.textFormatting());
        }

        @Override
        public float chanceToSpawn() {
            return 75;
        }

        @Override
        public void set(ItemBlueprint b, String data) {
            if (b instanceof RarityItemBlueprint gb) {
                gb.rarity.set(ExileDB.GearRarities().get(data));
            }
        }

        @Override
        public boolean canApplyTo(ProphecyStart start, ItemBlueprint b) {
            return b instanceof RarityItemBlueprint;
        }
    };


    public abstract MutableComponent getTooltip(String data);

    public abstract float chanceToSpawn();

    public abstract void set(ItemBlueprint b, String data);

    public abstract boolean canApplyTo(ProphecyStart start, ItemBlueprint b);
}
