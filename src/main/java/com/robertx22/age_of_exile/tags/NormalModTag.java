package com.robertx22.age_of_exile.tags;

public abstract class NormalModTag extends ModTag {

    private String id;

    public NormalModTag(String id) {
        this.id = id;
    }

    @Override
    public String GUID() {
        return id;
    }
}
