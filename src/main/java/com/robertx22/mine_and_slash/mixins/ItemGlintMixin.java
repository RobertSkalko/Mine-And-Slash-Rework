package com.robertx22.mine_and_slash.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.capability.player.container.SkillGemsScreen;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.interfaces.IRarityItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class ItemGlintMixin {


    @Inject(method = "renderSlot", at = @At(value = "HEAD"))
    private void drawMyGlint(GuiGraphics gui, Slot slot, CallbackInfo ci) {

        try {
            AbstractContainerScreen screen = (AbstractContainerScreen) (Object) this;


            if (ClientConfigs.getConfig().RENDER_ITEM_RARITY_BACKGROUND.get()) {
                ItemStack stack = slot.getItem();

                GearRarity rar = null;

                var data = ICommonDataItem.load(stack);

                if (data != null) {
                    if (data.getRarity() != null) {
                        rar = data.getRarity();
                    }
                }
                if (rar == null) {
                    if (stack.getItem() instanceof IRarityItem ri) {
                        rar = ri.getItemRarity(stack);
                    }
                }
                if (rar == null) {
                    return;
                }

                RenderSystem.enableBlend();
                gui.setColor(1.0F, 1.0F, 1.0F, ClientConfigs.getConfig().ITEM_RARITY_OPACITY.get().floatValue()); // transparency

                ResourceLocation tex = rar.getGlintTextureFull();

                if (ClientConfigs.getConfig().ITEM_RARITY_BACKGROUND_TYPE.get() == ClientConfigs.GlintType.BORDER) {
                    tex = rar.getGlintTextureBorder();
                }
                if (screen instanceof SkillGemsScreen && data instanceof SkillGemData) {
                    tex = rar.getGlintTextureCircle();
                }

                gui.blit(tex, slot.x, slot.y, 0, 0, 16, 16, 16, 16);
                gui.setColor(1.0F, 1.0F, 1.0F, 1F);
                RenderSystem.disableBlend();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
