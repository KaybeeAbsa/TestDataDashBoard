package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.Methods.SendCapturesEmail;
import co.za.absa.TestDataDashBoard.Methods.SendSignOffEmail;
import co.za.absa.TestDataDashBoard.Methods.SmtpMailSender;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.services.SitSignOffService;
import co.za.absa.TestDataDashBoard.services.UserService;
import com.jagacy.util.JagacyException;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.awt.*;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping(value = "/SitSignOffForm")
public class SitSignOffController {

    @Autowired
    SitSignOffService service;
    @Autowired
    SmtpMailSender smtpMailSender;

    private ArrayList<SITSignOff> sitSignOffs = new ArrayList<>();
    private ArrayList<Integer> formTokes = new ArrayList<>();

    @RequestMapping(value = "/SitSignOff/{reChangeName}/{reChangeNumber}/{reProjectManager}/{reTower}/{reImplDate}/{reSystemCompleted}/{reEmail}/{choices}/{documentPdf}", method = RequestMethod.GET)

    @ResponseBody
    public ArrayList<Integer> SitSignOff(@PathVariable String reChangeName, @PathVariable String reChangeNumber, @PathVariable String reProjectManager, @PathVariable String reTower, @PathVariable String reImplDate, @PathVariable String reSystemCompleted, @PathVariable String reEmail, @PathVariable String[] choices, @PathVariable String documentPdf)  {

       // System.out.println("Document: " + reDocument);
        formTokes.clear();
        String contactemail = "";
        String interfacearea = "";
        String sitsignoffperson = "";

        String subject = "SIT Sign Off";
        int splitSize = 0;

        String implDate=reImplDate.substring(4, reImplDate.length()-47);

        String comments = "System Tested";
        String reStatus = "Pending";

        SendSignOffEmail sendSignOffEmail = new SendSignOffEmail();
        SendCapturesEmail sendCapturesEmail=new SendCapturesEmail();

        int choicesSize = choices.length;

        if (choicesSize == 4) {

            for (int i = 0; i < choices.length; i++) {

                if (choices[i].contains("interfacearea")) {
                    interfacearea = choices[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (choices[i].contains("sitsignoffperson")) {
                    sitsignoffperson = choices[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (choices[i].contains("contactemail")) {
                    contactemail = choices[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            sitSignOff.setDocumentpdf(documentPdf);
            service.insertIntoSitSignOffColumn(sitSignOff);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

        } else if (choicesSize == 8) {

            splitSize = choices.length / 2;

            String[] splitArrayPart1 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart2 = Arrays.copyOfRange(choices, splitSize, choices.length);

            for (int i = 0; i < splitArrayPart1.length; i++) {

                if (splitArrayPart1[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart1[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart1[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart1[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart1[i].contains("contactemail")) {
                    contactemail = splitArrayPart1[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            sitSignOff.setDocumentpdf(documentPdf);
            service.insertIntoSitSignOffColumn(sitSignOff);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart2.length; i++) {

                if (splitArrayPart2[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart2[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    //System.out.println("interface  " + interfacearea);
                } else if (splitArrayPart2[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart2[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    //System.out.println("sitsignoffperson  " + sitsignoffperson);
                } else if (splitArrayPart2[i].contains("contactemail")) {
                    contactemail = splitArrayPart2[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");

                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(contactemail, sitSignOff1.getSitsignoffid());

        } else if (choicesSize == 12) {

            splitSize = choices.length / 3;
            String[] splitArrayPart3 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart4 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart5 = Arrays.copyOfRange(choices, 9, choices.length);

            for (int i = 0; i < splitArrayPart3.length; i++) {

                if (splitArrayPart3[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart3[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart3[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart3[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart3[i].contains("contactemail")) {
                    contactemail = splitArrayPart3[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }


            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setDocumentpdf(documentPdf);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart4.length; i++) {

                if (splitArrayPart4[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart4[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart4[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart4[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart4[i].contains("contactemail")) {
                    contactemail = splitArrayPart4[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);
           // formTokes.add(sitSignOff1.getSitsignoffid());
            sitSignOff1.getSitsignoffid();
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff1.getSitsignoffid());

            for (int i = 0; i < splitArrayPart5.length; i++) {

                if (splitArrayPart5[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart5[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart5[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart5[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart5[i].contains("contactemail")) {
                    contactemail = splitArrayPart5[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }


            SITSignOff sitSignOff2 = new SITSignOff();
            sitSignOff2.setChangename(reChangeName.toUpperCase());
            sitSignOff2.setChangenumber(reChangeNumber);
            sitSignOff2.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff2.setTower(reTower.toUpperCase());
            sitSignOff2.setTestingcompleted(reSystemCompleted);
            sitSignOff2.setInterfacearea(interfacearea);
            sitSignOff2.setEmail(reEmail.toUpperCase());
            sitSignOff2.setComments(comments);
            sitSignOff2.setImplementationdate(implDate);
            sitSignOff2.setStatus(reStatus);
            sitSignOff2.setDocumentpdf(documentPdf);
            sitSignOff2.setContactemail(contactemail.toUpperCase());
            sitSignOff2.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff2);

            sitSignOff2.getSitsignoffid();
            formTokes.add(sitSignOff2.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff2.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff2.getSitsignoffid());

        } else if (choicesSize == 16) {


            splitSize = choices.length / 4;
            String[] splitArrayPart6 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart7 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart8 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart9 = Arrays.copyOfRange(choices, 13, choices.length);

            for (int i = 0; i < splitArrayPart6.length; i++) {

                if (splitArrayPart6[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart6[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart6[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart6[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart6[i].contains("contactemail")) {
                    contactemail = splitArrayPart6[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setDocumentpdf(documentPdf);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart7.length; i++) {

                if (splitArrayPart7[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart7[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart7[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart7[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart7[i].contains("contactemail")) {
                    contactemail = splitArrayPart7[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff1.getSitsignoffid());

            for (int i = 0; i < splitArrayPart8.length; i++) {

                if (splitArrayPart8[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart8[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart8[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart8[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart8[i].contains("contactemail")) {
                    contactemail = splitArrayPart8[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff2 = new SITSignOff();
            sitSignOff2.setChangename(reChangeName.toUpperCase());
            sitSignOff2.setChangenumber(reChangeNumber);
            sitSignOff2.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff2.setTower(reTower.toUpperCase());
            sitSignOff2.setTestingcompleted(reSystemCompleted);
            sitSignOff2.setInterfacearea(interfacearea);
            sitSignOff2.setEmail(reEmail.toUpperCase());
            sitSignOff2.setComments(comments);
            sitSignOff2.setImplementationdate(implDate);
            sitSignOff2.setStatus(reStatus);
            sitSignOff2.setDocumentpdf(documentPdf);
            sitSignOff2.setContactemail(contactemail.toUpperCase());
            sitSignOff2.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff2);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff2.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff2.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff2.getSitsignoffid());

            for (int i = 0; i < splitArrayPart9.length; i++) {

                if (splitArrayPart9[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart9[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart9[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart9[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart9[i].contains("contactemail")) {
                    contactemail = splitArrayPart9[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff3 = new SITSignOff();
            sitSignOff3.setChangename(reChangeName.toUpperCase());
            sitSignOff3.setChangenumber(reChangeNumber);
            sitSignOff3.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff3.setTower(reTower.toUpperCase());
            sitSignOff3.setTestingcompleted(reSystemCompleted);
            sitSignOff3.setInterfacearea(interfacearea);
            sitSignOff3.setEmail(reEmail.toUpperCase());
            sitSignOff3.setComments(comments);
            sitSignOff3.setImplementationdate(implDate);
            sitSignOff3.setStatus(reStatus);
            sitSignOff3.setDocumentpdf(documentPdf);
            sitSignOff3.setContactemail(contactemail.toUpperCase());
            sitSignOff3.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff3);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff3.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff3.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff3.getSitsignoffid());

        } else if (choicesSize == 20) {


            splitSize = choices.length / 5;
            String[] splitArrayPart10 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart11 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart12 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart13 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart14 = Arrays.copyOfRange(choices, 17, choices.length);

            for (int i = 0; i < splitArrayPart10.length; i++) {

                if (splitArrayPart10[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart10[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart10[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart10[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart10[i].contains("contactemail")) {
                    contactemail = splitArrayPart10[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setDocumentpdf(documentPdf);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart11.length; i++) {

                if (splitArrayPart11[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart11[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart11[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart11[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart11[i].contains("contactemail")) {
                    contactemail = splitArrayPart11[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff1.getSitsignoffid());

            for (int i = 0; i < splitArrayPart12.length; i++) {

                if (splitArrayPart12[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart12[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart12[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart12[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart12[i].contains("contactemail")) {
                    contactemail = splitArrayPart12[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff2 = new SITSignOff();
            sitSignOff2.setChangename(reChangeName.toUpperCase());
            sitSignOff2.setChangenumber(reChangeNumber);
            sitSignOff2.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff2.setTower(reTower.toUpperCase());
            sitSignOff2.setTestingcompleted(reSystemCompleted);
            sitSignOff2.setInterfacearea(interfacearea);
            sitSignOff2.setEmail(reEmail.toUpperCase());
            sitSignOff2.setComments(comments);
            sitSignOff2.setImplementationdate(implDate);
            sitSignOff2.setStatus(reStatus);
            sitSignOff2.setDocumentpdf(documentPdf);
            sitSignOff2.setContactemail(contactemail.toUpperCase());
            sitSignOff2.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff2);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff2.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff2.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff2.getSitsignoffid());

            for (int i = 0; i < splitArrayPart13.length; i++) {

                if (splitArrayPart13[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart13[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart13[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart13[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart13[i].contains("contactemail")) {
                    contactemail = splitArrayPart13[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff3 = new SITSignOff();
            sitSignOff3.setChangename(reChangeName.toUpperCase());
            sitSignOff3.setChangenumber(reChangeNumber);
            sitSignOff3.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff3.setTower(reTower.toUpperCase());
            sitSignOff3.setTestingcompleted(reSystemCompleted);
            sitSignOff3.setInterfacearea(interfacearea);
            sitSignOff3.setEmail(reEmail.toUpperCase());
            sitSignOff3.setComments(comments);
            sitSignOff3.setImplementationdate(implDate);
            sitSignOff3.setStatus(reStatus);
            sitSignOff3.setDocumentpdf(documentPdf);
            sitSignOff3.setContactemail(contactemail.toUpperCase());
            sitSignOff3.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff3);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff3.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff3.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff3.getSitsignoffid());

            for (int i = 0; i < splitArrayPart14.length; i++) {

                if (splitArrayPart14[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart14[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart14[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart14[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart14[i].contains("contactemail")) {
                    contactemail = splitArrayPart14[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff4 = new SITSignOff();
            sitSignOff4.setChangename(reChangeName.toUpperCase());
            sitSignOff4.setChangenumber(reChangeNumber);
            sitSignOff4.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff4.setTower(reTower.toUpperCase());
            sitSignOff4.setTestingcompleted(reSystemCompleted);
            sitSignOff4.setInterfacearea(interfacearea);
            sitSignOff4.setEmail(reEmail.toUpperCase());
            sitSignOff4.setComments(comments);
            sitSignOff4.setImplementationdate(implDate);
            sitSignOff4.setStatus(reStatus);
            sitSignOff4.setDocumentpdf(documentPdf);
            sitSignOff4.setContactemail(contactemail.toUpperCase());
            sitSignOff4.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff4);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff4.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff4.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff4.getSitsignoffid());


        } else if (choicesSize == 24) {

            splitSize = choices.length / 6;
            String[] splitArrayPart15 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart16 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart17 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart18 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart19 = Arrays.copyOfRange(choices, 17, 20);
            String[] splitArrayPart20 = Arrays.copyOfRange(choices, 21, choices.length);

            for (int i = 0; i < splitArrayPart15.length; i++) {

                if (splitArrayPart15[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart15[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart15[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart15[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart15[i].contains("contactemail")) {
                    contactemail = splitArrayPart15[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setDocumentpdf(documentPdf);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart16.length; i++) {

                if (splitArrayPart16[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart16[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart16[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart16[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart16[i].contains("contactemail")) {
                    contactemail = splitArrayPart16[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff1.getSitsignoffid());

            for (int i = 0; i < splitArrayPart17.length; i++) {

                if (splitArrayPart17[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart17[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart17[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart17[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart17[i].contains("contactemail")) {
                    contactemail = splitArrayPart17[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff2 = new SITSignOff();
            sitSignOff2.setChangename(reChangeName.toUpperCase());
            sitSignOff2.setChangenumber(reChangeNumber);
            sitSignOff2.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff2.setTower(reTower.toUpperCase());
            sitSignOff2.setTestingcompleted(reSystemCompleted);
            sitSignOff2.setInterfacearea(interfacearea);
            sitSignOff2.setEmail(reEmail.toUpperCase());
            sitSignOff2.setComments(comments);
            sitSignOff2.setImplementationdate(implDate);
            sitSignOff2.setStatus(reStatus);
            sitSignOff2.setDocumentpdf(documentPdf);
            sitSignOff2.setContactemail(contactemail.toUpperCase());
            sitSignOff2.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff2);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff2.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff2.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff2.getSitsignoffid());

            for (int i = 0; i < splitArrayPart18.length; i++) {

                if (splitArrayPart18[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart18[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart18[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart18[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart18[i].contains("contactemail")) {
                    contactemail = splitArrayPart18[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff3 = new SITSignOff();
            sitSignOff3.setChangename(reChangeName.toUpperCase());
            sitSignOff3.setChangenumber(reChangeNumber);
            sitSignOff3.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff3.setTower(reTower.toUpperCase());
            sitSignOff3.setTestingcompleted(reSystemCompleted);
            sitSignOff3.setInterfacearea(interfacearea);
            sitSignOff3.setEmail(reEmail.toUpperCase());
            sitSignOff3.setComments(comments);
            sitSignOff3.setImplementationdate(implDate);
            sitSignOff3.setStatus(reStatus);
            sitSignOff3.setDocumentpdf(documentPdf);
            sitSignOff3.setContactemail(contactemail.toUpperCase());
            sitSignOff3.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff3);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff3.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff3.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff3.getSitsignoffid());

            for (int i = 0; i < splitArrayPart19.length; i++) {

                if (splitArrayPart19[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart19[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart19[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart19[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart19[i].contains("contactemail")) {
                    contactemail = splitArrayPart19[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff4 = new SITSignOff();
            sitSignOff4.setChangename(reChangeName.toUpperCase());
            sitSignOff4.setChangenumber(reChangeNumber);
            sitSignOff4.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff4.setTower(reTower.toUpperCase());
            sitSignOff4.setTestingcompleted(reSystemCompleted);
            sitSignOff4.setInterfacearea(interfacearea);
            sitSignOff4.setEmail(reEmail.toUpperCase());
            sitSignOff4.setComments(comments);
            sitSignOff4.setImplementationdate(implDate);
            sitSignOff4.setStatus(reStatus);
            sitSignOff4.setDocumentpdf(documentPdf);
            sitSignOff4.setContactemail(contactemail.toUpperCase());
            sitSignOff4.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff4);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff4.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff4.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff4.getSitsignoffid());

            for (int i = 0; i < splitArrayPart20.length; i++) {

                if (splitArrayPart20[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart20[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart20[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart20[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart20[i].contains("contactemail")) {
                    contactemail = splitArrayPart20[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff5 = new SITSignOff();
            sitSignOff5.setChangename(reChangeName.toUpperCase());
            sitSignOff5.setChangenumber(reChangeNumber);
            sitSignOff5.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff5.setTower(reTower.toUpperCase());
            sitSignOff5.setTestingcompleted(reSystemCompleted);
            sitSignOff5.setInterfacearea(interfacearea);
            sitSignOff5.setEmail(reEmail.toUpperCase());
            sitSignOff5.setComments(comments);
            sitSignOff5.setImplementationdate(implDate);
            sitSignOff5.setStatus(reStatus);
            sitSignOff5.setDocumentpdf(documentPdf);
            sitSignOff5.setContactemail(contactemail.toUpperCase());
            sitSignOff5.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff5);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff5.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff5.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff5.getSitsignoffid());

        }
        else if (choicesSize == 28) {

            splitSize = choices.length / 7;
            String[] splitArrayPart21 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart22 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart23 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart24 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart25 = Arrays.copyOfRange(choices, 17, 20);
            String[] splitArrayPart26 = Arrays.copyOfRange(choices, 21, 24);
            String[] splitArrayPart27= Arrays.copyOfRange(choices, 25, choices.length);

            for (int i = 0; i < splitArrayPart21.length; i++) {

                if (splitArrayPart21[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart21[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart21[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart21[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart21[i].contains("contactemail")) {
                    contactemail = splitArrayPart21[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff = new SITSignOff();
            sitSignOff.setChangename(reChangeName.toUpperCase());
            sitSignOff.setChangenumber(reChangeNumber);
            sitSignOff.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff.setTower(reTower.toUpperCase());
            sitSignOff.setTestingcompleted(reSystemCompleted);
            sitSignOff.setInterfacearea(interfacearea);
            sitSignOff.setEmail(reEmail.toUpperCase());
            sitSignOff.setComments(comments);
            sitSignOff.setImplementationdate(implDate);
            sitSignOff.setStatus(reStatus);
            sitSignOff.setDocumentpdf(documentPdf);
            sitSignOff.setContactemail(contactemail.toUpperCase());
            sitSignOff.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff.getSitsignoffid());

            for (int i = 0; i < splitArrayPart22.length; i++) {

                if (splitArrayPart22[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart22[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart22[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart22[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart22[i].contains("contactemail")) {
                    contactemail = splitArrayPart22[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff1 = new SITSignOff();
            sitSignOff1.setChangename(reChangeName.toUpperCase());
            sitSignOff1.setChangenumber(reChangeNumber);
            sitSignOff1.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff1.setTower(reTower.toUpperCase());
            sitSignOff1.setTestingcompleted(reSystemCompleted);
            sitSignOff1.setInterfacearea(interfacearea);
            sitSignOff1.setEmail(reEmail.toUpperCase());
            sitSignOff1.setComments(comments);
            sitSignOff1.setImplementationdate(implDate);
            sitSignOff1.setStatus(reStatus);
            sitSignOff1.setDocumentpdf(documentPdf);
            sitSignOff1.setContactemail(contactemail.toUpperCase());
            sitSignOff1.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff1);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff1.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff1.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff1.getSitsignoffid());

            for (int i = 0; i < splitArrayPart23.length; i++) {

                if (splitArrayPart23[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart23[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart23[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart23[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart23[i].contains("contactemail")) {
                    contactemail = splitArrayPart23[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff2 = new SITSignOff();
            sitSignOff2.setChangename(reChangeName.toUpperCase());
            sitSignOff2.setChangenumber(reChangeNumber);
            sitSignOff2.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff2.setTower(reTower.toUpperCase());
            sitSignOff2.setTestingcompleted(reSystemCompleted);
            sitSignOff2.setInterfacearea(interfacearea);
            sitSignOff2.setEmail(reEmail.toUpperCase());
            sitSignOff2.setComments(comments);
            sitSignOff2.setImplementationdate(implDate);
            sitSignOff2.setStatus(reStatus);
            sitSignOff2.setDocumentpdf(documentPdf);
            sitSignOff2.setContactemail(contactemail.toUpperCase());
            sitSignOff2.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff2);


            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff2.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff2.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff2.getSitsignoffid());

            for (int i = 0; i < splitArrayPart24.length; i++) {

                if (splitArrayPart24[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart24[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart24[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart24[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart24[i].contains("contactemail")) {
                    contactemail = splitArrayPart24[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff3 = new SITSignOff();
            sitSignOff3.setChangename(reChangeName.toUpperCase());
            sitSignOff3.setChangenumber(reChangeNumber);
            sitSignOff3.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff3.setTower(reTower.toUpperCase());
            sitSignOff3.setTestingcompleted(reSystemCompleted);
            sitSignOff3.setInterfacearea(interfacearea);
            sitSignOff3.setEmail(reEmail.toUpperCase());
            sitSignOff3.setComments(comments);
            sitSignOff3.setImplementationdate(implDate);
            sitSignOff3.setStatus(reStatus);
            sitSignOff3.setDocumentpdf(documentPdf);
            sitSignOff3.setContactemail(contactemail.toUpperCase());
            sitSignOff3.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff3);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff3.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff3.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff3.getSitsignoffid());

            for (int i = 0; i < splitArrayPart25.length; i++) {

                if (splitArrayPart25[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart25[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart25[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart25[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart25[i].contains("contactemail")) {
                    contactemail = splitArrayPart25[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff4 = new SITSignOff();
            sitSignOff4.setChangename(reChangeName.toUpperCase());
            sitSignOff4.setChangenumber(reChangeNumber);
            sitSignOff4.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff4.setTower(reTower.toUpperCase());
            sitSignOff4.setTestingcompleted(reSystemCompleted);
            sitSignOff4.setInterfacearea(interfacearea);
            sitSignOff4.setEmail(reEmail.toUpperCase());
            sitSignOff4.setComments(comments);
            sitSignOff4.setImplementationdate(implDate);
            sitSignOff4.setStatus(reStatus);
            sitSignOff4.setDocumentpdf(documentPdf);
            sitSignOff4.setContactemail(contactemail.toUpperCase());
            sitSignOff4.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff4);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff4.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff4.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff4.getSitsignoffid());

            for (int i = 0; i < splitArrayPart26.length; i++) {

                if (splitArrayPart26[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart26[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart26[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart26[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart26[i].contains("contactemail")) {
                    contactemail = splitArrayPart26[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff5 = new SITSignOff();
            sitSignOff5.setChangename(reChangeName.toUpperCase());
            sitSignOff5.setChangenumber(reChangeNumber);
            sitSignOff5.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff5.setTower(reTower.toUpperCase());
            sitSignOff5.setTestingcompleted(reSystemCompleted);
            sitSignOff5.setInterfacearea(interfacearea);
            sitSignOff5.setEmail(reEmail.toUpperCase());
            sitSignOff5.setComments(comments);
            sitSignOff5.setImplementationdate(implDate);
            sitSignOff5.setStatus(reStatus);
            sitSignOff5.setDocumentpdf(documentPdf);
            sitSignOff5.setContactemail(contactemail.toUpperCase());
            sitSignOff5.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff5);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff5.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff5.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff5.getSitsignoffid());

            for (int i = 0; i < splitArrayPart27.length; i++) {

                if (splitArrayPart27[i].contains("interfacearea")) {
                    interfacearea = splitArrayPart27[i].substring(16).replace("}", "").replace("\"", "").replace("]", "");
                    /*      System.out.println("interface  "+interfacearea);*/
                } else if (splitArrayPart27[i].contains("sitsignoffperson")) {
                    sitsignoffperson = splitArrayPart27[i].substring(19).replace("}", "").replace("\"", "").replace("]", "");
                    /*   System.out.println("sitsignoffperson  "+sitsignoffperson);*/
                } else if (splitArrayPart27[i].contains("contactemail")) {
                    contactemail = splitArrayPart27[i].substring(15).replace("}", "").replace("\"", "").replace("]", "");
                    /*System.out.println("contactEmail  "+contactemail);*/
                }

            }

            SITSignOff sitSignOff6 = new SITSignOff();
            sitSignOff6.setChangename(reChangeName.toUpperCase());
            sitSignOff6.setChangenumber(reChangeNumber);
            sitSignOff6.setProjectmanager(reProjectManager.toUpperCase());
            sitSignOff6.setTower(reTower.toUpperCase());
            sitSignOff6.setTestingcompleted(reSystemCompleted);
            sitSignOff6.setInterfacearea(interfacearea);
            sitSignOff6.setEmail(reEmail.toUpperCase());
            sitSignOff6.setComments(comments);
            sitSignOff6.setImplementationdate(implDate);
            sitSignOff6.setStatus(reStatus);
            sitSignOff6.setDocumentpdf(documentPdf);
            sitSignOff6.setContactemail(contactemail.toUpperCase());
            sitSignOff6.setContactperson(sitsignoffperson);
            service.insertIntoSitSignOffColumn(sitSignOff6);

            sitSignOff.getSitsignoffid();
            formTokes.add(sitSignOff6.getSitsignoffid());
            sendSignOffEmail.sendTestDataToEmail(contactemail, reProjectManager, sitSignOff6.getSitsignoffid());
            sendCapturesEmail.sendTestDataToEmail(reEmail, sitSignOff6.getSitsignoffid());

        }else if(choicesSize == 32){
            splitSize = choices.length / 8;
            String[] splitArrayPart28 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart29 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart30 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart31 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart32 = Arrays.copyOfRange(choices, 17, 20);
            String[] splitArrayPart33 = Arrays.copyOfRange(choices, 21, 24);
            String[] splitArrayPart34= Arrays.copyOfRange(choices, 25, 28);
            String[] splitArrayPart35= Arrays.copyOfRange(choices, 31, choices.length);


        }else if(choicesSize == 36){
            splitSize = choices.length / 9;
            String[] splitArrayPart36 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart37 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart38 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart39 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart40 = Arrays.copyOfRange(choices, 17, 20);
            String[] splitArrayPart41 = Arrays.copyOfRange(choices, 21, 24);
            String[] splitArrayPart42= Arrays.copyOfRange(choices, 25, 28);
            String[] splitArrayPart43= Arrays.copyOfRange(choices, 31,34);
            String[] splitArrayPart44= Arrays.copyOfRange(choices, 37, choices.length);

        }else if(choicesSize == 40){
            splitSize = choices.length / 10;
            String[] splitArrayPart45 = Arrays.copyOfRange(choices, 0, splitSize);
            String[] splitArrayPart46 = Arrays.copyOfRange(choices, 5, 8);
            String[] splitArrayPart47 = Arrays.copyOfRange(choices, 9, 12);
            String[] splitArrayPart48 = Arrays.copyOfRange(choices, 13, 16);
            String[] splitArrayPart49 = Arrays.copyOfRange(choices, 17, 20);
            String[] splitArrayPart50 = Arrays.copyOfRange(choices, 21, 24);
            String[] splitArrayPart51= Arrays.copyOfRange(choices, 25, 28);
            String[] splitArrayPart52= Arrays.copyOfRange(choices, 31,34);
            String[] splitArrayPart53= Arrays.copyOfRange(choices, 37, 40);
            String[] splitArrayPart54= Arrays.copyOfRange(choices, 43, choices.length);

        }

        return formTokes;
    }
}
