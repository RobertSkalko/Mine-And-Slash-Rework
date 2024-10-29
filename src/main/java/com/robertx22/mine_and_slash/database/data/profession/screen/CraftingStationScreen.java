package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import com.robertx22.mine_and_slash.database.data.profession.StationSyncData;
import com.robertx22.mine_and_slash.database.data.profession.items.ProfTierMatItem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

// todo sepearate screens for each station
public abstract class CraftingStationScreen extends AbstractContainerScreen<CraftingStationMenu> {
    public ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(SlashRef.MODID, "textures/gui/crafting_table2.png");


    Profession prof;

    public PrimaryMatInfoButton.InfoData primaryTier;

    public PrimaryMatInfoButton.InfoData primaryRar = new PrimaryMatInfoButton.InfoData(
            Words.PRIMARY_RARITY_MAT,
            RarityItems.RARITY_STONE.get(IRarity.COMMON_ID).get(),
            RarityItems.RARITY_STONE.values().stream().map(x -> (Item) x.get()).toList()
    );

    public CraftingStationScreen(String prof, CraftingStationMenu pMenu, Inventory pPlayerInventory, Component txt) {
        super(pMenu, pPlayerInventory, ExileDB.Professions().get(prof).locName());
        this.prof = ExileDB.Professions().get(prof);
        this.imageWidth = 176;
        this.imageHeight = 182;

        this.titleLabelX = this.getGuiLeft() + imageWidth / 2;
        this.titleLabelY = -15;
    }

    int ticks = 0;

    @Override
    protected void containerTick() {
        super.containerTick();

        ticks++;

        if (ticks % 20 == 0) {
            refreshPossibleRecipes();
        }
    }

    public StationSyncData getSyncedData() {
        return StationSyncData.SYNCED_DATA;
    }


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new LockButton(leftPos + 79, topPos + 33, this));
        this.addRenderableWidget(new CraftButton(leftPos + 79, topPos + 51, this));

        if (this.primaryTier != null) {
            this.addRenderableWidget(new PrimaryMatInfoButton(primaryTier, leftPos + 15, topPos + 75));
            this.addRenderableWidget(new PrimaryMatInfoButton(primaryRar, leftPos + 35, topPos + 75));
        }

        this.refreshPossibleRecipes();

        refreshRequiredMats();

    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    String possible = "";

    public void refreshRequiredMats() {
        var data = getSyncedData();

        if (data.recipe_locked && !data.recipe.isEmpty()) {
            var recipe = ExileDB.Recipes().get(data.recipe);
            refreshRequiredMats(recipe);
        }
    }

    public void refreshRequiredMats(ProfessionRecipe recipe) {


        this.children().removeIf(x -> x instanceof ItemButton);
        this.renderables.removeIf(x -> x instanceof ItemButton);

        int xoff = 0;
        int yoff = 0;

        int spacing = 18;

        for (ItemStack stack : recipe.getMaterials()) {
            this.addRenderableWidget(new ItemButton(stack, leftPos + 64 + xoff, topPos + 75 + yoff));
            xoff += spacing;
        }

    }

    public void refreshPossibleRecipes() {


        var all = getPossibleRecipes();

        String str = "";
        for (String s : all.stream().sorted(Comparator.comparing(x -> x.GUID())).map(x -> x.GUID()).collect(Collectors.toList())) {
            str += s;
        }

        if (!possible.equals(str)) {
            possible = str;

            this.children().removeIf(x -> x instanceof RecipeButton);
            this.renderables.removeIf(x -> x instanceof RecipeButton);

            int xoff = 0;
            int yoff = 0;

            int spacing = RecipeButton.XS + 2;

            for (ProfessionRecipe recipe : all) {
                this.addRenderableWidget(new RecipeButton(this, recipe, leftPos + imageWidth + xoff, topPos + yoff));

                xoff += spacing;
                if (xoff > (spacing * 2)) {
                    xoff = 0;
                    yoff += spacing;
                }
            }
        }

    }

    public List<ProfessionRecipe> getPossibleRecipes() {


        Item v1 = null;
        Item v2 = null;
        for (Slot slot : this.menu.matslots) {
            if (slot.getItem().getItem() instanceof RarityStoneItem r) {
                v1 = r;
            }
            if (slot.getItem().getItem() instanceof ProfTierMatItem mat) {
                v2 = mat;
            }
        }

        var recipes = new HashSet<>(ExileDB.Recipes().getFilterWrapped(x -> x.profession.equals(prof.GUID())).list);

        if (v1 != null && v2 != null) {
            for (ProfessionRecipe rec : ExileDB.Recipes().getFilterWrapped(x -> x.profession.equals(prof.GUID())).list) {
                if (!rec.isMadeWithPrimaryMats(v1, v2)) {
                    recipes.removeIf(x -> x.GUID().equals(rec.GUID()));
                }
            }
        } else {
            recipes = new HashSet<>();
        }
        recipes.addAll(ExileDB.Recipes().getFilterWrapped(x -> x.profession.equals(prof.GUID()) && x.canCraft(menu.getItems())).list);
        return recipes.stream().toList();

    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX - font.width(title) / 2, this.titleLabelY, ChatFormatting.YELLOW.getColor(), false);
        //pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, ChatFormatting.WHITE.getColor(), false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(BACKGROUND_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

    }

}
