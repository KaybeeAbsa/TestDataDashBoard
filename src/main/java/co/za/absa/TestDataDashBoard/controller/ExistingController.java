package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.*;
import co.za.absa.TestDataDashBoard.Methods.casa.EcasaRefById;
import co.za.absa.TestDataDashBoard.exceptions.DataNotFoundException;
import co.za.absa.TestDataDashBoard.model.Existing;
import co.za.absa.TestDataDashBoard.model.Users;
import co.za.absa.TestDataDashBoard.services.ExistingService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/existing")
public class ExistingController {

    @Autowired
    private ExistingService service;

    @Autowired
    private UserService users;

    @Autowired
    SmtpMailSender smtpMailSender;

    private CustomerProfileJagacy jagacyMethods = null;
    private String jagacyMessage;
    private ArrayList<Existing> testData = new ArrayList<>();
    private Existing existingClient = new Existing();
    private ArrayList<Existing> client;
    private String clientType;
    private String surname;
    private String name;
    private String dbName;
    private String dbSurname;
    private String dbSecondName;

    private String dbThirdName;
    private String product;
    private String reComment;
    private String subject;
    private String bureauType;
    private String dataScore;

    private String clientCode = null;
    private int update = 0;

    private String compliantStatus;

    private String newToProductAccount = "";

    private String newtoBankLock, softLock, ficRiskLock;

    private String dateClientOpned;
    private ArrayList<Users> usersList;

