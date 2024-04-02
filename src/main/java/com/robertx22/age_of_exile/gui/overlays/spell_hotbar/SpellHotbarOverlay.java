package com.robertx22.age_of_exile.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.overlays.EffectsOverlay;
import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.Locale;

public class SpellHotbarOverlay {

    private static final ResourceLocation HOTBAR_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/hotbar.png"
    );
    private static final ResourceLocation COOLDOWN_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/cooldown.png"
    );
    private static final ResourceLocation SPELL_READY_TEX = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/spell_ready.png"
    );
    private static final ResourceLocation SPELl_NO_MANA = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/no_mana.png"
    );

    private static final ResourceLocation SPELL_ON_COOLDOWN = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/on_cooldown.png"
    );

    private static final ResourceLocation CHARGE = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/charge_icon.png"
    );
    private static final ResourceLocation KEY_BG = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/keybind_bg.png"
    );
    private static final ResourceLocation MOD_BG = new ResourceLocation(SlashRef.MODID,
            "textures/gui/spells/modbg.png"
    );

    int CHARGE_SIZE = 9;

    static int WIDTH = 22;
    static int HEIGHT = 162;

    Minecraft mc = Minecraft.getInstance();


    public void onHudRender(GuiGraphics gui) {

        try {
            if (mc.options.renderDebug) {
                return;
            }
            if (mc.player.isSpectator()) {
                return;
            }
            if (ChatUtils.isChatOpen()) {
                return;
            }
            if (Load.player(mc.player) == null) {
                return;
            }


            RenderSystem.enableBlend(); // enables transparency

            int x = 0;
            int y = mc.getWindow()
                    .getGuiScaledHeight() / 2 - HEIGHT / 2;

            renderHotbar(gui, x, y);
            //renderSpellsOnHotbar(matrix, x, y);

            for (int i = 0; i < 8; i++) {

                int place = i;


                renderCurrentSpell(place, i, gui);

            }

            RenderSystem.disableBlend(); // enables transparency

            if (ClientConfigs.getConfig().GUI_POSITION.get() != GuiPosition.TOP_LEFT) {
                EffectsOverlay.render(3, y + 85, mc.player, gui, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderCurrentSpell(int place, int num, GuiGraphics gui) {

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        boolean render = true;

        Spell spell = null;
        try {
            spell = Load.player(mc.player).getSkillGemInventory().getHotbarGem(place).getSpell();

            if (spell == null) {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            render = false;
        }

        if (!render) {
            return;
        }

        int x = 3;
        int y = mc.getWindow()
                .getGuiScaledHeight() / 2 - HEIGHT / 2 + 3;

        y += num * 20;

        try {
            int xs = (int) (x);
            int ys = (int) (y);

            var tex = new ResourceLocation("");
            if (Load.Unit(mc.player)
                    .getCooldowns()
                    .getCooldownTicks(spell.GUID()) > 1) {
                tex = SPELL_ON_COOLDOWN;
            } else {
                tex = SPELL_READY_TEX;
            }

            gui.blit(tex, xs, ys, 0, 0, 16, 16, 16, 16);

            if (spell != null) {
                gui.blit(spell.getIconLoc(), xs, ys, 0, 0, 16, 16, 16, 16);

                if (spell.config.charges > 0) {
                    int charges = Load.player(mc.player)
                            .spellCastingData.charges.getCharges(spell.config.charge_name);

                    if (charges == 0) {
                        float needed = (float) spell.config.charge_regen;
                        float currentticks = (float) Load.player(mc.player)
                                .spellCastingData.charges.getCurrentTicksChargingOf(spell.config.charge_name);

                        float ticksleft = needed - currentticks;

                        float percent = ticksleft / needed;
                        percent = Mth.clamp(percent, 0, 1F);
                        drawCooldown(gui, percent, xs, ys);

                    }

                    int chargex = x + 21;

                    for (int i = 0; i < charges; i++) {
                        gui.blit(CHARGE, chargex, y + 5, 0, 0, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE, CHARGE_SIZE);
                        chargex += CHARGE_SIZE + 1;
                    }

                } else {

                    CooldownsData cds = Load.Unit(mc.player)
                            .getCooldowns();

                    float percent = (float) cds.getCooldownTicks(spell.GUID()) / (float) cds.getNeededTicks(spell.GUID());
                    if (cds.getCooldownTicks(spell.GUID()) > 1) {
                        percent = Mth.clamp(percent, 0, 1F);
                        drawCooldown(gui, percent, xs, ys);
                    }

                    int cdsec = cds.getCooldownTicks(spell.GUID()) / 20;
                    if (cdsec > 1) {
                        String stext = cdsec + "s";
                        GuiUtils.renderScaledText(gui, xs + 27, ys + 10, 0.75F, stext, ChatFormatting.YELLOW);
                    }
                }


                int xkey = xs + 15;
                int ykey = y + 14;
                int bgsize = 10;


                RenderSystem.enableBlend(); // enables transparency

                float alpha = 0.75f;
                gui.setColor(alpha, alpha, alpha, alpha);
                gui.blit(KEY_BG, xkey - 6, ykey - 6, 0, 0, bgsize, bgsize, bgsize, bgsize);
                gui.setColor(1.0F, 1.0F, 1.0F, 1);


                String txt = CLOC.translate(KeybindsRegister.getSpellHotbar(place).key.getKey().getDisplayName()).toUpperCase(Locale.ROOT);
                txt = txt.substring(0, 1);
                GuiUtils.renderScaledText(gui, xkey - 1, ykey, 1, txt, ChatFormatting.GREEN);

                var key = KeybindsRegister.getSpellHotbar(place);
                if (key.key.getKeyModifier() != KeyModifier.NONE) {

                    RenderSystem.enableBlend(); // enables transparency
                    gui.setColor(alpha, alpha, alpha, alpha);
                    gui.blit(MOD_BG, xkey - 18, ykey - 6, 0, 0, 13, bgsize, 13, bgsize);
                    gui.setColor(1.0F, 1.0F, 1.0F, 1F);

                    String modtext = KeybindsRegister.getSpellHotbar(place).key.getKeyModifier().toString().substring(0, 3);
                    GuiUtils.renderScaledText(gui, xkey - 11, ykey, 0.6F, modtext, ChatFormatting.YELLOW);
                }


                RenderSystem.disableBlend(); // enables transparency

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void drawCooldown(GuiGraphics gui, float percent, int x, int y) {

        gui.blit(COOLDOWN_TEX, x, y, 0, 0, 16, (int) (16 * percent), 16, 16);
    }

    private void renderHotbar(GuiGraphics gui, int x, int y) {

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(HOTBAR_TEX, x, y, 0, 0, WIDTH, HEIGHT);

    }

}