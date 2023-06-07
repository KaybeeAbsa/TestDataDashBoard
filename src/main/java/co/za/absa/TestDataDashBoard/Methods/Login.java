package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.LoginHeaders;
import co.za.absa.TestDataDashBoard.commonMethods.ResponseFromAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Login {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate() ;


    public String loginToGetSession()
    {

        HttpHeaders httpHeaders =new LoginHeaders().configureLoginHeaders();
        ResponseFromAPI responseFromAPI = new ResponseFromAPI();
        String session = getSession();
        Map<String,String> map = new HashMap<>();
        map.put("session",session);


        responseFromAPI.getResponseFromAPI("Login","Login.json",map,httpHeaders);

        return session;
    }
    public static String getSession() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


}
