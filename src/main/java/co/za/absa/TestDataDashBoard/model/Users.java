package co.za.absa.TestDataDashBoard.model;

import javax.persistence.*;

@Entity
@Table(name = "tblusers")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userid")
    private int userid;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private String status;
    public Users() {
    }

    public Users(int userid, String username, String password, String status) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
