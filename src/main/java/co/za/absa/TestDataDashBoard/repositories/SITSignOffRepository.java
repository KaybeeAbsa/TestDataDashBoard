package co.za.absa.TestDataDashBoard.repositories;

import co.za.absa.TestDataDashBoard.model.Existing;
import co.za.absa.TestDataDashBoard.model.SITSignOff;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.transaction.Transactional;
import java.sql.Blob;
import java.util.ArrayList;

@RepositoryRestController
public interface SITSignOffRepository extends CrudRepository<SITSignOff, Integer> {

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE contactemail = :contactemail AND status = :status AND sitsignoffid = :sitsignoffid", nativeQuery = true)
    public ArrayList<SITSignOff> findAuthorizations(@Param("contactemail") String contactemail, @Param("status") String status, @Param("sitsignoffid") int sitsignoffid);

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE contactemail = :contactemail AND sitsignoffid = :sitsignoffid", nativeQuery = true)
    public ArrayList<SITSignOff> findSignOffs(@Param("contactemail") String contactemail,@Param("sitsignoffid") int sitsignoffid);

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE email = :email AND changename = :changename", nativeQuery = true)
    public ArrayList<SITSignOff> findCapturedInformation(@Param("email") String email, @Param("changename") String changename);

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE email = :email", nativeQuery = true)
    public ArrayList<SITSignOff> findCapturedInformation(@Param("email") String email);

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE email = :email", nativeQuery = true)
    public ArrayList<SITSignOff> findAllCaptureInformation(@Param("email") String email);

    @Query( value = "SELECT * FROM public.tblsitsignoff WHERE email = :email AND status = :status AND sitsignoffid = :sitsignoffid", nativeQuery = true)
    public ArrayList<SITSignOff> findClientUsingEmail(@Param("email") String email, @Param("status") String status, @Param("sitsignoffid") int sitsignoffid);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblsitsignoff  SET status = :status WHERE sitsignoffid = :sitsignoffid" , nativeQuery = true)
    public int updateAuthorization(@Param("status") String status, @Param("sitsignoffid") int sitsignoffid);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblsitsignoff SET changename = :changename, changenumber = :changenumber, projectmanager = :projectmanager, tower = :tower, testingcompleted = :testingcompleted, contactperson = :contactperson, implementationdate = :implementationdate, status = :status, contactemail = :contactemail ,interfacearea = :interfacearea WHERE sitsignoffid = :sitsignoffid" , nativeQuery = true)
    public int updateSignOff( @Param("changename") String changename,@Param("changenumber") String changenumber,@Param("projectmanager") String projectmanager,@Param("tower") String tower,@Param("testingcompleted") String testingcompleted,@Param("contactperson") String contactperson,@Param("implementationdate") String implementationdate,@Param("contactemail") String contactemail,@Param("interfacearea") String interfacearea,@Param("status") String status,@Param("sitsignoffid") int sitsignoffid);

    @Transactional
    @Modifying
    @Query(value = "Update public.tblsitsignoff SET documentpdf = :documentpdf WHERE sitsignoffid = :sitsignoffid" , nativeQuery = true)
    public int uploadPDFfiels(@Param("documentpdf") String doc, @Param("sitsignoffid") int sitsignoffid);

}
