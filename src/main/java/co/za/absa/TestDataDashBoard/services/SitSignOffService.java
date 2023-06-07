package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import co.za.absa.TestDataDashBoard.repositories.SITSignOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Service
public class SitSignOffService {
    @Autowired
    private SITSignOffRepository repo;


    public SITSignOff insertIntoSitSignOffColumn(SITSignOff sitSignOff)
    {
         return repo.save(sitSignOff);
    }

    public ArrayList<SITSignOff> findAuthorizations(String contactemail, String status, int sitsignoffid){
        return repo.findAuthorizations(contactemail,status, sitsignoffid);
    }

    public ArrayList<SITSignOff> findCaptureInformation(String email, String changename){
        return repo.findCapturedInformation(email,changename);
    }

    public ArrayList<SITSignOff> findCaptureInformation(String email){
        return repo.findCapturedInformation(email);
    }

    public Object findAllCaptureInformation(){
        return repo.findAll();
    }
    public ArrayList<SITSignOff> findClientUsingEmail(String email, String status,int interfacearea){
        return repo.findClientUsingEmail(email,status,interfacearea);
    }

    public ArrayList<SITSignOff> findSignOffs(String contactemail, int interfacearea){
        return repo.findSignOffs(contactemail,interfacearea);
    }

    public int updatedStatusColumn(String status, int sitsignoffid)
    {
       return  repo.updateAuthorization(status, sitsignoffid);
    }

    public int updatedSignOff(String status, String changename, String changenumber, String projectmanager,String tower,String testingcompleted,String contactperson,String implementationdate,String contactemail,String interfacearea, int sitsignoffid)
    {
       return repo.updateSignOff(changename,changenumber,projectmanager,tower,testingcompleted,contactperson,implementationdate,contactemail,interfacearea,status, sitsignoffid);
    }

    public int uploadPdfFiles(String doc, int sitsignoffid)
    {
        return repo.uploadPDFfiels(doc, sitsignoffid);
    }
}
