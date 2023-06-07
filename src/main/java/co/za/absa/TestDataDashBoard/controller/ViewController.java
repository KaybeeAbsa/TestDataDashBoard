package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.Methods.SendModificationEmail;
import co.za.absa.TestDataDashBoard.Methods.SendSignOffEmail;
import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import com.jagacy.util.JagacyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/SitSignOffViewForm")
public class ViewController {

    @Autowired
    SitSignOffService service;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/SitSignOffView/{reChangeName}/{reChangeNumber}/{reProjectManager}/{reImplDate}/{reSystemCompleted}/{reInterfaceArea}/{reSitSignOffPerson}/{reScrumMaster}/{reContactEmail}/{reTower}/{reStatus}/{resignOffID}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> signOffs(@PathVariable String reChangeName,@PathVariable String reChangeNumber,@PathVariable String reProjectManager,@PathVariable String reImplDate,@PathVariable String reSystemCompleted,@PathVariable String reInterfaceArea,@PathVariable String reSitSignOffPerson,@PathVariable String reScrumMaster,@PathVariable String reContactEmail,@PathVariable String reTower,@PathVariable String reStatus,@PathVariable String resignOffID) throws JagacyException, Exception {

        SendModificationEmail sendSignOffEmail=new SendModificationEmail();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(reChangeName);
        System.out.println(resignOffID);
        int signOff=Integer. parseInt(resignOffID);

        String status;

        if(reStatus.equalsIgnoreCase("Approved")){
            status="Pending";
        }else{
            status=reStatus;
        }

        service.updatedSignOff(status,reChangeName.toUpperCase(),reChangeNumber,reProjectManager,reTower,reSystemCompleted,reSitSignOffPerson,reImplDate,reContactEmail.toUpperCase(),reInterfaceArea,signOff);

        if(reStatus.equalsIgnoreCase("Approved")){

           // System.out.println("done");

            String message = "Good Day," +" \r\n" + " please note A Modidification was made for" + reInterfaceArea + " Interface Area kindly please reauthorize"+ "\r\n" + "http://localhost:8081/TestDataDashBoard/homepage";
            String subject = "Authorizations To Actions";
            smtpMailSender.sendMails(reContactEmail,subject, message);

            //come back n modify

            sendSignOffEmail.sendTestDataToEmail(reContactEmail, reProjectManager,Integer.parseInt(resignOffID));

        }

        return null;
    }
}
