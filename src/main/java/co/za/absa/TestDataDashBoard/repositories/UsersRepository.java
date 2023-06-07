package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.transaction.Transactional;

@RepositoryRestController
public interface UsersRepository extends CrudRepository<Users, Integer> {

    @Transactional
    @Modifying
    @Query(value = "Update public.tblusers SET status = :status WHERE username = :username" , nativeQuery = true)
    public int updatedUserStatus(@Param("status") String status, @Param("username") String username);


}
