package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.aoe_data.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.DataGenHook;
import com.robertx22.age_of_exile.aoe_data.datapacks.lang_file.CreateLangFile;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.modpack_helper_lists.ModpackerHelperLists;
import net.minecraft.data.CachedOutput;

public class DataGeneration {

    public static void generateAll() {

        try {

            new DataGenHook().run(CachedOutput.NO_CACHE);

            ModpackerHelperLists.generate();
            CreateLangFile.create();
            GenerateCurioDataJsons.generate();
            ItemModelManager.INSTANCE.generateModels();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
