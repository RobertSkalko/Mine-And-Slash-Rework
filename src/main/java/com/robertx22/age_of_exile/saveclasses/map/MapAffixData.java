package com.robertx22.age_of_exile.saveclasses.map;


public class MapAffixData {

    public MapAffixData() {

    }
    

    public MapAffixData(BaseMapAffix affix, int percent) {
        this.GUID = affix.GUID();
        this.percent = percent;

        if (affix.isBeneficial()) {
            affectedEntities = AffectedEntities.Mobs;
        } else {
            affectedEntities = AffectedEntities.Players;
        }

    }

    public float getBonusLootMultiplier() {
        return 0.01F + (((float) percent / 1200) * getAffix().lootMulti());
    }

    public String GUID;

    public int percent;

    public AffectedEntities affectedEntities;

    public BaseMapAffix getAffix() {

        return SlashRegistry.MapAffixes()
                .get(GUID);
    }

    public List<StatModData> GetAllStats() {
        return getAffix().Stats(percent);
    }

}