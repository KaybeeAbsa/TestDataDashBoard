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

public class OverDraft {

    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());
    public String getOverDraftDetails(String accountNo,String enviroment) {

        String url = null;
        HttpHeaders headers;
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb-api-sit.absa.co.za:3443/sit/sb/rbbedb/CQgetOverdraftDetailsV4API/CQgetOverdraftDetailsV4";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSS().configureCIFHeaders();

        }else{

            url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbbedb/CQgetOverdraftDetailsV4API/CQgetOverdraftDetailsV4";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        }

        map.put("CQ478I_ACCOUNT_NBR",accountNo);

        String request = requests.getRequest("CQgetOverdraftDetailsV4.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;

        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return getOverdraft(response.getBody());

        }catch (Exception e)
        {
            e.fillInStackTrace();
            return null;
        }

    }

    public String deleteOverDraft(String accountNo, String accountSite,String enviroment) {

        String url = null;
        HttpHeaders headers;
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb-api-sit.absa.co.za:3443/sit/sb/rbbedb/CQgetOverdraftDetailsV4API/CQgetOverdraftDetailsV4";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSS().configureCIFHeaders();

        }else{

            url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbbedb/CQupdODDeleteDeclineV2API/CQupdODDeleteDeclineV2";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        }

        map.put("CQN433I_ACCOUNT_NBR",accountNo);
        map.put("CQN433I_PROCESSING_SITE",accountSite);

        String request = requests.getRequest("CQupdODDeleteDeclineV2API.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;

        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return getDeleteOverdraft(response.getBody());

        }catch (Exception e)
        {
            e.fillInStackTrace();
            return null;
        }

    }

    private String getOverdraft(String body)  {

        try{
        JSONObject jsonObject = new JSONObject(body);
        JSONObject outputClientKey = jsonObject.getJSONObject("CQS478O").getJSONObject("CQ478O_OUTPUT_AREA");

            return outputClientKey.getString("CQ478O_CURRENT_OVERDRAFT_LIMIT");
        }catch (Exception e)
        {
            return null;
        }
    }

    private String getDeleteOverdraft(String body)  {

        try{
            JSONObject jsonObject = new JSONObject(body);
            JSONObject outputClientKey = jsonObject.getJSONObject("NBSMSGO3").getJSONObject("NBNMSGO3_MSG_ENTRY");

            return outputClientKey.getString("NBNMSGO3_MSG_TXT");
        }catch (Exception e)
        {
            return null;
        }
    }



    public Timer checkHowLongDoesTheAPITake()
    {
        Timer timer = new Timer();
        return timer;
    }
}
