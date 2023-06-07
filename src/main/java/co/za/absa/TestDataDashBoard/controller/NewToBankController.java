package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.CIgetClientDetailsByIDNOV20;
import co.za.absa.TestDataDashBoard.Methods.SendEmailToRequestorServiceImpl;
import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.Existing;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.services.NewToBankService;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriter;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/ntb")
public class NewToBankController {

    @Autowired
    private NewToBankService service;

    @Autowired
    SmtpMailSender smtpMailSender;
    private ArrayList<Newtobank> testData;
    private ArrayList<Newtobank> Ntbdata = new ArrayList<>();
    private String reComment;
    private String subject;
    private String bureauType;
    private String bureauScore;

    CIgetClientDetailsByIDNOV20 cIgetClientDetailsByIDNOV20 = new CIgetClientDetailsByIDNOV20();


    @RequestMapping(value = "/ntbclients/{quantity}/{score}/{used}/{name}/{team}/{email}/{mobile}", method = RequestMethod.GET)
    public ArrayList<Newtobank> findNewToBankClients(@PathVariable int quantity, @PathVariable String score, @PathVariable String used, @PathVariable String name, @PathVariable String team, @PathVariable String email, @PathVariable String mobile) throws Exception {
        Ntbdata.clear();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateOnly.format(cal.getTime());

        String[] splitScore = score.split(",");

        bureauScore = splitScore[0];
        bureauType = splitScore[1];

        if (bureauScore.equalsIgnoreCase("high")) {
            testData = service.findNewToBankByHighScore(used, bureauType, 580);
        } else if (bureauScore.equalsIgnoreCase("low")) {
            testData = service.findNewToBankByLowScore(used, bureauType, 580);
        }

        ArrayList<Newtobank> requestor = service.findRequestorsDetails(name, date);

        if (requestor.size() > 45) {
            if (requestor.size() >= 2) {
                reComment = "Hi Team, " + "\n" + name + " Has reached his Daily Limit, please contact him for alignments " + "\n" + " Contact me on: " + mobile;
                subject = "Test Data DashBoard Request LIMIT Reached " + name + " From " + team;
                smtpMailSender.sendMails(email, subject, reComment);
                throw new DataNotFoundException("Hi " + name + "\n" + " You have exceeded the daily limit with for this specific data category. " + "\n" + "kindly contact Test.Data.Management@absa.africa");
            }
        }

        int noAccounts = quantity;

        if (testData.size() < quantity) {
            int testRemaing = quantity = testData.size();
            reComment = "Hi Team, " + "\n" + " I have requested for :" + noAccounts + " NEW TO BANK " + bureauScore + " Score Clients ID's and there's only " + testRemaing + " clients ID's";
            subject = "Test Data DashBoard Request" + name + " From " + team;
            smtpMailSender.sendMails(email, subject, reComment);
            throw new DataNotFoundException("You have Requested for :" + noAccounts + " Client Profiles and we have : " + testRemaing + " Number of records remaining...");
        }

        for (int x = 0; x <= quantity - 1; x++) {


            //Check if clients Exists.
             String clientKey[] = cIgetClientDetailsByIDNOV20.getClientDetailsById(testData.get(x).getIdtype(), "1", "I", "IMSV");


             if(clientKey[0].equalsIgnoreCase( "" ) || clientKey[0] == null)
            {

                service.updatedUsedColumn("YES", date, name, team, email, mobile, testData.get(x).getNewtobankid());
                Newtobank ntb = new Newtobank();
                ntb.setIdtype(testData.get(x).getIdtype());
                ntb.setName(testData.get(x).getName());
                ntb.setSecondname(testData.get(x).getSecondname());
                ntb.setSurname(testData.get(x).getSurname());
                ntb.setScore(testData.get(x).getScore());
                ntb.setUsed(testData.get(x).getUsed());
                ntb.setBureauType(bureauType);
                Ntbdata.add(ntb);

            }else{

                service.updatedUsedColumn("YES", date, name, team, email, mobile, testData.get(x).getNewtobankid());
                quantity++;
                continue;
            }
        }

        reComment = "Hi Team, " + "\n" + " I have requested for :" + noAccounts + " NEW TO BANK " + bureauScore + " Score Test accounts";
        subject = "Test Data DashBoard Request " + name + " From " + team;
        smtpMailSender.sendMails(email, subject, reComment);
        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();
        saveFile(Ntbdata, email);
        sendEmailToRequestorService.sendTestDataToEmail(email);

        return Ntbdata;
    }

    private void saveFile(ArrayList<Newtobank> ntbdata, String email) throws Exception {
       String filename = "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("RequestedData");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0",new Object[]{"ID Number","Surname","Name","Second Name","Score","Used","Bureau Type"});
        for (int i=1;i<ntbdata.size()+1;i++) {
            Newtobank newtobank = ntbdata.get(i-1);
            Object[] temp = newtobank.toString().split(";");
            data.put(""+i,temp);
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(filename));
            workbook.write(out);
            out.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
