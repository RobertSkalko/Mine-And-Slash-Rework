package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.database.data.profession.ICreativeTabTiered;
import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.LeveledItemBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.NameBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.RarityBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.ProfessionDropSourceBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.HealthUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SlashPotionItem extends AutoItem implements ICreativeTabTiered {

    String rar;
    Type type;

    public SlashPotionItem(String rar, Type type) {
        super(new Properties().stacksTo(64));
        this.rar = rar;
        this.type = type;
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(rar) + " " + type.name + " Potion";
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public Item getThis() {
        return this;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        int num = (int) this.getHealPercent(pStack);
        pTooltipComponents.clear();
        pTooltipComponents.addAll(new ExileTooltips()
                .accept(new NameBlock(pStack.getHoverName()))
                .accept(new RarityBlock(getRarity()))
                .accept(new ProfessionDropSourceBlock(Professions.ALCHEMY))
                .accept(new UsageBlock(Collections.singletonList(Itemtips.Restores.locName(Component.literal(num + "%").withStyle(ChatFormatting.GREEN), this.type.name).withStyle(ChatFormatting.GRAY))))
                .accept(new LeveledItemBlock(pStack))
                .accept(new UsageBlock(Collections.singletonList(Words.COOLDOWN.locName(Component.literal(getCooldownTicks() / 20 + "").withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD))))
                .release());

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack stack = p.getItemInHand(pUsedHand);


        if (!pLevel.isClientSide) {

            if (type == Type.HP) {
                EventBuilder.ofRestore(p, p, ResourceType.health, RestoreType.potion, HealthUtils.getMaxHealth(p) * getHealPercent(stack) / 100F).build().Activate();
                EventBuilder.ofRestore(p, p, ResourceType.magic_shield, RestoreType.potion, Load.Unit(p).getUnit().magicShieldData().getValue() * getHealPercent(stack) / 100F).build().Activate();
            } else {
                EventBuilder.ofRestore(p, p, ResourceType.mana, RestoreType.potion, Load.Unit(p).getUnit().manaData().getValue() * getHealPercent(stack) / 100F).build().Activate();
                EventBuilder.ofRestore(p, p, ResourceType.energy, RestoreType.potion, Load.Unit(p).getUnit().energyData().getValue() * getHealPercent(stack) / 100F).build().Activate();
            }


            for (SlashPotionItem c : getCooldownItems()) {
                p.getCooldowns().addCooldown(c, getCooldownTicks());
            }

            SoundUtils.playSound(p, SoundEvents.GENERIC_DRINK);
            stack.shrink(1);
        }


        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

    public int getCooldownTicks() {
        return 20 * 30;
    }

    public List<SlashPotionItem> getCooldownItems() {

        if (type == Type.HP) {
            return RarityItems.HEALTH_POTIONS.values().stream().map(x -> x.get()).collect(Collectors.toList());
        } else {
            return RarityItems.RESOURCE_POTIONS.values().stream().map(x -> x.get()).collect(Collectors.toList());

        }
    }

    public float getHealPercent(ItemStack stack) {
        var r = getRarity();
        SkillItemTier tier = LeveledItem.getTier(stack);
        return 5 + (0.25F * r.stat_percents.max * tier.statMulti); // todo maybe make separate value
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public enum Type {
        HP("Health", Items.POTATO),
        MANA("Mana", Items.CARROT);
        String name;

        Item craftItem;

        Type(String name, Item craftItem) {
            this.name = name;
            this.craftItem = craftItem;
        }
    }
}
