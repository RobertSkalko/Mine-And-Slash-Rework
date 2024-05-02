package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class GenerateWikiCommand {

    public enum WikiGen {
        CURRENCY_LIST() {
            @Override
            public String getText() {
                String s = "";
                var list = ExileDB.CurrencyItems().getFilterWrapped(x -> true).list;
                list.sort(Comparator.comparing(x -> x.GUID()));
                for (Currency cur : list) {
                    s += " - " + CLOC.translate(cur.locName()) + ": " + cur.locDescForLangFile() + " (Weight: " + cur.Weight() + ")" + "\n";
                }
                return s;
            }
        };

        public abstract String getText();
    }

    public static class Sugg extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return Arrays.stream(WikiGen.values()).map(x -> x.name()).collect(Collectors.toList());
        }
    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("generate_wiki").requires(e -> e.hasPermission(0))
                                .then(argument("type", StringArgumentType.string())
                                        .suggests(new Sugg())
                                        .executes(ctx -> run(ctx.getSource(), StringArgumentType.getString(ctx, "type"))))));
    }


    private static int run(CommandSourceStack source, String id) {

        try {

            if (source.getEntity() instanceof ServerPlayer p) {

                var e = WikiGen.valueOf(id);
                String text = e.getText();

                p.sendSystemMessage(Component.literal(ChatFormatting.YELLOW + "" + ChatFormatting.BOLD + "Click Here to the Copy Text.")
                        .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text))));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
