package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Read {
    private static final String log = "[com.falconraptor.utilities.files.Read.";
    private BufferedReader reader;

    public static boolean Read(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                Logger.logERROR(filename + " does not exist!");
                return false;
            }
            reader = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            Logger.logERROR(log + "Read] " + e);
            return false;
        }
        return true;
    }

    public ArrayList<String> read () {
        try {
            String line = reader.readLine();
            ArrayList<String> out = new ArrayList<>(0);
            while (line != null) {
                out.add(line);
                line = reader.readLine();
            }
            reader.close();
            return out;
        } catch (Exception e) {
            Logger.logERROR(log + "read] " + e);
            return null;
        }
    }

    public static String readjar(String filename) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(Read.class.getClassLoader().getResourceAsStream(filename)));
            String line = input.readLine();
            String out = "";
            while (line != null) {
                out += line + "↔";
                line = input.readLine();
            }
            input.close();
            return out;
        } catch (Exception e) {
            Logger.logERROR(log + "readjar] " + e);
            return "";
        }
    }

    public static BufferedImage readImagefromJar(String filename) {
        BufferedImage buff = null;
        try {
            buff = ImageIO.read(Read.class.getResourceAsStream(filename));
        } catch (Exception e) {
            Logger.logERROR(log + "readImagefromJar] " + e);
        }
        return buff;
    }
}
