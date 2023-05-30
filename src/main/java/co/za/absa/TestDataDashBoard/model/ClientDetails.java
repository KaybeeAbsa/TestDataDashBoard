package co.za.absa.TestDataDashBoard.model;

public class ClientDetails {

    private String Surname;
    private String names;
    private String dateOpened;
    private String AOL;

    public ClientDetails(String surname, String names, String dateOpened, String AOL) {

        Surname = surname;
        this.names = names;
        this.dateOpened = dateOpened;
        this.AOL = AOL;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getAOL() {
        return AOL;
    }

    public void setAOL(String AOL) {
        this.AOL = AOL;
    }
}
