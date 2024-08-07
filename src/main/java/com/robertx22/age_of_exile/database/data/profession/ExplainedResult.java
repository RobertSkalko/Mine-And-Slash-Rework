package com.robertx22.age_of_exile.database.data.profession;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ExplainedResult {

    public boolean can;
    public Component answer;

    private ExplainedResult(boolean can, Component answer) {
        this.can = can;
        this.answer = answer;
    }

    public static ExplainedResult success() {
        return new ExplainedResult(true, Component.empty());
    }

    public static ExplainedResult failure(Component txt) {
        return new ExplainedResult(false, Component.empty().append(txt).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
    }

    public static ExplainedResult success(Component txt) {
        return new ExplainedResult(true, Component.empty().append(txt).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
    }

    public static ExplainedResult silentlyFail() {
        return new ExplainedResult(false, null);
    }
}
