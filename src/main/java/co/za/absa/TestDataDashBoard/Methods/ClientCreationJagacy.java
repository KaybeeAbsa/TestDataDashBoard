package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.model.Newtobank;
import com.jagacy.Key;
import com.jagacy.Session3270;
import com.jagacy.util.JagacyException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ClientCreationJagacy extends Session3270
{


    private int userIdRow;
    private int userIdColumn;
    private String message;
    private String value;
    private  String data;
    private ArrayList<String> numbers;
    private String newCardNumber;
    private int valid = 0;
    public ClientCreationJagacy() throws JagacyException
    {
        super("sessionA","host3270.absa.co.za",993,"IBM-3279-5-E",true);
    }

    public String userLogin(String username,String password, String environment) throws JagacyException
    {
        try {
            waitForChange(10000);
            userIdRow = 22;
            userIdColumn = 26;
            if (environment.equalsIgnoreCase("IMSV")) {
                this.writePosition(userIdRow, userIdColumn, "IMSV");
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

            System.out.println("Jagacy Message: " + message);
            if (message.equalsIgnoreCase("INCORRECT OR NO PASSWORD ENTERED.") || message.equalsIgnoreCase("PASSWORD HAS EXPIRED.") || message.equalsIgnoreCase("USERID IS NOT DEFINED TO RACF.") || message.equalsIgnoreCase("USERID HAS BEEN REVOKED.") || message.equalsIgnoreCase("Your USERID is already logged on.") || message.equalsIgnoreCase("SIGNON FAILED. SEE IMS MESSAGES AND COD")) {
                return message;
            } else {

                userIdRow = 2;
                userIdColumn = 2;
                this.writePosition(userIdRow, userIdColumn, "/test mfs");
                this.writeKey(Key.ENTER);
                return message;
            }
        }catch (JagacyException j){
            return "USERID HAS BEEN REVOKED.";
        }
    }

    //Create Customer Onboarding
    public String IndividualClientsBoarding( String idtype, String surname, String firstName, String secondName, String email, String countNo, String clientTpe, String ProductType) throws JagacyException
    {

        try {
            this.waitForChange(1000);
            userIdRow = 2;
            userIdColumn = 1;
            this.writePosition(userIdRow, userIdColumn, "cif");
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);


            String date = idtype.substring(0, 6);
            String year = date.substring(0, 2);
            String month = date.substring(2, 4);
            String day = date.substring(4, 6);
            String newYear = "";
            String valid = year.substring(0, 1);

            if (valid.equalsIgnoreCase("0")) {
                newYear = "20" + year;
            } else {
                newYear = "19" + year;
            }

            String dateOfBirth = day + month + newYear;

            if ("i".equalsIgnoreCase("N")) {
                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 14;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "i");

                userIdRow = 8;
                userIdColumn = 35;
                this.writePosition(userIdRow, userIdColumn, "");
                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();

                if (message.equals("")) {

                    userIdRow = 2;
                    userIdColumn = 10;
                    this.writePosition(userIdRow, userIdColumn, "a");
                    this.writeKey(Key.ENTER);
                    this.waitForChange(1000);


                    return "";
                } else {
                    return message;
                }

            } else {


                this.writeKey(Key.ENTER);

                this.waitForChange(1000);

                userIdRow = 8;
                userIdColumn = 35;
                this.writePosition(userIdRow, userIdColumn, surname);

                String intial = null;
                //System.out.println("Second name: " + secondName + " length: " + secondName.length());
                if (secondName.isEmpty()) {
                    intial = firstName.substring(0, 1);
                } else {
                    intial = firstName.substring(0, 1) + "" + secondName.substring(0, 1);
                }

                // System.out.println("Intials: " + intial);
                userIdRow = 13;
                userIdColumn = 31;
                this.writePosition(userIdRow, userIdColumn, intial);

                userIdRow = 14;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "01");

      /*  int dateLength = dateOfBirth.length();
        if(dateLength != 8)
        {
            String newDate = "0" + dateOfBirth;
            userIdRow = 15;
            userIdColumn = 31;
            this.writePosition(userIdRow, userIdColumn, newDate);
        }else
        {
            userIdRow = 15;
            userIdColumn = 31;
            this.writePosition(userIdRow, userIdColumn, dateOfBirth);
        }*/

                userIdRow = 15;
                userIdColumn = 31;
                this.writePosition(userIdRow, userIdColumn, dateOfBirth);

                userIdRow = 16;
                userIdColumn = 31;
                this.writePosition(userIdRow, userIdColumn, "m");

                userIdRow = 19;
                userIdColumn = 36;
                this.writePosition(userIdRow, userIdColumn, "i");
                this.writeKey(Key.ENTER);
                this.waitForChange(10000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 50).trim();

              //  System.out.println("Passed : " + message);

                if (message.equalsIgnoreCase("TYPE E AND F5/F6 OR TYPE M/A/N AND ENTER")) {
                    message = "client already exists";
                    return message;
                }

       /*if(message != null)
        {
            System.out.println("Failed : " + message);
            return message;
        }else{*/
               // System.out.println("Passed : " + message);

                userIdRow = 2;
                userIdColumn = 9;
                this.writePosition(userIdRow, userIdColumn, "a");

                this.writeKey(Key.ENTER);
                this.waitForChange(10000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 50).trim();
//            System.out.println("Messages: " + message);

                userIdRow = 2;
                userIdColumn = 57;
                this.writePosition(userIdRow, userIdColumn, "888");

                String names = null;

                if (secondName.isEmpty()) {
                    names = firstName.trim();
                } else {
                    names = firstName + "" + secondName;
                }
              //  System.out.println("Names: " + names);

                userIdRow = 5;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, names);

                userIdRow = 10;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "01");

                userIdRow = 11;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, idtype);

                userIdRow = 11;
                userIdColumn = 58;
                this.writePosition(userIdRow, userIdColumn, "t");

                userIdRow = 12;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "sou01");

                userIdRow = 13;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "so003");

                userIdRow = 15;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "2");

                userIdRow = 17;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "e");

                userIdRow = 18;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "1");

                userIdRow = 19;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, clientTpe);
                //  this.waitForChange(30000);
                this.writeKey(Key.ENTER);
                this.waitForChange(10000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 50).trim();

               // System.out.println("Message before address: " + message);

                if (message.equalsIgnoreCase("INVALID CHARACTERS IN NAME") || message.equalsIgnoreCase("DATE ISSUED CANNOT BE LESS THAN DATE OF BIRTH")) {
                    return message;
                }

                if (message.equalsIgnoreCase("GENDER CODE INVALID TO ID NUMBER")) {

                    if ("M".equalsIgnoreCase("M")) {

                        userIdRow = 8;
                        userIdColumn = 23;
                        this.writePosition(userIdRow, userIdColumn, "F");

                        userIdRow = 7;
                        userIdColumn = 23;
                        this.writePosition(userIdRow, userIdColumn, "03");
                    } else {
                        userIdRow = 8;
                        userIdColumn = 23;
                        this.writePosition(userIdRow, userIdColumn, "M");

                        userIdRow = 7;
                        userIdColumn = 23;
                        this.writePosition(userIdRow, userIdColumn, "01");
                    }

                    this.writeKey(Key.ENTER);
                    this.waitForChange(10000);

                    //this.waitForChange(3000000);
                    userIdRow = 22;
                    userIdColumn = 8;
                    message = this.readPosition(userIdRow, userIdColumn, 50).trim();

               //     System.out.println("Message before address: " + message);

                    if (message.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE")) {
                        return message;
                    }
                }

                if (message.equalsIgnoreCase("BIRTH DATE NOT THE SAME AS IN ID NUMBER") || message.equalsIgnoreCase("DUPLICATE RECORDS EXIST, PLEASE RESOLVE") || message.equalsIgnoreCase("ID NUMBER INVALID")) {
                    return message;
                }

                userIdRow = 4;
                userIdColumn = 18;
                this.writePosition(userIdRow, userIdColumn, "270 rep road");

                userIdRow = 4;
                userIdColumn = 50;
                this.writePosition(userIdRow, userIdColumn, "270 rep road");

                userIdRow = 5;
                userIdColumn = 18;
                this.writePosition(userIdRow, userIdColumn, "270 rep road");

                userIdRow = 5;
                userIdColumn = 50;
                this.writePosition(userIdRow, userIdColumn, "270 rep road");

                userIdRow = 6;
                userIdColumn = 18;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 6;
                userIdColumn = 50;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 7;
                userIdColumn = 18;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 7;
                userIdColumn = 50;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 8;
                userIdColumn = 19;
                this.writePosition(userIdRow, userIdColumn, "2194");

                userIdRow = 8;
                userIdColumn = 50;
                this.writePosition(userIdRow, userIdColumn, "2194");

                userIdRow = 9;
                userIdColumn = 26;
                this.writePosition(userIdRow, userIdColumn, "so003");

                userIdRow = 10;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, email);

                userIdRow = 14;
                userIdColumn = 77;
                this.writePosition(userIdRow, userIdColumn, "Y");

                String newContacts = "0" + countNo;
                userIdRow = 15;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, countNo);

                userIdRow = 16;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 16;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 17;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 17;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 18;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 18;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 19;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 19;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 20;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 20;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 21;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "N");

                userIdRow = 21;
                userIdColumn = 68;
                this.writePosition(userIdRow, userIdColumn, "N");
                this.writeKey(Key.ENTER);
                this.waitForChange(10000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();
               // System.out.println("Messages after address: " + message);


                userIdRow = 4;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "1");

        /*userIdRow = 5;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "13");

        userIdRow = 6;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "07");

        userIdRow = 7;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "33331");

        userIdRow = 8;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "1038378");*/

                userIdRow = 13;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "absa");

                userIdRow = 14;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "270 rep road");

                userIdRow = 15;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 16;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "randburg");

                userIdRow = 17;
                userIdColumn = 30;
                this.writePosition(userIdRow, userIdColumn, "2194");

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();
//
//            System.out.println("Messages: " + message);

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 4;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "15");

                userIdRow = 5;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "20");

                userIdRow = 6;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "2");

                userIdRow = 7;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "f");

                userIdRow = 8;
                userIdColumn = 23;
                this.writePosition(userIdRow, userIdColumn, "z");

                userIdRow = 11;
                userIdColumn = 48;
                this.writePosition(userIdRow, userIdColumn, "y");

                userIdRow = 12;
                userIdColumn = 40;
                this.writePosition(userIdRow, userIdColumn, "03");

                userIdRow = 13;
                userIdColumn = 39;
                this.writePosition(userIdRow, userIdColumn, "n");

                userIdRow = 14;
                userIdColumn = 28;
                this.writePosition(userIdRow, userIdColumn, "t");

                userIdRow = 15;
                userIdColumn = 28;
                this.writePosition(userIdRow, userIdColumn, "1196035");

                userIdRow = 16;
                userIdColumn = 28;
                this.writePosition(userIdRow, userIdColumn, "t");
                userIdRow = 17;
                userIdColumn = 28;
                this.writePosition(userIdRow, userIdColumn, "1196035");

                userIdRow = 18;
                userIdColumn = 17;
                this.writePosition(userIdRow, userIdColumn, ProductType);

                userIdRow = 20;
                userIdColumn = 22;
                this.writePosition(userIdRow, userIdColumn, "1196035");

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 50).trim();

                //System.out.println("Message Empo Stuff: " + message);

                if (message.equalsIgnoreCase("CLIENT TYPE AND ACCOUNT TYPE INCOMPATIBLE") || message.equalsIgnoreCase("** CLIENT IS OVER-AGE FOR PROD") || message.equalsIgnoreCase("INVALID SITE FOR CAPTURE") || message.equalsIgnoreCase("ABSA ISLAMIC PRIVATE ACCOUNT MAY ONLY BE OPENED BY") || message.equalsIgnoreCase("INCOMPATIBLE CATEGORY AND DATE OF BIRTH") || message.equalsIgnoreCase("INVALID CATEGORY") || message.equalsIgnoreCase("ACC TYPE ONLY VALID FOR ABSA WEALTH SITES") || message.equalsIgnoreCase("ACCOUNT TYPE AND BANKING SECTOR INCOMPATIBLE") || message.equalsIgnoreCase("STRUCTURED LOAN MAY ONLY BE OPENED BY A PRIVATE BA")) {
                    return message;
                }


                userIdRow = 8;
                userIdColumn = 54;
                this.writePosition(userIdRow, userIdColumn, "1");

                if ("n".equalsIgnoreCase("N")) {
                    userIdRow = 11;
                    userIdColumn = 45;
                    this.writePosition(userIdRow, userIdColumn, "n");

                    userIdRow = 15;
                    userIdColumn = 37;
                    this.writePosition(userIdRow, userIdColumn, "n");
                } else {
                    userIdRow = 11;
                    userIdColumn = 45;
                    this.writePosition(userIdRow, userIdColumn, "n");

                    userIdRow = 12;
                    userIdColumn = 29;
                    this.writePosition(userIdRow, userIdColumn, "n");

                    userIdRow = 15;
                    userIdColumn = 37;
                    this.writePosition(userIdRow, userIdColumn, "no");

                    userIdRow = 17;
                    userIdColumn = 3;
                    this.writePosition(userIdRow, userIdColumn, "so003");

                    userIdRow = 17;
                    userIdColumn = 9;
                    this.writePosition(userIdRow, userIdColumn, "no");

                }

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();

