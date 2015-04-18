package com.falconraptor.utilities;

import com.falconraptor.utilities.files.Write;
import com.falconraptor.utilities.logger.Logger;

class Shutdown {
    private String packagename;

    public void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Write.makeDir("logs");
                Logger.saveLog("logs/" + packagename + "Log.log");
            }
        });
        Logger.logINFO("Shut Down Hook Attached.");
    }

    public void attachShutDownHook(Thread t) {
        Runtime.getRuntime().addShutdownHook(t);
        Logger.logINFO("Shut Down Hook Attached.");
    }
}
