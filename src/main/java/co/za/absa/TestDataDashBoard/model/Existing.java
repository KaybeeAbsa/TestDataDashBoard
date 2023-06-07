package co.za.absa.TestDataDashBoard.model;

import javax.persistence.*;

@Entity
@Table(name = "tblexistingclients")
public class Existing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "existingid")
    private int existingid;
    @Column(name = "idtype")
    private String idtype;
    @Column(name = "enviromenttype")
    private String enviromenttype;
    @Column(name = "requestdate")
    private String requestdate;
    @Column(name = "score")
    private int score;
    @Column(name = "used")
    private String used;
    @Column(name = "requestorname")
    private String requestorName;
    @Column(name = "reqestorteam")
    private String requestorTeam;
    @Column(name = "requestoremail")
    private String requestorEmail;
    @Column(name = "requestormobile")
    private String requestorMobile;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "secondname")
    private String secondname;
    @Column(name = "clientcode")
    private String clientCode;
    @Column(name = "bureautype")
    private String bureauType;
    @Column(name = "accountno")
    private String accountNo;
    @Column(name = "aol")
    private String aol;

    @Column(name = "comment")
    private String comment;
    private String ecasa;

    private String policy;
    @Column(name = "ficcompliantstatus")
    private String ficCompliantStatus;

    public Existing() {
    }

    public Existing(int existingid, String idtype, String enviromenttype, String requestdate, int score, String used, String requestorName, String requestorTeam, String requestorEmail, String requestorMobile, String surname, String name, String secondname, String clientCode, String bureauType, String accountNo, String aol, String comment, String ecasa, String policy, String ficCompliantStatus) {
        this.existingid = existingid;
        this.idtype = idtype;
        this.enviromenttype = enviromenttype;
        this.requestdate = requestdate;
        this.score = score;
        this.used = used;
        this.requestorName = requestorName;
        this.requestorTeam = requestorTeam;
        this.requestorEmail = requestorEmail;
        this.requestorMobile = requestorMobile;
        this.surname = surname;
        this.name = name;
        this.secondname = secondname;
        this.clientCode = clientCode;
        this.bureauType = bureauType;
        this.accountNo = accountNo;
        this.aol = aol;
        this.comment = comment;
        this.ecasa = ecasa;
        this.policy = policy;
        this.ficCompliantStatus = ficCompliantStatus;
    }

    public String getFicCompliantStatus() {
        return ficCompliantStatus;
    }

    public void setFicCompliantStatus(String ficCompliantStatus) {
        this.ficCompliantStatus = ficCompliantStatus;
    }

    public String getEcasa() {
        return ecasa;
    }

    public void setEcasa(String ecasa) {
        this.ecasa = ecasa;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getExistingid() {
        return existingid;
    }

    public void setExistingid(int existingid) {
        this.existingid = existingid;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
    }

    public String getAol() {
        return aol;
    }

    public void setAol(String aol) {
        this.aol = aol;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestorTeam() {
        return requestorTeam;
    }

    public void setRequestorTeam(String requestorTeam) {
        this.requestorTeam = requestorTeam;
    }

    public String getRequestorEmail() {
        return requestorEmail;
    }

    public void setRequestorEmail(String requestorEmail) {
        this.requestorEmail = requestorEmail;
    }

    public String getRequestorMobile() {
        return requestorMobile;
    }

    public void setRequestorMobile(String requestorMobile) {
        this.requestorMobile = requestorMobile;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getClientCode() {
        return clientCode;
    }

    public String getBureauType() {
        return bureauType;
    }

    public void setBureauType(String bureauType) {
        this.bureauType = bureauType;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getEnviromenttype() {
        return enviromenttype;
    }

    public void setEnviromenttype(String enviromenttype) {
        this.enviromenttype = enviromenttype;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return idtype + ';' +
                surname + ';' +
                name + ';' +
                secondname + ';' +
                score + ';' +
                clientCode + ';' +
                accountNo + ';' +
                aol + ';' +
                ecasa + ';' +
                policy + ';' +
                bureauType + ';' +
                ficCompliantStatus + ';' +
                comment ;
    }
}
