package com.robertx22.age_of_exile.capability.player.container;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
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
        this(new PlayerData(null), pContainerId, pContainer);
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
                    this.addSlot(new GemSlot(SkillGemData.SkillGemType.SUPPORT, data.getGemsInv(), index, xp, 38 + (s * 18)));
                    index++;
                }

            }


            for (int i = 0; i < GemInventoryHelper.TOTAL_AURAS; i++) {
                this.addSlot(new GemSlot(SkillGemData.SkillGemType.AURA, data.getAuraInv(), i, 36 + (i * 18), 148));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public class GemSlot extends Slot {
        SkillGemData.SkillGemType type;

        public GemSlot(SkillGemData.SkillGemType type, Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
            this.type = type;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            SkillGemData data = StackSaving.SKILL_GEM.loadFrom(pStack);
            if (player != null) {
                if (data != null && data.canPlayerWear(player)) {
                    if (data.type == type) {
                        return true;
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