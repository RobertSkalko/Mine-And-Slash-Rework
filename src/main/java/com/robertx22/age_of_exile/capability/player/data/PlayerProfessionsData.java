package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class PlayerProfessionsData {

    private HashMap<String, Data> map = new HashMap();

    public DailyDropModifiers daily_drop_multis = new DailyDropModifiers();

    public int getLevel(String id) {
        return map.getOrDefault(id, new Data()).lvl;
    }

    public int getExp(String id) {
        return map.getOrDefault(id, new Data()).exp;
    }

    public int getMaxExp(String id) {
        return map.getOrDefault(id, new Data()).getExpNeeded();
    }

    public void addExp(Player p, String id, int exp) {
        if (!map.containsKey(id)) {
            map.put(id, new Data());
        }


        var data = map.get(id);
        data.exp += exp;

        int perc = MathHelper.clamp((int) (map.get(id).exp / (float) map.get(id).getExpNeeded() * 100F), 0, 100);
        OnScreenMessageUtils.actionBar((ServerPlayer) p, Component.literal("+" + exp + " ").append(ExileDB.Professions().get(id).locName().append(" Exp (" + perc + "%)")).withStyle(ChatFormatting.GREEN));

        if (data.canLvl() && Load.Unit(p).getLevel() > data.lvl) {
            data.levelUp();

            Profession pro = ExileDB.Professions().get(id);
            p.sendSystemMessage(Chats.PROFESSION_LEVEL_UP.locName(pro.locName(), data.lvl));
        }
    }


    private static class Data {
        public int lvl = 1;
        public int exp = 0;

        public int getExpNeeded() {
            return LevelUtils.getExpRequiredForLevel(lvl + 1);
        }

        public boolean canLvl() {
            return exp >= getExpNeeded() && lvl < GameBalanceConfig.get().MAX_LEVEL;
        }

        public void levelUp() {
            exp -= getExpNeeded();
            lvl++;
        }
    }

    public static class DailyDropModifiers {

        private HashMap<String, List<DropModifier>> map = new HashMap<>();

        boolean day = false;

        private static DropModifier NONE = new DropModifier("", 1);

        public float getMulti(Profession pro, String id) {
            if (map.containsKey(pro.id)) {
                return map.get(pro.id).stream().filter(x -> x.id.equals(id)).findAny().orElse(NONE).multi;
            }
            return 1;
        }

        public List<Component> getTooltip() {

            List<Component> list = new ArrayList<>();

            for (Map.Entry<String, List<DropModifier>> en : map.entrySet()) {
                Profession pro = ExileDB.Professions().get(en.getKey());

                for (DropModifier mod : en.getValue()) {
                    ItemStack item = VanillaUTIL.REGISTRY.items().get(new ResourceLocation(mod.id)).getDefaultInstance();

                    list.add(pro.locName().append(": ").append(item.getHoverName()).append(": " + mod.multi + "x DROP RATE!")
                            .withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
                }
            }

            return list;
        }

        public void checkDayPass(ServerPlayer player) {


            if (!day) {
                boolean is = player.level().isDay();

                if (is) {

                    this.map = new HashMap<>();

                    player.sendSystemMessage(Chats.NEW_DAY.locName());

                    for (Profession p : ExileDB.Professions().getList()) {
                        if (!p.chance_drops.isEmpty()) {
                            String id = RandomUtils.randomFromList(RandomUtils.randomFromList(p.chance_drops).drops).item_id;
                            float multi = 10;
                            map.put(p.id, Arrays.asList(new DropModifier(id, multi)));
                        }
                    }

                    for (Component c : this.getTooltip()) {
                        player.sendSystemMessage(c);
                    }
                }
            }

            this.day = player.level().isDay();
        }

    }

    private static class DropModifier {
        public String id;
        public float multi;

        public DropModifier(String id, float multi) {
            this.id = id;
            this.multi = multi;
        }
    }

}
