package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.commands.Commands.literal;

public class CommandBuilder {

    List<ArgumentWrapper> args = new ArrayList<>();
    List<LiteralWrapper> literals = new ArrayList<>();
    Consumer<CommandContext<CommandSourceStack>> action;

    public static void of(CommandDispatcher dis, Consumer<CommandBuilder> c) {
        var b = new CommandBuilder();
        c.accept(b);
        b.build(dis);
    }

    public CommandBuilder addLiteral(String id, PermWrapper perm) {
        this.literals.add(new LiteralWrapper(id, perm));
        return this;
    }

    public CommandBuilder addArg(ArgumentWrapper arg) {
        this.args.add(arg);
        return this;
    }

    public CommandBuilder action(Consumer<CommandContext<CommandSourceStack>> ctx) {
        this.action = ctx;
        return this;

    }

    public void build(CommandDispatcher<CommandSourceStack> commandDispatcher) {

        List<String> ids = Arrays.asList(CommandRefs.ID);

        for (String id : ids) {

            // dont add different prefixes for commands unless ALL commands are made this way
            var first = literal(id);

            List<ArgumentBuilder> list = new ArrayList<>();
            list.add(first);

            for (LiteralWrapper literal : literals) {
                var next = literal(literal.id).requires(x -> x.hasPermission(literal.perm.perm));
                list.add(next);
            }
            for (ArgumentWrapper arg : args) {
                var next = arg.getType();
                if (arg.suggestions != null) {
                    next.suggests(arg.suggestions);
                }
                list.add(next);
            }


            for (int i = list.size() - 1; i > 0; i--) {
                var v1 = list.get(i);
                var v2 = list.get(i - 1);

                if (i == list.size() - 1) {
                    v1.executes(e -> {
                        action.accept(e);
                        return 0;
                    });
                }
                v2.then(v1);
            }


            commandDispatcher.register(first);
        }

    }

}
