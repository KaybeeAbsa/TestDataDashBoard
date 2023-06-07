package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import com.jagacy.util.JagacyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Locale;

@RestController
@RequestMapping(value = "/SignOffSitForm")
public class FindAddedSignOffController {

    @Autowired
    SitSignOffService service;

    @Autowired
    SmtpMailSender smtpMailSender;

    @RequestMapping(value = "/SignOff/{reEmail}/{reInterfaceArea}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<SITSignOff> signOffs(@PathVariable String reEmail, @PathVariable String reInterfaceArea) throws JagacyException, Exception {

        String reStatus = "Pending";

        String email=reEmail.toLowerCase();

        int signOff=Integer. parseInt(reInterfaceArea);

        ArrayList<SITSignOff> authorizations = service.findSignOffs(email,signOff);

        System.out.println(authorizations);
        return authorizations;
    }
}
