package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.*;

public class Read {
    public static String log = "[com.falconraptor.utilities.files.Read.";
    private static BufferedReader reader;
    private static File file;

    public static void Read(String filename) {
        try {
            file = new File(filename);
            if (!file.exists()) {
                Logger.logERROR(filename + " does not exist!");
                return;
            }
            reader = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            Logger.logERROR(log + "Read] " + e);
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
            Logger.logERROR(log + "read] " + e);
            return "";
        }
    }

    public static String readjar(String filename) {
        try {
            InputStream in = new Read().getClass().getResourceAsStream(filename);
            BufferedReader input = new BufferedReader(new InputStreamReader(in));
            String line = input.readLine();
            String out = "";
            while (line != null) {
                out += line + "!";
                line = input.readLine();
            }
            input.close();
            return out;
        } catch (Exception e) {
            Logger.logERROR(log + "readjar] " + e);
            return "";
        }
    }
}
