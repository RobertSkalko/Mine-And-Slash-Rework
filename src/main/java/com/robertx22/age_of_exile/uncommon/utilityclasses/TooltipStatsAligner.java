package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TooltipStatsAligner {
    List<Component> list = new ArrayList<>();
    Boolean addEmptyLine = true;
    List<Component> original = new ArrayList<>();

    public TooltipStatsAligner(List<Component> listInput) {
        list.addAll(listInput);

        this.original = new ArrayList<>(listInput);
    }

    public TooltipStatsAligner(List<Component> listInput, Boolean addEmptyLine) {
        list.addAll(listInput);

        this.original = new ArrayList<>(listInput);

        this.addEmptyLine = addEmptyLine;
    }


    // Build new tooltips
    //This method manipulate the stats part in tooltips as a whole.
    public List<Component> buildNewTooltipsStats() {

        if (!ClientConfigs.getConfig().ALIGN_STAT_TOOLTIPS.get()) {
            return original;
        }

        if (list.size() <= 1) {
            return original;
        }
        Minecraft mc = Minecraft.getInstance();
        // Create a Matcher for finding patterns in the stats, this patterns will match the value, like +3, -20%.
                /*
                    this regex make sure the stat pattern is like:
                        (something not spaces here, and must start from the beginning of line)(two spaces here, or sth, anyway it is from the StatNameRegex.java)(something not spaces here)
                    that means whether you use:
                    +3    Strength  or  Strength:    +3
                    they will be all good.
                */
        Pattern matcherForValue = Pattern.compile("^([^◆\\s]+)" + StatNameRegex.VALUEAndNAMESeparator + "(.+)");
        //add this to find smth match (+/-)(a figure), basically the stats value
        Pattern deepmatcher = Pattern.compile("([\\+\\-][0-9])");
        Function<String, Function<Pattern, Matcher>> getMatcherFunction =
                str -> pattern -> pattern.matcher(str);

        LinkedHashMap<Integer, Component> mapWithIndex;
        //wrap the list into a map, map key is value's index, map value is the value.
        mapWithIndex = IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(
                        integer -> integer,
                        integer -> list.get(integer),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        final int[] maxWidth = {0};
        Map<Integer, Component> targetMap = new HashMap<>();

        mapWithIndex.entrySet().stream()
                //take all stats
                .filter(x -> matcherForValue.asPredicate().test(x.getValue().getString()))
                .filter(x -> deepmatcher.asPredicate().test(x.getValue().getString()))
                .forEach(x -> {
                    //calc all target line's width, take the max.
                    Matcher matcher = getMatcherFunction.apply(x.getValue().getString()).apply(matcherForValue);
                    matcher.find();
                    String matchedValue = matcher.group(1);
                    int width = mc.font.width(matchedValue);
                    maxWidth[0] = Math.max(maxWidth[0], width);
                    targetMap.put(x.getKey(), x.getValue());
                });


        Map<Integer, Component> onlyContainTargetMap = new HashMap<>(targetMap);
        onlyContainTargetMap.entrySet()
                .forEach(x -> {
                    var currentComp = x.getValue();
                    Matcher matcher = getMatcherFunction.apply(x.getValue().getString()).apply(matcherForValue);
                    matcher.find();
                    String gearValue = matcher.group(1);
                    int width = mc.font.width(gearValue);
                    Style style = currentComp.getStyle();
                    String dotNeed = ".".repeat((maxWidth[0] - width) / 2);
                    String lastPart = StatNameRegex.VALUEAndNAMESeparator + matcher.group(2);
                    Component wholeComponent =
                            //use the append() method from MutableComponent is better because in this case I need to keep every Component sibling's Style, the
                            //ExileText will erase all the sibling distinction that sucks, and will also have potential format interference problem.
                            ExileText.ofText(gearValue).get()
                                    .append(ChatFormatting.BLACK + dotNeed)
                                    .append(lastPart)
                                    .withStyle(style);
                    x.setValue(wholeComponent);
                });
        mapWithIndex.putAll(onlyContainTargetMap);

        //put any post-post edit logic in here.
        var compList = new LinkedList<>(mapWithIndex.values());
        ListIterator<Component> iterator = compList.listIterator();
        MutableComponent emptyLine = ExileText.emptyLine().get();
        // place empty lines.
        while (iterator.hasNext()) {
            int index = iterator.nextIndex();

            if (getMatcherFunction.apply(compList.get(index).getString()).apply(Pattern.compile("◆ ")).find()) {
                String previousLine = iterator.hasPrevious() ? compList.get(index - 1).getString() : "";
                String nextLine = compList.get(index + 1).getString();

                if (!previousLine.isEmpty()) iterator.add(emptyLine);

                iterator.next();

                if (!nextLine.isEmpty()) iterator.add(emptyLine);

            } else {
                iterator.next();
            }
        }

        if (!addEmptyLine) {
            return compList;
        }
        if (compList.size() <= 1) {
            return compList;
        }

        if (!compList.get(iterator.previousIndex()).getString().equals("")) {
            compList.addLast(emptyLine);
        }

        return compList;
    }
}
