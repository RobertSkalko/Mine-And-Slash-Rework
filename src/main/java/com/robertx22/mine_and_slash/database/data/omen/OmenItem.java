package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.IOmen;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.*;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class OmenItem extends BaseBaublesItem implements IOmen {

    VanillaMaterial mat;


    public OmenItem(VanillaMaterial mat) {
        super(new Properties().durability(500 + mat.armormat.getDurabilityForType(ArmorItem.Type.CHESTPLATE) * 2)
                , "Omen");
        this.mat = mat;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        try {


            var omen = StackSaving.OMEN.loadFrom(stack);
            if (omen == null) {
                return;
            }

            list.clear();


            var set = new OmenSet(omen);

            int lvl = omen.lvl;

            Player p = ClientOnly.getPlayer();

            if (p == null) {
                return;
            }


            // todo note somewhere the weapon isnt included in omen counting

            list.addAll(new ExileTooltips()
                    .accept(new StatBlock() {
                        @Override
                        public List<? extends Component> getAvailableComponents() {
                            return set.getTooltip(p);
                        }
                    })
                    .accept(new NameBlock(omen.getOmen().locName().withStyle(ChatFormatting.LIGHT_PURPLE)))
                    .accept(new RequirementBlock(lvl))
                    .accept(new RarityBlock(omen.getRarity()))
                    .accept(new OperationTipBlock().setShift().setAlt())
                    .accept(new UsageBlock(omen.getReqTooltip()))

                    .release());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
