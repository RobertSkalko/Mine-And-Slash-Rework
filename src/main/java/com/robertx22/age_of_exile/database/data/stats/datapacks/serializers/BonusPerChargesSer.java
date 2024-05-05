package com.robertx22.age_of_exile.database.data.stats.datapacks.serializers;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.AutoStatGson;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.IStatSerializer;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.BonusStatPerEffectStacks;

// todo shouldnt i just change all this into gson?
public class BonusPerChargesSer implements IStatSerializer<BonusStatPerEffectStacks> {

    @Override
    public JsonObject statToJson(BonusStatPerEffectStacks obj) {
        JsonObject data = AutoStatGson.PARSER.parse(AutoStatGson.GSON.toJson(obj.data)).getAsJsonObject();

        JsonObject json = new JsonObject();
        this.saveBaseStatValues(obj, json);
        json.add("core_stat_data", data);

        return json;
    }

    @Override
    public BonusStatPerEffectStacks getStatFromJson(JsonObject json) {
        CoreStatData data = AutoStatGson.GSON.fromJson(json.get("core_stat_data"), CoreStatData.class);
        String id = json.get("id").getAsString();
        String name = json.get("name").getAsString();
        StatScaling scaling = StatScaling.valueOf(json.get("scale").getAsString());
        BonusStatPerEffectStacks stat = new BonusStatPerEffectStacks(id, name, scaling, data);
        this.loadBaseStatValues(stat, json);
        return stat;
    }
}
