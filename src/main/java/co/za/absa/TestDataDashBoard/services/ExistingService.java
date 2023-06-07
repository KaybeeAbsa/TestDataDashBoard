package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.Existing;
import co.za.absa.TestDataDashBoard.repositories.ExistingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExistingService {

    @Autowired
    private ExistingRepository repo;

    public ArrayList<Existing> findClientbyHighScore(String used, String product, String linkedSavings, String linkedCheque, String linkedCard, String linkedAvaf,String linkedHM,String linkedPL, String clienttype, String bureauType, String enviromenttype, int score){
        return repo.findCliEntsByHighScore(used, product,linkedSavings,linkedCheque,linkedCard,linkedAvaf,linkedHM,linkedPL, clienttype,bureauType,enviromenttype, score);
    }

    public ArrayList<Existing> findClientbyLowScore(String used, String product, String linkedSavings, String linkedCheque, String linkedCard, String linkedAvaf,String linkedHM,String linkedPL, String clienttype, String bureauType, String enviromenttype, int score){
        return repo.findCliEntsByLowScore(used, product,linkedSavings,linkedCheque,linkedCard,linkedAvaf,linkedHM,linkedPL, clienttype,bureauType,enviromenttype, score);
    }

    public ArrayList<Existing> findRequestorsDetails(String reEmail, String date){
        return repo.findRequestorsByUsed(reEmail ,date);
    }

    public int updatedUsedColumn(String used, String date, String rname, String rteam, String remail, String rmobile, String idNumber, String enviroment)
    {
        return repo.updatedUsedColumn(used, date, rname, rteam, remail, rmobile, idNumber, enviroment);
    }

}
