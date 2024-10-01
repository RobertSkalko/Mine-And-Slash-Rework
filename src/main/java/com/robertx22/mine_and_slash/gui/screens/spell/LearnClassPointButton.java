package com.robertx22.mine_and_slash.gui.screens.spell;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.gui.TextUtils;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.vanilla_mc.packets.AllocateClassPointPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;


public class LearnClassPointButton extends ImageButton {

    static ResourceLocation SPELL_SLOT = SlashRef.guiId("spells/slots/spell");
    static ResourceLocation PASSIVE = SlashRef.guiId("spells/slots/passive");
    static ResourceLocation OVERLAY = SlashRef.guiId("spells/slots/overlay");

    public static int BUTTON_SIZE_X = 18;
    public static int BUTTON_SIZE_Y = 18;

    Minecraft mc = Minecraft.getInstance();
    SpellSchoolScreen screen;

    Perk perk;


    public LearnClassPointButton(SpellSchoolScreen screen, Perk perk, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SPELL_SLOT, (button) -> {

            //    Packets.sendToServer(new AllocateClassPointPacket(screen.currentSchool(), perk, AllocateClassPointPacket.ACTION.ALLOCATE));

        });
        this.screen = screen;
        this.perk = perk;

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (this.active && this.visible) {
            boolean bl = this.clicked(mouseX, mouseY);
            if (bl) {
                this.playDownSound(Minecraft.getInstance().getSoundManager());
                if (button == 0) {
                    Packets.sendToServer(new AllocateClassPointPacket(screen.currentSchool(), perk, AllocateClassPointPacket.ACTION.ALLOCATE));
                }
                if (button == 1) {
                    Packets.sendToServer(new AllocateClassPointPacket(screen.currentSchool(), perk, AllocateClassPointPacket.ACTION.REMOVE));
                }
                this.onClick(mouseX, mouseY);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) {
            setModTooltip();
        }
        Minecraft mc = Minecraft.getInstance();

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (perk.isSpell()) {
            gui.blit(SPELL_SLOT, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);
        } else {
            gui.blit(PASSIVE, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);
        }

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(perk.getIcon(), getX() + 1, getY() + 1, 16, 16, 16, 16, 16, 16);
        gui.blit(OVERLAY, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);


        int currentlvl = Load.player(mc.player).ascClass.getLevel(perk.GUID());

        ChatFormatting color = ChatFormatting.GRAY;

        int maxlvl = perk.getMaxLevel();
        if (this.perk.isSpell()) {
            int lvl = perk.getSpell().getLevelOf(mc.player);
            if (lvl > currentlvl) {
                currentlvl = lvl;
            }
        }
        float perc = ((float) currentlvl / maxlvl);

        if (currentlvl < 1) {
            color = ChatFormatting.GRAY;
        } else {

            if (perc > 1f) {
                color = ChatFormatting.DARK_PURPLE;
            } else if (perc > 0.7f) {
                color = ChatFormatting.GOLD;
            } else if (perc > 0.5f) {
                color = ChatFormatting.LIGHT_PURPLE;
            } else if (perc > 0.3f) {
                color = ChatFormatting.AQUA;
            } else {
                color = ChatFormatting.GREEN;
            }
        }


        String lvltext = currentlvl + "/" + maxlvl;
        TextUtils.renderText(gui, 0.8F, lvltext, getX() + BUTTON_SIZE_X / 2, (int) (getY() + BUTTON_SIZE_Y * 0.85F), color);

        super.render(gui, mouseX, mouseY, delta);

    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        // this.renderTexture(pGuiGraphics, this.resourceLocation, this.getX(), this.getY(), this.xTexStart, this.yTexStart, this.yDiffTex, this.width, this.height, this.textureWidth, this.textureHeight);
    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();

        StatRangeInfo info = new StatRangeInfo(ModRange.hide());

        tooltip.addAll(perk.GetTooltipString(info));


        int reqlvl = screen.currentSchool().getLevelNeededToAllocate(screen.currentSchool().perks.get(perk.GUID()));
        tooltip.add(Chats.REQ_LVL.locName(reqlvl).withStyle(ChatFormatting.RED));


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));


    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }


}
