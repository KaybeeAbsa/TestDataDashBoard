package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.configuration.LdapUser;
import co.za.absa.TestDataDashBoard.configuration.UserContextService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserContextService userContextService;

    public UserController(final UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @GetMapping(value = "")
    public LdapUser getLdapUser(final Principal principal) {

        return userContextService.getUserDetails(principal);
    }
}
