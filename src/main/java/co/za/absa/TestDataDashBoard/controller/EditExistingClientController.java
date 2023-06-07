package co.za.absa.TestDataDashBoard.controller;


import co.za.absa.TestDataDashBoard.Methods.*;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.Combis;
import co.za.absa.TestDataDashBoard.model.Existing;
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
import org.json.JSONException;
import org.openqa.selenium.support.ui.Sleeper;
import org.postgresql.translation.messages_bg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/EditExistingClients")
public class EditExistingClientController {
    @Autowired
    private UserService users;

    @Autowired
    SmtpMailSender smtpMailSender;

    @Autowired
    private CombiService combiService;
    @Autowired
    private ClientCreationService service;
    private ClientCreationJagacy jagacyMethods = null;
    private String jagacyMessage;
    private String combiNu;
    private String reComment;
    private String subject;
    private String product;
    private String mrti;

    private ArrayList<Newtobank> client;
    private String password;
    private String username ;

    private String account;

    private  Newtobank ntb = new Newtobank();
    ArrayList<Combis> listCombi = new ArrayList<>();

    private InitiateMandates initiateMandates = new InitiateMandates();
    private ArrayList<Newtobank> Ntbdata = new ArrayList<>();

    private preAssessedLimits preAssessedLimits = new preAssessedLimits();
    private  ArrayList<Users> usersList;

