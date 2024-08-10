package com.robertx22.mine_and_slash.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.processors.helpers.MobBuilder;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.commands.Commands.literal;

public class SpawnBoss {
    
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("spawn").requires(e -> e.hasPermission(2))
                                .then(literal("boss")
                                        .requires(e -> e.hasPermission(2))
                                        .executes(ctx -> run(ctx.getSource().getPlayer())))));
    }

    private static int run(Player p) {

        try {
            EntityType<? extends Mob> type = EntityType.ZOMBIE;

            for (Mob en : MobBuilder.of(type, x -> {
                x.rarity = ExileDB.GearRarities().get(IRarity.MYTHIC_ID);
            }).summonMobs(p.level(), p.blockPosition())) {
                Load.Unit(en).setupRandomBoss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}