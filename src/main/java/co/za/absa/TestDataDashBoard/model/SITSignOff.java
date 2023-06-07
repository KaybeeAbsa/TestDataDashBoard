package co.za.absa.TestDataDashBoard.model;

import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "tblsitsignoff")
public class SITSignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sitsignoffid")
    private int sitsignoffid;
    @Column(name = "changename")
    private String changename;
    @Column(name = "changenumber")
    private String changenumber;
    @Column(name = "projectmanager")
    private String projectmanager;
    @Column(name = "tower")
    private String tower;
    @Column(name = "testingcompleted")
    private String testingcompleted;
    @Column(name = "interfacearea")
    private String interfacearea;
    @Column(name = "contactperson")
    private String contactperson;
    @Column(name = "email")
    private String email;
    @Column(name = "comments")
    private String comments;
    @Column(name = "implementationdate")
    private String implementationdate;
    @Column(name = "status")
    private String status;
    @Column(name = "contactemail")
    private String contactemail;
    @Column(name = "documentpdf",length = 10485760)
    private String documentpdf;

    public SITSignOff() {
    }

    public SITSignOff(int sitsignoffid, String changename, String changenumber, String projectmanager, String tower, String testingcompleted, String interfacearea, String contactperson, String email, String comments, String implementationdate, String status, String contactemail, String documentpdf) {
        this.sitsignoffid = sitsignoffid;
        this.changename = changename;
        this.changenumber = changenumber;
        this.projectmanager = projectmanager;
        this.tower = tower;
        this.testingcompleted = testingcompleted;
        this.interfacearea = interfacearea;
        this.contactperson = contactperson;
        this.email = email;
        this.comments = comments;
        this.implementationdate = implementationdate;
        this.status = status;
        this.contactemail = contactemail;
        this.documentpdf = documentpdf;
    }

    public String getDocumentpdf() {
        return documentpdf;
    }

    public void setDocumentpdf(String documentpdf) {
        this.documentpdf = documentpdf;
    }

    public int getSitsignoffid() {
        return sitsignoffid;
    }

    public void setSitsignoffid(int sitsignoffid) {
        this.sitsignoffid = sitsignoffid;
    }

    public String getChangename() {
        return changename;
    }

    public void setChangename(String changename) {
        this.changename = changename;
    }

    public String getChangenumber() {
        return changenumber;
    }

    public void setChangenumber(String changenumber) {
        this.changenumber = changenumber;
    }


    public String getProjectmanager() {
        return projectmanager;
    }

    public void setProjectmanager(String projectmanager) {
        this.projectmanager = projectmanager;
    }

    public String getTestingcompleted() {
        return testingcompleted;
    }

    public void setTestingcompleted(String testingcompleted) {
        this.testingcompleted = testingcompleted;
    }


    public String getTower() {
        return tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }


    public String getInterfacearea() {
        return interfacearea;
    }

    public void setInterfacearea(String interfacearea) {
        this.interfacearea = interfacearea;
    }


    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImplementationdate() {
        return implementationdate;
    }

    public void setImplementationdate(String implementationdate) {
        this.implementationdate = implementationdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactemail() {
        return contactemail;
    }

    public void setContactemail(String contactemail) {
        this.contactemail = contactemail;
    }

/*    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }*/

    @Override
    public String toString() {
        return "SITSignOff{" +
                "sitsignoffid=" + sitsignoffid +
                ", changename='" + changename + '\'' +
                ", changenumber='" + changenumber + '\'' +
                ", projectmanager='" + projectmanager + '\'' +
                ", tower='" + tower + '\'' +
                ", testingcompleted='" + testingcompleted + '\'' +
                ", interfacearea='" + interfacearea + '\'' +
                ", contactperson='" + contactperson + '\'' +
                ", email='" + email + '\'' +
                ", comments='" + comments + '\'' +
                ", implementationdate='" + implementationdate + '\'' +
                ", status='" + status + '\'' +
                ", contactemail='" + contactemail + '\'' +
                '}';
    }
}
