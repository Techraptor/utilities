package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Write {
    public static String log = "[com.falconraptor.utilities.files.Write.";
    private static BufferedWriter writer;
    private static FileWriter fw;
    private static File file;

    public static void Write(String filename, boolean hidden) {
        try {
            file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                Logger.logDEBUG(log + "Write] File Created");
            } else {
                file.delete();
                file.createNewFile();
                Logger.logDEBUG(log + "Write] File Recreated");
            }
            fw = new FileWriter(file.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            if (hidden) hide();
        } catch (Exception e) {
            Logger.logERROR(log + "Write] " + e);
        }
    }

    public static void makeDir(String directory) {
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
                Logger.logDEBUG(log + "makeDir] Directory Created");
            }
        } catch (Exception e) {
            Logger.logERROR(log + "makeDir] " + e);
        }
    }

    public static void write(String out) {
        try {
            writer.write(out);
        } catch (Exception e) {
            Logger.logERROR(log + "write] " + e);
        }
    }

    public static void newline() {
        try {
            writer.newLine();
        } catch (Exception e) {
            Logger.logERROR(log + "newline] " + e);
        }
    }

    public static void close() {
        try {
            writer.close();
            Logger.logDEBUG(log + "close] File Saved");
        } catch (Exception e) {
            Logger.logERROR(log + "close] " + e);
        }
    }

    public static void hide() {
        try {
            // win32 command line variant
            Process p = Runtime.getRuntime().exec("attrib +h " + file.getPath());
            p.waitFor(); // p.waitFor() important, so that the file really appears as hidden immediately after function exit.
        } catch (Exception e) {
            Logger.logERROR(log + "hide] " + e);
        }
    }

    public static void write(ArrayList<String> out) {
        try {
            for (String s : out) {
                write(s);
                newline();
            }
        } catch (Exception e) {
            Logger.logERROR(log + "write] " + e);
        }
    }
}
