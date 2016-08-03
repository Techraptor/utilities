package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;

public class Files {
    private static final String log = "[com.falconraptor.utilities.files.Files.";

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

    public static File downloadfile(String url, String filename) {
        try {
            URL download = new URL(url);
            Logger.logDEBUG(log + "downloadfile] Starting Download of " + filename + " from " + url);
            ReadableByteChannel rbc = Channels.newChannel(download.openStream());
            FileOutputStream fileOut = new FileOutputStream(filename);
            fileOut.getChannel().transferFrom(rbc, 0, 1 << 24);
            fileOut.flush();
            fileOut.close();
            rbc.close();
            Logger.logDEBUG(log + "downloadfile] Download Complete");
            return new File(filename);
        } catch (Exception e) {
            Logger.logERROR(log + "downloadfile] " + e);
            return null;
        }
    }

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) complete.update(buffer, 0, numRead);
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";
        for (byte aB : b) result += Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
        return result;
    }
}
