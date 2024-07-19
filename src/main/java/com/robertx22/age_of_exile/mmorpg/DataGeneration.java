package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.aoe_data.datapacks.curio_tags.GenerateCurioDataJsons;
import com.robertx22.age_of_exile.aoe_data.datapacks.generators.DataGenHook;
import com.robertx22.age_of_exile.aoe_data.datapacks.lang_file.CreateLangFile;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.aoe_data.datapacks.modpack_helper_lists.ModpackerHelperLists;
import net.minecraft.data.CachedOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class DataGeneration {

    public static void generateAll() {

        try {

            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CreateLangFile::create);


            new DataGenHook().run(CachedOutput.NO_CACHE);

            ModpackerHelperLists.generate();
            GenerateCurioDataJsons.generate();
            ItemModelManager.INSTANCE.generateModels();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