    @RequestMapping(value = "/EditClients/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{editClientType}/{editType}/{editClientData}/{reEditProduct}/{reSelectedProduct}/{reProductCode}/{reCombi}/{reTransactionType}/{reTransactionAmount}/{reTransction}/{reMandateAccountNo}/{reMandateAccountType}/{reMandateIDNumber}/{reColltnAmt}/{authType}/{authInputType}/{removeholdType}/{preClidentCode}/{preSalary1}/{preSalary2}/{preSalary3}/{preAvarageSalary}/{preGrossSalary}/{preNetSalary}/{removeholdAccountNo}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Newtobank> EditClients(@PathVariable String reName, @PathVariable String reTeam, @PathVariable String reEmail, @PathVariable String reMobile, @PathVariable String reEnviroment, @PathVariable String editClientType, @PathVariable String editType, @PathVariable String editClientData,@PathVariable String reEditProduct, @PathVariable String reSelectedProduct, @PathVariable String reProductCode, @PathVariable String reCombi, @PathVariable String reTransactionType, @PathVariable String reTransactionAmount,@PathVariable String reTransction,@PathVariable String reMandateAccountNo,@PathVariable String reMandateAccountType, @PathVariable String reMandateIDNumber,@PathVariable String reColltnAmt,@PathVariable String authType,@PathVariable String authInputType,@PathVariable String removeholdType,@PathVariable String preClidentCode,@PathVariable String preSalary1,@PathVariable String preSalary2,@PathVariable String preSalary3,@PathVariable String preAvarageSalary,@PathVariable String preGrossSalary,@PathVariable String preNetSalary,@PathVariable String removeholdAccountNo) throws Exception {

        Ntbdata.clear();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateOnly.format(cal.getTime());

     System.out.println("Edit Product Type: " + reEditProduct + " Edit Client Data: " + editClientData + " Edit Client Type: " + editClientType + " Transaction Type: " + reTransactionType + " Transaction Amount: " + reTransactionAmount);

     if(editType.equalsIgnoreCase("preAssessed")) {

      preAssessedLimits.prePropulation(preClidentCode,preSalary1,preSalary2,preSalary3,preAvarageSalary,preGrossSalary,preNetSalary);

      String verify = preAssessedLimits.verifyPreAssessedLimits(preClidentCode);

      if(verify.contains(preSalary1)){
       //   System.out.println("Passed");
          ntb.setClientCode(preClidentCode);
          ntb.setComment("Pre-Assessed Values have been added.");

      }else{
          throw new DataNotFoundException("Pre-Assessed Values Not Loaded, Use a differetnt Client Code..");
      }

     }else if(editType.equalsIgnoreCase("addMandate")){

         if (reMandateAccountType.equalsIgnoreCase("ChequeAcc")) {
             product = "CACC";
         } else if (reMandateAccountType.equalsIgnoreCase("SavingsAcc")) {
             product = "SVGS";
         } else if (reMandateAccountType.equalsIgnoreCase("TranAcc")) {
             product = "TRAN";
         }

         loadMandate(reMandateIDNumber, reName, reMandateAccountNo, product, reColltnAmt);
         mrti = loadMandate(reMandateIDNumber, reName, reMandateAccountNo, product, reColltnAmt);

         account = reMandateAccountNo;
         ntb.setIdtype(reMandateIDNumber);
         ntb.setComment("MRTI: "+  mrti);

     }else if(editType.equalsIgnoreCase("removeHolds")){

         usersList = users.findUsers();
         account = editClientData;

         for (int y = 0; y < usersList.size(); y++) {

             password = usersList.get(y).getPassword();
             username = usersList.get(y).getUsername();
             if (usersList.get(y).getUsername().equalsIgnoreCase("abks580") || usersList.get(y).getUsername().equalsIgnoreCase("ab0158h")) {
                 password = usersList.get(y).getPassword();
                 username = usersList.get(y).getUsername();
             } else {
                 password = "DD";
                 username = "DD";
                 continue;
             }

             // System.setProperty("sessionA.window", "true");
             jagacyMethods = new ClientCreationJagacy();
             jagacyMethods.open();

             jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

             if (jagacyMessage.length() != 0) {
                 jagacyMethods.close();
                 reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                 subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                 smtpMailSender.sendMails(reEmail, subject, reComment);
                 throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
             }

             if (jagacyMessage.length() == 0) {

                 if(removeholdType.equalsIgnoreCase("Cifholds")){

                     jagacyMessage = jagacyMethods.removeCIFHold("CLF", editClientType,removeholdAccountNo).trim();

                     if(jagacyMessage.equalsIgnoreCase("CIF HOLDS REMOVED")){
                         ntb.setComment(jagacyMessage);
                         if(editClientType.equalsIgnoreCase("clientCode")){
                             ntb.setClientCode(removeholdAccountNo);
                         }else{
                             account = removeholdAccountNo;
                         }
                     }else{
                         jagacyMethods.close();
                         throw new DataNotFoundException("CIF HOLDS NOT REMOVED...");
                     }
                 }else if(removeholdType.equalsIgnoreCase("ficholds")){

                     jagacyMessage = jagacyMethods.removeFicHold("ficr", editClientType,removeholdAccountNo).trim();

                     if(jagacyMessage.equalsIgnoreCase("CIF HOLDS REMOVED")){
                         ntb.setComment(jagacyMessage);
                         if(editClientType.equalsIgnoreCase("clientCode")){
                             ntb.setClientCode(removeholdAccountNo);

                         }else{
                             account = removeholdAccountNo;
                         }
                     }else{
                         jagacyMethods.close();
                         throw new DataNotFoundException(jagacyMessage);
                     }
                 }
                 jagacyMethods.close();
             }
             break;
         }

     }else if(editType.equalsIgnoreCase("addAccount")){

         usersList = users.findUsers();
         account = editClientData;

         for (int y = 0; y < usersList.size(); y++) {
             if (usersList.get(y).getUsername().equalsIgnoreCase("abks580") || usersList.get(y).getUsername().equalsIgnoreCase("ab0158h")) {
                     password = usersList.get(y).getPassword();
                     username = usersList.get(y).getUsername();
             } else {
                     password = "DD";
                     username = "DD";
                     continue;
             }

                // System.setProperty("sessionA.window", "true");
                 jagacyMethods = new ClientCreationJagacy();
                 jagacyMethods.open();

                 jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                 if (jagacyMessage.length() == 0) {

                     if(editClientType.equalsIgnoreCase("clientCode"))
                     {
                         jagacyMessage = jagacyMethods.existingCustomerCreation("CIF","A",editClientData,"","888",reProductCode,"1196035","20");
                     }else{
                         jagacyMessage = jagacyMethods.existingCustomerCreation("CIF","A","",editClientData,"888",reProductCode,"1196035","20");
                     }

                     System.out.println("Messages: " + jagacyMessage);

                     if (jagacyMessage.equalsIgnoreCase("") || jagacyMessage.equalsIgnoreCase("NO CLIENT RECORD FOUND E")||jagacyMessage.equalsIgnoreCase("APPLICATION NUMBER REQUIRED") || jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("INVALID SITE FOR CAPTURE") || jagacyMessage.equalsIgnoreCase("ABSA ISLAMIC PRIVATE ACCOUNT MAY ONLY BE OPENED BY") || jagacyMessage.equalsIgnoreCase("ACC TYPE ONLY VALID FOR ABSA WEALTH SITES") || jagacyMessage.equalsIgnoreCase("ACCOUNT TYPE AND BANKING SECTOR INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("STRUCTURED LOAN MAY ONLY BE OPENED BY A PRIVATE BA") || jagacyMessage.equalsIgnoreCase("INVALID CATEGORY") || jagacyMessage.equalsIgnoreCase("client already exists") || jagacyMessage.equalsIgnoreCase("BIRTH DATE NOT THE SAME AS IN ID NUMBER") || jagacyMessage.equalsIgnoreCase("INVALID CHARACTERS IN NAME") || jagacyMessage.equalsIgnoreCase("DATE ISSUED CANNOT BE LESS THAN DATE OF BIRTH") || jagacyMessage.equalsIgnoreCase("ID NUMBER INVALID") || jagacyMessage.equalsIgnoreCase("CLIENT TYPE AND ACCOUNT TYPE INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("INCOMPATIBLE CATEGORY AND DATE OF BIRTH")) {

                         //jagacyMethods.close();
                         reComment = "Hi Team, " + "\n" + " We are not able to add an existing account.."  + "\n" + " Contact me on: " + reMobile ;
                         subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                         smtpMailSender.sendMails(reEmail,subject, reComment);
                         jagacyMethods.close();
                         throw new DataNotFoundException("We are not able to add an existing account...");
                     }else{

                         account = jagacyMessage.trim();

                         jagacyMethods.close();

                         if(reTransction.equalsIgnoreCase("yes")) {
                             usersList = users.findUsers();


                             for (int x = 0; x < usersList.size(); x++) {

                                // System.out.println("Inside Loops: " + y + usersList.size());
                                 if (usersList.get(x).getUsername().equalsIgnoreCase("abks580") || usersList.get(x).getUsername().equalsIgnoreCase("ab0158h")) {
                                     password = usersList.get(x).getPassword();
                                     username = usersList.get(x).getUsername();
                                 } else {
                                     password = "DD";
                                     username = "DD";
                                     continue;
                                 }

                                 System.out.println("User: " + username);
                                 jagacyMethods = new ClientCreationJagacy();
                                 jagacyMethods.open();

                                 jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                                 if (jagacyMessage.length() != 0) {
                                     jagacyMethods.close();
                                     reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                                     subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                                     smtpMailSender.sendMails(reEmail, subject, reComment);
                                     throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
                                 }

                                 if (jagacyMessage.length() == 0) {

                                     if (reSelectedProduct.equalsIgnoreCase("Cheque")) {
                                         jagacyMessage = jagacyMethods.cashDeposit("FN71 CHEQ", "CD", account, "10000", "01").trim();
                                     } else if (reSelectedProduct.equalsIgnoreCase("savings")) {
                                         jagacyMessage = jagacyMethods.cashDeposit("FINS", "CD", account, "1000", "01").trim();
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
                             //  System.out.println("Inside Combi: " + usersList.size());

                             for (int x = 0; x < usersList.size(); x++) {
                                 listCombi = combiService.findCombi("NO", reEnviroment, reEditProduct);
                               //  System.out.println("Combi: " + listCombi.size());

                                 for (int i = 0; i < listCombi.size(); i++) {

                                     if (usersList.get(y).getUsername().equalsIgnoreCase("abks580") || usersList.get(y).getUsername().equalsIgnoreCase("ab0158h")) {
                                         password = usersList.get(y).getPassword();
                                         username = usersList.get(y).getUsername();

                                     } else {
                                         password = "DD";
                                         username = "DD";
                                         y++;
                                         continue;
                                     }

                                    // System.setProperty("sessionA.window", "true");
                                     jagacyMethods = new ClientCreationJagacy();
                                     jagacyMethods.open();

                                     jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                                     // System.out.println("Inside combi Login:" + jagacyMessage);
                                     if (jagacyMessage.length() == 0) {
                                         if (reSelectedProduct.equalsIgnoreCase("savings")) {
                                             combiNu = listCombi.get(i).getCombi();

                                             jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", "", account, "s", "8198", "n", combiNu,reEnviroment);

                                             if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists")  || jagacyMessage.equalsIgnoreCase("CMMI  NEWP Client has no Active Accounts")) {
                                                 if (i <= listCombi.size()) {
                                                     i++;
                                                     jagacyMethods.close();
                                                     combiService.updatedUsedColumn("NO", listCombi.get(i).getCombi());
                                                     continue;
                                                 }
                                             } else if(jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System") || jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System")){
                                                 if (i <= listCombi.size()) {
                                                     i++;
                                                     jagacyMethods.close();
                                                     combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());
                                                     continue;
                                                 }
                                             } else {

                                                 combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());

                                                 jagacyMessage = jagacyMethods.ChangeLimit("cmmi lmts", combiNu, "2000", "2000", "2000");
                                                 if(jagacyMessage.equalsIgnoreCase("Combi Limits not set") || jagacyMessage.equalsIgnoreCase("Card Number Invalid or not Entered               E")){
                                                     ntb.setComment("Combi Limits not set");
                                                 }
                                                 account = "[" + account + "," + combiNu + "]";
                                                 i = listCombi.size();
                                             }


                                         } else if (reSelectedProduct.equalsIgnoreCase("cheque")) {

                                             combiNu = listCombi.get(i).getCombi();
                                             // System.out.println("Inside Cheque Combi assign: " + combiNu);
                                             jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", account, "", "s", "8198", "n", combiNu, reEnviroment);
                                             //   System.out.println("i:" + i + " size: " + listCombi.size() + " Combi number: " + combiNu);
                                             //System.out.print("JAGACY Message combi: " + jagacyMessage);

                                             if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists")  || jagacyMessage.equalsIgnoreCase("CMMI  NEWP Client has no Active Accounts")) {
                                                 if (i <= listCombi.size()) {
                                                     i++;
                                                     jagacyMethods.close();
                                                     combiService.updatedUsedColumn("NO", listCombi.get(i).getCombi());
                                                     continue;
                                                 }
                                             } else if(jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System") || jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System")){
                                                 if (i <= listCombi.size()) {
                                                     i++;
                                                     jagacyMethods.close();
                                                     combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());
                                                     continue;
                                                 }
                                             }else if (jagacyMessage.equalsIgnoreCase("DFS057I REQUESTED BLOCK NOT AVAILABLE: CC24BEO    RC=1C") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP  > Data entry too long")|| jagacyMessage.equalsIgnoreCase("Combi Card not Assigned")) {
                                                 account = "[" + account + "]";
                                                 reComment = "The assign Combi System is down...";
                                                 i = listCombi.size();
                                                 jagacyMethods.close();
                                                 continue;
                                             } else {

                                                 combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());

                                                 jagacyMessage = jagacyMethods.ChangeLimit("cmmi lmts", combiNu, "2000", "2000", "2000");
                                                 if(jagacyMessage.equalsIgnoreCase("Combi Limits not set") || jagacyMessage.equalsIgnoreCase("Card Number Invalid or not Entered               E")){
                                                     ntb.setComment("Combi Limits not set");
                                                 }
                                                 account = "[" + account + "," + combiNu + "]";
                                                 i = listCombi.size();
                                             }
                                         }
                                         jagacyMethods.close();
                                         break;
                                     }else{
                                         account = "[" + account + "]";
                                         ntb.setComment("Combi Not assigned.");
                                         i = listCombi.size();
                                         jagacyMethods.close();
                                         continue;
                                     }
                                 }
                             }
                         }
                     }

                 }else{

                     System.out.println("Error Message Login: " + jagacyMessage);
                 }
                 break;
         }


     }else if(editType.equalsIgnoreCase("assignCombi")){

        // Ntbdata.clear();
         if(editClientType.equalsIgnoreCase("clientCode"))
         {
             jagacyMethods.close();
             reComment = "Hi Team, " + "\n" + " Please enter an Account number for transactions: " + "\n" + " Contact me on: " + reMobile ;
             subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
             smtpMailSender.sendMails(reEmail,subject, reComment);
             throw new DataNotFoundException("We Are unable to Process this request, please capture a valid Account number to add a combi on,  an email has been forwarded to Test Data Team...");
         }

         usersList = users.findUsers();
         account = editClientData;

         for (int y = 0; y < usersList.size(); y++) {
             listCombi = combiService.findCombi("NO", reEnviroment, reEditProduct);
          //   System.out.println("Combi: " + listCombi.size());

             for (int i = 0; i < listCombi.size(); i++) {

                 if (usersList.get(y).getUsername().equalsIgnoreCase("abks580") || usersList.get(y).getUsername().equalsIgnoreCase("ab0158h")) {
                     password = usersList.get(y).getPassword();
                     username = usersList.get(y).getUsername();

                 } else {
                     password = "DD";
                     username = "DD";
                     y++;
                     continue;
                 }

                 // System.setProperty("sessionA.window", "true");
                 jagacyMethods = new ClientCreationJagacy();
                 jagacyMethods.open();

                 jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                 if (jagacyMessage.length() == 0) {
                     if (reEditProduct.equalsIgnoreCase("savings")) {
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
                             if(jagacyMessage.equalsIgnoreCase("Combi Limits not set")|| jagacyMessage.equalsIgnoreCase("Card Number Invalid or not Entered               E")){
                                 ntb.setComment("Combi Limits not set");
                             }
                             account = "[" + account + "," + combiNu + "]";
                             i = listCombi.size();
                         }

                     } else if (reEditProduct.equalsIgnoreCase("cheque")) {

                         combiNu = listCombi.get(i).getCombi();

                         jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", account, "", "s", "8198", "n", combiNu,reEnviroment).trim();
                         System.out.println("i:" + i + " size: " + listCombi.size() + " Combi number: " + combiNu);

                         // jagacyMethods.close();
                         if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists")) {
                             if (i <= listCombi.size()) {
                                 i++;
                                 jagacyMethods.close();
                                 combiService.updatedUsedColumn("YES", listCombi.get(i).getCombi());
                                 continue;
                             }
                         } else if (jagacyMessage.equalsIgnoreCase("DFS057I REQUESTED BLOCK NOT AVAILABLE: CC24BEO    RC=1C") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP  > Data entry too long") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Client has Active Savings Acc, please Nominate") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("Combi Card not Assigned")) {

                             account = "[" + account + "]";
                             if(jagacyMessage.equalsIgnoreCase("CMMI  NEW  Client has Active Savings Acc, please Nominate")){
                                 reComment = "Client has an Active Savings Accounts, Combi not Assigned.";
                                 ntb.setComment(reComment);
                             }else if(jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged")){
                                 reComment = "Client already has a combi Number Assigned, Combi not Assigned.";
                                 ntb.setComment(reComment);
                             }else{
                                 reComment = "The assign Combi System is down.";
                                 ntb.setComment(reComment);
                             }

                             i = listCombi.size();
                             jagacyMethods.close();

                         } else {

                             combiService.updatedUsedColumn("yes", listCombi.get(i).getCombi());

                             jagacyMessage = jagacyMethods.ChangeLimit("cmmi lmts", combiNu, "2000", "2000", "2000");
                             if(jagacyMessage.equalsIgnoreCase("Combi Limits not set")|| jagacyMessage.equalsIgnoreCase("Card Number Invalid or not Entered               E")){
                                 ntb.setComment("Combi Limits not set");
                             }
                             account = "[" + account + "," + combiNu + "]";
                             i = listCombi.size();
                             jagacyMethods.close();
                         }
                     }
                     break;
                 }else{
                     reComment = "Combi Not Assigned.";
                     ntb.setComment(reComment);
                     i = listCombi.size();
                     jagacyMethods.close();
                     continue;
                 }
             }
         }


     } else if (editType.equalsIgnoreCase("transaction")) {
         usersList = users.findUsers();
         account = editClientData;

         for (int y = 0; y < usersList.size(); y++) {

            if (usersList.get(y).getUsername().equalsIgnoreCase("abks580") || usersList.get(y).getUsername().equalsIgnoreCase("ab0158h")) {
                 password = usersList.get(y).getPassword();
                 username = usersList.get(y).getUsername();
             } else {
                 password = "DD";
                 username = "DD";
                 continue;
             }

             jagacyMethods = new ClientCreationJagacy();
             jagacyMethods.open();

             jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

             if (jagacyMessage.length() != 0) {
                 jagacyMethods.close();
                 reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                 subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                 smtpMailSender.sendMails(reEmail, subject, reComment);
                 throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
             }

             if (jagacyMessage.length() == 0) {

                 if (reEditProduct.equalsIgnoreCase("Cheque")) {
                     jagacyMessage = jagacyMethods.cashDeposit("FN71 CHEQ", reTransactionType, account, reTransactionAmount, "01").trim();
                 } else if (reEditProduct.equalsIgnoreCase("savings")) {
                     jagacyMessage = jagacyMethods.cashDeposit("FINS", reTransactionType, account, reTransactionAmount, "01").trim();
                 }
                 jagacyMethods.close();
                 if (jagacyMessage.equalsIgnoreCase("TRANSACTION PROCESSED")) {
                     ntb.setComment(jagacyMessage);
                 }else{
                     jagacyMethods.close();
                     reComment = "Hi Team, " + "\n" + " Transaction failed on this accounts: " + account + " Reason: " + jagacyMessage  + "\n" + " Contact me on: " + reMobile ;
                     subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                     smtpMailSender.sendMails(reEmail,subject, reComment);
                     throw new DataNotFoundException("We Are unable to Process the requested Fund,  an email has been forwarded to Test Data Team...");
                 }
             }
             break;
         }
     }else if (editType.equalsIgnoreCase("addAuth")) {
         usersList = users.findUsers();
         account = editClientData;

         client = service.findNewToBankByLowScore("NO", "Experian", 600);

         for (int y = 0; y < usersList.size(); y++) {

             password = usersList.get(y).getPassword();
             username = usersList.get(y).getUsername();

            // System.setProperty("sessionA.window", "true");
             jagacyMethods = new ClientCreationJagacy();
             jagacyMethods.open();

             jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

             if (jagacyMessage.length() != 0) {
                 jagacyMethods.close();
                 reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                 subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                 smtpMailSender.sendMails(reEmail, subject, reComment);
                 throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
             }

             if (jagacyMessage.length() == 0) {

                 if (client.size() < 0) {
                     throw new DataNotFoundException("You have Requested for :" + client.size() + " Client Profiles and we have : " + "1" + " Number of records remaining.");
                 }

                 for(int x = 1; x <= client.size(); x++){

                     String idtype = client.get(x).getIdtype();
                     String lastName = client.get(x).getSurname();
                     String firstName = client.get(x).getName();
                     String secondName = client.get(x).getSecondname();

                     if (authType.equalsIgnoreCase("specialPowerOfAttorney")) {

                         jagacyMessage = jagacyMethods.addSpecialPowerOfAttorney("auth", editClientType,authInputType, "N", "01", idtype, lastName, firstName, secondName).trim();

                         if(jagacyMessage.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || jagacyMessage.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED") ||jagacyMessage.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")|| jagacyMessage.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                             jagacyMethods.close();
                             throw new DataNotFoundException(jagacyMessage);
                         }else if(jagacyMessage.equalsIgnoreCase("AUTH  CAP   ! Invalid format")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Account Number does not exists, Special Power of Attorney not added..");
                         }else if(jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01  MANAGER")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Client has an existing Special Power of Attorney..");
                         }else if(jagacyMessage.equalsIgnoreCase("SPECIAL POWER OF ATTORNEY INFORMATION UPDATED SUCCESSFULLY")){
                             jagacyMethods.close();
                             ntb.setComment("SPECIAL POWER OF ATTORNEY INFORMATION UPDATED SUCCESSFULLY");
                             service.updatedUsedColumn("YES", date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());

                             account = authInputType;
                             break;
                         }

                     } else if (authType.equalsIgnoreCase("generalPowerOfAttorney")) {

                         jagacyMessage = jagacyMethods.addGeneralPowerOfAttorney("auth", editClientType,authInputType, "N", "02", idtype, lastName, firstName, secondName).trim();

                         if(jagacyMessage.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || jagacyMessage.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED")|| jagacyMessage.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")|| jagacyMessage.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                             jagacyMethods.close();
                             throw new DataNotFoundException(jagacyMessage);
                         }else if(jagacyMessage.equalsIgnoreCase("AUTH  CAP   ! Invalid format")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Account Number does not exists, GENERAL Power of Attorney not added..");
                         }else if(jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01  MANAGER") || jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Client has an existing General Power of Attorney or Special Power of Attorney..");
                         }else if(jagacyMessage.equalsIgnoreCase("GENERAL POWER OF ATTORNEY INFORMATION UPDATED SUCCESSFULLY")){
                             jagacyMethods.close();
                             ntb.setComment("GENERAL POWER OF ATTORNEY INFORMATION UPDATED SUCCESSFULLY");
                             service.updatedUsedColumn("YES", date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());

                             if(editClientType.equalsIgnoreCase("clientCode")){
                                ntb.setClientCode(authInputType);
                            }else{
                                account = "0";
                            }
                            break;
                         }
                     }else if (authType.equalsIgnoreCase("signingInstruction")) {

                         jagacyMessage = jagacyMethods.SigningInstruction("auth", editClientType,authInputType, "N", "03", idtype, lastName, firstName, secondName).trim();

                         if(jagacyMessage.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || jagacyMessage.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 3")|| jagacyMessage.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED")|| jagacyMessage.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                             jagacyMethods.close();
                             throw new DataNotFoundException(jagacyMessage);
                         }else if(jagacyMessage.equalsIgnoreCase("AUTH  CAP   ! Invalid format")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Account Number does not exists, GENERAL Power of Attorney not added..");
                         }else if(jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01  MANAGER") || jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Client has an existing General Power of Attorney or Special Power of Attorney..");
                         }else if(jagacyMessage.equalsIgnoreCase("SIGNING INSTRUCTIONS INFORMATION UPDATED SUCCESSFULLY")){
                             jagacyMethods.close();
                             ntb.setComment("SIGNING INSTRUCTIONS INFORMATION UPDATED SUCCESSFULLY");
                             service.updatedUsedColumn("YES", date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());

                             if(editClientType.equalsIgnoreCase("clientCode")){
                                 ntb.setClientCode(authInputType);
                             }else{
                                 account = authInputType;
                             }
                             break;
                         }
                        }else if (authType.equalsIgnoreCase("youthConsent")) {

                         jagacyMessage = jagacyMethods.youthConsent("auth", editClientType,authInputType, "N", "04", idtype, lastName, firstName, secondName).trim();

                         if(jagacyMessage.equalsIgnoreCase("ONLY 1 YOUTH CONSENT PER ACCOUNT ALLOWED")|| jagacyMessage.equalsIgnoreCase("CLIENT OLDER THAN 16 YEARS") || jagacyMessage.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || jagacyMessage.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")|| jagacyMessage.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                             jagacyMethods.close();
                             throw new DataNotFoundException(jagacyMessage);
                         }else if(jagacyMessage.equalsIgnoreCase("AUTH  CAP   ! Invalid format")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Account Number does not exists, GENERAL Power of Attorney not added..");
                         }else if(jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01  MANAGER") || jagacyMessage.equalsIgnoreCase("SIGNATORY RESIDENTIAL ADDRESS              DESIGNATION :# 01")){
                             jagacyMethods.close();
                             throw new DataNotFoundException("Client has an existing General Power of Attorney or Special Power of Attorney..");
                         }else if(jagacyMessage.equalsIgnoreCase("YOUTH CONSENT INFORMATION UPDATED SUCCESSFULLY")){
                             jagacyMethods.close();
                             ntb.setComment("YOUTH CONSENT INFORMATION UPDATED SUCCESSFULLY");
                             service.updatedUsedColumn("YES", date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                             if(editClientType.equalsIgnoreCase("clientCode")){
                                 ntb.setClientCode(authInputType);
                             }else{
                                 account = authInputType;
                             }
                             break;
                         }
                     }
                     break;
                 }
                 jagacyMethods.close();
             }
             break;
         }
     }
        ntb.setAccountNo(account);
        Ntbdata.add(ntb);

        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();
        saveFile(Ntbdata, reEmail);
        sendEmailToRequestorService.sendTestDataToEmail(reEmail);

        return Ntbdata;
    }

    public String loadMandate(String id,String name,String accountNo, String accountType,String colltnAmt) throws JSONException, InterruptedException, ParseException {

        String mrti = "";
        String status = "";
        String authorise = "";

        String accNo = "";

        mrti = initiateMandates.initiate(accountNo, id,accountType,colltnAmt);
        System.out.println("ssssssssssssss: " +mrti);
        Thread.sleep(5000);
        authorise = initiateMandates.authoriseMandate(mrti, accNo, name);

            if (authorise.equalsIgnoreCase("Not Found")) {
                System.out.println("Authorised Not found");
                throw new DataNotFoundException("Mandate not loaded successfully");
            } else {
                status = initiateMandates.statusReports(mrti);
                if (status.equalsIgnoreCase("NO_RESPONSE") || status.equalsIgnoreCase("FAILED_AT_DEBTOR_BANK"))
                    throw new DataNotFoundException("Mandate not loaded successfully");
            }
            return mrti;

    }

    private void saveFile(ArrayList<Newtobank> testData, String email) throws Exception {
        //String filename = System.getProperty("user.dir") + "/src/main/resources/temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename = "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("RequestedData");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0",new Object[]{"ID Number","Surname","Name","Second Name","Score","ClientCode","Account Number","AOL","Bureau Type"});
        for (int i=1;i<testData.size()+1;i++) {
            Newtobank existing = testData.get(i-1);
            Object[] temp = existing.toString().split(";");
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
