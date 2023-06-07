package co.za.absa.TestDataDashBoard.Methods;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendSignOffEmail {

    public void sendTestDataToEmail(String email, String ScrumMaster, int token){


       // System.out.println(email);
        final String username = "Karabo.serope@absa.africa";  // like yourname@outlook.com
        final String password = "quantIty72";   // password here

        Properties props = getProperties();


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

            Multipart multipart = new MimeMultipart();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("NoReply@absa.africa"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("SIT Sign Off");

            messageBodyPart1.setText("Good day, "+"\n\n"+"Please note you have authorizations awaiting you to action. "+ "\n"+ " http://testdataportal.absa.africa:8081/TestDataDashBoard "+ "\n\n" +"Please use token " +token+ "\nTo access the authorization request."+"\n\n" +"Kind Regards,"+ "\n" +ScrumMaster);

            multipart.addBodyPart(messageBodyPart1);

            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getProperties()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.absa.co.za");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        return props;
    }


    private void deleteTheFile(String file) {
        File file1 = new File(file);
        if(file1.exists())
        {
            file1.delete();
        }
    }
}

