package com.falconraptor.utilities;

import com.falconraptor.utilities.logger.Logger;

class Colors {
    public static int[] checkErrors(String test) {
        return new int[]{findNum(test, "r="), findNum(test, "g="), findNum(test, "b=")};
    }

    private static int findNum(String abc, String find) {
        int a = 5;
        int i = 0;
        while (i < 4) {
            try {
                Integer.parseInt(abc.substring(abc.indexOf(find) + 2, abc.indexOf(find) + a));
                break;
            } catch (Exception var6) {
                String log = "[com.falconraptor.utilities.Colors.";
                Logger.logERROR(log + "findNum] " + var6);
                --a;
                ++i;
            }
        }
        return a;
    }
}
