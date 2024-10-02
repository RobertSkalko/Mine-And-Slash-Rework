package com.robertx22.mine_and_slash.vanilla_mc.new_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.WatcherEyeBlueprint;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.parts.ResetPlayerData;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenGuiPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PlayerCommands {

    public static void init(CommandDispatcher dis) {

        // todo also add a currenccy apply command
        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            RegistryWrapper<ItemModification> MOD = new RegistryWrapper(ExileRegistryTypes.ITEM_MOD);

            x.addLiteral("item_mod", PermWrapper.OP);
            x.addLiteral("use", PermWrapper.OP);
            x.addArg(PLAYER);
            x.addArg(MOD);

            x.action(e -> {
                var p = PLAYER.get(e);
                var mod = MOD.getFromRegistry(e);
                ItemStack stack = p.getMainHandItem();
                mod.applyMod(ExileStack.of(stack));

                p.setItemSlot(EquipmentSlot.MAINHAND, stack);

                p.sendSystemMessage(Component.literal("Applied Item Modification from Command").withStyle(ChatFormatting.GREEN));
            });

        }, "Applies an item modification to the item in player's hand.");

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            RegistryWrapper<ExileCurrency> CURRENCY = new RegistryWrapper(ExileRegistryTypes.CURRENCY);

            x.addLiteral("currency", PermWrapper.OP);
            x.addLiteral("use", PermWrapper.OP);
            x.addArg(PLAYER);
            x.addArg(CURRENCY);

            x.action(e -> {
                var p = PLAYER.get(e);
                var mod = CURRENCY.getFromRegistry(e);
                ItemStack stack = p.getMainHandItem();

                var ctx = new LocReqContext(p, stack, mod.getItem().getDefaultInstance());

                var ex = mod.canItemBeModified(ctx);
                if (ex.can) {
                    var res = mod.modifyItem(ctx);
                    p.setItemSlot(EquipmentSlot.MAINHAND, res.stack.getStack().copy());

                } else {
                    p.sendSystemMessage(ex.answer);
                }

            });

        }, "Tries to apply an item currency to the item in player's hand.");

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();

            x.addLiteral("info", PermWrapper.OP);
            x.addLiteral("area_level", PermWrapper.OP);

            x.addArg(PLAYER);

            x.action(e -> {
                var p = PLAYER.get(e);
                var info = LevelUtils.determineLevel(null, p.level(), p.blockPosition(), p, true);
                p.sendSystemMessage(info.getTooltip());
            });

        }, "Tells you how exactly the area level was calculated.");


        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            IntWrapper NUMBER = new IntWrapper("level");

            x.addLiteral("give", PermWrapper.OP);
            x.addLiteral("watcher_eye_jewel", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(NUMBER);

            x.action(e -> {
                var p = PLAYER.get(e);
                var num = NUMBER.get(e);

                var info = LootInfo.ofLevel(num);
                WatcherEyeBlueprint b = new WatcherEyeBlueprint(info);
                ItemStack stack = b.createStack();
                PlayerUtils.giveItem(stack, p);
            });

        }, "Gives a random watcher eye jewel, affix count depends on level");

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            IntWrapper NUMBER = new IntWrapper("point_amount");
            StringWrapper POINT_TYPE = new StringWrapper("point_type", () -> Arrays.stream(PlayerPointsType.values()).map(e -> e.name()).collect(Collectors.toList()));

            x.addLiteral("points", PermWrapper.OP);
            x.addLiteral("give", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(POINT_TYPE);
            x.addArg(NUMBER);

            x.action(e -> {
                var p = PLAYER.get(e);
                var num = NUMBER.get(e);
                var type = PlayerPointsType.valueOf(POINT_TYPE.get(e));

                var data = Load.player(p).points.get(type);

                var result = data.giveBonusPoints(num);

                if (result.answer != null) {
                    p.sendSystemMessage(result.answer);
                }
            });

        }, "Give player bonus points. The amount you can give is managed by the Game balance datapack. These are separate from points gained per level");

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            IntWrapper NUMBER = new IntWrapper("point_amount");
            StringWrapper POINT_TYPE = new StringWrapper("point_type", () -> Arrays.stream(PlayerPointsType.values()).map(e -> e.name()).collect(Collectors.toList()));

            x.addLiteral("points", PermWrapper.OP);
            x.addLiteral("cheat_give", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(POINT_TYPE);
            x.addArg(NUMBER);

            x.action(e -> {
                var p = PLAYER.get(e);
                var num = NUMBER.get(e);
                var type = PlayerPointsType.valueOf(POINT_TYPE.get(e));

                var data = Load.player(p).points.get(type);

                var result = data.giveCheatPoints(num);

                if (result.answer != null) {
                    p.sendSystemMessage(result.answer);
                }
            });

        }, "Give player bonus points. These are considered cheats and uncapped. Only meant for testing. These are separate from points gained per level");


        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            StringWrapper POINT_TYPE = new StringWrapper("point_type", () -> Arrays.stream(PlayerPointsType.values()).map(e -> e.name()).collect(Collectors.toList()));

            x.addLiteral("points", PermWrapper.OP);
            x.addLiteral("reset", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(POINT_TYPE);

            x.action(e -> {
                var p = PLAYER.get(e);
                var type = PlayerPointsType.valueOf(POINT_TYPE.get(e));

                var data = Load.player(p).points.get(type);

                data.resetBonusPoints();

                p.sendSystemMessage(Chats.RESET_POINTS.locName(type.word().locName()));
            });

        }, "Resets bonus points of player");


        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            StringWrapper TYPE = new StringWrapper("gui_type", () -> Arrays.stream(OpenGuiPacket.GuiType.values()).map(e -> e.name()).collect(Collectors.toList()));

            x.addLiteral("open", PermWrapper.OP);
            x.addArg(PLAYER);
            x.addArg(TYPE);

            x.action(e -> {
                var p = PLAYER.get(e);
                var type = OpenGuiPacket.GuiType.valueOf(TYPE.get(e));

                Packets.sendToClient(p, new OpenGuiPacket(type));
            });

        }, "Sends a packet from server to open a gui from the client, useful for testing");

        /*
        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();

            x.addLiteral("misc", PermWrapper.OP);
            x.addLiteral("unequip_support_gems", PermWrapper.OP);

            x.addArg(PLAYER);

            x.action(e -> {
                var p = PLAYER.get(e);
                Load.player(p).getSkillGemInventory().getGemsInv().
            });

        }, "Opens MNS Hub Gui");
         */

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            IntWrapper NUMBER = new IntWrapper("level");

            x.addLiteral("set", PermWrapper.OP);
            x.addLiteral("favor", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(NUMBER);

            x.action(e -> {
                var p = PLAYER.get(e);
                var num = NUMBER.get(e);
                Load.player(p).favor.set(p, num);
            });

        }, "Sets Favor");


        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            var PROFESSION = new RegistryWrapper<Profession>(ExileRegistryTypes.PROFESSION);
            IntWrapper NUMBER = new IntWrapper("level");

            x.addLiteral("set", PermWrapper.OP);
            x.addLiteral("profession_level", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(PROFESSION);
            x.addArg(NUMBER);


            x.action(e -> {
                var en = PLAYER.get(e);
                var num = NUMBER.get(e);
                var prof = PROFESSION.get(e);

                PlayerData data = Load.player(en);
                data.professions.setLevel(prof, num);
            });

        }, "Sets Mine and Slash Profession level");

        CommandBuilder.of(dis, x -> {
            PlayerWrapper PLAYER = new PlayerWrapper();
            StringWrapper STRING = new StringWrapper("reset_type", () -> Arrays.stream(ResetPlayerData.values()).map(e -> e.name()).collect(Collectors.toList()));

            x.addLiteral("reset", PermWrapper.OP);
            x.addLiteral("player_data", PermWrapper.OP);

            x.addArg(PLAYER);
            x.addArg(STRING);

            x.action(e -> {
                var p = PLAYER.get(e);
                var s = STRING.get(e);
                ResetPlayerData reset = ResetPlayerData.valueOf(s);
                reset.reset(p);
            });

        }, "Resets parts of a player's data. You can reset their level, talents etc");


    }
}