    @RequestMapping(value = "/findclient/{quantity}/{used}/{reName}/{reTeam}/{reEmail}/{reMobile}/{reEnviroment}/{reScore}/{reProduct}/{reProductClientType}/{reCombi}/{rePreApproved}/{reSelectedCompliant}/{reOverDraft}/{reClientType}/{linkedCheque}/{linkedSavings}/{linkedCard}/{linkedPL}/{linkedHM}/{linkedAvaf}/{policy}/{ficHold}/{cifHold}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Existing> createClient(@PathVariable int quantity, @PathVariable String used,  @PathVariable String reName,  @PathVariable String reTeam,  @PathVariable String reEmail,  @PathVariable String reMobile,  @PathVariable String reEnviroment,  @PathVariable String reScore, @PathVariable String reProduct , @PathVariable String reProductClientType,  @PathVariable String reCombi,  @PathVariable String rePreApproved, @PathVariable String reSelectedCompliant, @PathVariable String reOverDraft, @PathVariable String reClientType,@PathVariable String linkedCheque,@PathVariable String linkedSavings,@PathVariable String linkedCard,@PathVariable String linkedPL,@PathVariable String linkedHM,@PathVariable String linkedAvaf,@PathVariable String policy,@PathVariable String ficHold,@PathVariable String cifHold) throws JagacyException, InterruptedException, AWTException, ParseException,Exception {

        testData.clear();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateOnly.format(cal.getTime());

        String[] splitScore = reScore.split(",");

        dataScore = splitScore[0];
        bureauType = splitScore[1];

        if(reProductClientType.equalsIgnoreCase("MLOANS")){

            reProductClientType = "M/LOANS";
        }
        ArrayList<Existing> requestor = service.findRequestorsDetails(reEmail, date);

        EcasaRefById ecasaRefById = new EcasaRefById();
        Policy_CreateNewPolicy newPolicy = new Policy_CreateNewPolicy();
        String ecasaRefNumber = "";

        String policyNumber ="";

        if(requestor.size() > 0)
        {
            if(requestor.size() > 300){
                reComment = "Hi Team, " + "\n" + reName + " Has reached his Daily Limit, please contact him for alignments " + "\n" + " Contact me on: " + reMobile ;
                subject = "Test Data DashBoard Request LIMIT Reached " + reName + " From " + reTeam;
                smtpMailSender.sendMails(reEmail,subject, reComment);
                throw new DataNotFoundException("Hi " + reName + "\n" +  " You have exceeded the daily limit with for this specific data category." + "\n" + "kindly contact Test.Data.Management@absa.africa");
            }
        }

        if(reProduct.equalsIgnoreCase("ExCheque") || reProduct.equalsIgnoreCase("ExChequeNewToProduct") || linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExCheque")){
           linkedCheque="NO";
            product = "Cheque";
        }else if(reProduct.equalsIgnoreCase("ExSavings") || reProduct.equalsIgnoreCase("ExSavingsNewToProduct")|| linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExSavings")){
            linkedSavings="NO";
            product = "Savings";
        }else if(reProduct.equalsIgnoreCase("ExCard") || reProduct.equalsIgnoreCase("ExCardNewToProduct")|| linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExCard")){
            linkedCard="NO";
            product = "Card";
        }else if(reProduct.equalsIgnoreCase("ExPL") || reProduct.equalsIgnoreCase("ExPLNewToProduct")|| linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExPL")){
            linkedPL="NO";
            product = "PL";
        }else if(reProduct.equalsIgnoreCase("ExAvaf") || reProduct.equalsIgnoreCase("ExAvafNewToProduct")|| linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExAvaf")){
            linkedAvaf="NO";
            product = "Avaf";
        }else if(reProduct.equalsIgnoreCase("ExMln") || reProduct.equalsIgnoreCase("ExMlnNewToProduct")|| linkedCheque.equalsIgnoreCase("YES") && reProduct.equalsIgnoreCase("ExMln")){
            linkedHM="NO";
            product = "HomeLoan";
        }

        List<String> linked = Arrays.asList(linkedSavings,linkedCheque,linkedCard,linkedAvaf,linkedHM,linkedPL);
        List<String> linked1 = linked.stream().map(x->{if(x.equalsIgnoreCase("no")|x.equalsIgnoreCase("undefined"))
            return "NO";
        else return "YES";}).collect(Collectors.toList());
        linked = linked.stream().filter(x->!(x.equalsIgnoreCase("no")|x.equalsIgnoreCase("undefined"))).collect(Collectors.toList());

        int remainingQuantity = quantity;
        int originalQuantity = quantity;
        //Get list of id's from the database using a product

        if (dataScore.equalsIgnoreCase("high")) {
            client = service.findClientbyHighScore(used,product,linked1.get(0),linked1.get(1),linked1.get(2),linked1.get(3),linked1.get(4),linked1.get(5),reClientType, bureauType, reEnviroment,580);
        } else if (dataScore.equalsIgnoreCase("low")) {
            client = service.findClientbyLowScore(used,product,linked1.get(0),linked1.get(1),linked1.get(2),linked1.get(3),linked1.get(4),linked1.get(5),reClientType, bureauType, reEnviroment,580);
        }else{
            client = service.findClientbyLowScore(used,product,linked1.get(0),linked1.get(1),linked1.get(2),linked1.get(3),linked1.get(4),linked1.get(5),reClientType, bureauType, reEnviroment,580);
        }

        ArrayList<Users> usersList = users.findUsers();

        if(client.size() != 0) {
            System.out.println("client size: " + client.size());

            if (client.size() < quantity) {
                quantity = client.size();
            }

            for (int x = 0; x < quantity; x++) {

                int value = x + 1;

                if( x >= client.size()){
                    reComment = "Hi Team, " + "\n" + " We are out of " + reScore +  " Score Data" + product +  " " + dataScore + " accounts " + "\n" + " Contact me on: " + reMobile ;
                    subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
                    smtpMailSender.sendMails(reEmail,subject, reComment);
                    throw new DataNotFoundException("We are out of " + reScore + " Score Data, an email has been forwarded to Test Data Team...");
                }

                String usedValue = "YES";
                String idtype = client.get(x).getIdtype();
                dbSurname = client.get(x).getSurname();
                dbName = client.get(x).getName();
                dbSecondName = client.get(x).getSecondname();

                CIgetClientDetailsByIDNOV20 cIgetClientDetailsByIDNOV20 = new CIgetClientDetailsByIDNOV20();

                CIgetAcctLinkedToClientCodeV20API cIgetAcctLinkedToClientCodeV20API = new CIgetAcctLinkedToClientCodeV20API();

                FicLockAPI ficLock = new FicLockAPI();
                OverDraft od = new OverDraft();

                String clientCode = "";
                String clientType = "";
                String clientCodeValue = "";

                if(reClientType.equalsIgnoreCase("00101") ||reClientType.equalsIgnoreCase("00104") ){
                    clientCodeValue = "1";
                    clientType = "I";
                }else if(reClientType.equalsIgnoreCase("00103")){
                    clientCodeValue = "3";
                    clientType = "I";
                }else if(reClientType.equalsIgnoreCase("02301") || reClientType.equalsIgnoreCase("03201") || reClientType.equalsIgnoreCase("02901") ||reClientType.equalsIgnoreCase("02801")||reClientType.equalsIgnoreCase("03101")||reClientType.equalsIgnoreCase("08101")){
                    clientCodeValue = "5";
                    clientType = "N";
                }

                String clientKey[] = cIgetClientDetailsByIDNOV20.getClientDetailsById(idtype, clientCodeValue, clientType, reEnviroment);

                CIgetClientDetailsV22 cIgetClientDetailsV22 = new CIgetClientDetailsV22();

                if(clientKey[0] != null)
                {

                    if(clientKey[0].isEmpty())
                    {
                        service.updatedUsedColumn(usedValue, "", "", "", "", "", idtype, reEnviroment);
                        quantity++;
                        continue;
                    }

                     //get client Opened date.
                    dateClientOpned = cIgetClientDetailsV22.retrieveClientDetails(clientKey[0]);


                    //Check Date Opened and return based on client created date.
                        if(reEmail.equalsIgnoreCase("Pavan.Bosa@absa.africa")  ||  reEmail.equalsIgnoreCase("Shikha.Prakash@absa.africa")  ||reEmail.equalsIgnoreCase("Abinayaa.Venkatesan@absa.africa")){

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-day");
                            Date Newdate = new Date();

                            String years = dateClientOpned.substring(0,4);
                            String month= dateClientOpned.substring(4,6);
                            String days= dateClientOpned.substring(6,dateClientOpned.length());

                            String dates1 = String.valueOf(formatter.format(Newdate));

                            String year1 = dates1.substring(0,4);
                            String month1= dates1.substring(5,7);
                            String days1=dates1.substring(8,10);

                            dateClientOpned = years+"-"+month+"-"+days;
                            String  startDate = dateClientOpned;

                            dates1 = year1+"-"+month1+"-"+days1;
                            String  promotionDate = dates1;

                            LocalDate sdate = LocalDate.parse(startDate);
                            LocalDate pdate = LocalDate.parse(promotionDate);

                            LocalDate ssdate = LocalDate.of(sdate.getYear(), sdate.getMonth(), sdate.getDayOfMonth());
                            LocalDate ppdate = LocalDate.of(pdate.getYear(), pdate.getMonth(), pdate.getDayOfMonth());

                            Period period = Period.between(ssdate, ppdate);

                            if(period.getYears() < 3){
                                service.updatedUsedColumn(usedValue, "", "", "", "", "", idtype, reEnviroment);
                                quantity++;
                                continue;
                            }
                        }

                    //Get linked accounts to a client Code
                    Existing existing = cIgetAcctLinkedToClientCodeV20API.getClientDetailsLinkedClientCode(clientKey, client.get(x), reProduct,linked,reCombi, reEnviroment);

                    if(existing==null)
                    {
                        service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                        quantity++;
                        continue;
                    }

                    //Check account product code
                     if (!existing.getAccountNo().contains(reProductClientType)) {
                         service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                        quantity++;
                        continue;
                    }

                    //Check if accounts contain combi
                    if(reCombi.equalsIgnoreCase("NO") && existing.getAccountNo().contains("COMBI") ){
                        service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                        quantity++;
                        continue;
                    }

                    //Get Account Number
                    String [] accounts = getAccountNombers(existing.getAccountNo());

                    if(product.equalsIgnoreCase("Cheque")){

                        //Get Overdraft details
                        String overDraftAmount = od.getOverDraftDetails(accounts[0], reEnviroment);

                        //Validations
                        if(overDraftAmount == null || overDraftAmount.equalsIgnoreCase("")){

                            service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                            quantity++;
                            continue;
                        }

                        //Add Over Draft and Over Draft Validations.
                        if(reOverDraft.equalsIgnoreCase("yes")) {

                            if(overDraftAmount.equalsIgnoreCase("0") ){
                                boolean applyOverDraft = applyForOverDraft(accounts[0],reEnviroment, "1");

                                if(!applyOverDraft){
                                    service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                                    quantity++;
                                    continue;
                                }
                            }

                        }else{

                            if(!overDraftAmount.equalsIgnoreCase("0")){

                                service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                                quantity++;
                                continue;

                                /*  if(reEnviroment.equalsIgnoreCase("IMSV")){

                                String clientSite = cIgetClientDetailsV22.retrieveClientSite(clientKey[0]);

                                System.out.println("Site: " + clientSite);

                                if(clientSite.equalsIgnoreCase("NO Site")){

                                    service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                                    quantity++;
                                    continue;
                                }else{
                                    String deleteOD  = od.deleteOverDraft(accounts[0] , clientSite, reEnviroment);
                                    System.out.println("Delete od: " + deleteOD +  "  SIZE: "+ deleteOD.length());

                                }

                            }else{
                                boolean applyOverDraft = applyForOverDraft(accounts[0],reEnviroment, "3");
                                System.out.println("delete Od: " + applyOverDraft);
                            }*/
                            }

                        }
                    }

                    boolean preApprovals;
                    //Add Pre-approval and Validations
                    if(rePreApproved.equalsIgnoreCase("yes")) {
                        //System.out.println("Pre-approval : " + preApprovals + " : yes");
                         preApprovals = isPreApproved(clientKey[0],reEnviroment,rePreApproved);

                         if(!preApprovals){
                            service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                            quantity++;
                            continue;
                        }

                    }else{

                         preApprovals = isPreApproved(clientKey[0],reEnviroment,rePreApproved);

                        if(!preApprovals){
                            service.updatedUsedColumn("NO", "", "", "", "", "", idtype, reEnviroment);
                            quantity++;
                            continue;
                        }
                    }

                    //add Fic Lock's
                    if(!ficHold.equalsIgnoreCase("NO")) {

                        if(ficHold.equalsIgnoreCase("newToBankLock")){

                            newtoBankLock = "Y";
                            softLock="N";
                            ficRiskLock="N";
                        }else if(ficHold.equalsIgnoreCase("softLock")){

                            newtoBankLock = "N";
                            softLock="Y";
                            ficRiskLock="N";
                        }else{

                            newtoBankLock = "N";
                            softLock="N";
                            ficRiskLock="Y";
                        }

                        String ficLockStatus = ficLock.addFic(clientKey[0], newtoBankLock,softLock,ficRiskLock,reEnviroment);

                        if(!ficLockStatus.equalsIgnoreCase("0")){
                            quantity++;
                            continue;
                        }

                     /*  if(!addFicLock(clientKey[0],reEnviroment))
                        {
                            quantity++;
                            continue;
                        }*/

                    }else{

                        String getficLockStatus = ficLock.checkFicStatus(clientKey[0],reEnviroment);

                        if(!getficLockStatus.equalsIgnoreCase("")){
                            quantity++;
                            continue;
                        }
                    }

                    if(cifHold.equalsIgnoreCase("IdentificationRequired") || cifHold.equalsIgnoreCase("InsolventEstate")|| cifHold.equalsIgnoreCase("DeceasedEstate") || cifHold.equalsIgnoreCase("SpouseDeceased") || cifHold.equalsIgnoreCase("Curatorship") || cifHold.equalsIgnoreCase("ClientAgreementIssued") || cifHold.equalsIgnoreCase("NewPostalAddressRequired") || cifHold.equalsIgnoreCase("NewResidentialAddressRequired") || cifHold.equalsIgnoreCase("NewEmployersNameAddressRequired"))
                    {
                        addCifHold(clientKey[0],reEnviroment,cifHold);
                    }

                    ecasaRefNumber= ecasaRefById.ecasaRefById(idtype);

                    if((!policy.isEmpty() && !policy.equalsIgnoreCase("undefined"))  & !policy.equalsIgnoreCase("NO") & policy.length()==9)
                    {
                        policyNumber=newPolicy.createNewPolicy(clientKey[0],idtype,clientKey[2],clientKey[1],policy);
                    }

                    //get Fic Compliant Status.
                    compliantStatus  = cIgetClientDetailsV22.retrieveComplentStatus(clientKey[0]);

                   // String reCompliant = "Partially";

                    if(reSelectedCompliant.equalsIgnoreCase("Partially")){

                        System.out.println("Inside Partially");
                        if(!compliantStatus.equalsIgnoreCase(reSelectedCompliant)){
                            String upload = ficLock.uploadDocuments(clientKey[0],"Ent_ProofOfId",idtype);
                            System.out.println("Upload document: " + upload);

                            if(!upload.equalsIgnoreCase("OK")){
                                quantity++;
                                continue;
                            }
                            System.out.println("Partially Compliant");
                        }

                    }else if(reSelectedCompliant.equalsIgnoreCase("Compliant")){
                        System.out.println("Inside Compliant");
                        if(!compliantStatus.equalsIgnoreCase(reSelectedCompliant)){
                            String uploadId = ficLock.uploadDocuments(clientKey[0],"Ent_ProofOfId",idtype);

                            System.out.println("Upload id document: " + uploadId);

                            if(!uploadId.equalsIgnoreCase("OK")){
                                System.out.println("Inside Not Okay  compliant");
                                quantity++;
                                continue;
                            }

                            String uploadAddress = ficLock.uploadDocuments(clientKey[0],"Ent_ProofOfAddress",idtype);

                            System.out.println("Upload address document: " + uploadAddress);

                            if(!uploadAddress.equalsIgnoreCase("OK")){
                                quantity++;
                                continue;
                            }
                            System.out.println("Client Compliant");

                        }
                    }else if(reSelectedCompliant.equalsIgnoreCase("NonCompliant")){
                        System.out.println("Inside None-Comlpiant");
                        if(!compliantStatus.equalsIgnoreCase(reSelectedCompliant)){
                            quantity++;
                            continue;
                        }
                        System.out.println("Client None Compliant");
                    }

                    String linkedAccount="";
                    if(reProduct.contains("NewToProduct")) {
                        CreateClientAPI createClientAPI = new CreateClientAPI();
                        if (linkedSavings.equalsIgnoreCase("LinkedSavings"))
                            newToProductAccount = createClientAPI.svcreatelinksavingsnoticedepaccountv6(clientKey[0], "09040","20");
                        else if (linkedCheque.equalsIgnoreCase("LinkedCheque"))
                            newToProductAccount = createClientAPI.CQCreateAndLinkAccountV9(clientKey[0], "11032","20");
                    }

                    existing.setEcasa(ecasaRefNumber);
                    existing.setPolicy(policyNumber);

                    UpdateClientDetailsAPI updateClientDetailsAPI =  new UpdateClientDetailsAPI();

                    surname = existing.getSurname();
                    name  = existing.getName();
                    String firstName = null;
                    String initials = "";

                    if (dbSecondName.isEmpty()) {
                        firstName = dbName;
                        initials = dbName.substring(0, 1);
                    } else {
                        firstName = dbName + " " + dbSecondName;
                        initials = dbName.substring(0, 1) + dbSecondName.substring(0, 1);
                    }

                    int updatedClient  = 0;

                    if(surname.trim().equalsIgnoreCase(dbSurname.trim())){
                        // System.out.println("details updated");
                    }else {
                        updatedClient = 1;
                        //  System.out.println("Update Surnames and names");
                    }

                    if(name.trim().equalsIgnoreCase(initials.trim())){
                        // System.out.println("details updated");
                    }else{
                        updatedClient = 1;
                        // System.out.println("Update names");
                    }

                    String updated;
                    if(updatedClient ==1 && reEnviroment.equalsIgnoreCase("IMSV")){
                         updated =  updateClientDetailsAPI.updateClientDetails(existing.getClientCode(),firstName,dbSurname,existing.getIdtype(),initials,reMobile,reEmail);
                    }else if(reEnviroment.equalsIgnoreCase("IMSV")){
                      //  updated =  updateClientDetailsAPI.updateClientDetails(existing.getClientCode(),firstName,dbSurname,existing.getIdtype(),initials,reMobile,reEmail);
                    //fix and only update cell phone numbers
                    }

                    existing.setName(dbName);
                    existing.setFicCompliantStatus(compliantStatus);
                    testData.add(existing);

                    service.updatedUsedColumn(usedValue, date, reName, reTeam, reEmail, reMobile, idtype, reEnviroment);

                    if (testData.size() == originalQuantity) {
                        break;
                    }

                } else {
                    //Client key is null
                    service.updatedUsedColumn(usedValue, "", "", "", "", "", idtype, reEnviroment);
                    quantity++;
                  //  continue;
                }

            }

        }else{
            reComment = "Hi Team, " + "\n" + " We are out of " + reScore +  " Score Data" + product +  " " + dataScore + " accounts " + "\n" + " Contact me on: " + reMobile ;
            subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
            smtpMailSender.sendMails(reEmail,subject, reComment);
            throw new DataNotFoundException("We are out of " + reScore + " Score Data, an email has been forwarded to Test Data Team...");
        }

        reComment = "Hi Team, "
                + "\n" + " I have requested for " + remainingQuantity+ " " + reEnviroment + " " + product + " accounts with  " + reScore + " Score. "
                + "\n" + " Contact me on: " + reMobile  ;
        subject = "Test Data DashBoard Request " + reName + " From " + reTeam;
        smtpMailSender.sendMails(reEmail,subject, reComment);

        SendEmailToRequestorServiceImpl sendEmailToRequestorService = new SendEmailToRequestorServiceImpl();

        saveFile(testData, reEmail);
        sendEmailToRequestorService.sendTestDataToEmail(reEmail);
        return testData;
    }

