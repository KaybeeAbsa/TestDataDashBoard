package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.*;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.Combis;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.model.Users;
import co.za.absa.TestDataDashBoard.services.ClientCreationService;
import co.za.absa.TestDataDashBoard.services.CombiService;
import co.za.absa.TestDataDashBoard.services.UserService;
import com.jagacy.util.JagacyException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.testng.annotations.IFactoryAnnotation;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(value = "/testCard")

public class TestCardsController {

    @Autowired
    private ClientCreationService service;

    @Autowired
    private UserService users;

    @Autowired
    private CombiService combiService;

    @Autowired
    SmtpMailSender smtpMailSender;


    private ClientCreationJagacy jagacyMethods = null;
    private String jagacyMessage;
    private ArrayList<String> testData;
    private ArrayList<Newtobank> Ntbdata = new ArrayList<>();
    private String combiNu;
    private String reComment;
    private String subject;
    int int_random = ThreadLocalRandom.current().nextInt();
    private String bureauType;
    private String dataScore;
    private String clientKey;
    private String bureauScore;
    private String account;
    private ArrayList<Newtobank> client;
    private ArrayList<Combis> listCombi = new ArrayList<>();
    private ArrayList<Users> usersList;
    private String password = null;
    private String username = null;
    private String physicalCombiNumber = null;

