package com.robertx22.age_of_exile.saveclasses.map;


import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MapItemData implements ICommonDataItem<GearRarity> {

    public MapItemData() {

    }

    // todo minify these
    public int lvl = 1;
    public int tier = 0;
    public String rar = IRarity.COMMON_ID;
    public String mapUUID = UUID.randomUUID().toString();

    public List<MapAffixData> affixes = new ArrayList<MapAffixData>();


    private static MapItemData empty;

    public static MapItemData empty() {

        if (empty == null) {
            empty = new MapItemData();
            empty.mapUUID = "error";
        }

        return empty;

    }

    public boolean isEmpty() {
        return mapUUID == null || mapUUID.isEmpty() || mapUUID.equals("error");
    }

    @Override
    public float getBonusLootMulti() {
        float multi = 1F;
        float add = 1F;

        return 1 + (bonusFormula() * multi * add);
    }

    public float bonusFormula() {
        return (1 * getAffixMulti());
    }

    public float getBonusExpMulti() {
        float multi = 0.25F;
        float add = 1F;

        return 1 + (bonusFormula() * multi * add);
    }

    public void addTeamAffix() {
        if (!isTeam) {
            int percent = RandomUtils.RandomRange(75, 100);
            affixes.add(new MapAffixData(SlashRegistry.MapAffixes().get("team_bonus"), percent));
            affixes.add(new MapAffixData(SlashRegistry.MapAffixes().get("other_team_bonus"), percent));
            isTeam = true;
        }
    }

    public boolean increaseLevel(int i) {

        int lvl = this.lvl + i;

        if (lvl > ModConfig.INSTANCE.Server.MAXIMUM_PLAYER_LEVEL.get()) {
            return false;
        }

        this.lvl = lvl;

        return true;
    }

    public boolean increaseTier(int i) {

        int tier = this.tier + i;

        if (tier > ITiered.getMaxTier()) {
            return false;
        }

        this.tier = tier;

        return true;
    }

    private float getAffixMulti() {

        float total = 0;
        for (MapAffixData affix : affixes) {
            total += affix.getBonusLootMultiplier();
        }
        return total;
    }

    public static List<MapAffixData> getAllAffixesThatAffect(List<MapAffixData> affixes, LivingEntity entity) {

        AffectedEntities affected = AffectedEntities.All;

        if (entity instanceof PlayerEntity) {
            affected = AffectedEntities.Players;
        } else if (EntityTypeUtils.isMob(entity)) {
            affected = AffectedEntities.Mobs;
        }

        return getAllAffixesThatAffect(affixes, affected);

    }

    public static List<MapAffixData> getAllAffixesThatAffect(List<MapAffixData> affixes, AffectedEntities affected) {

        List<MapAffixData> list = new ArrayList<>();

        for (MapAffixData data : affixes) {
            if (data.affectedEntities.equals(affected)) {
                list.add(data);
            }
        }
        return list;
    }


    public List<MapAffixData> getAllAffixesThatAffect(AffectedEntities affected) {

        List<MapAffixData> list = new ArrayList<>();

        for (MapAffixData data : affixes) {
            if (data.affectedEntities.equals(affected)) {
                list.add(data);
            }
        }

        for (MapAffixData data : this.getIWP()
                .getMapAffixes()) {
            if (data.affectedEntities.equals(affected)) {
                list.add(data);
            }
        }

        return list;
    }

    public DimensionType setupPlayerMapData(BlockPos pos, PlayerEntity player) {

        UnitData unit = Load.Unit(player);

        ParticleUtils.spawnEnergyRestoreParticles(player, 10);

        return MapManager.setupPlayerMapDimension(player, unit, this, pos);

    }

    public List<MutableComponent> getTooltip() {
        List<MutableComponent> tooltip = new ArrayList<>();

        GearRarity rarity = Rarities.Gears.get(this.rar);

        tooltip.add(TooltipUtils.level(this.lvl));
        TooltipUtils.addEmpty(tooltip);

        if (isTeam) {
            tooltip.add(Styles.LIGHT_PURPLECOMP().appendText("Team Dungeon"));
            tooltip.add(Styles.GRAYITALICCOMP().appendText("Mobs are substantially stronger. Be sure to party up!"));
            TooltipUtils.addEmpty(tooltip);
        }

        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Mobs);
        addAffixTypeToTooltip(this, tooltip, AffectedEntities.Players);
        addAffixTypeToTooltip(this, tooltip, AffectedEntities.All);

        TooltipUtils.addEmpty(tooltip);

        try {
            tooltip.add(Styles.BLUECOMP()
                    .appendSibling(Words.World_Type.locName())
                    .appendText(": ")
                    .appendSibling(this.getIWP()
                            .locName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        TooltipUtils.addEmpty(tooltip);

        MutableComponent comp = TooltipUtils.rarityShort(rarity)
                .appendText(TextFormatting.GRAY + ", ")
                .appendSibling(Styles.GREENCOMP());

        boolean addedExp = false;
        if (getBonusExpAmountInPercent() > 0) {
            comp.appendSibling(Words.Exp.locName()
                    .appendText(
                            ": +" + this.getBonusExpAmountInPercent() + "%"));
            addedExp = true;
        }

        if (getBonusLootAmountInPercent() > 0) {
            if (addedExp) {
                comp.appendText(TextFormatting.GRAY + ", ");
            }

            comp.appendSibling(Words.Loot.locName()
                    .appendText(TextFormatting.YELLOW +
                            ": +" + this.getBonusLootAmountInPercent() + "%"));
        }
        comp.appendText(TextFormatting.GRAY + ", ")
                .appendSibling(Styles.GOLDCOMP()
                        .appendSibling(Words.Tier.locName()
                                .appendText(": " + this.tier)));

        tooltip.add(comp);

        TooltipUtils.addEmpty(tooltip);

        tooltip.add(Styles.BLUECOMP()
                .appendSibling(CLOC.tooltip("put_in_mapdevice")));

        TooltipUtils.removeDoubleBlankLines(tooltip, 20);

        return tooltip;

    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {
        if (ctx.data != null) {
            ctx.event.getToolTip()
                    .addAll(getTooltip());
        }
    }

    private int getBonusLootAmountInPercent() {
        return (int) ((this.getBonusLootMulti() - 1) * 100);
    }

    private int getBonusExpAmountInPercent() {
        return (int) ((this.getBonusExpMulti() - 1) * 100);
    }

    private static void addAffixTypeToTooltip(MapItemData data, List<MutableComponent> tooltip,
                                              AffectedEntities affected) {

        List<MapAffixData> affixes = new ArrayList<>(data.getAllAffixesThatAffect(affected));

        if (affixes.size() == 0) {
            return;
        }

        MutableComponent str = new StringTextComponent("");

        if (affected.equals(AffectedEntities.Players)) {
            str.appendSibling(Words.Player_Affixes.locName());
        } else if (affected.equals(AffectedEntities.Mobs)) {
            str.appendSibling(Words.Mob_Affixes.locName());
        } else {
            str.appendSibling(Words.Affixes_Affecting_All.locName());
        }

        tooltip.add(Styles.AQUACOMP()
                .appendSibling(str));

        for (MapAffixData affix : affixes) {

            for (StatModData statmod : affix.getAffix()
                    .Stats(affix.percent)) {

                TooltipInfo info = new TooltipInfo(
                        new EntityCap.DefaultImpl(), data.getRarity()
                        .StatPercents(), data.lvl);

                tooltip.addAll(statmod.GetTooltipString(info));

            }

        }
    }


    @Override
    public String getRarityRank() {
        return null;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }


    public int getTier() {
        return this.tier;
    }


    public int getLevel() {
        return this.lvl;
    }


    @Override
    public ItemstackDataSaver<? extends ICommonDataItem> getStackSaver() {
        return StackSaving.MAP;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        StackSaving.MAP.saveTo(stack, this);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        return Arrays.asList();
    }
}