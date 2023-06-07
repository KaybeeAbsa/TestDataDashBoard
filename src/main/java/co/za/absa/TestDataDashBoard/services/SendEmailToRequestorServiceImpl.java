package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.services.sendEmailToRequestorService.SendEmailToRequestorService;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmailToRequestorServiceImpl implements SendEmailToRequestorService {


    @Override
    public void sendMail(String emailFrom,String email, String subject, String file){

        System.out.println(email);
        final String username = "Karabo.serope@absa.africa";  // like yourname@outlook.com
        final String password = "quantIty72";   // password here

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.absa.co.za");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {


            BodyPart messageBodyPart1 = new MimeBodyPart();
            BodyPart messageBodyPart2 = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("kamogelo.motsinoni@absa.africa"));
            message.setSubject(subject);

            messageBodyPart1.setText("Good day, "+"/n"+" Attached find the data you requested");


            String filename = "src/main/resources/temp/RequstedTestData.xls";
            DataSource source = new FileDataSource(file);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("RequstedTestData.xls");


            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
