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
        //    System.out.println("Inside first catch..");
            e.fillInStackTrace();
            return "Fic Not Added";
        }

    }

    private String ficResponse(String body) {

        String array = "";
        try {

            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("CIupdFicLockResponse").getJSONObject("CIupdFicLockResponse").getString("returnCode");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;

    }

    public String checkFicStatus(String clientCode,String enviroment) {
        String url = null;
        HttpHeaders headers;
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/iigetficalistingdetailsv20api/IIgetFICAListingDetailsV20API";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSS().configureCIFHeaders();

        }else{

            url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/iigetficalistingdetailsv20api/IIgetFICAListingDetailsV20API";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        }

        System.out.println("ClientCode: " + clientCode );
        map.put("clientCode",clientCode);

        String request = requests.getRequest("getFICAListingDetailsV20API.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

            return ficStatusResponse(response.getBody());
        }catch (Exception e)
        {
            //    System.out.println("Inside first catch..");
            e.fillInStackTrace();
            return "Fic Not Added";
        }

    }

    private String ficStatusResponse(String body) {


        String array = "";
        try {

            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("outputCopybook").getJSONObject("outputArea").getString("FCAL1Reason");

        }catch (Exception e)
        {
            e.fillInStackTrace();
            array="";
        }
        return array;
    }

    public String uploadDocuments(String clientCode,String documentType,String idNumber) {
        String url = null;
        HttpHeaders headers;
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        url = "http://uat-cis.sdc.uat.intra.absa.co.za/UAT.CIS/contentstore/api/V1/CIS/Add";

        headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();

        map.put("clientCode",clientCode);
        map.put("documentType",documentType);
        map.put("idNumber",idNumber);

        String request = requests.getRequest("UploadClientDocument.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

            return uploadDocumentsResponse(response.getBody());
        }catch (Exception e)
        {
            //    System.out.println("Inside first catch..");
            e.fillInStackTrace();
            return "Document Not Uploaded";
        }
    }

    private String uploadDocumentsResponse(String body) {

        String array = "";
        try {

            JSONObject jsonObject = new JSONObject(body);
            array = jsonObject.getJSONObject("AddResult").getString("Status");

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
