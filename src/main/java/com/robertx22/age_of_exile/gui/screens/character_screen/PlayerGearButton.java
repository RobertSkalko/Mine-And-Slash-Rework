package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.gui.ItemSlotButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerGearButton extends ImageButton {

    public static int xSize = 99;
    public static int ySize = 80;

    static ResourceLocation TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/player_gear.png");
    BaseScreen screen;
    Player player;

    public PlayerGearButton(Player player, BaseScreen screen, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, ySize + 1, TEX, (button) -> {
        });
        this.player = player;
        this.screen = screen;

        // todo why is this broken

/*
        addItemButton(MyCurioUtils.get(RefCurio.NECKLACE, player, 0), this.getX() + 0, this.getY() + 4);
        addItemButton(MyCurioUtils.get(RefCurio.RING, player, 0), this.getX() + 0, this.getY() + 22);
        addItemButton(MyCurioUtils.get(RefCurio.RING, player, 1), this.getX() + 0, this.getY() + 40);

        addItemButton(player.getItemBySlot(EquipmentSlot.HEAD), this.getX() + 81, this.getY() + 4);
        addItemButton(player.getItemBySlot(EquipmentSlot.CHEST), this.getX() + 81, this.getY() + 22);
        addItemButton(player.getItemBySlot(EquipmentSlot.LEGS), this.getX() + 81, this.getY() + 40);
        addItemButton(player.getItemBySlot(EquipmentSlot.FEET), this.getX() + 81, this.getY() + 58);


 */
        // addItemButton(player.getEquippedStack(EquipmentSlot.MAINHAND), 58, 69);
        //addItemButton(player.getEquippedStack(EquipmentSlot.OFFHAND), 179, 69);

    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);

        MutableComponent str = Gui.MAINHUB_LEVEL.locName().append(String.valueOf(Load.Unit(player).getLevel()));

        Minecraft mc = Minecraft.getInstance();


        // player 3d view
        InventoryScreen.renderEntityInInventoryFollowsMouse(gui, this.getX() + 50, this.getY() + 77, 30, (float) (getX() + 51) - x, (float) (getY() + 75 - 50) - y, player);

        gui.drawString(mc.font, str, this.getX() + xSize / 2 - mc.font.width(str) / 2, this.getY() + 6, ChatFormatting.YELLOW.getColor());


    }

    private void addItemButton(ItemStack stack, int x, int y) {
        screen.publicAddButton(new ItemSlotButton(stack, this.getX() + x, this.getY() + y));
    }

}
