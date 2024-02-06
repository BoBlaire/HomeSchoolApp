package com.example.gfgapp.email;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PDFSender {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public PDFSender(final String user, final String password) {
        this.user = user;
        this.password = password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(props,
                new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                user, password);// Specify the Username and the PassWord
                    }
                });
    }
    /*
        protected PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(user, password);
        }
    */
    public synchronized void sendMail(String subject, String body,
                                      String sender, String recipients, String filePath) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler((DataSource) new ByteArrayDataSource(body.getBytes(), "text/plain"));
        DataSource source = new FileDataSource(filePath);


        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setDataHandler(new DataHandler(source));
        message.setFileName("Student Hour's.pdf");

        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

        Transport.send(message);
    }
}
