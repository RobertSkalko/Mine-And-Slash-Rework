package com.robertx22.age_of_exile.aoe_data.database.boss_spell;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class BossSpell implements ExileRegistry<BossSpell> {


    public abstract int castTicks();

    public abstract void onTick(LivingEntity en, int tick);

    public abstract void onStartOverride(LivingEntity en);

    public abstract void onFinish(LivingEntity en);


    public final void onStart(LivingEntity en) {
        this.onStartOverride(en);
        speak(getCastSpeech(), en);
    }

    public abstract String getCastSpeech();

    // todo localize later
    private void speak(String text, LivingEntity en) {
        for (Player p : EntityFinder.start(en, Player.class, en.blockPosition()).radius(50).searchFor(AllyOrEnemy.all).build()) {
            p.sendSystemMessage(Component.literal(text).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        }
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.BOSS_SPELL;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
