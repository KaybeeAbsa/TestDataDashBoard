package co.za.absa.TestDataDashBoard.Methods;

import com.jagacy.Key;
import com.jagacy.Session3270;
import com.jagacy.util.JagacyException;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CustomerProfileJagacy extends Session3270 {

    private int userIdRow;
    private int userIdColumn;
    private String message = null;
    private String[] readData;
    private String value = null;
    private ArrayList<String> accountInformation = null;

    public CustomerProfileJagacy() throws JagacyException
    {
        super("sessionA","host3270.absa.co.za",993,"IBM-3279-5-E",true);
    }

    public String userLogin(String username,String password, String enviroment) throws JagacyException
    {
        try {
            waitForChange(10000);
            userIdRow = 22;
            userIdColumn = 26;
            if (enviroment.equalsIgnoreCase("IMSV") || enviroment.equalsIgnoreCase("IV01") || enviroment.equalsIgnoreCase("iv02")) {

                if(enviroment.equalsIgnoreCase("IMSV")){
                    enviroment="IV02";
                }
                this.writePosition(userIdRow, userIdColumn, enviroment);
                this.writeKey(Key.ENTER);
            } else {
                this.writePosition(userIdRow, userIdColumn, "IMSS");
                this.writeKey(Key.ENTER);
            }

            this.waitForChange(10000);
            userIdRow = 14;
            userIdColumn = 10;
            this.writePosition(userIdRow, userIdColumn, username);

            userIdRow = 16;
            userIdColumn = 11;
            this.writePosition(userIdRow, userIdColumn, password);
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 23;
            userIdColumn = 22;
            message = this.readPosition(userIdRow, userIdColumn, 40).trim();

            //   System.out.println(message);

            if (message.equalsIgnoreCase("INCORRECT OR NO PASSWORD ENTERED.") || message.equalsIgnoreCase("PASSWORD HAS EXPIRED.") || message.equalsIgnoreCase("USERID IS NOT DEFINED TO RACF.") || message.equalsIgnoreCase("USERID HAS BEEN REVOKED.") || message.equalsIgnoreCase("Your USERID is already logged on.") || message.equalsIgnoreCase("SIGNON FAILED. SEE IMS MESSAGES AND COD")) {
                return message;
            } else {

                userIdRow = 2;
                userIdColumn = 2;
                this.writePosition(userIdRow, userIdColumn, "/test mfs");
                this.writeKey(Key.ENTER);
                return "";
            }
        }catch (JagacyException j){
            return "USERID HAS BEEN REVOKED.";
        }
    }

    public String CheckCustomerProfile(String userData,String clientType, String id_Number) throws JagacyException {

        try {
            this.waitForChange(3000);
            userIdRow = 2;
            userIdColumn = 2;
            this.writePosition(userIdRow, userIdColumn, userData);
            this.writeKey(Key.ENTER);
            this.waitForChange(3000);

            userIdRow = 15;
            userIdColumn = 12;
            this.writePosition(userIdRow, userIdColumn, clientType);

            userIdRow = 15;
            userIdColumn = 39;
            this.writePosition(userIdRow, userIdColumn, id_Number.trim());
            this.writeKey(Key.ENTER);
            this.waitForChange(3000);
            //   System.out.println("Id no: " + id_Number);

            userIdRow = 22;
            userIdColumn = 8;
            message = this.readPosition(userIdRow, userIdColumn, 70).trim();
            // System.out.println("Id no: " + message);

            if (message.equalsIgnoreCase("NO CLIENT RECORD FOUND ENTER TO PROCEED TO NAME SEARCH") || message.equalsIgnoreCase("ID NUMBER INVALID") || message.equalsIgnoreCase("INVALID PASSPORT NUMBER")) {
                return message;
            } else {

                userIdRow = 6;
                userIdColumn = 14;
                this.writePosition(userIdRow, userIdColumn, "e");

                userIdRow = 6;

                userIdColumn = 52;
                this.writePosition(userIdRow, userIdColumn, "001");
                this.writeKey(Key.ENTER);
                this.waitForChange(3000);
            }


            String customerType = null;
            if (clientType.equalsIgnoreCase("01")) {
                userIdRow = 18;
                userIdColumn = 22;
                customerType = this.readPosition(userIdRow, userIdColumn, 5).trim();
            } else {
                userIdRow = 9;
                userIdColumn = 16;
                customerType = this.readPosition(userIdRow, userIdColumn, 5).trim();
            }

            userIdRow = 5;
            userIdColumn = 22;
            String customerName = this.readPosition(userIdRow, userIdColumn, 25).trim();

            userIdRow = 4;
            userIdColumn = 22;
            String customerSurname = this.readPosition(userIdRow, userIdColumn, 25).trim();

            userIdRow = 21;
            userIdColumn = 44;
            message = this.readPosition(userIdRow, userIdColumn, 12).trim();

            message = message + ":" + customerType + ":" + customerSurname + ":" + customerName;
            this.writeKey(Key.CLEAR);
//        this.writeKey(Key.PA1);
            return message;
        }catch (JagacyException j){
            return "NO CLIENT RECORD FOUND ENTER TO PROCEED TO NAME SEARCH";
        }
    }

    public String CheckAUL(String useraul, String clientCode) throws JagacyException {
        this.waitForChange(3000);
        userIdRow = 2;
        userIdColumn = 2;
        this.writePosition(userIdRow, userIdColumn, useraul);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 9;
        userIdColumn = 24;
        this.writePosition(userIdRow, userIdColumn, clientCode.trim());
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 6;
        userIdColumn = 15;
        message = this.readPosition(userIdRow, userIdColumn, 4).trim();
        //  System.out.println("Segment: " + message);

        userIdRow = 10;
        userIdColumn = 39;
        this.writePosition(userIdRow, userIdColumn, "2");
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow =  8;
        userIdColumn = 37;
        message = this.readPosition(userIdRow,userIdColumn,5).trim();
        this.writeKey(Key.CLEAR);
//        this.writeKey(Key.PA1);
        return message;
    }

    public ArrayList<String> checkAccountProfiles(String useraul, String clientCode) throws JagacyException {

        accountInformation = new ArrayList<String>();
        this.waitForChange(3000);
        userIdRow = 0;
        userIdColumn = 0;
        this.writePosition(userIdRow, userIdColumn, useraul);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 9;
        userIdColumn = 24;
        this.writePosition(userIdRow, userIdColumn, clientCode.trim());
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 10;
        userIdColumn = 39;
        this.writePosition(userIdRow, userIdColumn, "3");
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        readData = this.readScreen();

        this.writeKey(Key.CLEAR);

        for(int x = 0; x < readData.length; x++)
        {
            if(readData[x].contains("CURRENT") || readData[x].contains("ACTIVE") || readData[x].contains("W/DRAWN") || readData[x].contains("OPEN") || readData[x].contains("ALLOCATE"))
            {
                value = readData[x].substring(1,30);//30
                accountInformation.add(value);
            }
        }
        return accountInformation;
    }

    public String preApprovedPLOD(String userData, String clientID, String new_Scoring_Grade, String new_TMR, String new_AMR, String new_AMLR, String new_SOL, String new_AOL, String new_Capped_AOL, String new_CF_Grade, String new_CDTO, String new_UDTO, String new_Gross_Income, String new_CB_Commit, String new_Max_Living_EXP, String new_Accl) throws JagacyException, AWTException, InterruptedException {

        try {
            this.waitForChange(10000);
            userIdRow = 2;
            userIdColumn = 1;
            this.writePosition(userIdRow, userIdColumn, userData);
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 8;
            userIdColumn = 22;
            this.writePosition(userIdRow, userIdColumn, clientID);
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 0;
            userIdColumn = 12;
            message = this.readPosition(userIdRow, userIdColumn, 25).trim();

            if (message.equalsIgnoreCase("Data entry too short") || message.equalsIgnoreCase("No client info to display")) {
                return message;
            } else {

                userIdRow = 23;
                userIdColumn = 38;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();

                if (message.equalsIgnoreCase("UPDATE LIMITS/RISK GRADES")) {

                    userIdRow = 5;
                    userIdColumn = 66;
                    this.writePosition(userIdRow, userIdColumn, new_Scoring_Grade);

                    userIdRow = 7;
                    userIdColumn = 67;
                    this.writePosition(userIdRow, userIdColumn, new_TMR);

                    userIdRow = 8;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_AMR);

                    userIdRow = 9;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_AMLR);

                    userIdRow = 10;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_Accl);

                    userIdRow = 11;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_SOL);

                    userIdRow = 12;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_AOL);

                    userIdRow = 13;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_Capped_AOL);


                    userIdRow = 14;
                    userIdColumn = 66;
                    this.writePosition(userIdRow, userIdColumn, new_CF_Grade);

                    userIdRow = 15;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_CDTO);

                    userIdRow = 9;
                    userIdColumn = 69;
                    this.writePosition(userIdRow, userIdColumn, new_Accl);

                    userIdRow = 16;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_UDTO);

                    userIdRow = 17;
                    userIdColumn = 67;
                    this.writePosition(userIdRow, userIdColumn, new_Gross_Income);

                    userIdRow = 18;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_CB_Commit);

                    userIdRow = 19;
                    userIdColumn = 68;
                    this.writePosition(userIdRow, userIdColumn, new_Max_Living_EXP);

                    this.writeKey(Key.PF4);
                    this.waitForChange(1000);

                    userIdRow = 0;
                    userIdColumn = 12;
                    message = this.readPosition(userIdRow, userIdColumn, 25).trim();

                    this.writeKey(Key.CLEAR);
                    return message;
                } else {
                    return message;
                }
            }

        }catch(JagacyException j){
            return "No client info to display";
        }

    }

    public String confirmPreApprovedPLOD(String userData, String clientID) throws JagacyException, AWTException, InterruptedException {
       // Robot robot = new Robot();

        try {
            this.waitForChange(10000);
            userIdRow = 2;
            userIdColumn = 1;
            this.writePosition(userIdRow, userIdColumn, userData);
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 2;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, clientID);
            this.writeKey(Key.ENTER);
            this.waitForChange(10000);

            userIdRow = 16;
            userIdColumn = 50;
            message = this.readPosition(userIdRow, userIdColumn, 5).trim();

            this.writeKey(Key.CLEAR);
            return message;

        }catch (JagacyException ex){
           // this.writeKey(Key.CLEAR);
            return "00";
        }
    }

    boolean zPressed = false;

    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_F4)
        {
            if (!zPressed) {
                zPressed = true;
            }
        }
    }

    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_F4) {
            zPressed = false;
        }
    }

    //Add OverDraft
    public String AddOD(String accountNo, String requiredAction, String overdraftType, String aggrementSize) throws JagacyException {
       try {
           this.waitForChange(1000);
           userIdRow = 0;
           userIdColumn = 0;
           this.writePosition(userIdRow, userIdColumn, "CHQO");
           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 0;
           userIdColumn = 7;
           this.writePosition(userIdRow, userIdColumn, "SOD");
           this.writeKey(Key.ENTER);
           this.waitForChange(30000);
           String account = "";

           if (accountNo.length() == 10) {
               account = accountNo;
           } else {
               String acc = accountNo.replace("[", "");
               String newAcc = acc.replace("]", "");

               int occurance = StringUtils.countOccurrencesOf(newAcc, ",");

               //System.out.println(occurance);
               String[] spiltAcc = newAcc.split(",");

               for (int x = 0; x <= occurance; x++) {
                   accountNo = spiltAcc[x];
                   if (accountNo.contains("FIXED D") || accountNo.contains("DEPPLUS") || accountNo.contains("FLEXI") || accountNo.contains("MONEY") || accountNo.contains("TAXFREE") || accountNo.contains("BIZ") || accountNo.contains("TRN-ACT") || accountNo.contains("ACTIVE") || accountNo.contains("TRNACT") || accountNo.contains("TRUSAVE") || accountNo.contains("TARGET") || accountNo.contains("FUTPLAN") || accountNo.contains("DYNFD") || accountNo.contains("TRUST") || accountNo.contains("PBWPACC") || accountNo.contains("ISLMAC") || accountNo.contains("WEALTHP") || accountNo.contains("PLATCLA") || accountNo.contains("PBWISLM") || accountNo.contains("BUSFRAN") || accountNo.contains("PBWTRUS") || accountNo.contains("PBWPONE") || accountNo.contains("WEALTHB") || accountNo.contains("LIQINV") || accountNo.contains("GROWBUS") || accountNo.contains("CLASBUS") || accountNo.contains("CIBMCOR") || accountNo.contains("BUSIESS") || accountNo.contains("PBWPACC") || accountNo.contains("ABSCACC") || accountNo.contains("CACC") || accountNo.contains("POTENT") || accountNo.contains("ABSAFLE") || accountNo.contains("CALLACC")) {
                       int messageLeangth = accountNo.length();
                       account = accountNo.substring(0, 18);
                       //     System.out.println("account numberB: " +account);
                       break;
                   }
               }
           }

           System.out.print("Account for OD: "+account);

           userIdRow = 2;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, account.trim());

           userIdRow = 4;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, requiredAction);

           userIdRow = 11;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, overdraftType);

           userIdRow = 12;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, aggrementSize);

           userIdRow = 13;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, "n");

           userIdRow = 14;
           userIdColumn = 30;
           this.writePosition(userIdRow, userIdColumn, "n");

           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 0;
           userIdColumn = 12;
           message = this.readPosition(userIdRow, userIdColumn, 60).trim();
           // System.out.println("Message: " + message);

           if (message.equalsIgnoreCase("INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS") || message.equalsIgnoreCase("TRANSACTION INVALID FOR ACCOUNT TYPE") || message.equalsIgnoreCase("TRAN PROHIBITED - RESTRICTIVE DIGITAL HOLD") || message.equalsIgnoreCase("OVERDRAFT INVALID FOR ACCOUNT TYPE") || message.equalsIgnoreCase("NOT AUTHORIZED TO PROCESS OVERDRAFT") || message.equalsIgnoreCase("ACCOUNT NUMBER DOES NOT EXIST") || message.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || message.equalsIgnoreCase("TRANSACTION NOT ALLOWED - UNDER DEBT COUNSELLING") || message.equalsIgnoreCase("ENTER APPLICATION NUMBER") || message.equalsIgnoreCase("ACCOUNT NUMBER MUST BE ENTERED")) {
               return message;
           } else {
               userIdRow = 8;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "01122023");

               userIdRow = 9;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "01122023");

               userIdRow = 15;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "grnt");

               userIdRow = 18;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "113");

               userIdRow = 19;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "1196035");

               userIdRow = 20;
               userIdColumn = 25;
               this.writePosition(userIdRow, userIdColumn, "n");

            /*userIdRow = 21;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "n");*/
               this.writeKey(Key.ENTER);
               this.waitForChange(1000);

               this.writeKey(Key.ENTER);
               this.waitForChange(2000);

               this.writeKey(Key.ENTER);
               this.waitForChange(3000);

               userIdRow = 0;
               userIdColumn = 12;
               message = this.readPosition(userIdRow, userIdColumn, 60).trim();

               if (message.equalsIgnoreCase("?? Missing data entry                           ERR-")) {
                   userIdRow = 23;
                   userIdColumn = 24;
                   this.writePosition(userIdRow, userIdColumn, "y");

                   this.writeKey(Key.ENTER);
                   this.waitForChange(30000);

               }

               this.writeKey(Key.ENTER);
               this.waitForChange(1000);

               userIdRow = 0;
               userIdColumn = 12;
               message = this.readPosition(userIdRow, userIdColumn, 60).trim();
               //  System.out.println("OD added Message: " + message);
               return message;
           }

       }catch(JagacyException j){
           return "INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS";
       }
    }


    //Delete OD
    public String deleteOverDraft(String accountNo, String requiredAction, String overdraftType, String aggrementSize) throws JagacyException {
        try {
            this.waitForChange(1000);
            userIdRow = 0;
            userIdColumn = 0;
            this.writePosition(userIdRow, userIdColumn, "CHQO");
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 0;
            userIdColumn = 7;
            this.writePosition(userIdRow, userIdColumn, "SOD");
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);
            String account = "";

            if (accountNo.length() == 10) {
                account = accountNo;
            } else {
                String acc = accountNo.replace("[", "");
                String newAcc = acc.replace("]", "");

                int occurance = StringUtils.countOccurrencesOf(newAcc, ",");

                //System.out.println(occurance);
                String[] spiltAcc = newAcc.split(",");

                for (int x = 0; x <= occurance; x++) {
                    accountNo = spiltAcc[x];
                    if (accountNo.contains("FIXED D") || accountNo.contains("DEPPLUS") || accountNo.contains("FLEXI") || accountNo.contains("MONEY") || accountNo.contains("TAXFREE") || accountNo.contains("BIZ") || accountNo.contains("TRN-ACT") || accountNo.contains("ACTIVE") || accountNo.contains("TRNACT") || accountNo.contains("TRUSAVE") || accountNo.contains("TARGET") || accountNo.contains("FUTPLAN") || accountNo.contains("DYNFD") || accountNo.contains("TRUST") || accountNo.contains("PBWPACC") || accountNo.contains("ISLMAC") || accountNo.contains("WEALTHP") || accountNo.contains("PLATCLA") || accountNo.contains("PBWISLM") || accountNo.contains("BUSFRAN") || accountNo.contains("PBWTRUS") || accountNo.contains("PBWPONE") || accountNo.contains("WEALTHB") || accountNo.contains("LIQINV") || accountNo.contains("GROWBUS") || accountNo.contains("CLASBUS") || accountNo.contains("CIBMCOR") || accountNo.contains("BUSIESS") || accountNo.contains("PBWPACC") || accountNo.contains("ABSCACC") || accountNo.contains("CACC") || accountNo.contains("POTENT") || accountNo.contains("ABSAFLE") || accountNo.contains("CALLACC")) {
                        int messageLeangth = accountNo.length();
                        account = accountNo.substring(0, 18);
                        //     System.out.println("account numberB: " +account);
                        break;
                    }
                }
            }

            //  System.out.print("Account for OD: "+account);

            userIdRow = 2;
            userIdColumn = 30;
            this.writePosition(userIdRow, userIdColumn, account.trim());

            userIdRow = 4;
            userIdColumn = 30;
            this.writePosition(userIdRow, userIdColumn, requiredAction);

            this.writeKey(Key.ENTER);
            this.waitForChange(30000);


            userIdRow = 0;
            userIdColumn = 12;
            message = this.readPosition(userIdRow, userIdColumn, 60).trim();
            System.out.println("Message 1 : " + message);

            if (message.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || message.equalsIgnoreCase("INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS") || message.equalsIgnoreCase("TRANSACTION INVALID FOR ACCOUNT TYPE") || message.equalsIgnoreCase("TRAN PROHIBITED - RESTRICTIVE DIGITAL HOLD") || message.equalsIgnoreCase("OVERDRAFT INVALID FOR ACCOUNT TYPE") || message.equalsIgnoreCase("NOT AUTHORIZED TO PROCESS OVERDRAFT") || message.equalsIgnoreCase("ACCOUNT NUMBER DOES NOT EXIST") || message.equalsIgnoreCase("NO RAC-SITE FOR ACCOUNT DOMICILE") || message.equalsIgnoreCase("TRANSACTION NOT ALLOWED - UNDER DEBT COUNSELLING") || message.equalsIgnoreCase("ENTER APPLICATION NUMBER") || message.equalsIgnoreCase("ACCOUNT NUMBER MUST BE ENTERED")) {
                return message;
            } else {
                userIdRow = 18;
                userIdColumn = 25;
                this.writePosition(userIdRow, userIdColumn, "113");

                userIdRow = 19;
                userIdColumn = 25;
                this.writePosition(userIdRow, userIdColumn, "1196035");

                userIdRow = 20;
                userIdColumn = 25;
                this.writePosition(userIdRow, userIdColumn, "Y");

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);
                userIdRow = 0;
                userIdColumn = 12;
                message = this.readPosition(userIdRow, userIdColumn, 60).trim();

                System.out.println("Error 2: "+ message);

                if (message.equalsIgnoreCase("NO OVERDRAFT/ECF TO DELETE/DECLINE               ENT-")) {
                    return message;
                }else{
                    userIdRow = 15;
                    userIdColumn = 24;
                    this.writePosition(userIdRow, userIdColumn, "Y");

                    this.writeKey(Key.ENTER);
                    this.waitForChange(30000);

                    userIdRow = 16;
                    userIdColumn = 1;
                    message = this.readPosition(userIdRow, userIdColumn, 60).trim();

                    return message;
                }

              //  return message;
            }

        }catch(JagacyException j){
            return "INCREASE NOT ALLOWED IF NO OVERDRAFT EXISTS";
        }
    }

    public String checkMpp() throws JagacyException {

        try{
            this.waitForChange(10000);
            userIdRow = 2;
            userIdColumn = 1;
            this.writePosition(userIdRow, userIdColumn, "mpp");
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 0;
            userIdColumn = 7;
            this.writePosition(userIdRow, userIdColumn, "pf13");
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 2;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "Y");

            userIdRow = 7;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "99");

            userIdRow = 8;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "8198");

            userIdRow = 9;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "1212");

            userIdRow = 10;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "122222");

            userIdRow = 11;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "121212");

            userIdRow = 12;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "034");

            userIdRow = 13;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "034");

            userIdRow = 14;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, "ABS");
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            return "complete";
        }catch(JagacyException ex){
            return "Error";
        }

    }

    //Aol
    public String CheckNHBM(String useraul, String accountNo) throws JagacyException {
        this.waitForChange(3000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, useraul);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        String acc = accountNo.replace("[","");
        String newAcc = acc.replace("]","");

        int occurance = StringUtils.countOccurrencesOf(newAcc, ",");

        //System.out.println(occurance);
        String[] spiltAcc = newAcc.split(",");
        String account = "";
        for(int x  = 0; x <= occurance; x++){
            accountNo = spiltAcc[x];
            if(accountNo.contains("FIXED D") || accountNo.contains("DEPPLUS") || accountNo.contains("FLEXI") || accountNo.contains("MONEY") || accountNo.contains("TAXFREE")|| accountNo.contains("BIZ") || accountNo.contains("TRN-ACT") || accountNo.contains("ACTIVE") || accountNo.contains("TRNACT") || accountNo.contains("TRUSAVE") || accountNo.contains("TARGET") || accountNo.contains("FUTPLAN") || accountNo.contains("DYNFD")|| accountNo.contains("TRUST") || accountNo.contains("PBWPACC") || accountNo.contains("ISLMAC") ||  accountNo.contains("WEALTHP") || accountNo.contains("PLATCLA") || accountNo.contains("PBWISLM") || accountNo.contains("BUSFRAN") || accountNo.contains("PBWTRUS") || accountNo.contains("PBWPONE") || accountNo.contains("WEALTHB") || accountNo.contains("LIQINV") || accountNo.contains("GROWBUS") || accountNo.contains("CLASBUS") || accountNo.contains("CIBMCOR") || accountNo.contains("BUSIESS") || accountNo.contains("PBWPACC") || accountNo.contains("ABSCACC") || accountNo.contains("CACC") || accountNo.contains("POTENT")|| accountNo.contains("ABSAFLE") || accountNo.contains("CALLACC")){
                int messageLeangth = accountNo.length();
                account = accountNo.substring(0, 18);
                System.out.println("account numberB: " +account);
                break;
            }
        }

        userIdRow = 3;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, account.trim());

        userIdRow = 4;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, "1");

        userIdRow = 6;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, "ABSA");
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 22;
        userIdColumn = 10;
        message = this.readPosition(userIdRow, userIdColumn, 30).trim();

        if(message.length() == 0)
        {
            userIdRow = 15;
            userIdColumn = 2;
            message = this.readPosition(userIdRow, userIdColumn, 30).trim();

            this.writeKey(Key.CLEAR);
            return message;

        }else{

            this.writeKey(Key.CLEAR);
            return message;
        }

    }

    public String addFicHold(String userData, String clientID) throws JagacyException, AWTException, InterruptedException {
       try {
           Robot robot = new Robot();

           this.waitForChange(1000);
           userIdRow = 2;
           userIdColumn = 1;
           this.writePosition(userIdRow, userIdColumn, userData);
           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 11;
           userIdColumn = 38;
           this.writePosition(userIdRow, userIdColumn, clientID);
           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 22;
           userIdColumn = 10;
           message = this.readPosition(userIdRow, userIdColumn, 25).trim();

           System.out.println("Messsage: " + message);
           if (message.equalsIgnoreCase("Data entry too short") || message.equalsIgnoreCase("No client info to display")) {
               return message;
           } else {
               userIdRow = 14;
               userIdColumn = 45;
               this.writePosition(userIdRow, userIdColumn, "y");
               this.writeKey(Key.ENTER);
               this.waitForChange(30000);

               userIdRow = 14;
               userIdColumn = 19;
               message = this.readPosition(userIdRow, userIdColumn, 25).trim();

               System.out.println("message: " + message);
               return message;
           }

       }catch(JagacyException j){
           return "Data entry too short";
       }
    }

    public void addCIFHold(String userData, String clientID,String cifHold) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 0;
        userIdColumn = 7;
        this.writePosition(userIdRow, userIdColumn, "hold");
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 3;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, clientID);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 0;
        userIdColumn = 13;
        message = this.readPosition(userIdRow, userIdColumn, 30).trim();

        if(cifHold.equalsIgnoreCase("IdentificationRequired")){
            userIdRow = 4;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");

        }else{
            userIdRow = 4;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("InsolventEstate"))
        {
            userIdRow = 5;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 5;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("DeceasedEstate")){
            userIdRow = 6;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 6;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("SpouseDeceased")){
            userIdRow = 7;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 7;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("Curatorship")){
            userIdRow = 8;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 8;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("ClientAgreementIssued")){
            userIdRow = 11;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 11;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("NewPostalAddressRequired")){
            userIdRow = 13;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 13;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("NewResidentialAddressRequired")){
            userIdRow = 14;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 14;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        if(cifHold.equalsIgnoreCase("NewEmployersNameAddressRequired")){
            userIdRow = 15;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "Y");
        }else{
            userIdRow = 15;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

    }
 }

