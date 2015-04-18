package com.falconraptor.utilities;

import com.falconraptor.utilities.logger.Logger;

class Colors {

    public static int[] checkErrors(String test) {
        return new int[]{findNum(test, "r="), findNum(test, "g="), findNum(test, "b=")};
    }

    private static int findNum(String abc, String find) {
        int a = 5;
        for (int i = 0; i < 4; i++) {
            try {
                Integer.parseInt(abc.substring(abc.indexOf(find) + 2, abc.indexOf(find) + a));
                break;
            } catch (Exception e) {
                String log = "[com.falconraptor.utilities.Colors.";
                if (Logger.level <= 5) Logger.logERROR(log + "findNum] " + e);
                a--;
            }
        }
        return a;
    }
}
