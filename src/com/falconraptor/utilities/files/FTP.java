package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;
import org.apache.commons.net.ftp.*;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.FileInputStream;

public class FTP {
	private static final String log = "[com.falconraptor.utilities.files.FTP.";
	FTPClient ftpClient;
	boolean loggedIn = false;

	public FTP (String u, String p, String h, Boolean s) {
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
			e.printStackTrace();
		}
	}

	public void uploadFile (String u, String p, String h, String up, String f, boolean s, boolean pr) {
		Logger.logDEBUG(log + "uploadFile] Starting Upload of " + f + " to " + h + "/" + up);
		try {
			ftpClient.storeFile(f, new FileInputStream(f));
			ftpClient.noop();
			Logger.logDEBUG(log + "uploadFile] Upload Complete");
		} catch (Exception ex) {
			Logger.logERROR(log + "uploadFile] " + ex);
		}
	}

	public void logout () {
		try {
			ftpClient.logout();
			if (ftpClient.isConnected()) ftpClient.disconnect();
			loggedIn = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
