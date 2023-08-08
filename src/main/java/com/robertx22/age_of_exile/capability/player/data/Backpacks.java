package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.capability.player.helper.MyInventory;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;

public class Backpacks {

    public Backpacks(Player player) {
        this.player = player;
    }

    public enum BackpackType {
        GEARS("gear", Words.Gear),
        CURRENCY("currency", Words.Currency),
        SKILL_GEMS("skill_gem", Words.SkillGem);
        

        public String id;
        public Words name;

        BackpackType(String id, Words name) {
            this.id = id;
            this.name = name;
        }
    }

    Player player;

    public static int MAX_SIZE = 6 * 9;

    private MyInventory gears = new MyInventory(MAX_SIZE);
    private MyInventory currencies = new MyInventory(MAX_SIZE);
    private MyInventory skillGems = new MyInventory(MAX_SIZE);


    public MyInventory getInv(BackpackType type) {

        if (type == BackpackType.CURRENCY) {
            return currencies;
        }
        if (type == BackpackType.GEARS) {
            return gears;
        }
        if (type == BackpackType.SKILL_GEMS) {
            return skillGems;
        }

        return null;
    }


    // todo every time before you open backpack, it will replace locked slots with blocked slots that cant be clicked on and throw out/give items back

    public void openBackpack(BackpackType type, Player p) {
        if (!p.level().isClientSide) {
            MyInventory inv = getInv(type);
            p.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> {
                return ChestMenu.sixRows(i, playerInventory, inv);
            }, Component.literal("")));
        }
    }
}
