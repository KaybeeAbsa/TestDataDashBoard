package co.za.absa.TestDataDashBoard.services;

import co.za.absa.TestDataDashBoard.model.ManualRequest;
import co.za.absa.TestDataDashBoard.repositories.ManualRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManualRequestService {

    @Autowired
    private ManualRequestRepository repo;

    public ManualRequest saveRequest(ManualRequest manualRequest){
        return repo.save(manualRequest);
    }

}
