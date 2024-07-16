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
    // SMTP server details
    private String mailhost = "smtp.gmail.com";
    private String user; // Gmail username
    private String password; // Gmail password
    private Session session; // Email session

    static {
        // Add JSSE provider for SSL
        Security.addProvider(new JSSEProvider());
    }

    /**
     * Constructor initializes the user credentials and sets up mail properties.
     * @param user     Gmail username.
     * @param password Gmail password.
     */
    public PDFSender(final String user, final String password) {
        this.user = user;
        this.password = password;

        // Set mail properties for Gmail SMTP
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        // Initialize the mail session with authentication
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Return the Gmail username and password for authentication
                return new PasswordAuthentication(user, password);
            }
        });
    }

    /**
     * Sends an email with the given subject, body, sender, recipients, and file attachment.
     * @param subject    Subject of the email.
     * @param body       Body of the email.
     * @param sender     Sender's email address.
     * @param recipients Recipients' email addresses.
     * @param filePath   Path to the file to be attached.
     * @throws Exception If an error occurs while sending the email.
     */
    public synchronized void sendMail(String subject, String body, String sender, String recipients, String filePath) throws Exception {
        // Create a new MimeMessage
        MimeMessage message = new MimeMessage(session);

        // Set the data handler with the email body
        DataHandler handler = new DataHandler((DataSource) new ByteArrayDataSource(body.getBytes(), "text/plain"));

        // Set the data source with the file to be attached
        DataSource source = new FileDataSource(filePath);

        // Set the sender's email address
        message.setSender(new InternetAddress(sender));
        // Set the email subject
        message.setSubject(subject);
        // Attach the file to the email
        message.setDataHandler(new DataHandler(source));
        // Set the filename for the attachment
        message.setFileName("Student Hour's.pdf");

        // Check if there are multiple recipients
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients)); // Set multiple recipients
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients)); // Set single recipient

        // Send the email
        Transport.send(message);
    }
}
