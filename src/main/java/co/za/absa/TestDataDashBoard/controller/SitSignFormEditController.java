package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SendModificationEmail;
import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import com.jagacy.util.JagacyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

@RestController
@RequestMapping(value = "/SitSignOffEditForm")
public class SitSignFormEditController {

    @Autowired
    SitSignOffService service;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/findclients/{reEmail}/{tokenId}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> findClient(@PathVariable String reEmail,@PathVariable String tokenId) {

        String reStatus = "Pending";
        ArrayList<SITSignOff> clientFound = service.findClientUsingEmail(reEmail.toUpperCase(), reStatus,Integer.parseInt(tokenId));

        if(clientFound.isEmpty())
        {
            throw new DataNotFoundException("Incorrect Token or Token Approved already, please use Token provided on email.");
        }
        return clientFound;
    }

    @RequestMapping(value = "/SitSignOffEdit/{reChangeName}/{reChangeNumber}/{reContactPerson}/{reImplDate}/{reSystemCompleted}/{reInterfaceArea}/{reProjectManager}/{reContactEmail}/{reTower}/{reStatus}/{resignOffID}", method = RequestMethod.GET)
    @ResponseBody
    public int signOffs(@PathVariable String reChangeName, @PathVariable String reChangeNumber, @PathVariable String reContactPerson, @PathVariable String reImplDate, @PathVariable String reSystemCompleted, @PathVariable String reInterfaceArea, @PathVariable String reProjectManager, @PathVariable String reContactEmail, @PathVariable String reTower, @PathVariable String reStatus, @PathVariable String resignOffID) throws JagacyException, Exception {

        SendModificationEmail sendSignOffEmail=new SendModificationEmail();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        int signOff=Integer. parseInt(resignOffID);

        String status;

        if(reStatus.equalsIgnoreCase("Approved")){
            status="Pending";
        }else{
            status=reStatus;
        }

      int updated =   service.updatedSignOff(status,reChangeName.toUpperCase(),reChangeNumber,reProjectManager,reTower,reSystemCompleted,reContactPerson,reImplDate,reContactEmail.toUpperCase(),reInterfaceArea,signOff);

        if(updated != 1){
            throw new DataNotFoundException("Sign-Off Details have not been Updated.");
        }

        if(reStatus.equalsIgnoreCase("Approved")){

            String message = "Good Day," +" \r\n" + " please note A Modidification was made for" + reInterfaceArea + " Interface Area kindly please reauthorize"+ "\r\n" + "http://testdataportal.absa.africa:8081/TestDataDashBoard/login";
            String subject = "Authorizations To Actions";
            smtpMailSender.sendMails(reContactEmail,subject, message);

            //come back n modify
            sendSignOffEmail.sendTestDataToEmail(reContactEmail, reProjectManager,signOff);
        }

        return updated;
    }

    //Upload PDF
    @RequestMapping(value = "/UploadPDF/{signOffToken}/{documentPDF}", method = RequestMethod.GET)
    @ResponseBody
    public int uploadPdfFiles(@PathVariable String[] signOffToken,@PathVariable String documentPDF) {

        //System.out.println("Inside Upload PDF");
        int updated= 0;

        for(int x = 0; x < signOffToken.length; x++){
         //  System.out.println(signOffToken[x]);
        //   System.out.println(documentPDF);
            updated =   service.uploadPdfFiles(documentPDF, Integer.parseInt(signOffToken[x]));
      //    System.out.println("Updated: " +updated);
        }

        return updated;
    }


}
