package com.robertx22.age_of_exile.capability.player.container;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
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
        this(new RPGPlayerData(null), pContainerId, pContainer);
    }


    public SkillGemsMenu(RPGPlayerData rpg, int pContainerId, Container pContainer) {
        super(SlashContainers.SKILL_GEMS.get(), pContainerId);


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

        var data = rpg.getSkillGemInventory();
        int gems = 0;

        // todo gem slots

        for (int i = 0; i < GemInventoryHelper.TOTAL_AURAS; i++) {
            this.addSlot(new Slot(data.getAuraInv(), i, 36 + (i * 18), 148));
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