package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
    here is the detail of this aligner:                            this space is from the translate() in StatNameRegex.java, so they are all same.
                                                                     ⬆
        +3    Strength           Calc the max width           +3   |   Strength            add some black dot to      +3...|   Strength
        +30%    Fire Damege     -------------------->         +30% |   Fire Damege         -------------------->      +30%.|   Fire Damege
        +0.5%    Health                                       +0.5%|   Health               fill the space            +0.5%|   Health
 */
public class TooltipStatsFactory {
    List<String> list = new ArrayList<>();
    List<Integer> width = new ArrayList<>();
    List<Integer> addTime = new ArrayList<>();

    // Constructor that takes a list of Components and extracts their string representations
    public TooltipStatsFactory(List<Component> listInput) {
        // Extract strings from Components and add them to the internal list
        listInput.forEach(x -> list.add(x.getString()));
    }

    // Build new tooltips
    //This method manipulate the stats part in tooltips as a whole, then Iterate it to get info with keeping the same tooltips order.
    public List<Component> buildNewTooltipsStats() {
        // List to store the modified strings
        List<String> mediaStatsList = new ArrayList<>();
        List<Component> finalStatsList = new ArrayList<>();

        // Iterate over the internal list of Stats
        for (String x : list) {
            Minecraft mc = Minecraft.getInstance();
            // Create a Matcher for finding patterns in the stats, this patterns will match the value, like +3, -20%.
            /*
                this regex make sure the stat pattern is like:
                    (something not spaces here, and must start from the beginning of line)(two spaces here, or sth, anyway it is from the StatNameRegex.java)(something not spaces here)
                that means whether you use:
                +3    Strength  or  Strength:    +3
                they will be all good.
            */
            Matcher matcherForValue = Pattern.compile("^(\\S+)" + StatNameRegex.VALUEAndNAMESeparator + "(\\S+)").matcher(x);
            Matcher matcherForStatDesc = Pattern.compile("^ (\\[)").matcher(x);

            //that's so weird that the stat desc will lose its Format. I have to add for it at here.
            if (matcherForStatDesc.find()){
                mediaStatsList.add(ChatFormatting.BLUE + matcherForStatDesc.replaceFirst("$1"));
                continue;
            }

            // Check if a pattern is found in the string
            if (matcherForValue.find()) {
                // Retrieve the value part
                String matchText = matcherForValue.group(1);

                // Calculate the width of the value text using Minecraft's font
                this.width.add(mc.font.width(matchText));

                // Replace the first occurrence of the pattern with a placeholder.
                //no need to add formatter here.
                mediaStatsList.add(Pattern.compile("([\\+\\-]\\S+)").matcher(x).replaceFirst("$1replacement_seg"));
            } else {
                mediaStatsList.add(x);
            }
            //ClientOnly.printInChat(Component.literal(x));

        }
        //I found that the black "." nobody can notice and absolutely is a good icon to align the different value.
        // In this part, I calculate the number of increments for each width, I just need to add some black dot to make every value equal in width
        addTime = TooltipUtils.incrementArray(width);

        // List to store the final modified Components

        // Iterate over the modified strings and corresponding increment counts
        for (int i = 0, j = 0; i < addTime.size() && j < mediaStatsList.size(); j++) {
            String currentStat = mediaStatsList.get(j);
            int currentAddTime = addTime.get(i);

            if (currentStat.equals("")) continue;

            if (Pattern.compile("replacement_seg").matcher(currentStat).find()){
                // Generate a string to replace the placeholder, based on the increment count
                String stringUsedToReplace = ChatFormatting.BLACK + ".".repeat(currentAddTime) + ChatFormatting.RESET;
                // Replace the placeholder with the generated string and create a new Component
                String replacedStats = currentStat.replace("replacement_seg", stringUsedToReplace);
                mediaStatsList.set(j, replacedStats);
                i++;
            }
            //just add empty line for special stat, both up and down.
            if (Pattern.compile("◆ ").matcher(currentStat).find()){
                int index = j ;
                String previousLine = (index > 0) ? mediaStatsList.get(index - 1) : "";
                String nextLine = (index < mediaStatsList.size() - 1) ? mediaStatsList.get(index + 1) : "";
                if (!previousLine.isEmpty()) {
                    mediaStatsList.add(index, "");
                    index++;
                }

                if (!nextLine.isEmpty()) {
                    mediaStatsList.add(index + 1, "");
                }
            }

        }
        if (!mediaStatsList.isEmpty()) {// not empty, sometime it will.
            if (!mediaStatsList.get(mediaStatsList.size() - 1).equals("")) {// check if the last line is emty line, if it is then dont add
                if (mediaStatsList.size() > 1) mediaStatsList.add("");// if there is only one stat in mediaStatsList, like most of the talents, just dont add, cuz it will be so weird.
            }
        }
        mediaStatsList.forEach(x -> finalStatsList.add(Component.literal(x)));
        return finalStatsList;
    }
}