    private String sourceFund = "20";
    @RequestMapping(value = "/createNewClient/{quantity}/{used}/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{reScore}/{reProduct}/{reClientType}/{reProductCode}/{reCombi}/{reTransction}/{reBrand}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Newtobank> createClient(@PathVariable int quantity, @PathVariable String used, @PathVariable String reName, @PathVariable String reTeam, @PathVariable String reEmail, @PathVariable String reMobile, @PathVariable String reEnviroment, @PathVariable String reScore, @PathVariable String reProduct, @PathVariable String reClientType, @PathVariable String reProductCode, @PathVariable String reCombi, @PathVariable String reTransction, @PathVariable String reBrand) throws JagacyException, Exception {

        listCombi.clear();
        Ntbdata.clear();
        int remainingQuantity = quantity;
        int data = 0;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateOnly.format(cal.getTime());
        System.out.println(reBrand);
        ArrayList<Newtobank> requestor = service.findRequestorsDetails(reName, date);

        if (requestor.size() > 0) {
            if (requestor.size() >= 45) {
                reComment = "Hi Team, " + "\n" + reName + " Has reached his Daily Limit, please contact him for alignments " + "\n" + " Contact me on: " + reMobile;
                subject = "Test Data DashBoard Request LIMIT Reached " + reName + " From " + reTeam;
                smtpMailSender.sendMails(reEmail, subject, reComment);
                throw new DataNotFoundException("Hi " + reName + "\n" + " You have exceeded the daily limit with for this specific data category." + "\n" + "kindly contact Test.Data.Management@absa.africa");
            }
        }

        String[] splitScore = reScore.split(",");

        dataScore = splitScore[0];
        bureauType = splitScore[1];

        if (dataScore.equalsIgnoreCase("high")) {
            client = service.findNewToBankByHighScore(used,bureauType,600);
        } else if (dataScore.equalsIgnoreCase("low")) {
            client = service.findNewToBankByLowScore(used, bureauType, 600);
        }

        if (client != null) {

            if (client.size() < quantity) {
                quantity = client.size();
                // System.out.println("We have " + quantity + " number of records remaining");
                throw new DataNotFoundException("You have Requested for :" + remainingQuantity + " Client Profiles and we have : " + quantity + " Number of records remaining...");
            }

            //try{
            for (int x = 0; x < quantity; x++) {

                String usedValue = "YES";
                String idtype = client.get(x).getIdtype();
                String lastName = client.get(x).getSurname();
                String firstName = client.get(x).getName();
                String secondName = client.get(x).getSecondname();

                CreateClientAPI createClientAPI = new CreateClientAPI();

                String initials = "";

                if (secondName.isEmpty()) {
                    initials = firstName.substring(0, 1);
                } else {
                    firstName = firstName + " " + secondName;
                    initials = firstName.substring(0, 1) + secondName.substring(0, 1);
                }

                if(reClientType.equalsIgnoreCase("00101") || reClientType.equalsIgnoreCase("00104")){
                    clientKey = createClientAPI.createClient("01", firstName, lastName, idtype, reClientType, reEmail, initials,reMobile,"","");
                }else if(reClientType.equalsIgnoreCase("00103")){
                    String passport = "KLM"+getRandomNumberString();
                    clientKey = createClientAPI.createClient("03", firstName, lastName, idtype, "00101", reEmail, initials,reMobile,passport,"SO003");
                }else{
                    //Business
                }

                //timeout api
                if (clientKey != null) {

                    boolean duplicate_profile_exits = !(clientKey.equalsIgnoreCase("DUPLICATE PROFILE EXITS"));
                    if (duplicate_profile_exits) {
                        account = createClientAPI.CQCreateAndLinkAccountV9(clientKey, reProductCode, sourceFund);
                    } else {
                        data = service.updatedUsedColumn(usedValue, "No Date", reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                        if (quantity != client.size()) {
                            quantity++;
                            continue;
                        }
                    }

                } else {

                    usersList = users.findUsers();

                    for (int y = 0; y < usersList.size(); y++) {

                        try {

                            if (usersList.get(y).getUsername().equalsIgnoreCase("abks580")) {
                                password = usersList.get(y).getPassword();
                                username = usersList.get(y).getUsername();
                            } else {
                                password = "DD";
                                username = "DD";
                            }

                            //  System.setProperty("sessionA.window", "true");
                            jagacyMethods = new ClientCreationJagacy();
                            jagacyMethods.open();

                            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                            if (y + 1 == usersList.size() && jagacyMessage.length() != 0) {
                                if (quantity != client.size()) {
                                    quantity++;
                                }

                                jagacyMethods.close();
                                reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                                subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                                smtpMailSender.sendMails(reEmail, subject, reComment);
                                throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
                            }

                            if (jagacyMessage.length() == 0) {

                                if (reClientType.equalsIgnoreCase("00101") || reClientType.equalsIgnoreCase("00401")) {
                                    jagacyMessage = jagacyMethods.IndividualClientsBoarding(idtype, lastName, firstName, secondName, reEmail, reMobile, reClientType, reProductCode);
                                } else {

                                    String companyName = null;
                                    String businessRegistration = null;
                                    String productCode = "";
                                    if (reProduct.equalsIgnoreCase("ExCheque")) {
                                        productCode = "11020";
                                    } else if (reProduct.equalsIgnoreCase("ExSavings")) {
                                        productCode = "09074";
                                    }

                                    if (reClientType.equalsIgnoreCase("03001")) {
                                        companyName = "IMPERATIVE " + int_random + " TECHNOLOGYs";
                                        businessRegistration = "2009/012778/07";
                                    } else if (reClientType.equalsIgnoreCase("03101")) {
                                        companyName = "The " + int_random + " CC";
                                        businessRegistration = "1989/041625/23";
                                    } else if (reClientType.equalsIgnoreCase("02301")) {
                                        companyName = int_random + " GROUPS PTY LTD";
                                        businessRegistration = "1989/003502/07";
                                        System.out.println("Inside business client creation");
                                    } else if (reClientType.equalsIgnoreCase("03201")) {
                                        companyName = int_random + "GROUPS LTD";
                                        businessRegistration = "2000/824538/10";
                                    }
                                    jagacyMessage = jagacyMethods.BusinessClientsOnBoarding(companyName, "n", "05", businessRegistration, reClientType, reMobile, firstName, reEmail, productCode);
                                }

                                System.out.println("Client created: " + jagacyMessage);

                                if (jagacyMessage.equalsIgnoreCase("") || jagacyMessage.equalsIgnoreCase("APPLICATION NUMBER REQUIRED") || jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("INVALID SITE FOR CAPTURE") || jagacyMessage.equalsIgnoreCase("ABSA ISLAMIC PRIVATE ACCOUNT MAY ONLY BE OPENED BY") || jagacyMessage.equalsIgnoreCase("ACC TYPE ONLY VALID FOR ABSA WEALTH SITES") || jagacyMessage.equalsIgnoreCase("ACCOUNT TYPE AND BANKING SECTOR INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("STRUCTURED LOAN MAY ONLY BE OPENED BY A PRIVATE BA") || jagacyMessage.equalsIgnoreCase("INVALID CATEGORY") || jagacyMessage.equalsIgnoreCase("client already exists") || jagacyMessage.equalsIgnoreCase("BIRTH DATE NOT THE SAME AS IN ID NUMBER") || jagacyMessage.equalsIgnoreCase("INVALID CHARACTERS IN NAME") || jagacyMessage.equalsIgnoreCase("DATE ISSUED CANNOT BE LESS THAN DATE OF BIRTH") || jagacyMessage.equalsIgnoreCase("ID NUMBER INVALID") || jagacyMessage.equalsIgnoreCase("CLIENT TYPE AND ACCOUNT TYPE INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("INCOMPATIBLE CATEGORY AND DATE OF BIRTH")) {

                                    if (jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("client already exists")) {
                                        data = service.updatedUsedColumn(usedValue, "No Date", reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                                    }

                                    if (quantity != client.size()) {
                                        quantity++;
                                        continue;
                                    }

                                    if (quantity == client.size()) {
                                        reComment = "Hi Team, " + "\n" + " We are out of NEW TO BANK clients ID numbers.." + "\n" + " Contact me on: " + reMobile;
                                        subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                                        smtpMailSender.sendMails(reEmail, subject, reComment);
                                        jagacyMethods.close();
                                        throw new DataNotFoundException("We are out of New To Bank ID Numbers, an email has been forwarded to Test Data Team...");
                                    }
                                    jagacyMethods.close();
                                    break;
                                }

                                String[] spiltAcc = jagacyMessage.split(",");
                                String accountNo = spiltAcc[0];
                                String clientCode = spiltAcc[1];

                                System.out.println("Account N0: " + accountNo + " ClientCode: " + clientCode);
                                int messageLeangth = accountNo.length();
                                account = accountNo.substring(25, messageLeangth);

                                System.out.println("Account No: " + account);
                                jagacyMethods.close();
                                // break;

                            } else {
                                jagacyMethods.close();
                            }
                        } catch (JagacyException io) {
                            if (quantity != client.size()) {
                                quantity++;
                                jagacyMethods.close();
                            }
                            if (client.size() == quantity) {
                                reComment = "Hi Team, " + "\n" + " We are out of NEW TO BANK clients ID numbers.." + "\n" + " Contact me on: " + reMobile;
                                subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                                smtpMailSender.sendMails(reEmail, subject, reComment);
                                throw new DataNotFoundException("We are out of New To Bank ID Numbers, an email has been forwarded to Test Data Team...");
                            }
                            break;
                        }
                    }
                    jagacyMethods.close();

                    if (client.size() == x) {
                        reComment = "Hi Team, " + "\n" + " We are out of NEW TO BANK clients ID numbers.." + "\n" + " Contact me on: " + reMobile;
                        subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                        smtpMailSender.sendMails(reEmail, subject, reComment);
                        throw new DataNotFoundException("We are out of New To Bank ID Numbers, an email has been forwarded to Test Data Team...");
                    }
                }

                CCDebitCardIssuanceV1 ccDebitCardIssuanceV1=new CCDebitCardIssuanceV1();
                physicalCombiNumber=ccDebitCardIssuanceV1.getCombies(account,clientKey,reBrand,reProductCode,firstName);

                //System out of API'S and Mainframe
                if(reTransction.equalsIgnoreCase("yes")) {
                    usersList = users.findUsers();

                    for (int y = 0; y < usersList.size(); y++) {

                        if (usersList.get(y).getUsername().equalsIgnoreCase("abks580")) {
                            password = usersList.get(y).getPassword();
                            username = usersList.get(y).getUsername();
                        } else {
                            password = "DD";
                            username = "DD";
                        }

                        jagacyMethods = new ClientCreationJagacy();
                        jagacyMethods.open();

                        jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                        if (y + 1 == usersList.size() && jagacyMessage.length() != 0) {
                            if (quantity != client.size()) {
                                quantity++;
                            }

                            jagacyMethods.close();
                            reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                            subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                            smtpMailSender.sendMails(reEmail, subject, reComment);
                            throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
                        }

                        if (jagacyMessage.length() == 0) {

                            if (reProduct.equalsIgnoreCase("Cheque")) {
                                jagacyMessage = jagacyMethods.cashDeposit("FN71 CHEQ", "CD", account, "1000", "01");
                            } else if (reProduct.equalsIgnoreCase("savings")) {
                                jagacyMessage = jagacyMethods.cashDeposit("FINS", "CD", account, "1000", "01");
                            }

                            if (jagacyMessage.equalsIgnoreCase("TRANSACTION PROCESSED")) {
                                System.out.println("Inside successful transaction");

                            }
                        }
                        jagacyMethods.close();
                        break;
                    }

                }

                if (reCombi.equalsIgnoreCase("yes")) {
                    usersList = users.findUsers();

                    for (int y = 0; y < usersList.size(); y++) {
                        listCombi = combiService.findCombi("no", reEnviroment,reProduct);

                        for (int i = 0; i < listCombi.size(); i++) {

                            if (usersList.get(y).getUsername().equalsIgnoreCase("abks580")) {
                                password = usersList.get(y).getPassword();
                                username = usersList.get(y).getUsername();
                            } else {
                                password = "DD";
                                username = "DD";
                            }

                            // System.setProperty("sessionA.window", "true");
                            jagacyMethods = new ClientCreationJagacy();
                            jagacyMethods.open();

                            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                            if (jagacyMessage.length() != 0) {

                            }

                            if (reProduct.equalsIgnoreCase("savings")) {
                                combiNu = listCombi.get(i).getCombi();

                                jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", "", account, "s", "8198", "n", combiNu, reEnviroment);

                                if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists")) {
                                    if (i <= listCombi.size()) {
                                        i++;
                                        jagacyMethods.close();
                                        combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());
                                        continue;
                                    }
                                } else {

                                    combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());

                                    jagacyMessage = jagacyMethods.ChangeLimit("cmmi lmts", combiNu, "2000", "2000", "2000");
                                    account = "[" + account + "," + combiNu + "]";
                                    i = listCombi.size();
                                }


                            } else if (reProduct.equalsIgnoreCase("cheque")) {

                                combiNu = listCombi.get(i).getCombi();
                                System.out.println("Inside Cheque Combi assign: " + combiNu);
                                jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", account, "", "s", "8198", "n", combiNu, reEnviroment);
                                System.out.println("i:" + i + " size: " + listCombi.size() + " Combi number: " + combiNu);

                                if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists")) {
                                    if (i <= listCombi.size()) {
                                        i++;
                                        jagacyMethods.close();
                                        combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());
                                        continue;
                                    }
                                } else if (jagacyMessage.equalsIgnoreCase("DFS057I REQUESTED BLOCK NOT AVAILABLE: CC24BEO    RC=1C") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP  > Data entry too long")) {
                                    account = "[" + account + "]";
                                    reComment = "The assign Combi System is down...";
                                    i = listCombi.size();
                                    jagacyMethods.close();
                                    continue;
                                } else {

                                    combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());

                                    jagacyMessage = jagacyMethods.ChangeLimit("cmmi lmts", combiNu, "2000", "2000", "2000");
                                    account = "[" + account + "," + combiNu + "]";
                                    i = listCombi.size();
                                }
                            }
                            jagacyMethods.close();
                            break;
                        }
                        jagacyMethods.close();
                        break;
                    }
                }

