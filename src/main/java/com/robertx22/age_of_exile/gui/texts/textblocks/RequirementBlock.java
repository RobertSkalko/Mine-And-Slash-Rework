package com.robertx22.age_of_exile.gui.texts.textblocks;

import com.google.common.collect.ImmutableList;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement.CHECK_YES_ICON;
import static com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement.NO_ICON;

public class RequirementBlock extends AbstractTextBlock {
    private EntityData playerData = null;

    @Nullable
    public StatRequirement statRequirement;
    //Is the item level means the level req? idk but after search the usage of TooltipUtils.addRequirements, the lvl member in gear and map are use as lvl req.
    @Nullable
    public Integer levelRequirement;

    @Nullable
    public List<? extends Component> customComponents;

    public RequirementBlock() {
        super();
    }

    public RequirementBlock(Player player) {
        super();
        this.playerData = Load.Unit(player);
    }


    public RequirementBlock(@NotNull Integer levelRequirement) {
        super();
        this.levelRequirement = levelRequirement;
    }

    public RequirementBlock(@NotNull List<? extends Component> customComponents) {
        this.customComponents = customComponents.stream()
                .map(x -> Component.literal("").append(Component.literal("\u003F" + " ").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)).append((x.copy().withStyle(ChatFormatting.GRAY)))).toList();
    }

    public RequirementBlock setStatRequirement(@Nullable StatRequirement statRequirement) {
        this.statRequirement = statRequirement;
        return this;
    }

    public RequirementBlock setLevelRequirement(@Nullable Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        ImmutableList.Builder<Component> builder = ImmutableList.builder();
        if (this.playerData == null){
            this.playerData = Load.Unit(ClientOnly.getPlayer());
        }

        if (levelRequirement != null) {
            boolean ifMetLevel = this.playerData.getLevel() >= levelRequirement;

            if (ifMetLevel) {
                builder.add(Component.literal("").append(Component.literal(CHECK_YES_ICON).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD)).append(Component.literal(" ")).append(Itemtips.LEVEL_REQ.locName(levelRequirement).withStyle().withStyle(ChatFormatting.GRAY)));
            } else {
                builder.add(Component.literal("").append(Component.literal(NO_ICON).withStyle(ChatFormatting.RED, ChatFormatting.BOLD)).append(Component.literal(" ")).append(Itemtips.LEVEL_REQ.locName(levelRequirement).withStyle().withStyle(ChatFormatting.DARK_GRAY)));
            }

        }

        if (statRequirement != null) {

            Optional.of(statRequirement)
                    .map(req -> req.GetTooltipString(this.levelRequirement, this.playerData))
                    .ifPresent(x -> x.forEach(builder::add));

        }

        if (this.customComponents != null) {
            builder.addAll(this.customComponents);
        }

        return builder.build();


    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.REQUIREMENT;
    }
}
