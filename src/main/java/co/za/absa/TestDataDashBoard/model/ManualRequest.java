package co.za.absa.TestDataDashBoard.model;

import javax.persistence.*;

@Entity
@Table(name = "tblManualRequests")
public class ManualRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "requestid")
    private int requestid;

    @Column(name = "requestorname")
    private String requestorName;

    @Column(name = "requestorteam")
    private String requestorTeam;

    @Column(name = "requestoremail")
    private String requestorEmail;

    @Column(name = "requestormobile")
    private String requestorMobile;

    @Column(name = "requestquantity")
    private int requestQuantity;

    @Column(name = "requestscore")
    private String requestScore;

    @Column(name = "requestenviroment")
    private String requestEnviroment;

    @Column(name = "requestproduct")
    private String requestProduct;

    @Column(name = "requestclienttype")
    private String requestClientType;

    @Column(name = "requestpreapproved")
    private String requestPreApproved;

    @Column(name = "requestcombi")
    private String requestCombi;

    @Column(name = "requestcomment",length=10485760)
    private String requestComment;

    public ManualRequest(int requestid, String requestorName, String requestorTeam, String requestorEmail, int requestQuantity, String requestScore, String requestEnviroment, String requestProduct, String requestClientType, String requestPreApproved, String requestCombi, String requestComment) {
        this.requestid = requestid;
        this.requestorName = requestorName;
        this.requestorTeam = requestorTeam;
        this.requestorEmail = requestorEmail;
        this.requestQuantity = requestQuantity;
        this.requestScore = requestScore;
        this.requestEnviroment = requestEnviroment;
        this.requestProduct = requestProduct;
        this.requestClientType = requestClientType;
        this.requestPreApproved = requestPreApproved;
        this.requestCombi = requestCombi;
        this.requestComment = requestComment;
    }

    public ManualRequest() {

    }

    public int getRequestid() {
        return requestid;
    }

    public void setRequestid(int requestid) {
        this.requestid = requestid;
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

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public String getRequestScore() {
        return requestScore;
    }

    public void setRequestScore(String requestScore) {
        this.requestScore = requestScore;
    }

    public String getRequestEnviroment() {
        return requestEnviroment;
    }

    public void setRequestEnviroment(String requestEnviroment) {
        this.requestEnviroment = requestEnviroment;
    }

    public String getRequestProduct() {
        return requestProduct;
    }

    public void setRequestProduct(String requestProduct) {
        this.requestProduct = requestProduct;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getRequestPreApproved() {
        return requestPreApproved;
    }

    public void setRequestPreApproved(String requestPreApproved) {
        this.requestPreApproved = requestPreApproved;
    }

    public String getRequestCombi() {
        return requestCombi;
    }

    public void setRequestCombi(String requestCombi) {
        this.requestCombi = requestCombi;
    }

    public String getRequestComment() {
        return requestComment;
    }

    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }

    @Override
    public String toString() {
        return  requestorName + ';' +
                requestorTeam + ';' +
                requestorEmail + ';' +
                requestorMobile + ';' +
                requestQuantity +';' +
                requestScore + ';' +
                requestEnviroment + ';' +
                requestProduct + ';' +
                requestClientType + ';' +
                requestPreApproved + ';' +
                requestCombi + ';' +
                requestComment;
    }


}
