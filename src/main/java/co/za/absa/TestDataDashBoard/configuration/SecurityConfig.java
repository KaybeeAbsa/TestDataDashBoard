package co.za.absa.TestDataDashBoard.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LdapContextSource ldapContextSource;

    @Autowired
    public SecurityConfig(final LdapContextSource ldapContextSource) {
        this.ldapContextSource = ldapContextSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .contextSource(ldapContextSource)
                .userDnPatterns("CN={0},OU=Users,OU=CORP Accounts,DC=corp,DC=dsarena,DC=com")
                .userSearchFilter("(sAMAccountName={0})");
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/homepage",true)
                .failureUrl("/login?error=true") .permitAll()
                .and().httpBasic()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll();
    }


}
