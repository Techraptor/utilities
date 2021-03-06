package com.falconraptor.utilities;

import com.falconraptor.utilities.logger.Logger;
import org.codemonkey.simplejavamail.Mailer;

import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.*;
import java.util.Properties;

public class Email {
    public static final int TO = 1;
    public static final int CC = 2;
    public static final int BCC = 3;
    private static final String log = "[com.falconraptor.utilities.Email.";
    public final org.codemonkey.simplejavamail.Email email = new org.codemonkey.simplejavamail.Email();
    public Properties properties = new Properties();

    public Email(String u, String s, String b) {
        email.setFromAddress(u.substring(0, u.indexOf("@")), u);
        email.setSubject(s);
        email.setTextHTML(b);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.trust", "*");
    }

    public Email setBody(String b) {
        email.setTextHTML(b);
        return this;
    }

    public Email setSubject(String s) {
        email.setSubject(s);
        return this;
    }

    public Email addRecipient(String r, int t) {
        email.addRecipient(r.substring(0, r.indexOf("@")), r, (t == TO) ? RecipientType.TO : (t == CC) ? RecipientType.CC : (t == BCC) ? RecipientType.BCC : null);
        return this;
    }

    public Email addAttachment(String n, String m) throws FileNotFoundException {
        if (!new File(n).exists()) throw new FileNotFoundException("File '" + n + "' was not found");
        email.addAttachment(n, new DataSource() {
            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(n);
            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return new FileOutputStream(n);
            }

            @Override
            public String getContentType() {
                return m;
            }

            @Override
            public String getName() {
                return n;
            }
        });
        return this;
    }

    public void send(String u, String p, String h, int po) {
        properties.put("mail.smtp.host", h);
        properties.put("mail.smtp.port", po);
        Mailer mailer = new Mailer(Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(u, p);
            }
        }));
        Session session = mailer.createMailSession(h, po, u, p);
        mailer.sendMail(email);
        properties.clear();
        try {
            session.getTransport().close();
            session.getStore().close();
            session.getDebugOut().flush();
            session.getDebugOut().close();
            session.getProperties().clear();
        } catch (Exception e) {
            Logger.logERROR(log);
        }
    }
}
