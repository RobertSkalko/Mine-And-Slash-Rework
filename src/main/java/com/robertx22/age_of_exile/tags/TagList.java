package com.robertx22.age_of_exile.tags;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


// todo if saved as string, i cant get the tag back easily, and allow making of any tags.. but if i dont, custom tags are not possible hm
// transform tags into a normal class, that just has special auto loc, NOT a registry, so any tag is valid even without loc
public class TagList<T extends ModTag> {


    public TagList(List<T> tags) {
        this.tags = tags.stream().map(x -> x.GUID()).collect(Collectors.toSet());
    }

    public TagList(T... tags) {
        this.tags = new HashSet<>();
        for (T tag : tags) {
            this.tags.add(tag.GUID());
        }
    }

    public List<T> getTags(T clas) {
        return this.tags.stream().map(x -> (T) clas.fromString(x)).collect(Collectors.toList());
    }

    public Set<String> tags;

    public boolean contains(String tag) {
        return tags.contains(tag);
    }

    public boolean contains(T tag) {
        return tags.contains(tag.GUID());
    }

    public boolean containsAny(List<T> tag) {
        return tag.stream().anyMatch(x -> tags.contains(x.GUID()));
    }

    public void add(T t) {
        tags.add(t.GUID());
    }

    public void addAll(List<? extends T> list) {
        tags.addAll(list.stream().map(x -> x.GUID()).toList());
    }

}
