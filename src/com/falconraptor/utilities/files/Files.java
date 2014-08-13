package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Files {
    public static String log = "[com.falconraptor.utilities.files.Files.";

    public static String getrelativepath(String path) {
        File test = new File("").getAbsoluteFile();
        if (test.getAbsolutePath().substring(0, 1).equals(path.substring(0, 1))) {
            File parent = new File(test.getParent());
            int parents = 1;
            while (parent.getAbsolutePath().length() > 4) {
                parent = new File(parent.getParent());
                parents++;
            }
            path = path.substring(3);
            for (int i = 0; i < parents; i++) path = "..\\" + path;
        }
        return path;
    }

    public static void downloadfile(String url, String filename) {
        try {
            URL download = new URL(url);
            if (Logger.level <= 3) {
                Logger.logDEBUG(log + "downloadfile] Starting Download of " + filename + " from " + url);
            }
            ReadableByteChannel rbc = Channels.newChannel(download.openStream());
            FileOutputStream fileOut = new FileOutputStream(filename);
            fileOut.getChannel().transferFrom(rbc, 0, 1 << 24);
            fileOut.flush();
            fileOut.close();
            rbc.close();
            if (Logger.level <= 3) {
                Logger.logDEBUG(log + "downloadfile] Download Complete");
            }
        } catch (Exception e) {
            if (Logger.level <= 5) Logger.logERROR(log + "downloadfile] " + e);
        }
    }
}
