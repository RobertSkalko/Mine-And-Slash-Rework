package com.robertx22.age_of_exile.vanilla_mc.commands.reset;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ResetSpellCooldowns {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("reset").requires(e -> e.hasPermission(2))
                                .then(literal("spell_cooldowns")
                                        .then(argument("target", EntityArgument.entity())
                                                .executes(
                                                        ctx -> run(EntityArgument.getPlayer(ctx, "target")))))));
    }

    private static int run(Player en) {

        try {
            Load.Unit(en)
                    .getCooldowns()
                    .onTicksPass(555555);

            for (int i = 0; i < 10; i++) {
                Load.spells(en)
                        .getCastingData().charges.onTicks(en, 500000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
