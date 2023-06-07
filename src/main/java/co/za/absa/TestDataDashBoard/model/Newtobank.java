/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.za.absa.TestDataDashBoard.model;

import javax.persistence.*;

/**
 *
 * @author ABKS580
 */

@Entity
@Table(name = "tblnewtobank")
public class Newtobank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "newtobankid")
    private int newtobankid;
    @Column(name = "idtype")
    private String idtype;
    @Column(name = "surname")
    private String surname;
    @Column(name = "name")
    private String name;
    @Column(name = "secondname")
    private String secondname;
    @Column(name = "score")
    private int score;
    @Column(name = "used")
    private String used;
    @Column(name = "bureautype")
    private String bureauType;
    @Column(name = "requestdate")
    private String requestdate;
    @Column(name = "requestorname")
    private String requestorName;
    @Column(name = "reqestorteam")
    private String requestorTeam;
    @Column(name = "requestoremail")
    private String requestorEmail;
    @Column(name = "requestormobile")
    private String requestorMobile;
    @Column(name = "clientcode")
    private String clientCode;
    @Column(name = "accountno")
    private String accountNo;
    @Column(name = "combi")
    private String combi;
    @Column(name = "comment")
    private String comment;
    private String apiCombi;
    @Column(name = "ecasa")
    private String ecasa;
    @Column(name = "policy")
    private String policy;

    public Newtobank() {
    }

    public int getNewtobankid() {
        return newtobankid;
    }

    public void setNewtobankid(int newtobankid) {
        this.newtobankid = newtobankid;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
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

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
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

    public String getBureauType() {
        return bureauType;
    }

    public void setBureauType(String bureauType) {
        this.bureauType = bureauType;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(String requestdate) {
        this.requestdate = requestdate;
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

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCombi() {
        return combi;
    }

    public void setCombi(String combi) {
        this.combi = combi;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApiCombi() {
        return apiCombi;
    }

    public void setApiCombi(String apiCombi) {
        this.apiCombi = apiCombi;
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

    @Override
    public String toString() {

        return
                idtype + ';' +
                        surname + ';' +
                        name + ';' +
                        secondname + ';' +
                        score + ';' +
                        used + ';' +
                        bureauType;

    }


    public String toString1() {

        return
                idtype + ';' +
                        surname + ';' +
                        name + ';' +
                        secondname + ';' +
                        score + ';' +
                        used + ';' +
                        clientCode + ';' +
                        accountNo + ';' +
                        ecasa + ';' +
                        policy + ';' +
                        comment + ';' +
                        bureauType + ';' +
                        apiCombi;

    }

}