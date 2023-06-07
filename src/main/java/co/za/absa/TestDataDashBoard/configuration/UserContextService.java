package co.za.absa.TestDataDashBoard.configuration;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class UserContextService {

    private final LdapTemplate ldapTemplate;

    public UserContextService(final LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public LdapUser getUserDetails(final Principal principal) {
        return ldapTemplate.search(query().filter(new EqualsFilter("cn", principal.getName())), new PersonAttributeMapper()).get(0);
    }
}
