package com.robertx22.age_of_exile.database.data.profession.screen;

import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.packets.CraftPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class CraftButton extends ImageButton {

    public static int XS = 34;
    public static int YS = 34;

    Minecraft mc = Minecraft.getInstance();
    ProfessionRecipe rec;

    public CraftButton(ProfessionRecipe recipe, BlockPos pos, int xPos, int yPos) {
        super(xPos, yPos, XS, YS, 0, 0, YS, SlashRef.guiId("craftbutton"), (button) -> {
            Packets.sendToServer(new CraftPacket(recipe.GUID(), pos));
        });
        this.rec = recipe;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        super.renderWidget(gui, mouseX, mouseY, delta);
        ItemStack stack = rec.toResultStackForJei();
        RenderUtils.renderStack(gui, stack, getX(), getY());
    }

    public void setModTooltip() {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(rec.toResultStackForJei().getTooltipLines(ClientOnly.getPlayer(), TooltipFlag.NORMAL))));
    }

}