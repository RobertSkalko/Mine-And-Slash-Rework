package com.robertx22.age_of_exile.database.data.unique_items.drop_filters;


public class DropFilterData {


    public String type = "";

    public String id = "";

    public static DropFilterData of(DropFilter filter, String id) {
        DropFilterData data = new DropFilterData();
        data.type = filter.GUID();
        data.id = id;
        return data;
    }
}
