package com.robertx22.mine_and_slash.a_libraries.jei;

import com.robertx22.mine_and_slash.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class LockRecipePacket extends MyPacket<LockRecipePacket> {

    String recipeId = "";

    public LockRecipePacket(String recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("lock_jei_recipe");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        // todo
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Player p = ctx.getPlayer();

        if (p.containerMenu instanceof CraftingStationMenu menu) {
            if (menu.be != null) {

                var rec = ExileDB.Recipes().get(recipeId);

                menu.be.tryInputRecipe(rec, p);

            }
        }

    }

    @Override
    public MyPacket<LockRecipePacket> newInstance() {
        return new LockRecipePacket("");
    }
}
