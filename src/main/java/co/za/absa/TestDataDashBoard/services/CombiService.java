package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.Combis;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import co.za.absa.TestDataDashBoard.repositories.CombiRepository;
import co.za.absa.TestDataDashBoard.repositories.NewToBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CombiService {

    @Autowired
    private CombiRepository repo;

    public ArrayList<Combis> findCombi(String used, String environment,String product){
        return repo.findCombiByUsed(used, environment,product);
    }

    public int updatedUsedColumn(String used, String combi)
    {
        return repo.updatedUsedColumn(used, combi);
    }
}
