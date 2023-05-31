package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSS;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSV;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class FicLockAPI {

    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());
    public String addFic(String clientCode,String newToBankLock,String softLock,String riskLock,String enviroment) {
        String url = null;
        HttpHeaders headers;
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb-api-sit.absa.co.za:3443/sit/sb/rbbrb/ciupdficlockv20api/CIupdFicLockV20API";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSS().configureCIFHeaders();

        }else{

             url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbbrb/ciupdficlockv20api/CIupdFicLockV20API";
             headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        }

        map.put("clientCode",clientCode);
        map.put("newToBankLock",newToBankLock);
        map.put("softLock",softLock);
        map.put("riskLock",riskLock);

        String request = requests.getRequest("FicLockV20API.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

            return ficResponse(response.getBody());
        }catch (Exception e)
        {
            e.fillInStackTrace();
            return "Fic Not Added";
        }

    }

    private String ficResponse(String body) {

        String array = "";
        try {

            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("outputMessage").getJSONObject("outputErrorMessage").getJSONObject("messageEntry").getString("messageText");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;

    }


    public Timer checkHowLongDoesTheAPITake()
    {
        Timer timer = new Timer();
        return timer;
    }
}
