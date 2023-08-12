package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class PlayerConfigData {

    public AutoSalvage salvage = new AutoSalvage();

    public class AutoSalvage {


        // todo test this
        public boolean trySalvageOnPickup(Player player, ItemStack stack) {

            if (stack.isEnchanted()) {
                return false; // we don't want to auto salvage gear that is likely to have been worn or important
            }


            ICommonDataItem<GearRarity> data = ICommonDataItem.load(stack);

            if (data != null) {
                if (salvages(data.getSalvageType(), data.getRarityId())) {
                    SoundUtils.playSound(player, SoundEvents.ANVIL_USE, 1, 1);
                    stack.shrink(100);
                    data.getSalvageResult(stack).forEach(e -> PlayerUtils.giveItem(e, player));

                    return true;
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
