package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.config.forge.ServerContainer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ModErrors {
    static List<String> printed = new ArrayList<>();

    public static void print(Exception e) {

        if (ServerContainer.get().LOG_ERRORS.get()) {
            if (ServerContainer.get().STOP_ERROR_SPAM.get()) {

                String error = getStackTrace(e);

                if (printed.contains(error)) { // todo, there's an empty nullpoint error somewhere from the code...
                    return;
                }
                printed.add(error);
            }

            e.printStackTrace();
        }
    }

    private static String getStackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }
}
