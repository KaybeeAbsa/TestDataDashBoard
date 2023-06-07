package co.za.absa.TestDataDashBoard.configuration;

import javax.naming.directory.Attributes;

public class LdapUser {

    public static final String LDAP_GIVEN_NAME = "givenName";
    public static final String LDAP_SURNAME = "sn";
    public static final String LDAP_MAIL = "mail";
    public static final String LDAP_USERNAME = "cn";

    private String firstName;
    private String surname;
    private String fullName; // givenName sn
    private String mail; // mail
    private String userName; // cn

    public LdapUser(final Attributes attributes) {
        try {
            this.firstName = attributes.get(LDAP_GIVEN_NAME).get().toString();
            this.surname = attributes.get(LDAP_SURNAME).get().toString();
            this.fullName = String.format("%s %s", firstName, surname);
            this.mail = attributes.get(LDAP_MAIL).get().toString();
            this.userName = attributes.get(LDAP_USERNAME).get().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }

    public String getUserName() {
        return userName;
    }
}