//            System.out.println("Messages: " + message);

                userIdRow = 12;
                userIdColumn = 33;
                this.writePosition(userIdRow, userIdColumn, "20");

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 22;
                userIdColumn = 8;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 11;
                userIdColumn = 21;
                String account = this.readPosition(userIdRow, userIdColumn, 25).trim();

                userIdRow = 3;
                userIdColumn = 21;
                String clientCode = this.readPosition(userIdRow, userIdColumn, 12).trim();

                userIdRow = 14;
                userIdColumn = 1;
                message = this.readPosition(userIdRow, userIdColumn, 25).trim();
                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                this.writeKey(Key.CLEAR);
                this.waitForChange(1000);
                message = message + account + "," + clientCode;
               // System.out.println("Account No: " + account);
                return message;

            }
        }catch (JagacyException J){
            return "INVALID CHARACTERS IN NAME";
        }
    }

    //Cash Deposit(CD)
    public String cashDeposit(String userData, String transactionType, String accountNo, String amount, String transactionNo) throws JagacyException {

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 2;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        if(userData.equalsIgnoreCase("FINS")){
            this.writeKey(Key.CLEAR);
            this.waitForChange(3000);
            userIdRow = 0;
            userIdColumn = 0;
            this.writePosition(userIdRow, userIdColumn, userData);
            this.writeKey(Key.ENTER);
            this.waitForChange(3000);
        }

        userIdRow = 2;
        userIdColumn = 25;
        this.writePosition(userIdRow, userIdColumn, transactionType);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);

        userIdRow = 1;
        userIdColumn = 25;
        this.writePosition(userIdRow, userIdColumn, accountNo);

        userIdRow = 2;
        userIdColumn = 25;
        this.writePosition(userIdRow, userIdColumn, amount);

        if (userData.equalsIgnoreCase("FINS")) {
            this.waitForChange(1000);
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            userIdRow = 21;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 25);
            System.out.println("Cd: " + message);
            //this.writeKey(Key.CLEAR);
            return message;
        } else {
            userIdRow = 10;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, transactionNo);
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            userIdRow = 21;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 25);
          //  System.out.println(message);
            if(message.equalsIgnoreCase("CONTROLLER OVERIDE REQUIR")){
                userIdRow = 17;
                userIdColumn = 25;
                this.writePosition(userIdRow, userIdColumn, "123456");
                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 21;
                userIdColumn = 1;
                message = this.readPosition(userIdRow, userIdColumn, 25);
              //  System.out.println("Cd: " + message);
                this.writeKey(Key.CLEAR);
                return message;
            }else if(message.equalsIgnoreCase("TELLER OVERIDE REQUIRED")){
                this.writeKey(Key.ENTER);
                this.waitForChange(1000);

                userIdRow = 21;
                userIdColumn = 1;
                message = this.readPosition(userIdRow, userIdColumn, 25);
              //  System.out.println("Cd: " + message);
                this.writeKey(Key.CLEAR);
                this.waitForChange(1000);
                return message;
            }else {
                this.writeKey(Key.CLEAR);
                this.waitForChange(1000);
                return message;
            }
        }
    }

    public String assignCombiCardsNumbers(String userData, String cardType, String Personalized, String ChequeAccountNo,String SavingsAccountNo, String brand, String branch, String autoLink, String cardNo, String enviroment) throws JagacyException {
       try {
           this.waitForChange(30000);
           this.writeKey(Key.ENTER);

           this.waitForChange(30000);
           userIdRow = 2;
           userIdColumn = 1;
           this.writePosition(userIdRow, userIdColumn, userData);
           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 2;
           userIdColumn = 41;
           this.writePosition(userIdRow, userIdColumn, cardType);

           userIdRow = 3;
           userIdColumn = 41;
           this.writePosition(userIdRow, userIdColumn, Personalized);

           ChequeAccountNo = ChequeAccountNo.replace("-", "");

           if (ChequeAccountNo.equals("")) {
              // System.out.println("inside Savings: " + SavingsAccountNo);
               if (enviroment.equalsIgnoreCase("IMSV")) {

                   userIdRow = 6;
                   userIdColumn = 41;
                   this.writePosition(userIdRow, userIdColumn, SavingsAccountNo);
                   //return "Combi brand for Savings do not exist";
               } else {
                   userIdRow = 6;
                   userIdColumn = 41;
                   this.writePosition(userIdRow, userIdColumn, SavingsAccountNo);
               }

           } else {

               userIdRow = 6;
               userIdColumn = 41;
               this.writePosition(userIdRow, userIdColumn, ChequeAccountNo);
           }

           this.writeKey(Key.ENTER);
           this.waitForChange(30000);

           userIdRow = 0;
           userIdColumn = 1;
           message = this.readPosition(userIdRow, userIdColumn, 55).trim();

           if (message.equalsIgnoreCase("CMMI  NEWP Client has no Active Accounts")) {
               return message;
           } else {


               if (Personalized.equalsIgnoreCase("n")) {

                   String[] screenData = this.readScreen();

                   int combiBrand = 0;
                   for (int x = 0; x < screenData.length; x++) {

                       if (enviroment.equalsIgnoreCase("IMSS")) {
                           if (screenData[x].contains("VASA")) {
                               combiBrand = x;
                           }
                       } else if(enviroment.equalsIgnoreCase("IMSV") && ChequeAccountNo.equals("")){
                           //System.out.println("Assign Savings");
                           if (screenData[x].contains("CSCD")) {
                               combiBrand = x;
                           }
                       }else {
                          // System.out.println("Assign Cheques");
                           if (screenData[x].contains("LGDC")) {
                               combiBrand = x;
                           }
                       }

                   }

                   userIdRow = combiBrand;
                   userIdColumn = 2;
                   this.writePosition(userIdRow, userIdColumn, brand);
                   this.writeKey(Key.ENTER);
                   this.waitForChange(30000);

                   this.writeKey(Key.ENTER);
                   this.waitForChange(30000);

                  this.writeKey(Key.ENTER);
                   this.waitForChange(30000);

                   newCardNumber = cardNo.replace("-", "");
                   userIdRow = 7;
                   userIdColumn = 23;
                   this.writePosition(userIdRow, userIdColumn, newCardNumber);

               } else {

                   userIdRow = 4;
                   userIdColumn = 2;
                   this.writePosition(userIdRow, userIdColumn, brand);

                   this.writeKey(Key.ENTER);
                   this.waitForChange(30000);

                   this.writeKey(Key.ENTER);
                   this.waitForChange(30000);
               }

               userIdRow = 9;
               userIdColumn = 23;
               this.writePosition(userIdRow, userIdColumn, branch);

               if (ChequeAccountNo != null && SavingsAccountNo != null) {

                   userIdRow = 12;
                   userIdColumn = 37;
                   this.writePosition(userIdRow, userIdColumn, ChequeAccountNo);

                   userIdRow = 13;
                   userIdColumn = 37;
                   this.writePosition(userIdRow, userIdColumn, SavingsAccountNo);

               } else if (ChequeAccountNo != null) {

                   userIdRow = 12;
                   userIdColumn = 37;
                   this.writePosition(userIdRow, userIdColumn, ChequeAccountNo);
               } else {

                   userIdRow = 13;
                   userIdColumn = 37;
                   this.writePosition(userIdRow, userIdColumn, SavingsAccountNo);
               }

               userIdRow = 19;
               userIdColumn = 20;
               this.writePosition(userIdRow, userIdColumn, autoLink);
               this.writeKey(Key.ENTER);
               this.waitForChange(30000);

               userIdRow = 0;
               userIdColumn = 1;
               message = this.readPosition(userIdRow, userIdColumn, 60).trim();

             ///  System.out.println("Combi message: " + message + " : " + newCardNumber);

               if (message.equalsIgnoreCase("CMMI  NEWP          Combi Card Number already exists") || message.equalsIgnoreCase("CMMI  NEW  Client has Active Savings Acc, please Nominate") || message.equalsIgnoreCase("CMMI  NEW  Combi Card Number already exists") || message.equalsIgnoreCase("ACCS - Stock Code is different in System") || message.equalsIgnoreCase("CMMI  NEWP     ACCS - Stock Code is different in System")) {
                   System.out.println(message);
                   return message;
               } else if (message.equalsIgnoreCase("CMMI  NEW  Additional Combi Card, Card Fee will be charged")) {
                   return message;
               }

               if (message.length() == 0) {

                   userIdRow = 23;
                   userIdColumn = 70;
                   message = this.readPosition(userIdRow, userIdColumn, 10).trim();
                 //  System.out.println("Heading Message2: " + message);

                   userIdRow = 1;
                   userIdColumn = 23;
                   message = this.readPosition(userIdRow, userIdColumn, 25).trim();
                   //System.out.println("Heading Message3: " + message);
                   this.writeKey(Key.CLEAR);
                   return message;

               } else {

                   this.writeKey(Key.ENTER);
                   this.waitForChange(300000);

                   userIdRow = 23;
                   userIdColumn = 70;
                   message = this.readPosition(userIdRow, userIdColumn, 10).trim();
               //    System.out.println("Heading Message 2: " + message);

                   userIdRow = 1;
                   userIdColumn = 23;
                   message = this.readPosition(userIdRow, userIdColumn, 25).trim();
                 //  System.out.println("Heading Message 3: " + message);
                   this.writeKey(Key.CLEAR);
                   return message;
               }
           }
       }catch(JagacyException j)
       {
           return "CMMI  NEWP Client has no Active Accounts";
       }
    }

    public String ChangeLimit(String userLimit, String cardNo, String FirstAmount, String secondAmount, String thirdAmount) throws JagacyException, AWTException {

        try {
            this.waitForChange(10000);
            String newCardNumber = cardNo.replace("-", "");
            userIdRow = 2;
            userIdColumn = 1;
            this.writePosition(userIdRow, userIdColumn, userLimit);
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 2;
            userIdColumn = 25;
            this.writePosition(userIdRow, userIdColumn, newCardNumber.trim());
            this.writeKey(Key.ENTER);
            this.waitForChange(30000);

            userIdRow = 0;
            userIdColumn = 12;
            message = this.readPosition(userIdRow, userIdColumn, 50).trim();

            if (message.equalsIgnoreCase("Card Number Invalid or not Entered               E")) {
                return message;
            }

            userIdRow = 6;
            userIdColumn = 27;
            this.writePosition(userIdRow, userIdColumn, FirstAmount);

            userIdRow = 7;
            userIdColumn = 27;
            this.writePosition(userIdRow, userIdColumn, secondAmount);

            userIdRow = 8;
            userIdColumn = 27;
            this.writePosition(userIdRow, userIdColumn, thirdAmount);

            this.writeKey(Key.PF14);
            this.waitForChange(10000);

            userIdRow = 1;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 50).trim();

            return message;
        }catch(JagacyException J){
            System.out.println("Invalid Position");
            return "Combi Limits not set";
        }
    }

    public String BusinessClientsOnBoarding(String companyName, String clientTypeGroup, String idType, String idNumber, String clientType, String contactNo, String contactPerson, String email, String ProductType) throws JagacyException
    {
        this.waitForChange(10000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn,"CIF");
        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 8;
        userIdColumn = 35;
        this.writePosition(userIdRow, userIdColumn, companyName);

        userIdRow = 19;
        userIdColumn = 36;
        this.writePosition(userIdRow, userIdColumn, clientTypeGroup);

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 2;
        userIdColumn = 10;
        this.writePosition(userIdRow, userIdColumn, "a");

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 2;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "888");

        userIdRow = 4;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, idType);

        userIdRow = 5;
        userIdColumn = 22;
        this.writePosition(userIdRow, userIdColumn, idNumber);

        userIdRow = 6;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "E");

        userIdRow = 7;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, clientType);

        if(clientType.equalsIgnoreCase("03001")){
            userIdRow = 8;
            userIdColumn = 22;
            this.writePosition(userIdRow, userIdColumn, "A01");
        }

        userIdRow = 10;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "11110");

        userIdRow = 12;
        userIdColumn = 27;
        this.writePosition(userIdRow, userIdColumn, "A01");

        userIdRow = 12;
        userIdColumn = 71;
        this.writePosition(userIdRow, userIdColumn, "100");

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 3;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        userIdRow = 3;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        userIdRow = 5;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 5;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 6;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 6;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 7;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, "2194");

        userIdRow = 7;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "2194");

        userIdRow = 8;
        userIdColumn = 22;
        this.writePosition(userIdRow, userIdColumn, "SO003");

        userIdRow = 8;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, email);

        userIdRow = 11;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, contactPerson);

        userIdRow = 12;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, "01");

        userIdRow = 12;
        userIdColumn = 61;
        this.writePosition(userIdRow, userIdColumn, contactNo);

        userIdRow = 15;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, "Y");

        userIdRow = 16;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "y");

        userIdRow = 16;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "y");


        userIdRow = 17;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "y");

        userIdRow = 17;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "y");

        userIdRow = 18;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 18;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 19;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 19;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 20;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 20;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 21;
        userIdColumn = 48;
        this.writePosition(userIdRow, userIdColumn, "N");

        userIdRow = 21;
        userIdColumn = 68;
        this.writePosition(userIdRow, userIdColumn, "N");
        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 5;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        userIdRow = 5;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        userIdRow = 7;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 7;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 8;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 8;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "RANDBURG");

        userIdRow = 9;
        userIdColumn = 19;
        this.writePosition(userIdRow, userIdColumn, "2194");

        userIdRow = 9;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "2194");

        userIdRow = 10;
        userIdColumn = 30;
        this.writePosition(userIdRow, userIdColumn, "SO003");

        userIdRow = 10;
        userIdColumn = 73;
        this.writePosition(userIdRow, userIdColumn, "SO003");

        userIdRow = 12;
        userIdColumn = 30;
        this.writePosition(userIdRow, userIdColumn, "SO003");

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 4;
        userIdColumn = 29;
        this.writePosition(userIdRow, userIdColumn, "37");

        userIdRow = 8;
        userIdColumn = 28;
        this.writePosition(userIdRow, userIdColumn, "t");

        userIdRow = 9;
        userIdColumn = 28;
        this.writePosition(userIdRow, userIdColumn, "1196035");

        userIdRow = 10;
        userIdColumn = 28;
        this.writePosition(userIdRow, userIdColumn, "t");
        userIdRow = 11;
        userIdColumn = 28;
        this.writePosition(userIdRow, userIdColumn, "1196035");

        userIdRow = 17;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, ProductType);

        userIdRow = 19;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "1196035");

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 8;
        userIdColumn = 54;
        this.writePosition(userIdRow, userIdColumn, "1");

        userIdRow = 11;
        userIdColumn = 45;
        this.writePosition(userIdRow, userIdColumn, "N");

            /*userIdRow = 12;
             userIdColumn = 29;
            this.writePosition(userIdRow, userIdColumn, TaxDataNo);*/

        userIdRow = 15;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, "N");

            /*userIdRow = 17;
            userIdColumn = 3;
            this.writePosition(userIdRow, userIdColumn, country1);

            userIdRow = 17;
            userIdColumn = 9;
            this.writePosition(userIdRow, userIdColumn, TaxDataNo);*/

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 12;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, "37");

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        userIdRow = 11;
        userIdColumn = 21;
        String account = this.readPosition(userIdRow, userIdColumn, 25).trim();

        userIdRow = 3;
        userIdColumn = 21;
        String clientCode = this.readPosition(userIdRow, userIdColumn, 12).trim();

        userIdRow = 14;
        userIdColumn = 1;
        message = this.readPosition(userIdRow, userIdColumn, 25).trim();
        this.writeKey(Key.ENTER);
        this.waitForChange(10000);

        this.writeKey(Key.CLEAR);
        this.waitForChange(10000);
        message = message + account + "," + clientCode;
        System.out.println("Account with Client Code: " + message);
        return message;
    }

    public String existingCustomerCreation(String accountCode, String code, String clientCode,String accountNo, String codeNo, String productCode, String employeeNo, String accountAmount) throws JagacyException, AWTException {
        System.out.println("Inside method add: " );
        Robot rob = new Robot();
        this.waitForChange(10000);
        userIdRow = 2;
        userIdColumn = 2;
        this.writePosition(userIdRow, userIdColumn, accountCode);
        this.writeKey(Key.ENTER);

        this.waitForChange(1000);
        userIdRow = 4;
        userIdColumn = 72;
        this.writePosition(userIdRow, userIdColumn, code);

        if(accountNo.equals("")){
            userIdRow = 11;
            userIdColumn = 15;
            this.writePosition(userIdRow, userIdColumn, clientCode);
            this.writeKey(Key.ENTER);

            this.waitForChange(1000);
        }else{

            System.out.println("Inside search by account no");
            userIdRow = 7;
            userIdColumn = 27;
            this.writePosition(userIdRow, userIdColumn, accountNo);
            this.writeKey(Key.ENTER);

            this.waitForChange(1000);
        }

        userIdRow = 22;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 25).trim();

        System.out.println("Jst msg: " + message);
        if(message.equalsIgnoreCase("NO CLIENT RECORD FOUND E"))
        {
            return message;
        }else
        {
            this.waitForChange(1000);
            userIdRow = 2;
            userIdColumn = 57;
            this.writePosition(userIdRow, userIdColumn, codeNo);
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            userIdRow = 22;
            userIdColumn = 7;
            message = this.readPosition(userIdRow, userIdColumn, 25).trim();

            System.out.println("error message: " + message);
            userIdRow = 16;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "y");

            userIdRow = 16;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "y");


            userIdRow = 17;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "y");

            userIdRow = 17;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "y");

            userIdRow = 18;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 18;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 19;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 19;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 20;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 20;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 21;
            userIdColumn = 48;
            this.writePosition(userIdRow, userIdColumn, "N");

            userIdRow = 21;
            userIdColumn = 68;
            this.writePosition(userIdRow, userIdColumn, "N");
            rob.keyPress(KeyEvent.VK_TAB);

            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            userIdRow = 22;
            userIdColumn = 8;
            message = this.readPosition(userIdRow, userIdColumn, 25).trim();
            System.out.println("Error: " + message);

            if(message.equalsIgnoreCase("INVALID DIALLING CODE")){
                userIdRow = 11;
                userIdColumn = 29;
                this.writePosition(userIdRow, userIdColumn, "0");

                this.writeKey(Key.ENTER);
                this.waitForChange(1000);
            }

            userIdRow = 18;
            userIdColumn = 17;
            this.writePosition(userIdRow, userIdColumn, productCode);

            userIdRow = 20;
            userIdColumn = 22;
            this.writePosition(userIdRow, userIdColumn, employeeNo);
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            userIdRow = 12;
            userIdColumn = 33;
            this.writePosition(userIdRow, userIdColumn, accountAmount);
            this.writeKey(Key.ENTER);
            this.waitForChange(1000);

            this.writeKey(Key.ENTER);
            this.waitForChange(3000);

            userIdRow = 11;
            userIdColumn = 21;
            message = this.readPosition(userIdRow, userIdColumn, 25);
            System.out.println("account no: " + message);

            this.writeKey(Key.ENTER);
            this.waitForChange(3000);

            this.writeKey(Key.CLEAR);

            return  message;
        }
    }

    public String addFicHold(String userData, String clientID) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        //System.out.println("Client Code: " + clientID);
        userIdRow = 11;
        userIdColumn = 38;
        this.writePosition(userIdRow, userIdColumn, clientID);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 22;
        userIdColumn = 10;
        message = this.readPosition(userIdRow, userIdColumn, 25).trim();

        System.out.println("Messsage: "  +message);
        if(message.equalsIgnoreCase("Data entry too short") || message.equalsIgnoreCase("No client info to display"))
        {
            return message;
        }else
        {
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
    }

    public String addSpecialPowerOfAttorney(String userData, String editClientType,String authInput,String authOption, String authType, String idNumber, String surname, String firstName, String secondNames) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 4;
        userIdColumn = 53;
        this.writePosition(userIdRow, userIdColumn, authOption);

        System.out.println("client details: " + authInput);
        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 7;
            userIdColumn = 52;
            this.writePosition(userIdRow, userIdColumn, authInput);

        }else{
            userIdRow = 7;
            userIdColumn = 17;
            this.writePosition(userIdRow, userIdColumn, authInput);
        }

        userIdRow = 10;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, authType);

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error Message: "  + message);

        if(message.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || message.equalsIgnoreCase("INVALID - ENTER 01,02,03,") || message.equalsIgnoreCase(" PLEASE ENTER ACCOUNT NUMB")|| message.equalsIgnoreCase("INVALID - ENTER N,C,D OR") || message.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED") || message.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")){
            return message;
        }else{
            userIdRow = 0;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 45).trim();
            System.out.println("Error Message 2: "  + message);

            if(message.equalsIgnoreCase("AUTH  CAP   ! Invalid format") || message.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
               return message;
            }else{

                message = authCreateClienSpecialPowerOdAttorney(idNumber, surname, firstName, secondNames);

            }
        }
        return message;
    }

    public String addGeneralPowerOfAttorney(String userData, String editClientType,String authInput,String authOption, String authType, String idNumber, String surname, String firstName, String secondNames) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 4;
        userIdColumn = 53;
        this.writePosition(userIdRow, userIdColumn, authOption);

        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 7;
            userIdColumn = 52;
            this.writePosition(userIdRow, userIdColumn, authInput);

        }else{
            userIdRow = 7;
            userIdColumn = 17;
            this.writePosition(userIdRow, userIdColumn, authInput);
        }

        userIdRow = 10;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, authType);

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error Message: "  + message);

        if(message.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || message.equalsIgnoreCase("INVALID - ENTER 01,02,03,") || message.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED")||message.equalsIgnoreCase("CAMS ACCOUNT NO ACCESS ALLOWED")|| message.equalsIgnoreCase(" PLEASE ENTER ACCOUNT NUMB")|| message.equalsIgnoreCase("INVALID - ENTER N,C,D OR") || message.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")){
            return message;
        }else{
            userIdRow = 0;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 45).trim();
            System.out.println("Error Message 2: "  + message);

            if(message.equalsIgnoreCase("AUTH  CAP   ! Invalid format") || message.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                return message;
            }else{

                message = authCreateClientGeneralPowerOdAttorney(idNumber, surname, firstName, secondNames);

            }
        }
        return message;
    }

    public String youthConsent(String userData, String editClientType,String authInput,String authOption, String authType, String idNumber, String surname, String firstName, String secondNames) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 4;
        userIdColumn = 53;
        this.writePosition(userIdRow, userIdColumn, authOption);

        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 7;
            userIdColumn = 52;
            this.writePosition(userIdRow, userIdColumn, authInput);

        }else{
            userIdRow = 7;
            userIdColumn = 17;
            this.writePosition(userIdRow, userIdColumn, authInput);
        }

        userIdRow = 10;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, authType);

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error Message: "  + message);

        if(message.equalsIgnoreCase("ONLY 1 YOUTH CONSENT PER ACCOUNT ALLOWED") || message.equalsIgnoreCase("CLIENT OLDER THAN 16 YEARS") || message.equalsIgnoreCase("INVALID FOR CLIENT TYPE") ||message.equalsIgnoreCase("INVALID - ENTER 01,02,03,") || message.equalsIgnoreCase(" PLEASE ENTER ACCOUNT NUMB")|| message.equalsIgnoreCase("INVALID - ENTER N,C,D OR") || message.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 1")){
            return message;
        }else{
            userIdRow = 0;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 45).trim();
            System.out.println("Error Message 2: "  + message);

            if(message.equalsIgnoreCase("AUTH  CAP   ! Invalid format") || message.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                return message;
            }else{

                message = authCreateClientYouthContest(idNumber, surname, firstName, secondNames);

            }
        }
        return message;
    }

    public String SigningInstruction(String userData, String editClientType,String authInput,String authOption, String authType, String idNumber, String surname, String firstName, String secondNames) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 4;
        userIdColumn = 53;
        this.writePosition(userIdRow, userIdColumn, authOption);

        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 7;
            userIdColumn = 52;
            this.writePosition(userIdRow, userIdColumn, authInput);

        }else{
            userIdRow = 7;
            userIdColumn = 17;
            this.writePosition(userIdRow, userIdColumn, authInput);
        }

        userIdRow = 10;
        userIdColumn = 37;
        this.writePosition(userIdRow, userIdColumn, authType);

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();
        System.out.println("Error Message: "  + message);

        if(message.equalsIgnoreCase("INVALID FOR CLIENT TYPE") || message.equalsIgnoreCase("INVALID - ENTER 01,02,03,") || message.equalsIgnoreCase(" PLEASE ENTER ACCOUNT NUMB")|| message.equalsIgnoreCase("INVALID - ENTER N,C,D OR") || message.equalsIgnoreCase("ENTER APPLICABLE A/C NUMBER ONLY FOR OPTION 3")){
            return message;
        }else{
            userIdRow = 0;
            userIdColumn = 1;
            message = this.readPosition(userIdRow, userIdColumn, 45).trim();
            System.out.println("Error Message 2: "  + message);

            if(message.equalsIgnoreCase("AUTH  CAP   ! Invalid format") || message.equalsIgnoreCase("AUTH  CAP   < Data entry too short")){
                return message;
            }else{

                message = authCreateClientSignatureInstructions(idNumber, surname, firstName, secondNames);

            }
        }
        return message;
    }

    public String authCreateClientGeneralPowerOdAttorney(String idNumber, String surname,String firstNames, String secondNames) throws JagacyException {
        this.waitForChange(1000);

        System.out.println("Inside auth client");
        userIdRow = 3;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "888");
        this.waitForChange(1000);

        //Title
        userIdRow = 4;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "01");

        //intials

        String intial = null;
        if(secondNames.length() == 0){
            intial = firstNames.substring(0,1);
        }else{
            intial = firstNames.substring(0,1) + "" + secondNames.substring(0,1);
        }
        userIdRow = 4;
        userIdColumn = 32;
        this.writePosition(userIdRow, userIdColumn, intial);

        //Date of Birth
        String date = idNumber.substring(0,6);
        String year = date.substring(0,2);
        String month = date.substring(2,4);
        String day = date.substring(4,6);
        String newYear = "";
        String valid = year.substring(0,1);

        System.out.println(idNumber);
        if(valid.equalsIgnoreCase("0"))
        {
            newYear = "20" + year;
        }else
        {
            newYear = "19" + year;
        }

        String dateOfBirth = day+month+newYear;
        userIdRow = 4;
        userIdColumn = 63;
        this.writePosition(userIdRow, userIdColumn, dateOfBirth);

        //Surname
        userIdRow = 5;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, surname);

        //Gender
        userIdRow = 5;
        userIdColumn = 57;
        this.writePosition(userIdRow, userIdColumn, "m");

        //First Name
        String names = null;
        if(secondNames.length() == 0){
            names = firstNames;
        }else{
            names = firstNames + "  " + secondNames;
        }
        userIdRow = 6;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn,names);

        //Language
        userIdRow = 6;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "E");

        //id Type
        userIdRow = 7;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "01");

        //id number
        userIdRow = 7;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, idNumber);

        //Date issued
        userIdRow = 7;
        userIdColumn = 62;
        this.writePosition(userIdRow, userIdColumn, "t");

        //County of Birth
        userIdRow = 8;
        userIdColumn = 67;
        this.writePosition(userIdRow, userIdColumn, "so003");


        //Natinality
        userIdRow = 9;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "sou01");

        //occupitaion status
        userIdRow = 10;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "01");

        //Occipation Code
        userIdRow = 11;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "01");
        //Address
        userIdRow = 12;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "01");


        //Address
        userIdRow = 13;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        //Town
        userIdRow = 15;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "Randburg");

        //City
        userIdRow = 16;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "Randburg");
        //Postal
        userIdRow = 17;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "2194");
        //Country staying
        userIdRow = 17;
        userIdColumn = 71;
        this.writePosition(userIdRow, userIdColumn, "so003");
        //email
        userIdRow = 18;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "Test.data.management@absa.africa");

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);
        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error 2: " + message);

        userIdRow = 12;
        userIdColumn = 1;
        message = this.readPosition(userIdRow, userIdColumn, 80).trim();
        System.out.println("Error 3: " + message);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);
        return message;
    }

    public String authCreateClienSpecialPowerOdAttorney(String idNumber, String surname,String firstNames, String secondNames) throws JagacyException {
        this.waitForChange(1000);

        System.out.println("Inside auth client");
        userIdRow = 3;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "888");
        this.waitForChange(1000);

        //Title
        userIdRow = 4;
        userIdColumn = 16;
        this.writePosition(userIdRow, userIdColumn, "01");

        //intials

        String intial = null;
        if(secondNames.length() == 0){
            intial = firstNames.substring(0,1);
        }else{
            intial = firstNames.substring(0,1) + "" + secondNames.substring(0,1);
        }
        userIdRow = 4;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, intial);

        //Date of Birth
        String date = idNumber.substring(0,6);
        String year = date.substring(0,2);
        String month = date.substring(2,4);
        String day = date.substring(4,6);
        String newYear = "";
        String valid = year.substring(0,1);

        System.out.println(idNumber);
        if(valid.equalsIgnoreCase("0"))
        {
            newYear = "20" + year;
        }else
        {
            newYear = "19" + year;
        }

        String dateOfBirth = day+month+newYear;
        userIdRow = 4;
        userIdColumn = 63;
        this.writePosition(userIdRow, userIdColumn, dateOfBirth);

        //Surname
        userIdRow = 5;
        userIdColumn = 16;
        this.writePosition(userIdRow, userIdColumn, surname);

        //Gender
        userIdRow = 5;
        userIdColumn = 57;
        this.writePosition(userIdRow, userIdColumn, "m");

        //First Name
        String names = null;
        if(secondNames.length() == 0){
            names = firstNames;
        }else{
            names = firstNames + "  " + secondNames;
        }
        userIdRow = 6;
        userIdColumn = 16;
        this.writePosition(userIdRow, userIdColumn,names);

        //Language
        userIdRow = 6;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "E");

        //id Type
        userIdRow = 7;
        userIdColumn = 16;
        this.writePosition(userIdRow, userIdColumn, "01");

        //id number
        userIdRow = 7;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, idNumber);

        //Date issued
        userIdRow = 7;
        userIdColumn = 62;
        this.writePosition(userIdRow, userIdColumn, "t");

        //County of Birth
        userIdRow = 8;
        userIdColumn = 67;
        this.writePosition(userIdRow, userIdColumn, "so003");


        //Natinality
        userIdRow = 9;
        userIdColumn = 16;
        this.writePosition(userIdRow, userIdColumn, "sou01");

        //occupitaion status
        userIdRow = 10;
        userIdColumn = 22;
        this.writePosition(userIdRow, userIdColumn, "01");

        //Occipation Code
        userIdRow = 11;
        userIdColumn = 22;
        this.writePosition(userIdRow, userIdColumn, "01");
        //Address
        userIdRow = 12;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "01");


        //Address
        userIdRow = 13;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        //Town
        userIdRow = 15;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "Randburg");

        //City
        userIdRow = 16;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "Randburg");
        //Postal
        userIdRow = 17;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "2194");
        //Country staying
        userIdRow = 17;
        userIdColumn = 71;
        this.writePosition(userIdRow, userIdColumn, "so003");
        //email
        userIdRow = 18;
        userIdColumn = 18;
        this.writePosition(userIdRow, userIdColumn, "Test.data.management@absa.africa");

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);
        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error 2: " + message);

        userIdRow = 12;
        userIdColumn = 1;
        message = this.readPosition(userIdRow, userIdColumn, 80).trim();
        System.out.println("Error 3: " + message);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);
        return message;
    }

    public String authCreateClientSignatureInstructions(String idNumber, String surname,String firstNames, String secondNames) throws JagacyException {
        this.waitForChange(1000);

        System.out.println("Inside auth client");
        userIdRow = 2;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "01");

        userIdRow = 3;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "888");

        //Title
        userIdRow = 4;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, "01");

        //intials

        String intial = null;
        if(secondNames.length() == 0){
            intial = firstNames.substring(0,1);
        }else{
            intial = firstNames.substring(0,1) + "" + secondNames.substring(0,1);
        }
        userIdRow = 4;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, intial);

        //Date of Birth
        String date = idNumber.substring(0,6);
        String year = date.substring(0,2);
        String month = date.substring(2,4);
        String day = date.substring(4,6);
        String newYear = "";
        String valid = year.substring(0,1);

        System.out.println(idNumber);
        if(valid.equalsIgnoreCase("0"))
        {
            newYear = "20" + year;
        }else
        {
            newYear = "19" + year;
        }

        String dateOfBirth = day+month+newYear;
        userIdRow = 4;
        userIdColumn = 54;
        this.writePosition(userIdRow, userIdColumn, dateOfBirth);

        //Surname
        userIdRow = 5;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, surname);

        //Gender
        userIdRow = 5;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "m");

        //First Name
        String names = null;
        if(secondNames.length() == 0){
            names = firstNames;
        }else{
            names = firstNames + "  " + secondNames;
        }
        userIdRow = 6;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn,names);

        //Language
        userIdRow = 6;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "E");

        //id Type
        userIdRow = 7;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, "01");

        //id number
        userIdRow = 7;
        userIdColumn = 25;
        this.writePosition(userIdRow, userIdColumn, idNumber);

        //Date issued
        userIdRow = 7;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "t");

        //County of Birth
        userIdRow = 8;
        userIdColumn = 58;
        this.writePosition(userIdRow, userIdColumn, "so003");


        //Natinality
        userIdRow = 8;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "sou01");

        //occupitaion status
        userIdRow = 11;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "01");

        //Occipation Code
        userIdRow = 12;
        userIdColumn = 23;
        this.writePosition(userIdRow, userIdColumn, "01");
        //Address
        userIdRow = 10;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "01");


        //Address
        userIdRow = 14;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        //Town
        userIdRow = 16;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "Randburg");

        //City
        userIdRow = 17;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "Randburg");
        //Postal
        userIdRow = 18;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "2194");
        //Country staying
        userIdRow = 18;
        userIdColumn = 62;
        this.writePosition(userIdRow, userIdColumn, "so003");
        //email
        userIdRow = 19;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "Test.data.management@absa.africa");

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);
        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error 2: " + message);

        userIdRow = 10;
        userIdColumn = 1;
        message = this.readPosition(userIdRow, userIdColumn, 80).trim();
        System.out.println("Error 3: " + message);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);
        return message;
    }

    public String authCreateClientYouthContest(String idNumber, String surname,String firstNames, String secondNames) throws JagacyException {
        this.waitForChange(1000);

        System.out.println("Inside auth client");
        userIdRow = 2;
        userIdColumn = 59;
        this.writePosition(userIdRow, userIdColumn, "01");

        userIdRow = 3;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "888");

        //Title
        userIdRow = 4;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, "01");

        //intials

        String intial = null;
        if(secondNames.length() == 0){
            intial = firstNames.substring(0,1);
        }else{
            intial = firstNames.substring(0,1) + "" + secondNames.substring(0,1);
        }
        userIdRow = 4;
        userIdColumn = 33;
        this.writePosition(userIdRow, userIdColumn, intial);

        //Date of Birth
        String date = idNumber.substring(0,6);
        String year = date.substring(0,2);
        String month = date.substring(2,4);
        String day = date.substring(4,6);
        String newYear = "";
        String valid = year.substring(0,1);

        System.out.println(idNumber);
        if(valid.equalsIgnoreCase("0"))
        {
            newYear = "20" + year;
        }else
        {
            newYear = "19" + year;
        }

        String dateOfBirth = day+month+newYear;
        userIdRow = 4;
        userIdColumn = 56;
        this.writePosition(userIdRow, userIdColumn, dateOfBirth);

        //Surname
        userIdRow = 5;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, surname);

        //Gender
        userIdRow = 5;
        userIdColumn = 50;
        this.writePosition(userIdRow, userIdColumn, "m");

        //First Name
        String names = null;
        if(secondNames.length() == 0){
            names = firstNames;
        }else{
            names = firstNames + "  " + secondNames;
        }
        userIdRow = 6;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn,names);

        //Language
        userIdRow = 6;
        userIdColumn = 52;
        this.writePosition(userIdRow, userIdColumn, "E");

        //id Type
        userIdRow = 7;
        userIdColumn = 11;
        this.writePosition(userIdRow, userIdColumn, "01");

        //id number
        userIdRow = 7;
        userIdColumn = 26;
        this.writePosition(userIdRow, userIdColumn, idNumber);

        //Date issued
        userIdRow = 7;
        userIdColumn = 54;
        this.writePosition(userIdRow, userIdColumn, "t");

        //County of Birth
        userIdRow = 8;
        userIdColumn = 60;
        this.writePosition(userIdRow, userIdColumn, "so003");


        //Natinality
        userIdRow = 8;
        userIdColumn = 15;
        this.writePosition(userIdRow, userIdColumn, "sou01");

        //occupitaion status
        userIdRow = 10;
        userIdColumn = 22;
        this.writePosition(userIdRow, userIdColumn, "01");

        //Occipation Code
        userIdRow = 11;
        userIdColumn = 22   ;
        this.writePosition(userIdRow, userIdColumn, "01");
        //Address
        userIdRow = 9;
        userIdColumn = 55;
        this.writePosition(userIdRow, userIdColumn, "01");


        //Address
        userIdRow = 12  ;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "270 REP ROAD");

        //Town
        userIdRow = 14;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "Randburg");

        //City
        userIdRow = 15;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "Randburg");
        //Postal
        userIdRow = 16;
        userIdColumn = 31;
        this.writePosition(userIdRow, userIdColumn, "2194");
        //Country staying
        userIdRow = 16;
        userIdColumn = 64;
        this.writePosition(userIdRow, userIdColumn, "so003");
        //email
        userIdRow = 17;
        userIdColumn = 17;
        this.writePosition(userIdRow, userIdColumn, "Test.data.management@absa.africa");

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);
        userIdRow = 21;
        userIdColumn = 7;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();

        System.out.println("Error 2: " + message);

        userIdRow = 11;
        userIdColumn = 1;
        message = this.readPosition(userIdRow, userIdColumn, 80).trim();
        System.out.println("Error 3: " + message);
        this.writeKey(Key.ENTER);
        this.waitForChange(3000);
        return message;
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

    public String removeCIFHold(String userData, String editClientType,String clientID) throws JagacyException, AWTException, InterruptedException {
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

        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 3;
            userIdColumn = 23;
            this.writePosition(userIdRow, userIdColumn, clientID);

        }else{
            userIdRow = 2;
            userIdColumn = 23;
            this.writePosition(userIdRow, userIdColumn, clientID);
        }
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 0;
        userIdColumn = 13;
        message = this.readPosition(userIdRow, userIdColumn, 30).trim();

        userIdRow = 4;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove IdentificationRequired
        if(message.equalsIgnoreCase("y")){
            userIdRow = 4;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");

        }

        userIdRow = 5;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove InsolventEstate
        if(message.equalsIgnoreCase("y"))
        {
            userIdRow = 5;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 6;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove DeceasedEstate
        if(message.equalsIgnoreCase("y")){
            userIdRow = 6;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 7;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove SpouseDeceased
        if(message.equalsIgnoreCase("y")){
            userIdRow = 7;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 8;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove Curatorship
        if(message.equalsIgnoreCase("y")){
            userIdRow = 8;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 11;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove ClientAgreementIssued
        if(message.equalsIgnoreCase("y")){
            userIdRow = 11;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 13;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove NewPostalAddressRequired
        if(message.equalsIgnoreCase("Y")){
            userIdRow = 13;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 14;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove NewResidentialAddressRequired
        if(message.equalsIgnoreCase("Y")){
            userIdRow = 14;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        userIdRow = 15;
        userIdColumn = 50;
        message = this.readPosition(userIdRow, userIdColumn, 2).trim();
        //Remove NewEmployersNameAddressRequired
        if(message.equalsIgnoreCase("Y")){
            userIdRow = 15;
            userIdColumn = 50;
            this.writePosition(userIdRow, userIdColumn, "N");
        }

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        return "CIF HOLDS REMOVED";
    }

    public String removeFicHold(String userData, String editClientType,String clientID) throws JagacyException, AWTException, InterruptedException {
        Robot robot = new Robot();

        this.waitForChange(1000);
        userIdRow = 2;
        userIdColumn = 1;
        this.writePosition(userIdRow, userIdColumn, userData);
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        if(editClientType.equalsIgnoreCase("clientCode")){
            userIdRow = 11;
            userIdColumn = 38;
            this.writePosition(userIdRow, userIdColumn, clientID);

        }else{
            return "Use only client code to remove fic hold...";
        }
        this.writeKey(Key.ENTER);
        this.waitForChange(30000);

        userIdRow = 0;
        userIdColumn = 13;
        message = this.readPosition(userIdRow, userIdColumn, 45).trim();
        System.out.println("error messgae:  " + message);

        if(message.equalsIgnoreCase("< Data entry too short")){
            return message;
        }

        userIdRow = 22;
        userIdColumn = 10;
        message = this.readPosition(userIdRow, userIdColumn, 30).trim();
        System.out.println("error messgae: 1 " + message);

        if(message.equalsIgnoreCase("CLIENT IS NOT IN FIC LOCK")){
            return message;
        }else{
            userIdRow = 19;
            userIdColumn = 40;
            this.writePosition(userIdRow, userIdColumn, "y");

            this.writeKey(Key.ENTER);
            this.waitForChange(30000);
        }

        userIdRow = 21;
        userIdColumn = 10;
        message = this.readPosition(userIdRow, userIdColumn, 30).trim();

        return message;
    }

}
