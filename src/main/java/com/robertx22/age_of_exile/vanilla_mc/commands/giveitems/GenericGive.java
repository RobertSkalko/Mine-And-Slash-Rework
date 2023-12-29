package com.robertx22.age_of_exile.vanilla_mc.commands.giveitems;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ITypeBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.RarityItemBlueprint;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.GearRaritySuggestions;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.function.Function;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class GenericGive {

    String id;
    ExileRegistryType type;
    Function<LootInfo, RarityItemBlueprint> blueprint;

    public GenericGive(String id, ExileRegistryType type, Function<LootInfo, RarityItemBlueprint> blueprint) {
        this.id = id;
        this.type = type;
        this.blueprint = blueprint;
    }


    public void register(CommandDispatcher<CommandSourceStack> dis) {

        dis.register(
                literal(CommandRefs.ID)
                        .then(literal("give").requires(e -> e.hasPermission(2))
                                .then(literal(id)
                                        .then(argument("target", EntityArgument.player())
                                                .then(argument("type", StringArgumentType.word())
                                                        .suggests(type != null ? new DatabaseSuggestions(type) : null)
                                                        .then(argument("level", IntegerArgumentType.integer()).then(argument(
                                                                "rarity", StringArgumentType.string()).suggests(new GearRaritySuggestions())
                                                                .then(argument("amount", IntegerArgumentType.integer(1, 5000)).executes(e -> execute(
                                                                        e.getSource(), EntityArgument.getPlayer(e, "target"), StringArgumentType.getString(e, "type"
                                                                        ), IntegerArgumentType.getInteger(e, "level"), StringArgumentType.getString(e, "rarity"),
                                                                        IntegerArgumentType.getInteger(e, "amount")
                                                                ))))))))));
    }

    private int execute(CommandSourceStack commandSource, Player player, String type, int lvl, String rarity, int amount) {


        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        for (int i = 0; i < amount; i++) {

            RarityItemBlueprint blueprint = this.blueprint.apply(LootInfo.ofLevel(lvl));
            blueprint.level.set(lvl);

            if (ExileDB.GearRarities().isRegistered(rarity)) {
                blueprint.rarity.set(ExileDB.GearRarities().get(rarity));
            }

            if (blueprint instanceof ITypeBlueprint typeb) {
                if (!type.equals("random")) {
                    typeb.setType(type);
                }
            }

            PlayerUtils.giveItem(blueprint.createStack(), player);

        }

        return 0;
    }
}
