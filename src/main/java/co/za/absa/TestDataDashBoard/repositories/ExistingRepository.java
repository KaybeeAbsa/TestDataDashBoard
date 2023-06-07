package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.Existing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RepositoryRestController
public interface ExistingRepository extends CrudRepository<Existing, Integer> {

    @Query( value = "SELECT * FROM public.tblexistingclients WHERE used = :used AND product = :product  AND linkedsavings = :linkedsavings AND linkedcheque = :linkedcheque AND linkedcard = :linkedcard AND linkedavaf = :linkedavaf AND linkedhm = :linkedhm AND linkedpl = :linkedpl  AND clientype = :clientype AND bureautype = :bureautype AND enviromenttype = :enviromenttype AND score >= :score", nativeQuery = true)
    public ArrayList<Existing> findCliEntsByHighScore(@Param("used") String used, @Param("product") String product,@Param("linkedsavings")String linkedsavings,@Param("linkedcheque") String linkedcheque,@Param("linkedcard") String linkedcard,@Param("linkedavaf") String linkedavaf,@Param("linkedhm")String linkedhm,@Param("linkedpl")String linkedpl, @Param("clientype") String clientype, @Param("bureautype") String bureautype, @Param("enviromenttype") String enviromenttype, @Param("score") int score);

    @Query( value = "SELECT * FROM public.tblexistingclients WHERE used = :used AND product = :product AND tblexistingclients.linkedsavings = :linkedsavings AND tblexistingclients.linkedcheque = :linkedcheque AND tblexistingclients.linkedcard = :linkedcard AND tblexistingclients.linkedavaf = :linkedavaf AND tblexistingclients.linkedhm = :linkedhm AND tblexistingclients.linkedpl = :linkedpl AND tblexistingclients.clientype = :clientype AND tblexistingclients.bureautype = :bureautype AND tblexistingclients.enviromenttype = :enviromenttype AND tblexistingclients.score <= :score", nativeQuery = true)
    public ArrayList<Existing> findCliEntsByLowScore(@Param("used") String used, @Param("product") String product,@Param("linkedsavings")String linkedsavings,@Param("linkedcheque") String linkedcheque,@Param("linkedcard") String linkedcard,@Param("linkedavaf") String linkedavaf,@Param("linkedhm")String linkedhm,@Param("linkedpl")String linkedpl, @Param("clientype") String clientype, @Param("bureautype") String bureautype, @Param("enviromenttype") String enviromenttype, @Param("score") int score);

    @Query( value = "SELECT * FROM public.tblexistingclients  WHERE requestoremail = :requestoremail AND requestdate = :requestdate", nativeQuery = true)
    public ArrayList<Existing> findRequestorsByUsed(@Param("requestoremail") String requestoremail, @Param("requestdate") String requestdate);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblexistingclients  SET used = :used, requestdate = :requestdate , requestorname = :requestorname, reqestorteam = :reqestorteam, requestoremail = :requestoremail, requestormobile = :requestormobile WHERE idtype = :idtype AND enviromenttype = :enviromenttype" , nativeQuery = true)
    public int updatedUsedColumn(@Param("used") String used, @Param("requestdate") String date, @Param("requestorname") String requestorname, @Param("reqestorteam") String reqestorteam, @Param("requestoremail") String requestoremail, @Param("requestormobile") String requestormobile, @Param("idtype") String idtype, @Param("enviromenttype") String enviromenttype);

}
