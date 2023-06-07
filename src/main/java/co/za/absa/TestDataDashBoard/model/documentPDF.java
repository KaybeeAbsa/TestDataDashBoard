package co.za.absa.TestDataDashBoard.model;

public class documentPDF {
    String[] tokenID;
    String documentPDF;

    public documentPDF(String[] tokenID, String documentPDF) {
        this.tokenID = tokenID;
        this.documentPDF = documentPDF;
    }

    public String[] getTokenID() {
        return tokenID;
    }

    public void setTokenID(String[] tokenID) {
        this.tokenID = tokenID;
    }

    public String getDocumentPDF() {
        return documentPDF;
    }

    public void setDocumentPDF(String documentPDF) {
        this.documentPDF = documentPDF;
    }
}
