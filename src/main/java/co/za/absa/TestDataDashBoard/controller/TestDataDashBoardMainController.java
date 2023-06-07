package co.za.absa.TestDataDashBoard.controller;

import co.za.absa.TestDataDashBoard.configuration.UserContextService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class TestDataDashBoardMainController {

    private final UserContextService userContextService;

    public TestDataDashBoardMainController(final UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @GetMapping(value = "/")
    public ModelAndView theHomePage(Principal principal) {
        return new ModelAndView("homepage");
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homePage()
    {
        return "homepage";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home()
    {
        return "home";
    }

    @RequestMapping(value = "/specialRequest", method = RequestMethod.GET)
    public String specialRequest()
    {
        return "specialRequest";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact()
    {
        return "contact";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about()
    {
        return "about";
    }

    @RequestMapping(value = "/testCard", method = RequestMethod.GET)
    public String testCard()
    {
        return "testCard";
    }
    @RequestMapping(value = "/sitSignOffCapture", method = RequestMethod.GET)
    public String tester()
    {
        return "sitSignOffCapture";
    }

    @RequestMapping(value = "/quotation", method = RequestMethod.GET)
    public String quotation()
    {
        return "quotation";
    }
    @RequestMapping(value = "/sitSignFormView", method = RequestMethod.GET)
    public String view()
    {
        return "sitSignFormView";
    }

    @RequestMapping(value = "/sitSignFormEdit", method = RequestMethod.GET)
    public String edit()
    {
        return "sitSignFormEdit";
    }

    @RequestMapping(value = "/sitSignOff", method = RequestMethod.GET)
    public String sitSignOff()
    {
        return "sitSignOff";
    }

    @RequestMapping(value = "/sitSignFormViewAllCapturedForms", method = RequestMethod.GET)
    public String sitSignOffViewAllCapturedForms()
    {
        return "sitSignFormViewAllCapturedForms";
    }

    @RequestMapping(value = "/sitSignFormViewCapture", method = RequestMethod.GET)
    public String sitSignFormViewCapture()
    {
        return "sitSignFormViewCapture";
    }
}
