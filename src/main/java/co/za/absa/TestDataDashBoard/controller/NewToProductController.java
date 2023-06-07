package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.model.Existing;
import com.jagacy.util.JagacyException;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/newtoproduct")
public class NewToProductController {

    @RequestMapping(value = "/{quantity}/{used}/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{reScore}/{reProduct}/{reProductCode}/{reCombi}/{rePreApproved}/{reOverDraft}/{reClientType}/{linkedCheque}/{linkedSavings}/{linkedCard}/{linkedPL}/{linkedHM}/{linkedAvaf}/{ecasa}/{policy}", method = RequestMethod.GET)
    @ResponseBody
    public void newToProduct(@PathVariable int quantity, @PathVariable String used, @PathVariable String reName, @PathVariable String reTeam, @PathVariable String reEmail, @PathVariable String reMobile, @PathVariable String reEnviroment, @PathVariable String reScore, @PathVariable String reProduct , @PathVariable String reProductCode, @PathVariable String reCombi, @PathVariable String rePreApproved, @PathVariable String reOverDraft, @PathVariable String reClientType, @PathVariable String linkedCheque, @PathVariable String linkedSavings, @PathVariable String linkedCard, @PathVariable String linkedPL, @PathVariable String linkedHM, @PathVariable String linkedAvaf, @PathVariable String ecasa, @PathVariable String policy) throws JagacyException, InterruptedException, AWTException, ParseException,Exception {




    }
}
