package com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.commands.Commands.literal;

public class CommandBuilder {
    public static List<CommandBuilder> ALL = new ArrayList<>();


    List<ArgumentWrapper> args = new ArrayList<>();
    List<LiteralWrapper> literals = new ArrayList<>();
    Consumer<CommandContext<CommandSourceStack>> action;
    String description;

    public static void of(CommandDispatcher dis, Consumer<CommandBuilder> c, String desc) {
        var b = new CommandBuilder();
        b.description = desc;
        c.accept(b);
        b.build(dis);

        ALL.add(b);
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

        var first = literal(CommandRefs.ID);

        List<Integer> optionals = new ArrayList<>();
        List<ArgumentBuilder> list = new ArrayList<>();
        list.add(first);

        int x = 0;


        for (LiteralWrapper literal : literals) {
            var next = literal(literal.id).requires(e -> e.hasPermission(literal.perm.perm));
            list.add(next);
            x++;
        }
        for (ArgumentWrapper arg : args) {
            if (arg.isOptional()) {
                optionals.add(x);
            }
            var next = arg.getType();
            if (arg.suggestions != null) {
                next.suggests(arg.suggestions);
            }
            list.add(next);
            x++;
        }


        for (int i = list.size() - 1; i > 0; i--) {
            var v1 = list.get(i);
            var v2 = list.get(i - 1);

            if (optionals.contains(i) || i == list.size() - 1) {
                v1.executes(e -> {
                    action.accept(e);
                    return 0;
                });
            }
            v2.then(v1);

        }


        commandDispatcher.register(first);


        generateWikiString();

    }


    private String commandString;

    public String getWikiString() {
        String s = commandString;

        s += "\n" + description;
        return s;
    }

    private void generateWikiString() {
        String command = "/" + CommandRefs.ID + "";

        for (LiteralWrapper literal : this.literals) {
            command += " " + literal.id;
        }
        for (ArgumentWrapper arg : this.args) {
            command += " " + arg.argName;
        }
        this.commandString = command;
    }

}
