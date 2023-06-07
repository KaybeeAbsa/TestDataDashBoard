package co.za.absa.TestDataDashBoard.Methods;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendQuotationEmail {
    public void sendTestDataToEmail(String email){


        System.out.println(email);
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
        // String filename= System.getProperty("user.dir") + "/src/main/resources/temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename= "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename1= "C:/Temp/Quotation" + email.split("@")[0] + ".pdf";

        //String fileName1 = "C:/Temp/Quotation.pdf";

        try {


            BodyPart messageBodyPart1 = new MimeBodyPart();
            BodyPart messageBodyPart2 = new MimeBodyPart();
            BodyPart messageBodyPart3 = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Test.Data.Management@absa.africa"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("hcs@absa.africa"));
            message.setSubject("Test Data And Quotation");

            messageBodyPart1.setText("Good day, "+"\n\n"+" Find attached requested test data and Quotation. \n\n Kind regards \n " +
                    "Test Data Management Team");


            DataSource source = new FileDataSource(filename);
            DataSource source1 = new FileDataSource(filename1);

            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("RequstedTestData.xls");

            messageBodyPart3.setDataHandler(new DataHandler(source1));
            messageBodyPart3.setFileName("Quotation.pdf");



            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            multipart.addBodyPart(messageBodyPart3);
            message.setContent(multipart);
            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        deleteTheFile(filename);
        deleteTheFile(filename1);
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
