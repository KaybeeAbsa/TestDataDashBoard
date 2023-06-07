package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.*;
import co.za.absa.TestDataDashBoard.Methods.casa.ScreenCustomer;
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

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(value = "/clientCreation")
public class ClientCreationController {

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
    private ArrayList<Newtobank> Ntbdata = new ArrayList<>();
    private String combiNu;
    private String reComment;
    private String subject;
    int int_random = ThreadLocalRandom.current().nextInt();
    private String bureauType;
    private String dataScore;
    private String clientKey = null;
    private String account;
    private ArrayList<Newtobank> client;
    private ArrayList<Combis> listCombi = new ArrayList<>();
    private ArrayList<Users> usersList;
    private String password = null;
    private String username = null;
    private String passport = null;
    private CustomerProfileJagacy jagacyCustomerProfile;

    @RequestMapping(value = "/createClient/{quantity}/{used}/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{reScore}/{reProduct}/{reIdType}/{reClientType}/{reProductCode}/{reCombi}/{reTransction}/{policy}/{ficHold}/{cifHold}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Newtobank> createClient(@PathVariable int quantity, @PathVariable String used, @PathVariable String reName, @PathVariable String reTeam, @PathVariable String reEmail, @PathVariable String reMobile, @PathVariable String reEnviroment, @PathVariable String reScore, @PathVariable String reProduct, @PathVariable String reIdType, @PathVariable String reClientType, @PathVariable String reProductCode, @PathVariable String reCombi, @PathVariable String reTransction,@PathVariable String policy,@PathVariable String ficHold,@PathVariable String cifHold) throws JagacyException, Exception {

        CreateClientAPI createClientAPI = new CreateClientAPI();
        ScreenCustomer screenClient = new ScreenCustomer();
        Policy_CreateNewPolicy newPolicy = new Policy_CreateNewPolicy();

        listCombi.clear();
        Ntbdata.clear();
        ArrayList<Users> usersList = null;

        int remainingQuantity = quantity;
        String usedValue = "YES";
        String companyName = null;
        String businessRegistration = null;

        String productCode = null;
        String ecasaRefNumber = null;
        String policyNumber =null;
        String initials = null;
        String accountNo =null;
        String clientCode=null;
        String date = null;
        String idType = null;
        String lastName = null;
        String firstName = null;
        String secondName = null;
        String userStatus = null;
        String sourceOfFund = "20";
        int userId = 0;

        //Get Calender date, format(Month, Day, Year)
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        date = dateOnly.format(cal.getTime());

        //get clients details from database using Name and Calender date
        ArrayList<Newtobank> requestor = service.findRequestorsDetails(reName, date);

        //Check if the requestor did reach the daily limit.
        if (requestor.size() > 0) {
            if (requestor.size() >= 45) {
                reComment = "Hi Team, " + "\n" + reName + " Has reached his Daily Limit, please contact him for alignments " + "\n" + " Contact me on: " + reMobile;
                subject = "Test Data DashBoard Request LIMIT Reached " + reName + " From " + reTeam;
                smtpMailSender.sendMails(reEmail, subject, reComment);
                throw new DataNotFoundException("Hi " + reName + "\n" + " You have exceeded the daily limit with for this specific data category." + "\n" + "kindly contact Test.Data.Management@absa.africa");
            }
        }

        //splits score details from front end
        String[] splitScore = reScore.split(",");

        //get values score splitted details using index
        dataScore = splitScore[0];
        bureauType = splitScore[1];

        //Looping through number of requested test data.
        for (int x = 0; x < quantity; x++) {

            Newtobank ntb = new Newtobank();

            if(reIdType.equalsIgnoreCase("01") || reIdType.equalsIgnoreCase("03")) {

                //get Test data(Id Number, Names) details from the database using Bureau score
                if (dataScore.equalsIgnoreCase("high")) {
                    client = service.findNewToBankByHighScore(used, bureauType, 580);
                } else if (dataScore.equalsIgnoreCase("low")) {
                    client = service.findNewToBankByLowScore(used, bureauType, 580);
                }

                //Check if we have enough Test data from the database.
                if (client.size() < quantity) {
                    quantity = client.size();
                    throw new DataNotFoundException("You have Requested for :" + remainingQuantity + " Client Profiles and we have : " + quantity + " Number of records remaining.");
                }

                //Assign Test data details from the database
                idType = client.get(x).getIdtype();
                lastName = client.get(x).getSurname();
                firstName = client.get(x).getName();
                secondName = client.get(x).getSecondname();

                if (secondName.isEmpty()) {
                    initials = firstName.substring(0, 1);
                } else {
                    firstName = firstName + " " + secondName;
                    initials = firstName.substring(0, 1) + secondName.substring(0, 1);
                }
            }

            if(reEnviroment.equalsIgnoreCase("IMSS")){
                clientKey="IMSS";
            }else{

                if(reIdType.equalsIgnoreCase("01")){
                    clientKey = createClientAPI.createClient(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile,"","");
                }else if(reIdType.equalsIgnoreCase("03")){
                    passport = "KLM"+getRandomNumberString();
                    clientKey = createClientAPI.createClient(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile,passport,"SO003");
                }else if(reIdType.equalsIgnoreCase("05")){

                    sourceOfFund = "37";

                    if (reClientType.equalsIgnoreCase("03001") ||reClientType.equalsIgnoreCase("3001")) {
                        lastName = reName + int_random + " TECHNOLOGYS";
                        reClientType = "3001";
                        idType = "200901277807";
                        clientKey = createClientAPI.createBusinessClients(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile);

                    } else if (reClientType.equalsIgnoreCase("03101") || reClientType.equalsIgnoreCase("3101")) {
                        lastName = reName + int_random + " CC";
                        reClientType = "3101";
                        idType = "198904162523";
                        clientKey = createClientAPI.createBusinessClients(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile);

                    } else if (reClientType.equalsIgnoreCase("02301") || reClientType.equalsIgnoreCase("2301")) {
                        lastName = reName +int_random+" (PTY) LTD";
                        reClientType = "2301";
                        idType = "198900350207";
                        clientKey = createClientAPI.createBusinessClients(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile);

                    } else if (reClientType.equalsIgnoreCase("03201") || reClientType.equalsIgnoreCase("3201")) {
                        lastName = reName + int_random + "GROUPS LTD";
                        reClientType = "3201";
                        idType = "200082453810";

                        clientKey = createClientAPI.createBusinessClients(reIdType, firstName, lastName, idType, reClientType, reEmail, initials,reMobile);
                    }

                }
            }

              // System.out.println("Client Key: " + clientKey);
                //Validate API response
                if (clientKey != null) {

                    //Validate response message
                   if(clientKey.equalsIgnoreCase("DUPLICATE PROFILE EXITS") || clientKey.equalsIgnoreCase("DUPLICATE PROFILE FOUND")) {

                       //Update database record and increase quantity.
                        service.updatedUsedColumn(usedValue, "", "", "", "", "", client.get(x).getNewtobankid());
                        if (quantity != client.size()) {
                            quantity++;
                        }
                        continue;
                   } else if(clientKey.equalsIgnoreCase("INVALID INPUT COMBINATION") ){
                       throw new DataNotFoundException("Business Client account not created... " + clientKey);
                   }else if(reEnviroment.equalsIgnoreCase("IMSV")){
                    //Link Product to created Client Code/Key
                        if(reProduct.equalsIgnoreCase("cheque")){
                            account = createClientAPI.CQCreateAndLinkAccountV9(clientKey, reProductCode,sourceOfFund);

                        }else if(reProduct.equalsIgnoreCase("savings")){
                            account = createClientAPI.svcreatelinksavingsnoticedepaccountv6(clientKey, reProductCode,sourceOfFund);
                        }

                        if(account.equalsIgnoreCase("Client type and account type incompatible.") || account.equalsIgnoreCase("INVALID SOURCE OF FUNDS")|| account.equalsIgnoreCase("** PRODUCT HAS BEEN DISCO") || account.equalsIgnoreCase("Invalid category code.")){
                            throw new DataNotFoundException(account);
                        }
                        System.out.println("Account Number: " + account + " Product: " + reProduct);

                    }else {

                       //Get list of users from Database
                       usersList = users.findUsers();

                       //Loop through List of users.
                       for (int y = 0; y < usersList.size(); y++) {

                           try {

                               //get user details, username and password
                               if (usersList.get(y).getUsername().equalsIgnoreCase("abks580")) {
                                   password = usersList.get(y).getPassword();
                                   username = usersList.get(y).getUsername();
                               } else {
                                   password = "DD";
                                   username = "DD";
                                   continue;
                               }

                               //Lauch Mainframe
                               //System.setProperty("sessionA.window", "true");
                               jagacyMethods = new ClientCreationJagacy();
                               jagacyMethods.open();

                               //Login on Mainframe
                               jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                               //Validate login response
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

                                   //Create client on mainframe
                                   jagacyMessage = jagacyMethods.IndividualClientsBoarding(idType, lastName, firstName, secondName, reEmail, reMobile, reClientType, reProductCode);

                                   System.out.println("Client created: " + jagacyMessage);

                                   //Validate response
                                   if (jagacyMessage.equalsIgnoreCase("")|| jagacyMessage.equalsIgnoreCase("DUPLICATE PROFILE EXITS")|| jagacyMessage.equalsIgnoreCase("client not created") || jagacyMessage.equalsIgnoreCase("APPLICATION NUMBER REQUIRED") || jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("INVALID SITE FOR CAPTURE") || jagacyMessage.equalsIgnoreCase("ABSA ISLAMIC PRIVATE ACCOUNT MAY ONLY BE OPENED BY") || jagacyMessage.equalsIgnoreCase("ACC TYPE ONLY VALID FOR ABSA WEALTH SITES") || jagacyMessage.equalsIgnoreCase("ACCOUNT TYPE AND BANKING SECTOR INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("STRUCTURED LOAN MAY ONLY BE OPENED BY A PRIVATE BA") || jagacyMessage.equalsIgnoreCase("INVALID CATEGORY") || jagacyMessage.equalsIgnoreCase("client already exists") || jagacyMessage.equalsIgnoreCase("BIRTH DATE NOT THE SAME AS IN ID NUMBER") || jagacyMessage.equalsIgnoreCase("INVALID CHARACTERS IN NAME") || jagacyMessage.equalsIgnoreCase("DATE ISSUED CANNOT BE LESS THAN DATE OF BIRTH") || jagacyMessage.equalsIgnoreCase("ID NUMBER INVALID") || jagacyMessage.equalsIgnoreCase("CLIENT TYPE AND ACCOUNT TYPE INCOMPATIBLE") || jagacyMessage.equalsIgnoreCase("INCOMPATIBLE CATEGORY AND DATE OF BIRTH")) {

                                       if (jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("client already exists")) {
                                           service.updatedUsedColumn(usedValue, "", "", "", "", "", client.get(x).getNewtobankid());
                                       }

                                       if (quantity != client.size()) {
                                           quantity++;
                                           //continue;
                                       }

                                       //Validate user response
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

                                   //spilt accounts details
                                   String[] spiltAcc = jagacyMessage.split(",");
                                   accountNo = spiltAcc[0];
                                   clientCode = spiltAcc[1];
                                   clientKey = clientCode;

                                   System.out.println("Account N0: " + accountNo + " ClientCode: " + clientCode);
                                   int messageLeangth = accountNo.length();
                                   account = accountNo.substring(25, messageLeangth);
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

                       if (jagacyMessage.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || jagacyMessage.equalsIgnoreCase("client already exists")){
                           continue;
                       }

                       jagacyMethods.close();

                       if (client.size() == x) {
                           reComment = "Hi Team, " + "\n" + " We are out of NEW TO BANK clients ID numbers.." + "\n" + " Contact me on: " + reMobile;
                           subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                           smtpMailSender.sendMails(reEmail, subject, reComment);
                           throw new DataNotFoundException("We are out of New To Bank ID Numbers, an email has been forwarded to Test Data Team...");
                       }
                    }
                //System out of API'S and Mainframe

                if(reTransction.equalsIgnoreCase("yes")) {
                    usersList = users.findUsers();

                 //   System.out.println("Inside fund accounts.." + usersList.size());
                    for (int y = 0; y <= usersList.size(); y++) {

                        userStatus = usersList.get(y).getStatus();
                        username = usersList.get(y).getUsername();

                        if(userStatus.equalsIgnoreCase("FREE")){

                            password = usersList.get(y).getPassword();
                            username = usersList.get(y).getUsername();

                            users.updateUsersStatus("Busy",username);

                            jagacyMethods = new ClientCreationJagacy();
                            jagacyMethods.open();

                            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);


                             if (jagacyMessage.equalsIgnoreCase("INCORRECT OR NO PASSWORD ENTERED.") || jagacyMessage.equalsIgnoreCase("PASSWORD HAS EXPIRED.") || jagacyMessage.equalsIgnoreCase("USERID IS NOT DEFINED TO RACF.") || jagacyMessage.equalsIgnoreCase("USERID HAS BEEN REVOKED.") || jagacyMessage.equalsIgnoreCase("Your USERID is already logged on.") || jagacyMessage.equalsIgnoreCase("SIGNON FAILED. SEE IMS MESSAGES AND COD")) {
                                users.updateUsersStatus("FREE",username);

                                if(y + 1 == usersList.size()){
                                    jagacyMethods.close();
                                    reComment = "Hi Team, " + "\n" + " All Users are busy :" + "\n" + " Contact me on: " + reMobile;
                                    subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                                    smtpMailSender.sendMails(reEmail, subject, reComment);
                                    throw new DataNotFoundException("All Users are busy, please try again after 2 minutes...");
                                }else{
                                    y++;
                                    continue;
                                }

                               }

                            if (jagacyMessage.length() == 0) {

                                if (reProduct.equalsIgnoreCase("Cheque")) {
                                    jagacyMessage = jagacyMethods.cashDeposit("FN71 CHEQ", "CD", account, "10000", "01").trim();
                                    jagacyMethods.close();
                                } else if (reProduct.equalsIgnoreCase("savings")) {
                                    jagacyMessage = jagacyMethods.cashDeposit("FINS", "CD", account, "1000", "01").trim();
                                    jagacyMethods.close();
                                }

                                users.updateUsersStatus("FREE",username);
                                if (jagacyMessage.equalsIgnoreCase("TRANSACTION PROCESSED")) {
                                    //System.out.println("Inside successful transaction");
                                }
                            }

                            break;
                        }else{
                            y++;
                            continue;
                        }
                    }
                }

                if (reCombi.equalsIgnoreCase("yes")) {
                    usersList = users.findUsers();
                  //  System.out.println("Inside Combi: " + usersList.size());

                    for (int y = 0; y < usersList.size(); y++) {
                        listCombi = combiService.findCombi("NO", reEnviroment,reProduct);
                       // System.out.println("Combi: " + listCombi.size());

                        for (int i = 0; i < listCombi.size(); i++) {

                            if (usersList.get(y).getUsername().equalsIgnoreCase("abks580")) {
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

                                if (reProduct.equalsIgnoreCase("savings")) {

                                    combiNu = listCombi.get(i).getCombi();

                                    jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", "", account, "s", "8198", "n", combiNu,reEnviroment);

                                   // System.out.print("JAGACY Message combi: " + jagacyMessage);
                                    if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists") || jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System")) {
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


                                } else if (reProduct.equalsIgnoreCase("cheque")) {

                                    combiNu = listCombi.get(i).getCombi();

                                    System.out.println(combiNu);
                                   jagacyMessage = jagacyMethods.assignCombiCardsNumbers("CMMI NEWP", "C", "N", account, "", "s", "8198", "n", combiNu, reEnviroment);

                                    if (jagacyMessage.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged") || jagacyMessage.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP          Combi Card Number already exists") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP Client has no Active Accounts")) {
                                        if (i < listCombi.size()) {
                                            i++;
                                            jagacyMethods.close();
                                            if(i == listCombi.size()){
                                                ntb.setComment("Combi not Assigned, We are out of Combi Card Numbers");
                                                break;
                                            }else{
                                                combiService.updatedUsedColumn("NO", listCombi.get(i).getCombi());
                                                continue;
                                            }

                                        }
                                    } else if(jagacyMessage.equalsIgnoreCase("ACCS - Stock Code is different in System") || jagacyMessage.equalsIgnoreCase("CMMI  NEWP     ACCS - Stock Code is different in System")){
                                       // System.out.print("Error assign combi:" + jagacyMessage);
                                        if (i <= listCombi.size()) {
                                            i++;
                                            jagacyMethods.close();
                                            combiService.updatedUsedColumn("YES", listCombi.get(i).getCombi());
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
                        break;
                    }
                }

                if(reIdType.equalsIgnoreCase("01") || reIdType.equalsIgnoreCase("03")){
                    ecasaRefNumber = screenClient.screenACustomer(firstName, secondName,idType);
                }

                if((!policy.isEmpty() & !policy.equalsIgnoreCase("undefined")) & !policy.equalsIgnoreCase("NO") & policy.length()==9)
                {
                    policyNumber=newPolicy.createNewPolicy(clientKey,idType,firstName,lastName,policy);
                }

                if(ficHold.equalsIgnoreCase("yes")) {

                    if(!addFicLock(clientKey,reEnviroment))
                    {
                            quantity++;
                            continue;
                    }

                }

                if(cifHold.equalsIgnoreCase("IdentificationRequired") || cifHold.equalsIgnoreCase("InsolventEstate")|| cifHold.equalsIgnoreCase("DeceasedEstate") || cifHold.equalsIgnoreCase("SpouseDeceased") || cifHold.equalsIgnoreCase("Curatorship") || cifHold.equalsIgnoreCase("ClientAgreementIssued") || cifHold.equalsIgnoreCase("NewPostalAddressRequired") || cifHold.equalsIgnoreCase("NewResidentialAddressRequired") || cifHold.equalsIgnoreCase("NewEmployersNameAddressRequired"))
                {
                    addCifHold(clientKey,reEnviroment,cifHold);
                }

                if(reIdType.equalsIgnoreCase("01") || reIdType.equalsIgnoreCase("03")){
                    service.updatedUsedColumn(usedValue, date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                }

               // service.updatedUsedColumn(usedValue, date, reName, reTeam, reEmail, reMobile, client.get(x).getNewtobankid());
                reComment = "Hi Team, " + "\n" + " I have requested for " + remainingQuantity + " " + reProduct + " " + dataScore + " Score Test accounts " + "\n" + " Contact me on: " + reMobile;
                subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();

                if(reIdType.equalsIgnoreCase("01")){
                    ntb.setIdtype(client.get(x).getIdtype());
                    ntb.setEcasa(ecasaRefNumber);
                    ntb.setPolicy(policyNumber);
                    ntb.setName(client.get(x).getName());
                    ntb.setSecondname(client.get(x).getSecondname());
                    ntb.setSurname(client.get(x).getSurname());
                    ntb.setScore(client.get(x).getScore());
                    ntb.setUsed(client.get(x).getUsed());
                    ntb.setBureauType(bureauType);
                }else if(reIdType.equalsIgnoreCase("03")){
                    ntb.setIdtype(passport);
                    ntb.setEcasa(ecasaRefNumber);
                    ntb.setPolicy(policyNumber);
                    ntb.setName(client.get(x).getName());
                    ntb.setSecondname(client.get(x).getSecondname());
                    ntb.setSurname(client.get(x).getSurname());
                    ntb.setScore(client.get(x).getScore());
                    ntb.setUsed(client.get(x).getUsed());
                    ntb.setBureauType(bureauType);
                }else{

                    ntb.setIdtype(idType);
                    ntb.setSurname(lastName);
                }

                ntb.setClientCode(clientKey);
                ntb.setAccountNo(account);

                Ntbdata.add(ntb);
            }else{
                    //service.updatedUsedColumn(usedValue, "", "", "", "", "", client.get(x).getNewtobankid());
                    if (quantity != client.size()) {
                        quantity++;
                    }
                    continue;
                }

        }

        reComment = "Hi Team, "
                + "\n" + " I have requested for " + remainingQuantity + " " + reEnviroment + " " + reProduct + " Accounts with " + reScore + " Score. "
                + "\n" + " Contact me on: " + reMobile +"|"+ reEmail;
        subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
        smtpMailSender.sendMails(reEmail,subject, reComment);
        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();

        saveFile(Ntbdata, reEmail);
        sendEmailToRequestorService.sendTestDataToEmail(reEmail);

        return Ntbdata;
    }

    private void saveFile(ArrayList<Newtobank> testData, String email) throws Exception {
        // String filename = System.getProperty("user.dir") + "/src/main/resources/temp/RequstedTestData" + email.split("@")[0] + ".xlsx";
        String filename = "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("RequestedData");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0", new Object[]{"ID Number", "Surname", "Name", "Second Name", "Score", "Used", "ClientCode", "Account Number", "Comment","Ecasa","Policy Number", "Bureau Type"});
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

    private boolean addFicLock(String clientCode,String reEnviroment) throws InterruptedException, JagacyException, AWTException {

        boolean approved = false;
        String password,username;

        usersList = users.findUsers();

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("ab0158h")) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }

            jagacyMethods = new ClientCreationJagacy();
            jagacyMethods.open();

            //Login on Jagacy(3270) Mainframe
            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

            System.out.println("Client Code: "+ clientCode);
            jagacyMessage = jagacyMethods.addFicHold("FICN", clientCode);

            if (jagacyMessage.equalsIgnoreCase("RISK LOCK ALREADY PLACED") || jagacyMessage.equalsIgnoreCase("< Data entry too short")) {

                jagacyMethods.close();
                approved = false;
                continue;
            }else {
                jagacyMethods.close();
                approved = true;
                jagacyMethods.close();
                break;
            }

        }
        return approved;
    }

    private boolean addCifHold(String clientCode,String reEnviroment, String holdType) throws InterruptedException, JagacyException, AWTException {

        boolean approved = false;
        String password,username;

        ArrayList<Users> usersList = users.findUsers();

        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("ab0158h")) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }
            jagacyMethods = new ClientCreationJagacy();
            jagacyMethods.open();

            //Login on Jagacy(3270) Mainframe
            jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

            jagacyMethods.addCIFHold("CLF", clientCode,holdType);

                jagacyMethods.close();
                approved = true;
                jagacyMethods.close();
                break;
        }
        return approved;
    }


}
