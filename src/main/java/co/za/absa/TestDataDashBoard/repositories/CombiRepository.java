package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.Combis;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RepositoryRestController
public interface CombiRepository extends CrudRepository<Combis, Integer> {
    @Query( value = "SELECT * FROM public.tblcombis  WHERE used = :used AND enviromenttype = :enviromenttype AND product = :product", nativeQuery = true)
    public ArrayList<Combis> findCombiByUsed(@Param("used") String used, @Param("enviromenttype") String enviromenttype, @Param("product") String product);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblcombis SET used = :used WHERE combi = :combi" , nativeQuery = true)
    public int updatedUsedColumn(@Param("used") String used, @Param("combi") String combi);

}
