package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FTP {
    private static final String log = "[com.falconraptor.utilities.files.FTP.";
    public FTPClient ftpClient;
    public boolean loggedIn = false;

    public FTP(String u, String p, String h, Boolean s) {
        try {
            if (s) {
                FTPSClient temp = new FTPSClient();
                temp.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
                ftpClient = temp;
            } else ftpClient = new FTPClient();
            ftpClient.connect(h);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception("FTP server refused connection");
            }
            if (!ftpClient.login(u, p)) {
                ftpClient.logout();
                throw new Exception("Wrong user/pass");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setPassiveNatWorkaround(true);
            loggedIn = true;
        } catch (Exception e) {
            Logger.logERROR(log + "FTP] " + e);
        }
    }

    public FTP(String u,String p,String h){
        this(u,p,h,false);
    }

    public Boolean uploadFile(String f) {
        if (!loggedIn) return false;
        Logger.logDEBUG(log + "uploadFile] Starting Upload of " + f);
        try {
            Boolean done = ftpClient.storeFile(f, new FileInputStream(f));
            Logger.logDEBUG(log + "uploadFile] Upload Complete");
            return done;
        } catch (Exception ex) {
            Logger.logERROR(log + "uploadFile] " + ex);
            return false;
        }
    }

    public void logout() {
        try {
            ftpClient.logout();
            if (ftpClient.isConnected()) ftpClient.disconnect();
            loggedIn = false;
        } catch (Exception e) {
            Logger.logERROR(log + "logout] " + e);
        }
    }

    public InputStream downloadFileStream(String file) {
        try{
            return ftpClient.retrieveFileStream(file);
        }catch(Exception e){
            Logger.logERROR(log + "downloadFile] " + e);
            return null;
        }
    }

    public Boolean downloadFile(String file) {
        try {
            FileOutputStream os = new FileOutputStream(new File(file));
            Boolean done = ftpClient.retrieveFile(file, os);
            os.flush();
            os.close();
            return done;
        } catch (Exception e) {
            Logger.logERROR(log + "downloadFile] " + e);
            return null;
        }
    }
}
