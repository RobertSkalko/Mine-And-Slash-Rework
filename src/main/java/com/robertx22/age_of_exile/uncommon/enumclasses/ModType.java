package com.robertx22.age_of_exile.uncommon.enumclasses;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.Locale;

public enum ModType {

    FLAT(false, "flat", AttributeModifier.Operation.ADDITION),
    ITEM_FLAT(false, "item", AttributeModifier.Operation.ADDITION),
    ITEM_PERCENT(true, "item_percent", AttributeModifier.Operation.MULTIPLY_TOTAL),
    PERCENT(true, "percent", AttributeModifier.Operation.MULTIPLY_BASE),
    MORE(true, "more", AttributeModifier.Operation.MULTIPLY_TOTAL);

    ModType(boolean isperc, String id, AttributeModifier.Operation op) {
        this.id = id;
        this.isPercent = isperc;
        this.operation = op;

    }

    boolean isPercent;
    public AttributeModifier.Operation operation;
    public String id;

    public boolean isFlat() {
        return !isPercent;
    }

    public boolean isPercent() {
        return isPercent;
    }

    public boolean isItemLocal() {
        return this == ITEM_FLAT || this == ITEM_PERCENT;
    }

    public ModType toNonItemType() {
        if (this == ModType.ITEM_FLAT) {
            return ModType.FLAT;
        }
        if (this == ModType.ITEM_PERCENT) {
            return ModType.PERCENT;
        }
        return this;
    }


    public static ModType fromString(String str) {

        for (ModType type : ModType.values()) {
            if (type.id.toLowerCase(Locale.ROOT)
                    .equals(str.toLowerCase(Locale.ROOT))) {
                return type;
            }
        }

        try {
            ModType TYPE = valueOf(str);

            if (TYPE != null) {
                return TYPE;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return FLAT;

    }

}
