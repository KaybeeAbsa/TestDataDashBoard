package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import com.jagacy.util.JagacyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/SignOffViewForm")
public class SitSignFormViewController {

    @Autowired
    SitSignOffService service;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/SignOffView/{reEmail}/{reInterfaceArea}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> viewSignOff(@PathVariable String reEmail, @PathVariable String reInterfaceArea) throws JagacyException, Exception {


        ArrayList<SITSignOff> viewDetails = service.findCaptureInformation(reEmail.toUpperCase(), reInterfaceArea.toUpperCase());

        if(viewDetails.isEmpty())
        {
            throw new DataNotFoundException("Incorrect Change/Project Name, Enter Valid Change/Project Name.");
        }

        return viewDetails;
    }

    @RequestMapping(value = "/SignOffViewAllForms", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllFormDeatils() {

        Object viewDetails = service.findAllCaptureInformation();

        if(viewDetails ==null)
        {
            throw new DataNotFoundException("We do not have any available captured Sign Off forms.");
        }

        return viewDetails;
    }

    @RequestMapping(value = "/SignOffViewCaptureEmail/{reEmail}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> viewSignOff(@PathVariable String reEmail){

        ArrayList<SITSignOff> viewDetails = service.findCaptureInformation(reEmail.toUpperCase());

        if(viewDetails.isEmpty())
        {
            throw new DataNotFoundException("Incorrect Change/Project Name, Enter Valid Change/Project Name.");
        }

        return viewDetails;
    }
}
