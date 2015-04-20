package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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

    public static void downloadfile(String url, String filename) {
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
        } catch (Exception e) {
            Logger.logERROR(log + "downloadfile] " + e);
        }
    }

    public static void uploadFile(String u, String p, String h, String up, String f, boolean s, boolean pr) {
        Logger.logDEBUG(log + "uploadFile] Starting Upload of " + f + " to " + h + "/" + up);
        try {
            FTPClient ftp;
            if (s) {
                FTPSClient temp = new FTPSClient();
                temp.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
                ftp = temp;
            } else ftp = new FTPClient();
            if (pr) ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
            ftp.connect(h);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new Exception("FTP server refused connection");
            }
            if (!ftp.login(u, p)) {
                ftp.logout();
                throw new Exception("Wrong user/pass");
            }
            ftp.enterLocalPassiveMode();
            ftp.setPassiveNatWorkaround(true);
            ftp.storeFile(f, new FileInputStream(f));
            ftp.noop();
            ftp.logout();
            if (ftp.isConnected()) ftp.disconnect();
            Logger.logDEBUG(log + "uploadFile] Upload Complete");
        } catch (Exception ex) {
            Logger.logERROR(log + "uploadFile] " + ex);
        }
    }
}
