package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class PlayerConfigData {

    public enum Config {

        /// todo add more configs like.. minions should attack x?

        CAST_FAIL("cast_fail_messages", Words.CAST_FAIL_MSGS),
        MOB_DEATH_MESSAGES("mob_death_messages", Words.MOB_DEATH_MESSAGES),
        DAMAGE_MESSAGES("damage_messages", Words.DAMAGE_MESSAGES),
        AUTO_PVE("auto_pve", Words.AUTOMATIC_PVE),
        STAT_ORDER_TEST("stat_order_test", Words.STAT_ORDER_TEST),
        DAMAGE_CONFLICT_MSG("damage_conflict_check", Words.DMG_CONFLICT_CHECK);
        public String id;
        public Words word;

        Config(String id, Words word) {
            this.id = id;
            this.word = word;
        }
    }


    public AutoSalvage salvage = new AutoSalvage();


    public HashMap<String, Boolean> configs = new HashMap<>();

    public boolean isConfigEnabled(Config id) {
        return configs.getOrDefault(id.id, false);
    }


    public class AutoSalvage {


        // todo test this
        public boolean trySalvageOnPickup(Player player, ItemStack stack) {

            if (stack.isEnchanted()) {
                return false; // we don't want to auto salvage gear that is likely to have been worn or important
            }

            ICommonDataItem<GearRarity> data = ICommonDataItem.load(stack);

            if (data != null) {
                if (data.isSalvagable()) {
                    if (salvages(data.getSalvageType(), data.getRarityId())) {
                        SoundUtils.playSound(player, SoundEvents.EXPERIENCE_ORB_PICKUP, 0.75F, 1.25F);
                        stack.shrink(100);
                        data.getSalvageResult(stack).forEach(e -> {
                            PlayerUtils.giveItem(e, player);
                            Load.backpacks(player).getBackpacks().tryAutoPickup(player, stack);
                        });

                        return true;
                    }
                }
            }

            return false;
        }

        private HashMap<ToggleAutoSalvageRarity.SalvageType, HashMap<String, Boolean>> map = new HashMap<>();

        public boolean salvages(ToggleAutoSalvageRarity.SalvageType type, String rar) {
            return map.getOrDefault(type, new HashMap<>()).getOrDefault(rar, false);
        }

        public void toggle(ToggleAutoSalvageRarity.SalvageType type, String rarity) {
            if (!map.containsKey(type)) {
                map.put(type, new HashMap<>());
            }
            var m2 = map.get(type);

            if (!m2.containsKey(rarity)) {
                m2.put(rarity, false);
            }

            boolean bool = m2.get(rarity);

            if (bool) {
                m2.put(rarity, false);
            } else {
                m2.put(rarity, true);
            }
        }
    }

}
