package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.misc.DamageTakenToMana;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatOrderTest {

    static List<String> warnings = new ArrayList<>();

    public static void removeTest(String id) {
        cached.removeIf(x -> x.getId().equals(id));
    }

    public static class FirstAndSecond {

        public Stat shouldBeFirst;
        public Stat shouldBeSecond;

        public String getId() {
            String id = this.shouldBeFirst.GUID() + "_" + this.shouldBeSecond.GUID();

            return id;
        }

        public FirstAndSecond(Stat shouldBeFirst, Stat shouldBeSecond) {
            this.shouldBeFirst = shouldBeFirst;
            this.shouldBeSecond = shouldBeSecond;
        }

        public boolean isCorrect(HashMap<String, Integer> map) {
            if (map.containsKey(shouldBeFirst.GUID()) && map.containsKey(shouldBeSecond.GUID())) {
                return map.get(shouldBeFirst.GUID()) < map.get(shouldBeSecond.GUID());
            }

            return true;
        }

        public String test(HashMap<String, Integer> map, Level wor) {

            boolean works = isCorrect(map);

            if (!works) {
                String id = getId();


                if (!warnings.contains(id)) {
                    warnings.add(id);

                    for (Player player : wor.players()) {
                        player.sendSystemMessage(Component.literal(shouldBeFirst.GUID() + " Stat SHOULD come before " + shouldBeSecond.GUID()).withStyle(ChatFormatting.RED));
                        player.sendSystemMessage(Component.literal("Tell this to Mine and Slash author").withStyle(ChatFormatting.YELLOW));
                        player.sendSystemMessage(Component.literal("Stat bugs are considered game breaking!").withStyle(ChatFormatting.YELLOW));
                        player.sendSystemMessage(Component.literal("You can turn off this warning in config: [STAT_ORDER_WARNINGS]").withStyle(ChatFormatting.AQUA));
                    }
                }
                return id;
            }
            return "";
        }
    }


    private static List<FirstAndSecond> cached = new ArrayList<>();

    // whenever I think a stat might be buggy, good to add it here.
    public static List<FirstAndSecond> getAll() {

        if (cached.isEmpty()) {
            List<FirstAndSecond> all = new ArrayList<>();

            all.add(new FirstAndSecond(OffenseStats.ACCURACY.get(), DodgeRating.getInstance()));
            all.add(new FirstAndSecond(ArmorPenetration.getInstance(), Armor.getInstance()));
            //all.add(new FirstAndSecond(OffenseStats.TOTAL_DAMAGE.get(), Armor.getInstance()));
            all.add(new FirstAndSecond(OffenseStats.CRIT_CHANCE.get(), OffenseStats.CRIT_DAMAGE.get()));
            all.add(new FirstAndSecond(OffenseStats.TOTAL_DAMAGE.get(), ResourceStats.LIFESTEAL.get()));
            all.add(new FirstAndSecond(ResourceStats.LIFESTEAL.get(), DamageTakenToMana.getInstance()));

            cached = all;
        }
        return cached;

    }
}
