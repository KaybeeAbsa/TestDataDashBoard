package co.za.absa.TestDataDashBoard.configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ldap")
@EnableEncryptableProperties
public class LdapConfigurationProperties {

    private String password;
    private String userDn;
    private String base;
    private String url;
    private String userSearchFilter;
    private String userDnPatterns;

    public LdapConfigurationProperties() {}

    public LdapConfigurationProperties(String password, String userDn, String base, String url, String userSearchFilter, String userDnPatterns) {
        this.password = password;
        this.userDn = userDn;
        this.base = base;
        this.url = url;
        this.userSearchFilter = userSearchFilter;
        this.userDnPatterns = userDnPatterns;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserDn() {
        return userDn;
    }

    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserSearchFilter() {
        return userSearchFilter;
    }

    public void setUserSearchFilter(String userSearchFilter) {
        this.userSearchFilter = userSearchFilter;
    }

    public String getUserDnPatterns() {
        return userDnPatterns;
    }

    public void setUserDnPatterns(String userDnPatterns) {
        this.userDnPatterns = userDnPatterns;
    }
}
