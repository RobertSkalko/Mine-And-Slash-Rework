package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.database.data.profession.Crafting_State;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.CraftPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CraftButton extends ImageButton {

    public static int XS = 18;
    public static int YS = 19;
    public static CraftingStationScreen pbe;

    Minecraft mc = Minecraft.getInstance();

    public CraftButton(int xPos, int yPos, CraftingStationScreen be) {
        super(xPos, yPos, XS, YS, 0, 0, YS, SlashRef.guiId("craftbutton"), (button) -> {
            Packets.sendToServer(new CraftPacket(be.getSyncedData().getBlockPos()));
        });
        pbe = be;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        ResourceLocation tex = SlashRef.guiId("craftbutton");
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), 0, (pbe.getSyncedData().craftingState == Crafting_State.ACTIVE || pbe.getSyncedData().craftingState == Crafting_State.IDLE) ? 0 : 19, 18, 19);
    }

    public void setModTooltip() {
        List<MutableComponent> list = new ArrayList<>();

        var rec = pbe.getSyncedData().recipe;
        int lvl = 1;
        int owner = 1;

        Profession prof = null;
        if (!rec.isEmpty()) {
            var recipe = ExileDB.Recipes().get(rec);
            lvl = recipe.getLevelRequirement();
            owner = Load.player(mc.player).professions.getLevel(recipe.profession);
            prof = ExileDB.Professions().get(recipe.profession);
        }

        if (lvl > owner) {
            list.add(Chats.PROF_RECIPE_LEVEL_NOT_ENOUGH.locName(prof.locName(), lvl, owner).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        } else {
            if (pbe.getSyncedData().craftingState == Crafting_State.ACTIVE || pbe.getSyncedData().craftingState == Crafting_State.IDLE)
                list.add(Component.literal("Stop Crafting").withStyle(ChatFormatting.DARK_AQUA));
            else
                list.add(Component.literal("Start Crafting").withStyle(ChatFormatting.DARK_AQUA));
        }


        this.setTooltip(Tooltip.create(TooltipUtils.joinMutableComps(list.listIterator(), Component.literal("\n"))));
    }

}