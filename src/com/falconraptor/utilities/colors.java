package com.falconraptor.utilities;

import com.falconraptor.utilities.logger.Logger;

public class Colors {
    private static String log = "[com.falconraptor.utilities.Colors.";

    public static int[] checkerrors(String test) {
        return new int[]{findnum(test, "r="), findnum(test, "g="), findnum(test, "b=")};
    }

    public static int findnum(String abc, String find) {
        int a = 5, t;
        for (int i = 0; i < 4; i++) {
            try {
                t = Integer.parseInt(abc.substring(abc.indexOf(find) + 2, abc.indexOf(find) + a));
                break;
            } catch (Exception e) {
                if (Logger.level <= 5) Logger.logERROR(log + "findnum] " + e);
                a--;
            }
        }
        return a;
    }
}
