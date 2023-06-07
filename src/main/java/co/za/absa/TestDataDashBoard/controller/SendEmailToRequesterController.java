package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.services.SendEmailToRequestorServiceImpl;
import co.za.absa.TestDataDashBoard.services.sendEmailToRequestorService.CheckFile;
import co.za.absa.TestDataDashBoard.services.sendEmailToRequestorService.SendEmailToRequestorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;

@Controller
public class SendEmailToRequesterController {




    @PostMapping("/fileUpload")
    public RedirectView getFileFromForm()
    {
        String file = "src\\main\\resources\\templates\\temp\\RequstedTestData.xls";
        SendEmailToRequestorService sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();
        CheckFile checkFile = new CheckFile();

        {
            sendEmailToRequestorService.sendMail("Karabo.serope@absa.africa","Karabo.serope@absa.africa","TDM ",file);
            //deleteTheFile(file);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8081/TestDataDashBoard/home");
        return redirectView;
    }


    private void deleteTheFile(String file) {
        File file1 = new File(file);
        if(file1.exists())
        {
            file1.delete();
        }
    }





}
