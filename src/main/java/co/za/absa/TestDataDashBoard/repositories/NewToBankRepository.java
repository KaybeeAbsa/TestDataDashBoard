package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.Existing;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RepositoryRestController
public interface NewToBankRepository  extends CrudRepository<Newtobank, Integer> {

    @Query( value = "SELECT * FROM tblnewtobank t WHERE t.used = :used AND t.bureautype = :bureautype", nativeQuery = true)
    public ArrayList<Newtobank> findCleintsByUsed(@Param("used") String used, @Param("bureautype") String bureautype);

    @Query( value = "SELECT * FROM public.tblnewtobank  WHERE requestorName = :requestorName AND requestdate = :requestdate ", nativeQuery = true)
    public ArrayList<Newtobank> findRequestorsByUsed(@Param("requestorName") String requestorName, @Param("requestdate") String requestdate);

    @Query( value = "SELECT * FROM public.tblnewtobank  WHERE used = :used AND bureautype = :bureautype AND score >= :score", nativeQuery = true)
    public ArrayList<Newtobank> findClientsByHighScore(@Param("used") String used,@Param("bureautype") String bureautype, @Param("score") int score);

    @Query( value = "SELECT * FROM public.tblnewtobank  WHERE used = :used AND bureautype = :bureautype AND score <= :score", nativeQuery = true)
    public ArrayList<Newtobank> findClientsByLowScore(@Param("used") String used, @Param("bureautype") String bureautype, @Param("score") int score);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblnewtobank SET used = :used, requestdate = :requestdate, requestorname = :requestorname, reqestorteam = :reqestorteam, requestoremail = :requestoremail, requestormobile = :requestormobile WHERE newtobankid = :newtobankid" , nativeQuery = true)
    public int updatedUsedColumn(@Param("used") String used, @Param("requestdate") String requestdate, @Param("requestorname") String requestorname, @Param("reqestorteam") String reqestorteam, @Param("requestoremail") String requestoremail, @Param("requestormobile") String requestormobile, @Param("newtobankid") int newtobankid);

}