    public String[] getAccountNombers(String accountNo) {
        String accounts = "";
        String accountsWithPRoducts = accountNo.replace("[","").replace("]","");
        String []accountsWithPRoduct = accountsWithPRoducts.split(",");
        for (String s:accountsWithPRoduct ) {
            String temp[] = s.split("  ");
            accounts += temp[0]+",";
        }
        return accounts.substring(0,accounts.length()-1).split(",");
    }

    private boolean applyForOverDraft( String accountNo,String reEnviroment, String option) throws JagacyException {

        String password,username;
        boolean applied = false;
        ArrayList<Users> usersList = users.findUsers();
        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("tdm0001") ) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }

            try{
                jagacyMethods = new CustomerProfileJagacy();
                jagacyMethods.open();

                jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                if (jagacyMessage.equalsIgnoreCase("INCORRECT OR NO PASSWORD ENTERED.") || jagacyMessage.equalsIgnoreCase("PASSWORD HAS EXPIRED.") || jagacyMessage.equalsIgnoreCase("USERID IS NOT DEFINED TO RACF.") || jagacyMessage.equalsIgnoreCase("USERID HAS BEEN REVOKED.") || jagacyMessage.equalsIgnoreCase("Your USERID is already logged on.") || jagacyMessage.equalsIgnoreCase("SIGNON FAILED. SEE IMS MESSAGES AND COD")) {
                    reEnviroment = "IV01";
                    i= i - 1;
                    continue;
                }else{
                    String checkMpp = jagacyMethods.checkMpp();

                    if(checkMpp.equalsIgnoreCase("Error")){
                        jagacyMethods.close();
                        applied = false;
                        break;
                    }

                    String overDraft;

                    if(option.equalsIgnoreCase("1")){
                        overDraft = jagacyMethods.AddOD(accountNo, option, "2", "9000");

                        if (overDraft.equalsIgnoreCase("ACCOUNT NUMBER MUST BE ENTERED") ||overDraft.equalsIgnoreCase("INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS") || overDraft.equalsIgnoreCase("NOT AUTHORIZED TO PROCESS OVERDRAFT") || overDraft.equalsIgnoreCase("ACCOUNT NUMBER DOES NOT EXIST") || overDraft.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || overDraft.equalsIgnoreCase("TRANSACTION NOT ALLOWED - UNDER DEBT COUNSELLING")) {
                            jagacyMethods.close();
                            applied = false;
                            break;
                        }else {
                            applied = true;
                            jagacyMethods.close();
                            break;
                        }

                    }else{
                        overDraft = jagacyMethods.deleteOverDraft(accountNo, option, "2", "9000");

                        if (overDraft.equalsIgnoreCase("OVERDRAFT/ECF CANCELLATION PROCESSED") || overDraft.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || overDraft.equalsIgnoreCase("NO OVERDRAFT/ECF TO DELETE/DECLINE               ENT-") ||overDraft.equalsIgnoreCase("ACCOUNT NUMBER MUST BE ENTERED") ||overDraft.equalsIgnoreCase("INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS") || overDraft.equalsIgnoreCase("NOT AUTHORIZED TO PROCESS OVERDRAFT") || overDraft.equalsIgnoreCase("ACCOUNT NUMBER DOES NOT EXIST") || overDraft.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || overDraft.equalsIgnoreCase("TRANSACTION NOT ALLOWED - UNDER DEBT COUNSELLING")) {
                            applied = true;
                            jagacyMethods.close();
                            break;
                        }else {
                            applied = true;
                            jagacyMethods.close();
                            break;
                        }
                    }
                }

            }catch (JagacyException jay){
                applied = false;
                return applied;
            }

        }

        return applied;
    }

    private boolean isPreApproved(String clientCode,String reEnviroment, String preApprovalStatus) throws InterruptedException, JagacyException, AWTException {

        boolean approved = false;
        String password,username;
        ArrayList<Users> usersList = users.findUsers();

        int counter = 1;

        for (int i = 0; i < usersList.size(); i++) {

            if (usersList.get(i).getUsername().equalsIgnoreCase("abks580") || usersList.get(i).getUsername().equalsIgnoreCase("tdm0001")) {
                password = usersList.get(i).getPassword();
                username = usersList.get(i).getUsername();

            } else {
                password = "DD";
                username = "DD";
                continue;
            }

            try{
                jagacyMethods = new CustomerProfileJagacy();
                jagacyMethods.open();

                //Login on Jagacy(3270) Mainframe
                jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                if (jagacyMessage.equalsIgnoreCase("INCORRECT OR NO PASSWORD ENTERED.") || jagacyMessage.equalsIgnoreCase("PASSWORD HAS EXPIRED.") || jagacyMessage.equalsIgnoreCase("USERID IS NOT DEFINED TO RACF.") || jagacyMessage.equalsIgnoreCase("USERID HAS BEEN REVOKED.") || jagacyMessage.equalsIgnoreCase("Your USERID is already logged on.") || jagacyMessage.equalsIgnoreCase("SIGNON FAILED. SEE IMS MESSAGES AND COD")) {

                    if(jagacyMessage.trim().equalsIgnoreCase("Your USERID is already logged on.") && !reEnviroment.trim().equalsIgnoreCase("IV01")){
                        i--;
                        reEnviroment ="IV01";
                        jagacyMethods.close();

                        if(counter==2){
                            reEnviroment = "iv02";
                            counter=1;
                        }
                        counter++;
                        continue;

                    }
                    jagacyMethods.close();
                    // break;
                    //  continue;
                }else{

                    String preConfirm;

                    preConfirm = jagacyMethods.confirmPreApprovedPLOD("ACSQ AMLR", clientCode);

                    if(preApprovalStatus.equalsIgnoreCase("yes")){

                        if (preConfirm.equalsIgnoreCase("00")) {


                            String preApproved = jagacyMethods.preApprovedPLOD("TRCQ AMLR", clientCode, "01", "370000", "300", "300", "300", "300", "300", "01", "300", "300", "580000", "300", "300", "300");

                            if (preApproved.equalsIgnoreCase("Update Successful")) {

                                preConfirm = jagacyMethods.confirmPreApprovedPLOD("ACSQ AMLR", clientCode);

                                if (preConfirm.equalsIgnoreCase("00")) {
                                    jagacyMethods.close();
                                    approved = false;
                                    // continue;
                                }else {

                                    approved = true;
                                    jagacyMethods.close();
                                    break;
                                }
                                // continue;
                            }else {
                                jagacyMethods.close();
                                approved = false;
                            }

                        }else if(preConfirm.equalsIgnoreCase("No client info to display") || preConfirm.equalsIgnoreCase("")){
                            jagacyMethods.close();
                            approved = false;

                        }else {
                            approved = true;
                            jagacyMethods.close();
                            break;
                            //continue;
                        }

                    }else{

                        if (preConfirm.equalsIgnoreCase("00")) {

                            jagacyMethods.close();
                            approved = true;
                            break;
                        }else if(preConfirm.equalsIgnoreCase("No client info to display") || preConfirm.equalsIgnoreCase("")){

                             jagacyMethods.close();
                            approved = true;
                            break;
                        }else {
                            approved = false;
                            jagacyMethods.close();
                            //break;
                            //continue;
                        }
                    }
                }

            }catch(JagacyException ex){
                approved = false;
                return approved;
            }
        }

        return approved;
    }

    private void saveFile(ArrayList<Existing> testData, String email) throws Exception {

        String filename = "C:/Temp/RequstedTestData" + email.split("@")[0] + ".xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("RequestedData");
        Map<String, Object[]> data = new TreeMap<String, Object[]>();

        data.put("0",new Object[]{"ID Number","Surname","Name","Second Name","Score","ClientCode","Account Number","AOL","Ecasa RefNo","Policy","Bureau Type", "Fic Compliant Status"});

        for (int i=1;i<testData.size()+1;i++) {
            Existing existing = testData.get(i-1);
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
  /*
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

            try{
                jagacyMethods = new CustomerProfileJagacy();
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

            }catch(JagacyException jay){
                approved = false;
                return approved;
            }



        }
        return approved;
    }

    */
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
            jagacyMethods = new CustomerProfileJagacy();
            jagacyMethods.open();

            try{
                jagacyMessage = jagacyMethods.userLogin(username, password, reEnviroment);

                jagacyMethods.addCIFHold("CLF", clientCode,holdType);

                jagacyMethods.close();
                approved = true;
                jagacyMethods.close();
                break;
            }catch(JagacyException jay){
                approved=false;
                return approved;
            }
            //Login on Jagacy(3270) Mainframe

        }
        return approved;
    }


}
