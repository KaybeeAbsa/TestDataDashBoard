package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
@RestController
@RequestMapping(value = "/specialRequest")
public class SpecialRequestController{

        @Autowired
        SmtpMailSender smtpMailSender;

        @RequestMapping(path = "/request/{reName}/{reTeam}/{reMail}/{reMobile}/{reBureau}/{reComment}")
        public void sendEmail(@PathVariable String reName, @PathVariable String reTeam, @PathVariable String reMail, @PathVariable String reMobile, @PathVariable String reBureau , @PathVariable String reComment) throws MessagingException {
             String message = "Hi Team," +" \r\n" + " please send my request to: "+ reBureau + "\r\n" + reComment + "\r\n" + " Contact me on: " + reMobile;
             String subject = "Special Request to " + reBureau + " for " + reName + " From " + reTeam + " Team";
             smtpMailSender.sendMails(reMail,subject, message);
        }

}
