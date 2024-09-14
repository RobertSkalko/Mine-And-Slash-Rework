package com.robertx22.mine_and_slash.capability.player.container;

import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.player.helper.GemInventoryHelper;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SkillGemsMenu extends AbstractContainerMenu {
    private static final int PAYMENT_SLOT = 0;
    private static final int SLOT_COUNT = 1;
    private static final int DATA_COUNT = 3;
    private static final int INV_SLOT_START = 1;
    private static final int INV_SLOT_END = 28;
    private static final int USE_ROW_SLOT_START = 28;
    private static final int USE_ROW_SLOT_END = 37;


    public SkillGemsMenu(int pContainerId, Container pContainer) {
        this(new PlayerData(ClientOnly.getPlayer()), pContainerId, pContainer);
    }

    Player player;

    public SkillGemsMenu(PlayerData rpg, int pContainerId, Container pContainer) {
        super(SlashContainers.SKILL_GEMS.get(), pContainerId);

        this.player = rpg.player;

        //this.addDataSlots(pBeaconData);
        int x = 36;
        int y = 174;

        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(pContainer, l + k * 9 + 9, x + l * 18, y + k * 18));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(pContainer, i1, x + i1 * 18, 232));
        }

        try {
            var data = rpg.getSkillGemInventory();
            int index = 0;


            for (int i = 0; i < GemInventoryHelper.MAX_SKILL_GEMS; i++) {

                int xp = 16 + (i * 25);
                if (i > 3) {
                    xp += 7;
                }
                //  this.addSlot(new GemSlot(SkillGemData.SkillGemType.SKILL, data.getGemsInv(), index, xp, 16));
                index++;

                for (int s = 0; s < GemInventoryHelper.SUPPORT_GEMS_PER_SKILL; s++) {
                    this.addSlot(new GemSlot(i, s, player, SkillGemData.SkillGemType.SUPPORT, data.getGemsInv(), index, xp, 38 + (s * 18)));
                    index++;
                }

            }


            for (int i = 0; i < GemInventoryHelper.TOTAL_AURAS; i++) {
                this.addSlot(new GemSlot(i, 0, player, SkillGemData.SkillGemType.AURA, data.getAuraInv(), i, 36 + (i * 18), 148));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public class GemSlot extends Slot {
        SkillGemData.SkillGemType type;

        Player p;

        int num;
        int slotIndex;

        public GemSlot(int num, int slotindex, Player p, SkillGemData.SkillGemType type, Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
            this.type = type;
            this.p = p;
            this.slotIndex = slotindex;
            this.num = num;
        }

        @Override
        public void setByPlayer(ItemStack pStack) {
            Load.Unit(p).setEquipsChanged();
            super.setByPlayer(pStack);
        }

        public boolean canSocket() {

            if (this.type == SkillGemData.SkillGemType.SUPPORT) {
                var data = Load.player(p).spellCastingData.getSpellData(this.num);

                // todo why is this null
                if (data.getData() == null) {
                    return false;
                }
                int slots = data.getData().getMaxLinks(player).links;
                return slots > this.slotIndex;
            }
            return true;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {

            SkillGemData data = StackSaving.SKILL_GEM.loadFrom(pStack);
            if (player != null) {
                if (data != null && data.canPlayerWear(player)) {
                    if (data.type == type) {
                        if (canSocket()) {
                            return true;
                        }
                    }

                }
            }
            return false;
        }
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}