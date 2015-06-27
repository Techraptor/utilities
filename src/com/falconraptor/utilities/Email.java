package com.falconraptor.utilities;

import org.codemonkey.simplejavamail.Mailer;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import java.io.*;
import java.util.Properties;

public class Email {
	public static int TO = 1;
	public static int CC = 2;
	public static int BCC = 3;
	org.codemonkey.simplejavamail.Email email = new org.codemonkey.simplejavamail.Email();
	Properties properties = new Properties();

	public Email (String u, String s, String b) {
		email.setFromAddress(u.substring(0, u.indexOf("@")), u);
		email.setSubject(s);
		email.setTextHTML(b);
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.ssl.trust", "*");
	}

	public void addRecipient (String r, int t) {
		email.addRecipient(r.substring(0, r.indexOf("@")), r, (t == TO) ? RecipientType.TO : (t == CC) ? RecipientType.CC : (t == BCC) ? RecipientType.BCC : null);
	}

	public void addAttachment (String n, String m) throws FileNotFoundException {
		if (!new File(n).exists()) throw new FileNotFoundException("File '" + n + "' was not found");
		email.addAttachment(n, new DataSource() {
			@Override
			public InputStream getInputStream () throws IOException {
				return new FileInputStream(n);
			}

			@Override
			public OutputStream getOutputStream () throws IOException {
				return new FileOutputStream(n);
			}

			@Override
			public String getContentType () {
				return m;
			}

			@Override
			public String getName () {
				return n;
			}
		});
	}

	public void send (String u, String p, String h, int po) {
		properties.put("mail.smtp.host", h);
		properties.put("mail.smtp.port", po);
		Mailer mailer = new Mailer(Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication () {
				return new PasswordAuthentication(u, p);
			}
		}));
		mailer.createMailSession(h, po, u, p);
		mailer.sendMail(email);
	}
}
