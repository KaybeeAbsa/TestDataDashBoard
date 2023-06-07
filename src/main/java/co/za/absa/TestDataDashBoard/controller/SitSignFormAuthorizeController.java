package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/Authorization")
public class SitSignFormAuthorizeController {


    @Autowired
    SitSignOffService service;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/findClient/{reEmail}/{tokenId}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> findClient(@PathVariable String reEmail,@PathVariable String tokenId) {

        String reStatus = "Pending";
        ArrayList<SITSignOff> clientFound = service.findAuthorizations(reEmail.toUpperCase(), reStatus ,Integer.parseInt(tokenId));

        System.out.println("Size: "+ clientFound.size());

        if(clientFound.isEmpty())
        {
            System.out.println("Inside is Empty");
            throw new DataNotFoundException("Incorrect Token or Token Approved already, please use Token provided on email.");
        }

        return clientFound;
    }

    @RequestMapping(value = "/approveStatus/{reapproval}/{tokeId}", method = RequestMethod.GET)
    @ResponseBody
    public int updateStatus(@PathVariable String reapproval, @PathVariable String tokeId)
    {
        int updated =  service.updatedStatusColumn(reapproval,Integer.parseInt(tokeId));

        if(updated != 1){
            throw new DataNotFoundException("Approval Status have not been Updated.");
        }
        return updated;
    }

}
