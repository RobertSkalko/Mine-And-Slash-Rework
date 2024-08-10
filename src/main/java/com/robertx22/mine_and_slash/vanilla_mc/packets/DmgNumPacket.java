package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.DamageParticleAdder;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class DmgNumPacket extends MyPacket<DmgNumPacket> {

    public String string;
    public int id;
    public boolean iscrit = false;
    public ChatFormatting format = ChatFormatting.RED;

    public DmgNumPacket() {

    }


    public DmgNumPacket(LivingEntity entity, String str, boolean iscrit, ChatFormatting format) {
        string = str;
        this.id = entity.getId();
        this.iscrit = iscrit;
        this.format = format;


        // todo this is horrible but i'll need to wait for damage indicator mods to be ported

        ItemEntity en = new ItemEntity(entity.level(), entity.getX(), entity.getEyeY(), entity.getZ(), new ItemStack(SlashItems.INVISIBLE_ICON.get(), 1));
        en.setNeverPickUp();
        en.setCustomName(Component.literal(format + this.string));
        en.setInvisible(true);
        en.setCustomNameVisible(true);
        en.lifespan = 20;

        entity.level().addFreshEntity(en);
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "dmgnum");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        string = tag.readUtf(500);
        id = tag.readInt();
        this.iscrit = tag.readBoolean();
        this.format = ChatFormatting.getByName(tag.readUtf(100));

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(string);
        tag.writeInt(id);
        tag.writeBoolean(iscrit);
        tag.writeUtf(format.getName());

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if (ClientConfigs.getConfig().ENABLE_FLOATING_DMG.get()) {
            DamageParticleAdder.displayParticle(ctx.getPlayer().level().getEntity(id), this);
        }
    }

    @Override
    public MyPacket<DmgNumPacket> newInstance() {
        return new DmgNumPacket();
    }
}
