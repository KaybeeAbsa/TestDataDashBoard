package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.Methods.SendEmailToRequestorServiceImpl;
import co.za.absa.TestDataDashBoard.model.ManualRequest;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.services.ManualRequestService;
import com.jagacy.util.JagacyException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RestController
@RequestMapping(value = "/manualRequest")
public class ManualRequestController {

    @Autowired
    private ManualRequestService service;


    private ManualRequest manualRequest = new ManualRequest();

    private  ArrayList<ManualRequest> listManual = new ArrayList<>();

    @RequestMapping(value = "/manual/{reQuantity}/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{reScore}/{reProduct}/{reClientType}/{rePreApproval}/{reCombi}/{reComment}", method = RequestMethod.GET)
    @ResponseBody
    public ManualRequest createClient(@PathVariable int reQuantity, @PathVariable String reName, @PathVariable String reTeam, @PathVariable String reEmail, @PathVariable String reMobile, @PathVariable String reEnviroment, @PathVariable String reScore, @PathVariable String reProduct, @PathVariable String reClientType, @PathVariable String rePreApproval, @PathVariable String reCombi, @PathVariable String reComment) throws JagacyException, Exception {

        manualRequest.setRequestQuantity(reQuantity);
        manualRequest.setRequestorName(reName);
        manualRequest.setRequestorTeam(reTeam);
        manualRequest.setRequestorEmail(reEmail);
        manualRequest.setRequestorMobile(reMobile);
        manualRequest.setRequestEnviroment(reEnviroment);
        manualRequest.setRequestScore(reScore);
        manualRequest.setRequestProduct(reProduct);
        manualRequest.setRequestClientType(reClientType);
        manualRequest.setRequestPreApproved(rePreApproval);
        manualRequest.setRequestCombi(reCombi);
        manualRequest.setRequestComment(reComment);

        manualRequest  =   service.saveRequest(manualRequest);

        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();

        listManual.add(manualRequest);
        saveFile(listManual, reEmail);
        sendEmailToRequestorService.sendManualRequestTestDataToEmail(reEmail, reName);

        return manualRequest;
    }

    private void saveFile(ArrayList<ManualRequest> testData, String email) throws Exception {
        // String filename = System.getProperty("user.dir") + "/src/main/resources/temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename = "C:/Temp/ManualTestDataTemplate" + email.split("@")[0] + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ManualTestDataTemplate");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0", new Object[]{"Requestor Name", "Requestor Team", "Requestor Email", "Requestor Mobile","Request Quantity","Request Score", "Request Enviroment","Request Product","Request ClientType","Request PreApproved","Request Combi","Request Comment"});
        for (int i = 1; i < testData.size() + 1; i++) {
            ManualRequest manual = testData.get(i - 1);
            Object[] temp = manual.toString().split(";");
            data.put("" + i, temp);
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(filename));
            workbook.write(out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