                data = service.updatedUsedColumn(usedValue, date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                System.out.println("User Keys: " + clientKey + " : AccountNo: " + account);
                Newtobank ntb = new Newtobank();

                ntb.setIdtype(client.get(x).getIdtype());
                ntb.setName(client.get(x).getName());
                ntb.setSecondname(client.get(x).getSecondname());
                ntb.setSurname(client.get(x).getSurname());
                ntb.setScore(client.get(x).getScore());
                ntb.setUsed(client.get(x).getUsed());
                ntb.setClientCode(clientKey);
                ntb.setAccountNo(account);
                ntb.setComment(reComment);
                ntb.setBureauType(bureauType);
                ntb.setApiCombi(physicalCombiNumber);


                Ntbdata.add(ntb);
            }
        } else {
            System.out.println("User List Null");
        }

        reComment = "Hi Team, " + "\n" + " I have requested for " + remainingQuantity + " " + reProduct + " " + bureauScore + " Score Test accounts " + "\n" + " Contact me on: " + reMobile;
        subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();
        SendQuotationEmail sendQuotationEmail=new SendQuotationEmail();

        PDFConverter pdfConverter=new PDFConverter();
        saveFile(Ntbdata, reEmail);
        pdfConverter.test(reTeam,reName,quantity,reEmail, reBrand);
        sendQuotationEmail.sendTestDataToEmail(reEmail);
        return Ntbdata;
    }

    private void saveFile(ArrayList<Newtobank> testData, String email) throws Exception {
        // String filename = System.getProperty("user.dir") + "/src/main/resources/temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename = "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("RequestedData");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0", new Object[]{"ID Number", "Surname", "Name", "Second Name", "Score", "Used", "ClientCode", "Account Number", "Comment", "Bureau Type","Physical Combi Number"});
        for (int i = 1; i < testData.size() + 1; i++) {
            Newtobank newtobank = testData.get(i - 1);
            Object[] temp = newtobank.toString1().split(";");
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

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}