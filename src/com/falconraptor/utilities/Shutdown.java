package com.falconraptor.utilities;

import com.falconraptor.utilities.files.Writer;
import com.falconraptor.utilities.logger.Logger;

public class Shutdown {
    public String packagename;

    public void attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Writer.makeDir("logs");
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
