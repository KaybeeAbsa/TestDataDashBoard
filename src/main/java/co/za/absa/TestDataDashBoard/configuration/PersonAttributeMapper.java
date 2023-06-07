package co.za.absa.TestDataDashBoard.configuration;

import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class PersonAttributeMapper implements AttributesMapper<LdapUser> {

    @Override
    public LdapUser mapFromAttributes(final Attributes attributes) throws NamingException {
        return new LdapUser(attributes);
    }
}
