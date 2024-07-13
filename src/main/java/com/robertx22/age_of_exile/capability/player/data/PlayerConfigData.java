package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.commands.auto_salvage.AutoSalvageGenericConfigure;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Optional;

public class PlayerConfigData {

    public enum Config {

        /// todo add more configs like.. minions should attack x?

        CAST_FAIL("cast_fail_messages", true, Words.CAST_FAIL_MSGS, false),
        MOB_DEATH_MESSAGES("mob_death_messages", false, Words.MOB_DEATH_MESSAGES, false),
        DAMAGE_MESSAGES("damage_messages", false, Words.DAMAGE_MESSAGES, false),
        AUTO_PVE("auto_pve", false, Words.AUTOMATIC_PVE, false),
        AGGRESSIVE_SUMMONS("aggressive_summons", true, Words.AGGRESIVE_SUMMONS, false),
        STAT_ORDER_TEST("stat_order_test", false, Words.STAT_ORDER_TEST, true),
        DAMAGE_CONFLICT_MSG("damage_conflict_check", false, Words.DMG_CONFLICT_CHECK, true),
        DROP_MAP_CHEST_CONTENTS_ON_GROUND("drop_map_chest_contents_on_ground", false, Words.DROP_MAP_CHEST_CONTENTS_ON_GROUND, false);

        public String id;
        public Words word;
        public boolean isDebug;
        public boolean enabledByDefault;


        Config(String id, boolean enabledByDefault, Words word, boolean isdebug) {
            this.id = id;
            this.word = word;
            this.isDebug = isdebug;
            this.enabledByDefault = enabledByDefault;
        }
    }


    public AutoSalvage salvage = new AutoSalvage();

    public HashMap<String, Boolean> configs = new HashMap<>();

    public boolean isConfigEnabled(Config id) {
        if (!configs.containsKey(id.id)) {
            configs.put(id.id, id.enabledByDefault);
        }
        return configs.getOrDefault(id.id, id.enabledByDefault);
    }


    public class AutoSalvage {

        //  private HashMap<ToggleAutoSalvageRarity.SalvageType, HashMap<String, Boolean>> map = new HashMap<>();
        // Salvage Type -> <rarity, enabled>
        private HashMap<ToggleAutoSalvageRarity.SalvageType, HashMap<String, Boolean>> map = new HashMap<>();

        // Salvage Type -> <ID, enabled>
        // Ex:
        // SalvageType.SPELL, <plus_aoe, disabled>
        // SalvageType.GEAR, <shield, enabled>

        // this configuration should take precedence over the rarity config because it's more specific
        private HashMap<ToggleAutoSalvageRarity.SalvageType, HashMap<String, Boolean>> tmap = new HashMap<>();

        public HashMap<ToggleAutoSalvageRarity.SalvageType, HashMap<String, Boolean>> getTMap() {
            if (tmap == null) {
                tmap = new HashMap<>();
            }
            return tmap;
        }

        // todo test this
        public boolean trySalvageOnPickup(Player player, ItemStack stack) {

            if (stack.isEnchanted()) {
                return false; // we don't want to auto salvage gear that is likely to have been worn or important
            }

            ICommonDataItem<GearRarity> data = ICommonDataItem.load(stack);
            boolean doSalvage = false;

            if (data != null) {
                if (data.isSalvagable()) {

                    Optional<Boolean> typeSalvageEnabled = checkTypeSalvageConfig(data.getSalvageType(), data.getSalvageConfigurationId());

                    if (typeSalvageEnabled.isEmpty()) {
                        if (checkRaritySalvageConfig(data.getSalvageType(), data.getRarityId())) {
                            doSalvage = true;
                        }
                    } else {
                        doSalvage = typeSalvageEnabled.get();
                    }
                }

                if (doSalvage) {
                    SoundUtils.playSound(player, SoundEvents.EXPERIENCE_ORB_PICKUP, 0.75F, 1.25F);
                    stack.shrink(100);
                    data.getSalvageResult(stack).forEach(e -> {
                        PlayerUtils.giveItem(e, player);
                        Load.backpacks(player).getBackpacks().tryAutoPickup(player, stack);
                    });
                    return true;
                }

            }

            return false;
        }


        public boolean checkRaritySalvageConfig(ToggleAutoSalvageRarity.SalvageType type, String rar) {
            return map.getOrDefault(type, new HashMap<>()).getOrDefault(rar, false);
        }

        public Optional<Boolean> checkTypeSalvageConfig(ToggleAutoSalvageRarity.SalvageType type, String id) {
            if (id == null) {
                return Optional.empty();
            }

            if (!getTMap().containsKey(type)) {
                return Optional.empty();
            }

            if (getTMap().containsKey(type) && !getTMap().get(type).containsKey(id)) {
                return Optional.empty();
            }

            return Optional.of(tmap.get(type).get(id));
        }

        public void toggleRaritySalvageConfig(ToggleAutoSalvageRarity.SalvageType type, String rarity) {
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

        public void setAutoSalvageForTypeAndId(ToggleAutoSalvageRarity.SalvageType salvageType, String id, AutoSalvageGenericConfigure.AutoSalvageConfigAction action) {

            if (action == AutoSalvageGenericConfigure.AutoSalvageConfigAction.CLEAR) {
                if (!getTMap().containsKey(salvageType)) {
                    return;
                }
                var idMap = tmap.get(salvageType);
                idMap.remove(id);
                return;
            }

            if (!tmap.containsKey(salvageType)) {
                getTMap().put(salvageType, new HashMap<>());
            }

            getTMap().get(salvageType).put(id, action == AutoSalvageGenericConfigure.AutoSalvageConfigAction.ENABLE);

        }

        public HashMap<String, Boolean> getConfiguredMapForSalvageType(ToggleAutoSalvageRarity.SalvageType salvageType) {
            return getTMap().getOrDefault(salvageType, new HashMap<>());
        }

    }

}
