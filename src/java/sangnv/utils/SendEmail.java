/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Shang
 */
public class SendEmail {

    private static final String FROM = "system.simpleblog@gmail.com";
    private static final String PASSWORD = "Dcvfasxz1357";
    private static final String CONFIRM_URL = "http://localhost:8084/SimpleBlog/confirmPage";

//    private static final String HOST = "localhost";

    public static void sendMail(String to, String msg) throws RuntimeException, AddressException, MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        properties.setProperty("mail.smtp.host", HOST);
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);
        // Set From: header field of the header.
        message.setFrom(new InternetAddress(FROM));
        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        // Set Subject: header field
        message.setSubject("Your confirmation code from Simple Blog register");
        // Send the actual HTML message, as big as you like
        message.setContent(
                "<h1>" + msg + "</h1><br/>"
                + "<h5>Please input your confirmation code to the web link:</h5>"
                + "<a href=\"" + CONFIRM_URL + "\">Click here</a>",
                "text/html");

        // Send message  
        Transport.send(message);
        System.out.println("message sent successfully....");

    }

}
