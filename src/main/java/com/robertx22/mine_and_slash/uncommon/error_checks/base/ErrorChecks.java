package com.robertx22.mine_and_slash.uncommon.error_checks.base;

import java.util.ArrayList;
import java.util.List;

public class ErrorChecks {

    public static List<IErrorCheck> getAll() {

        List<IErrorCheck> list = new ArrayList<>();

        //list.add(new AllGearsHavePossibleAffixCheck());

        return list;

    }

}
