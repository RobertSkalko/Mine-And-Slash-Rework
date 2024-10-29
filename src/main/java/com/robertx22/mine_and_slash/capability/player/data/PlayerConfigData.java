package com.robertx22.mine_and_slash.capability.player.data;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage.AutoSalvageGenericConfigure;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Optional;

public class PlayerConfigData {

    public enum Config {

        CAST_FAIL("cast_fail_messages", true, Words.TITLE_FEATURE_CAST_FAIL, Words.CAST_FAIL_MSGS, false),
        MOB_DEATH_MESSAGES("mob_death_messages", false, Words.TITLE_FEATURE_MOB_KILL_LOOT, Words.MOB_DEATH_MESSAGES, false),
        EXP_CHAT_MESSAGES("exp_chat_messages", false, Words.TITLE_FEATURE_EXP_MSG, Words.EXP_CHAT_MESSAGES, false),
        DAMAGE_MESSAGES("damage_messages", false, Words.TITLE_FEATURE_DAMAGE_LOG, Words.DAMAGE_MESSAGES, false),
        PROFESSION_MESSAGES("profession_exp_messages", false, Words.TITLE_FEATURE_PROF_EXP, Words.PROFESSION_MESSAGES, false),
        AUTO_PVE("auto_pve", false, Words.TITLE_FEATURE_AUTO_TEAM, Words.AUTOMATIC_PVE, false),
        AGGRESSIVE_SUMMONS("aggressive_summons", true, Words.TITLE_FEATURE_AGGRO_SUMMONS, Words.AGGRESIVE_SUMMONS, false),
        STAT_ORDER_TEST("stat_order_test", false, Words.TITLE_FEATURE_STAT_ORDER_DEBUG, Words.STAT_ORDER_TEST, true),
        DAMAGE_CONFLICT_MSG("damage_conflict_check", false, Words.TITLE_FEATURE_DMG_CONFLICT_DEBUG, Words.DMG_CONFLICT_CHECK, true),
        EVERYONE_IS_ALLY("everyone_is_ally", false, Words.TITLE_FEATURE_EVERYONE_ALLY, Words.EVERYONE_IS_ALLY, false),
        DROP_MAP_CHEST_CONTENTS_ON_GROUND("drop_map_chest_contents_on_ground", false, Words.TITLE_FEATURE_DROP_MAP_CHEST_ITEMS, Words.DROP_MAP_CHEST_CONTENTS_ON_GROUND, false);

        public String id;
        public Words title;

        public Words word;
        public boolean isDebug;
        public boolean enabledByDefault;


        Config(String id, boolean enabledByDefault, Words title, Words word, boolean isdebug) {
            this.id = id;
            this.title = title;
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


            ExileStack ex = ExileStack.of(stack);

            if (stack.isEnchanted()) {
                return false; // we don't want to auto salvage gear that is likely to have been worn or important
            }

            ICommonDataItem<GearRarity> data = ICommonDataItem.load(stack);
            boolean doSalvage = false;

            if (data != null) {
                if (data.isSalvagable(ex)) {

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
                    data.getSalvageResult(ex).forEach(e -> {
                        Backpacks backpacks = Load.backpacks(player).getBackpacks();
                        //copy tryAutoPickup() but without playing sound
                        if (player.getInventory().countItem(SlashItems.MASTER_BAG.get()) >= 1) {
                            for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
                                if (type.isValid(e)) {
                                    var bag = backpacks.getInv(type);

                                    if (bag.hasFreeSlots()) {
                                        bag.addItem(e.copy());
                                        e.shrink(e.getCount() + 10);
                                    } else {
                                        PlayerUtils.giveItem(e, player);
                                    }
                                }
                            }

                        } else {
                            PlayerUtils.giveItem(e, player);
                        }
                        backpacks.tryAutoPickup(player, stack);
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
