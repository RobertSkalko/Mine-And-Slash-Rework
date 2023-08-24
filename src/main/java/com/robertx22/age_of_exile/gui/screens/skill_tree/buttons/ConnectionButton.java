package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class ConnectionButton extends ImageButton {

    public static int SIZE = 6;

    public static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/lines.png");

    public PointData one;
    public PointData two;


    public ConnectionButton(SkillTreeScreen screen, TalentTree school, PointData one, PointData two, int x, int y) {
        super(x, y, SIZE, SIZE, 0, 0, 0, ID, (action) -> {
        });
        this.one = one;
        this.two = two;

        connection = Load.player(ClientOnly.getPlayer()).talents.getConnection(school, one, two);
    }

    public Perk.Connection connection;

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        // do nothing. use the custom method
    }


    public void renderButtonForReal(GuiGraphics gui) {

        if (connection == Perk.Connection.POSSIBLE) {
            gui.blit(ConnectionButton.ID, this.getX(), this.getY(), 0, 0, 6, 6);
        } else if (connection == Perk.Connection.LINKED) {
            gui.blit(ConnectionButton.ID, this.getX(), this.getY(), 6, 0, 6, 6);
        } else if (connection == Perk.Connection.BLOCKED) {
            gui.blit(ConnectionButton.ID, this.getX(), this.getY(), 12, 0, 6, 6);
        }


    }

}