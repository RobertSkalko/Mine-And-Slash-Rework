package com.robertx22.mine_and_slash.uncommon.enumclasses;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Locale;

public enum ModType {

    FLAT(false, "flat", AttributeModifier.Operation.ADDITION),
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
