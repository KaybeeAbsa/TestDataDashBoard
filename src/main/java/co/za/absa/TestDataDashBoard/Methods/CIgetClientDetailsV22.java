package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSV;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CompliantStatusHeaders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CIgetClientDetailsV22 {

    private RestTemplate restTemplate = new RestTemplate();

    public String getAOL(String clientKey)
    {
        String url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cigetclientdetailsv22api/CIgetClientDetailsV22";

        HttpHeaders headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        Requests requests = new Requests();

        Map<String,String> map = new HashMap<>();
        map.put("clientCode",clientKey);

        String request = requests.getRequest("CIgetClientDetailsV22.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return isClinetRegisteredForIB(response.getBody());
        }catch (Exception e)
        {
            e.fillInStackTrace();
            return "NO";
        }
    }

    private String isClinetRegisteredForIB(String body) {

        String array = "";
        try {
            JSONObject jsonObject = new JSONObject(body);
            array= jsonObject.getJSONObject("CLSRT1O").getJSONObject("CIgetClientDetailsV22Response").getString("internetBankingInd");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }

        if(array.equals("Y"))
        {
            return "YES";
        }else {
            return "NO";
        }

    }


    public String retrieveClientDetails(String clientKey)
    {
        String url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cigetclientdetailsv22api/CIgetClientDetailsV22";

        HttpHeaders headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        Requests requests = new Requests();

        Map<String,String> map = new HashMap<>();
        map.put("clientCode",clientKey);

        String request = requests.getRequest("CIgetClientDetailsV22.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return clientDetailsResponse(response.getBody());
        }catch (Exception e)
        {
            e.fillInStackTrace();
            return "NO Date";
        }
    }

    public String retrieveClientSite(String clientKey)
    {
        String url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cigetclientdetailsv22api/CIgetClientDetailsV22";

        HttpHeaders headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        Requests requests = new Requests();

        Map<String,String> map = new HashMap<>();
        map.put("clientCode",clientKey);

        String request = requests.getRequest("CIgetClientDetailsV22.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return clientSiteDetails(response.getBody());
        }catch (Exception e)
        {
            e.fillInStackTrace();
            return "NO Site";
        }
    }

    public String retrieveComplentStatus(String clientKey)
    {

        String url = "http://regulatory-compliance-service.cos-uat.cto-dataeng.270-nonprod.caas.absa.co.za/v1/involved-parties-compliance-packs";

        HttpHeaders headers = new CompliantStatusHeaders().configureCIFHeaders();
        Requests requests = new Requests();

        Map<String,String> map = new HashMap<>();
        map.put("clientCode",clientKey);

        String request = requests.getRequest("compliance.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;

        try {

            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

            return clientCompliantResponse(response.getBody());
        }catch (Exception e)
        {
            e.fillInStackTrace();
            return "NO Fic compiant Status";
        }
    }
    private String clientDetailsResponse(String body) {

        String array = "";
        try {
            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("CLSRT1O").getJSONObject("CIgetClientDetailsV22Response").getString("dateClientOpened");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;

    }

    private String clientSiteDetails(String body) {

        String array = "";
        try {
            JSONObject jsonObject = new JSONObject(body);
            System.out.println(jsonObject);
            array = jsonObject.getJSONObject("CLSRT1O").getJSONObject("CIgetClientDetailsV22Response").getString("branchClientOpened");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;

    }
    private String clientCompliantResponse(String body) {

        String array = "";
        try {

            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("content").getJSONObject("complianceRequirements").getString("complianceStatus");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;

    }
}
