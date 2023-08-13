package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StaffWeapon extends ModWeapon implements IAutoModel {
    VanillaMaterial mat;

    public StaffWeapon(VanillaMaterial mat) {
        super(mat.toolmat, new Properties().durability(250 + mat.toolmat.getUses())
                , WeaponTypes.staff);
        this.mat = mat;

        this.attackSpeed = -3;

    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.handheld(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        try {

            // todo make this save right click spell on the player, not the item

            if (!world.isClientSide) {

                /*
                GearItemData gear = StackSaving.GEARS.loadFrom(stack);

                if (gear != null && gear.hasSpell()) {
                    Spell spell = gear.getSpell();

                    if (TellServerToCastSpellPacket.tryCastSpell(player, spell)) {
                        player.swing(hand);
                        return ActionResult.success(stack);
                    }

                }

                 */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return InteractionResultHolder.pass(stack);

    }
}
