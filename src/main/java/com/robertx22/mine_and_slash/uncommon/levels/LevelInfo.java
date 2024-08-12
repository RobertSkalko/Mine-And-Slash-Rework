package com.robertx22.mine_and_slash.uncommon.levels;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class LevelInfo {

    private int level = 1;

    public int getLevel() {
        return level;
    }

    public List<LevelChangeData> changes = new ArrayList<>();

    public Component getTooltip() {

        List<MutableComponent> list = new ArrayList<>();

        int last = -1;
        for (LevelChangeData change : changes) {
            if (last < 0) {
                last = 0;
            }
            boolean changed = last != change.levelAfterChange;
            last = change.levelAfterChange;

            String changedtext = changed ? "" : " - Didn't Change";
            list.add(Component.literal(change.change.name() + " " + change.source.name() + "\nLevel after Change: " + change.levelAfterChange + changedtext));
        }

        list.add(Component.literal("Final Level: " + level));

        return TooltipUtils.joinMutableComps(list.listIterator(), Component.literal("\n"));

    }

    public void set(LevelSource source, int num) {
        var data = new LevelChangeData(num, ChangeType.SET, source);
        changes.add(data);
        level = data.levelAfterChange;
    }

    public void capToRange(LevelSource source, MinMax range) {
        var data = new LevelChangeData(range.capNumber(this.level), ChangeType.LEVEL_CAPPED_TO_RANGE, source);
        changes.add(data);
        level = data.levelAfterChange;
    }

    public void add(LevelSource source, int lvls) {
        var data = new LevelChangeData(level + lvls, ChangeType.BONUS_LEVELS, source);
        changes.add(data);
        level = data.levelAfterChange;
    }

    public enum ChangeType {
        SET,
        LEVEL_CAPPED_TO_RANGE,
        BONUS_LEVELS
    }

    public enum LevelSource {
        ENTITY_CONFIG,
        MAP_DIMENSION,
        LEVEL_VARIANCE,
        DISTANCE_FROM_SPAWN,
        DIMENSION,
        MIN_LEVEL_AREA,
        NEAREST_PLAYER,
        MAX_LEVEL,
        BIOME
    }
}
