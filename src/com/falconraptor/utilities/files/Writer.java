package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
    private static final String log = "[com.falconraptor.utilities.files.Writer.";
    private BufferedWriter writer;
    private File file;

    public Writer(String filename, boolean hidden) {
        try {
            file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                Logger.logDEBUG(log + "Writer] File Created");
            } else {
                file.delete();
                file.createNewFile();
                Logger.logDEBUG(log + "Writer] File Recreated");
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            if (hidden) hide(file);
        } catch (Exception e) {
            Logger.logERROR(log + "Writer] " + e);
        }
    }

    public Writer(String filename) {
        this(filename,false);
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

    public static void hide(String file) {
        hide(new File(file));
    }

    public static void hide(File file) {
        try {
            Process p = Runtime.getRuntime().exec("attrib +h " + file.getPath());
            p.waitFor(); // p.waitFor() important, so that the file really appears as hidden immediately after function exit.
        } catch (Exception e) {
            Logger.logERROR(log + "hide] " + e);
        }
    }

    public Writer flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            Logger.logERROR(log + "flush] " + e);
        }
        return this;
    }

    public Writer close() {
        try {
            flush();
            writer.close();
            Logger.logDEBUG(log + "close] File Saved");
        } catch (Exception e) {
            Logger.logERROR(log + "close] " + e);
        }
        return this;
    }

    public Writer write(ArrayList<?> out) {
        try {
            for (Object s : out) writeln(s.toString());
            flush();
        } catch (Exception e) {
            Logger.logERROR(log + "write] " + e);
        }
        return this;
    }

    public Writer write(Object[] out) {
        try{
            for (Object o:out) writeln(o.toString());
            flush();
        }catch (Exception e){
            Logger.logERROR(log+"write] "+e);
        }
        return this;
    }

    public Writer writeln(String out) {
        try {
            write(out);
            newline();
        } catch (Exception e) {
            Logger.logERROR(log + "write] " + e);
        }
        return this;
    }

    public Writer write(String out) {
        try {
            writer.write(out);
        } catch (Exception e) {
            Logger.logERROR(log + "write] " + e);
        }
        return this;
    }

    public Writer newline() {
        try {
            writer.newLine();
        } catch (Exception e) {
            Logger.logERROR(log + "newline] " + e);
        }
        return this;
    }
}
