package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.LevelPart;
import com.robertx22.mine_and_slash.loot.generators.stack_changers.IStackAction;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

// use once and discard!
public abstract class ItemBlueprint {

    public LootInfo info = null;


    public ItemBlueprint(LootInfo info) {
        this.info = info;
        this.level.number = info.level;


        this.onConstruct();
    }


    public List<IStackAction> actionsAfterGeneration = new ArrayList<>();

    boolean itemWasGenerated = false;

    void onConstruct() {

    }

    public LevelPart level = new LevelPart(this);

    protected abstract ItemStack generate();


    final public ItemStack createStack() {
        checkAndSetGeneratedBoolean();
        ItemStack stack = generate();
        actionsAfterGeneration.forEach(x -> x.changeStack(stack));
        return stack;
    }

    private void checkAndSetGeneratedBoolean() {

        if (itemWasGenerated) {
            try {
                throw new Exception("Do not use a blueprint instance to make more than 1 item!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            itemWasGenerated = true;
        }

    }

}
