package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Read {
    public static String log = "[com.falconraptor.utilities.files.Read.";
    private static BufferedReader reader;
    private static File file;

    public static void Read(String filename) {
        try {
            file = new File(filename);
            if (!file.exists()) {
                if (Logger.level <= 5) Logger.logERROR(filename + " does not exist!");
                return;
            }
            reader = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            if (Logger.level <= 5) Logger.logERROR(log + "Read] " + e);
        }
    }

    public static String read() {
        try {
            String line = reader.readLine();
            String out = "";
            while (line != null) {
                out += line + "\n";
                line = reader.readLine();
            }
            reader.close();
            return out;
        } catch (Exception e) {
            if (Logger.level <= 5) Logger.logERROR(log + "read] " + e);
            return "";
        }
    }
}
