package co.za.absa.TestDataDashBoard.model;

public class AccountLinkedToClientCode {

    private String product;
    private String accountNumber;
    private String corp;
    private String productType;
    private String status;

    public AccountLinkedToClientCode(String product, String accountNumber, String corp, String productType, String status) {
        this.product = product;
        this.accountNumber = accountNumber;
        this.corp = corp;
        this.productType = productType;
        this.status = status;
    }

    public AccountLinkedToClientCode()
    {

    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
