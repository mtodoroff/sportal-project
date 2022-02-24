package com.sportal.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CensoredWords {
    private static final String regexCensorship = "^(тъпанар|идиот|майка ти|педал)";

    public static String getRegexCensorship() {
        return regexCensorship;
    }
}
