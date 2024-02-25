package com.robertx22.age_of_exile.vanilla_mc.commands.report;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.maps.generator.BuiltRoom;
import com.robertx22.age_of_exile.maps.generator.DungeonBuilder;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;

public class ReportMapIssue {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("report").requires(e -> e.hasPermission(0))
                                .then(literal("map_bug")
                                        .executes(ctx -> run(ctx.getSource())))));
    }


    private static int run(CommandSourceStack source) {

        try {

            if (source.getEntity() instanceof ServerPlayer p) {


                String text = "Map Bug Report, Problem Room: ";

                DungeonBuilder builder = new DungeonBuilder(0, p.chunkPosition());
                builder.build();
                BuiltRoom room = builder.builtDungeon.getRoomForChunk(p.chunkPosition());

                text += room.room.loc.toString();

                p.sendSystemMessage(Component.literal(ChatFormatting.GREEN + "-----------------------"));
                p.sendSystemMessage(Component.literal(ChatFormatting.AQUA + "Please make sure the problem is in the same chunk, or stand in the same spot as the map problem when using the command."));
                p.sendSystemMessage(Component.literal(ChatFormatting.RED + text));
                p.sendSystemMessage(Component.literal(ChatFormatting.YELLOW + "" + ChatFormatting.BOLD + "Click Here to Copy Text. Then Paste the text in a bug report.")
                        .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text))));
                p.sendSystemMessage(Component.literal(ChatFormatting.GREEN + "-----------------------"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}

