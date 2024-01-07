package com.robertx22.age_of_exile.vanilla_mc.commands.reset;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.player.data.PlayerProfessionsData;
import com.robertx22.age_of_exile.capability.player.data.RestedExpData;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.characters.CharStorageData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class ResetCommand {

    public enum ResetType {
        LEVEL() {
            @Override
            public void reset(Player p) {
                Load.Unit(p).setLevel(1);
            }
        },
        PROFESSIONS() {
            @Override
            public void reset(Player p) {
                Load.player(p).professions = new PlayerProfessionsData();
            }
        },
        RESTED_EXP() {
            @Override
            public void reset(Player p) {
                Load.player(p).rested_xp = new RestedExpData();
            }
        },
        CHARACTERS() {
            @Override
            public void reset(Player p) {
                Load.player(p).characters = new CharStorageData();
            }
        },
        BONUS_TALENTS() {
            @Override
            public void reset(Player p) {
                Load.player(p).bonusTalents = 0;
            }
        },
        STATS() {
            @Override
            public void reset(Player p) {
                Load.player(p).statPoints = new StatPointsData();
            }
        },
        TALENTS() {
            @Override
            public void reset(Player p) {
                Load.player(p).talents = new TalentsData();
            }
        };

        public abstract void reset(Player p);
    }

    public static class ResetSuggestions extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return Arrays.stream(ResetType.values()).map(x -> x.name()).collect(Collectors.toList());
        }
    }

    public static void register(CommandDispatcher<CommandSourceStack> dis) {
        dis.register(
                literal(CommandRefs.ID)
                        .then(literal("reset_player_data").requires(e -> e.hasPermission(2))
                                .then(argument("type", StringArgumentType.word()).suggests(new ResetSuggestions())
                                        .then(argument("target", EntityArgument.entity())
                                                .executes(ctx -> run(EntityArgument.getPlayer(ctx, "target"), StringArgumentType.getString(ctx, "type")))))));
    }

    private static int run(Player en, String ty) {

        try {
            ResetType type = ResetType.valueOf(ty);
            type.reset(en);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
