package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceOnAction extends AutoHashClass {

    public ResourceType resource;
    public String action;
    public String actionName;

    public static ResourceOnAction onBlock(ResourceType type) {
        return new ResourceOnAction(type, EventData.IS_BLOCKED, "Block");
    }

    public static ResourceOnAction onDodge(ResourceType type) {
        return new ResourceOnAction(type, EventData.IS_DODGED, "Dodge");
    }

    private ResourceOnAction(ResourceType resource, String actiontype, String actionname) {
        this.resource = resource;
        this.actionName = actionname;
        this.action = actiontype;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, action);
    }

    public static List<ResourceOnAction> allCombos() {
        List<ResourceOnAction> list = new ArrayList<>();
        
        for (ResourceType type : ResourceType.values()) {
            list.add(onBlock(type));
            list.add(onDodge(type));

        }
        return list;

    }

}