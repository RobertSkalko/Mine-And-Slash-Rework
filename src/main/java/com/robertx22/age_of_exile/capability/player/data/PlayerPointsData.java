package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.uncommon.localization.Chats;

import java.util.HashMap;

public class PlayerPointsData {

    private HashMap<PlayerPointsType, SingleData> map = new HashMap<>();


    public SingleData get(PlayerPointsType type) {
        if (!map.containsKey(type)) {
            var d = new SingleData();
            d.type = type;
            map.put(type, d);
        }
        return map.get(type);

    }

    public class SingleData {

        public PlayerPointsType type = PlayerPointsType.TALENTS;
        private int bonus_points = 0;

        // todo start using this for reset points
        public int reset_points = 0;

        public int getBonusPoints() {
            return bonus_points;
        }

        public void resetBonusPoints() {
            bonus_points = 0;
        }

        public ExplainedResult giveBonusPoints(int num) {
            bonus_points += num;

            if (bonus_points > type.getConfig().max_bonus_points) {
                return ExplainedResult.failure(Chats.FAILED_TO_AWARD_POINTS.locName(type.word().locName(), type.getConfig().max_bonus_points));
            }
            return ExplainedResult.success(Chats.AWARDED_POINTS.locName(num, type.word().locName()));

        }
    }

}
