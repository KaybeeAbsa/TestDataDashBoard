package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.repositories.ClientCreationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientCreationService {

    @Autowired
    private ClientCreationRepository repo;

    public ArrayList<Newtobank> findNewToBankByHighScore(String used, String bureauType, int score){
        return repo.findClientsByHighScore(used, bureauType, score);
    }

    public ArrayList<Newtobank> findNewToBankByLowScore(String used, String bureauType, int score){
        return repo.findClientsByLowScore(used, bureauType,score);
    }

    public ArrayList<Newtobank> findRequestorsDetails(String rname, String date){
        return repo.findRequestorsByUsed(rname, date);
    }

    public int updatedUsedColumn(String used, String date, String rname, String rteam, String remail, String rmobile, int newtobankid)
    {
        return repo.updatedUsedColumn(used, date, rname, rteam, remail, rmobile, newtobankid);
    }

}
