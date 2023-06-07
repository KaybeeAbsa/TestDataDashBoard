package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSV;
import co.za.absa.TestDataDashBoard.commonMethods.ResponseFromAPI;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Policy_CreateNewPolicy {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public String createNewPolicy(String clientKey,String idNumber,String initials,String surname,String policyType)
    {
        HttpHeaders headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        Login login = new Login();
        String session = login.loginToGetSession();

        ResponseFromAPI responseFromAPI = new ResponseFromAPI();
        Map<String,String> map = new HashMap<>();

        map.put("clientKey",clientKey);
        map.put("clientKey1",clientKey);
        map.put("idNumber",idNumber);
        map.put("initials",initials);
        map.put("surname",surname);
        map.put("policyType",policyType);
        map.put("session",session);

        String response =responseFromAPI.getResponseFromAPI("Policy_CreateNewPolicy","Policy_CreateNewPolicy.json",map,headers);

        return getPolicy(response);

    }

    private String getPolicy(String body) {

        String response="";
        try {
            JSONObject jsonObject = new JSONObject(body);
            response= jsonObject.getJSONObject("policyCreateNewPolicyResult").getJSONObject("dsecontracts").getJSONArray("dc2DSEContract").getJSONObject(0).getString("refNo");

        }catch (Exception e)
        {
            e.fillInStackTrace();
        }

        return response.substring(0,11).trim();

    }
}
