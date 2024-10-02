package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.network.chat.MutableComponent;

import java.util.Comparator;
import java.util.Optional;

public class UpgradeAffixItemMod extends GearModification {

    public AffixFinderData data;

    public enum AffixFinder {
        SPECIFIC_RARITY(Words.SPECIFIC_RARITY_AFFIX) {
            @Override
            public Optional<AffixData> getAffix(GearItemData gear, AffixFinderData data) {
                return gear.affixes.getPrefixesAndSuffixes().stream().filter(x -> x.rar.equals(data.target_rar)).findAny();
            }

            @Override
            public MutableComponent getTooltip(AffixFinderData data) {
                return this.getWordINTERNAL().locName(data.getTargetRarity().coloredName());
            }
        },
        RANDOM_AFFIX(Words.RANDOM_AFFIX) {
            @Override
            public Optional<AffixData> getAffix(GearItemData gear, AffixFinderData data) {
                return Optional.of(RandomUtils.randomFromList(gear.affixes.getPrefixesAndSuffixes()));
            }
        },
        LOWEST_RARITY_AFFIX(Words.LOWEST_RARITY_AFFIX) {
            @Override
            public Optional<AffixData> getAffix(GearItemData gear, AffixFinderData data) {
                return gear.affixes.getPrefixesAndSuffixes().stream().min(Comparator.comparingInt(x -> x.getRarity().item_tier));
            }
        };

        public AffixFinderData get() {
            return new AffixFinderData(this, "");
        }

        public AffixFinderData ofSpecificRarity(String rar) {
            return new AffixFinderData(this, rar);
        }

        AffixFinder(Words word) {
            this.word = word;
        }

        private Words word;

        protected Words getWordINTERNAL() {
            return word;
        }

        public MutableComponent getTooltip(AffixFinderData data) {
            return word.locName();
        }

        public abstract Optional<AffixData> getAffix(GearItemData gear, AffixFinderData data);
    }


    public record AffixFinderData(AffixFinder finder, String target_rar) {

        GearRarity getTargetRarity() {
            return ExileDB.GearRarities().get(target_rar);
        }
    }

    public UpgradeAffixItemMod(String id, AffixFinderData data) {
        super(ItemModificationSers.UPGRADE_AFFIX_RARITY, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.GEAR.edit(gear -> {
            data.finder.getAffix(gear, data).ifPresent(affix -> {
                affix.upgradeRarity();
            });
        });

    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return UpgradeAffixItemMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.finder.getTooltip(data));
    }

    @Override
    public String locDescForLangFile() {
        return "Upgrades Rarity and re-rolls Numbers of %1$s";
    }
}
