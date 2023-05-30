package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.ManualRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ManualRequestRepository extends CrudRepository<ManualRequest, IllegalAccessError> {
}
