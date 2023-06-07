package co.za.absa.TestDataDashBoard.Methods;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SmtpMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMails(String email, String subject, String comment){

        final String username = "Karabo.serope@absa.africa";
        final String password = "quantIty72";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.absa.co.za");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("Test.Data.Management@absa.africa"));
            message.setSubject(subject);
            message.setText(comment);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
