package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/contact")
public class contactController {

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(path = "/contactUs/{reName}/{reTeam}/{reMail}/{reMobile}/{reComment}")
    public void sendEmail(@PathVariable String reName, @PathVariable String reTeam,@PathVariable String reMail,@PathVariable String reMobile,@PathVariable String reComment) throws MessagingException {
        String message = "Hi Team," + "\n" + reComment + "\n" + " Contact me on: " + reMobile;
        String subject = "Contact " + reName + " From " + reTeam;
        smtpMailSender.sendMails(reMail,subject, message);
    }
}
