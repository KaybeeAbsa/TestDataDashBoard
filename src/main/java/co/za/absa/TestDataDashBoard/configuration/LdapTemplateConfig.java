package co.za.absa.TestDataDashBoard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapTemplateConfig {

    private final LdapConfigurationProperties properties;

    public LdapTemplateConfig(LdapConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
        ldapTemplate.setIgnorePartialResultException(true);
        ldapTemplate.setIgnoreNameNotFoundException(true);
        return ldapTemplate;
    }

    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(properties.getUrl());
        contextSource.setUserDn(properties.getUserDn());
        contextSource.setPassword(properties.getPassword());
        contextSource.setBase(properties.getBase());
        contextSource.setReferral("follow");
        contextSource.afterPropertiesSet();
        return contextSource;
    }
}
