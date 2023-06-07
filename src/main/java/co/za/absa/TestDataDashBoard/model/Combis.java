package co.za.absa.TestDataDashBoard.model;


import javax.persistence.*;

@Entity
@Table(name = "tblcombis")
public class Combis {
    @Id
    @Basic(optional = false)
    @Column(name = "combi")
    private String combi;
    @Column(name = "enviromenttype")
    private String enviromenttype;
    @Column(name = "used")
    private String used;

    @Column(name = "product")
    private String product;
    public Combis() {

    }

    public Combis(String combi, String enviromenttype, String used, String product) {
        this.combi = combi;
        this.enviromenttype = enviromenttype;
        this.used = used;
        this.product = product;
    }

    public String getCombi() {
        return combi;
    }

    public void setCombi(String combi) {
        this.combi = combi;
    }

    public String getEnviromenttype() {
        return enviromenttype;
    }

    public void setEnviromenttype(String enviromenttype) {
        this.enviromenttype = enviromenttype;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
