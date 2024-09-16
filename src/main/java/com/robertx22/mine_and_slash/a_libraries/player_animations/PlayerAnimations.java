package com.robertx22.mine_and_slash.a_libraries.player_animations;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

// need to test in multiplayer, it probably doesn't work yet. need to send packets to nearby players probably
public class PlayerAnimations {
    public static HashMap<UUID, KeyframeAnimationPlayer> castingAnimationPlayerLookup = new HashMap<>();

    public enum CastEnum {
        CAST_START, CAST_FINISH
    }


    public static void initClient() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                SpellAnimations.ANIMATION_RESOURCE,
                40,
                (player) -> {
                    var animation = new ModifierLayer<>();

                    animation.addModifierLast(new AdjustmentModifier((partName) -> {
                        switch (partName) {
                            case "rightArm", "leftArm" -> {
                                return Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(
                                        player.getXRot() * Mth.DEG_TO_RAD,
                                        Mth.DEG_TO_RAD * (player.yHeadRot - player.yBodyRot),
                                        0), Vec3f.ZERO));

                            }
                            default -> {
                                return Optional.empty();
                            }
                        }
                    }));
                    // this makes the casting hand be the one with the weapon
                    animation.addModifierLast(new MirrorModifier() {
                        @Override
                        public boolean isEnabled() {
                            var gear = StackSaving.GEARS.loadFrom(player.getMainHandItem());
                            if (gear != null && gear.isWeapon()) {
                                return false;
                            }
                            return true;
                        }
                    });
                    return animation;
                });

    }

    // todo need to cleanup this lol
    public static void onSpellCast(Player player, Spell spell, CastEnum c) {

        var x = spell.getAnimation(c);

        if (x != null) {
            if (c == CastEnum.CAST_START) {
                animatePlayerStart(player, x.getLocation());
            } else {
                handleClientBoundOnCastFinished(x.getLocation(), spell, false);
            }
        }
    }

    private static void animatePlayerStart(Player player, ResourceLocation resourceLocation) {


        var keyframeAnimation = PlayerAnimationRegistry.getAnimation(resourceLocation);
        if (keyframeAnimation != null) {

            AnimationHolder config = SpellAnimations.MAP.getOrDefault(resourceLocation.getPath(), AnimationHolder.none());

            //noinspection unchecked
            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player).get(SpellAnimations.ANIMATION_RESOURCE);
            if (animation != null) {

                var castingAnimationPlayer = new KeyframeAnimationPlayer(keyframeAnimation);

                castingAnimationPlayerLookup.put(player.getUUID(), castingAnimationPlayer);
                var armsFlag = true; //SHOW_FIRST_PERSON_ARMS.get();
                var itemsFlag = true;//SHOW_FIRST_PERSON_ITEMS.get();

                if (armsFlag || itemsFlag) {
                    castingAnimationPlayer.setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL);
                    castingAnimationPlayer.setFirstPersonConfiguration(new FirstPersonConfiguration(armsFlag, armsFlag, true, !config.hideOffhand));
                } else {
                    //castingAnimationPlayer.setFirstPersonMode(FirstPersonMode.DISABLED);
                }
                animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(2, Ease.INOUTSINE), castingAnimationPlayer, true);
            }
        }
    }

    public static void handleClientBoundOnCastFinished(ResourceLocation finishAnimation, Spell spell, boolean cancelled) {
        var player = Minecraft.getInstance().player;

        if (finishAnimation != null && !cancelled) {
            animatePlayerStart(player, finishAnimation);
        }
        /*else if (finishAnimation != AnimationHolder.pass() || cancelled) {
            var animationPlayer = castingAnimationPlayerLookup.getOrDefault(player.getUUID(), null);
            if (animationPlayer != null) {
                animationPlayer.stop();
            }
        }

         */
    }
}
