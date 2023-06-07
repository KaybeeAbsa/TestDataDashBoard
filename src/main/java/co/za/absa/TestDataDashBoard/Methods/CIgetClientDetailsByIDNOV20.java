package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSS;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetClientDetailsByIDNOV20HeadersIMSV;
import co.za.absa.TestDataDashBoard.model.Existing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CIgetClientDetailsByIDNOV20 {


    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());
    public String[] getClientDetailsById(String idNumber,String clientCode,String clientType,String enviroment) {
        String url = null;
        HttpHeaders headers;//= new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb-api-sit.absa.co.za:3443/sit/sb/rbb/cigetclientdetailsbyidnov20api/CIgetClientDetailsByIDNOV20";
            headers = new CIgetClientDetailsByIDNOV20HeadersIMSS().configureCIFHeaders();

        }else{

             url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cigetclientdetailsbyidnov20api/CIgetClientDetailsByIDNOV20";
             headers = new CIgetClientDetailsByIDNOV20HeadersIMSV().configureCIFHeaders();
        }

       // System.out.println("di number: " + idNumber + " Client Code: " + clientCode + " Client TYPE: "+ clientType);
        map.put("idNumber",idNumber);
        map.put("idDocumentType",clientCode);
        map.put("cifClientGroup",clientType);
        String request = requests.getRequest("CIgetClientDetailsByIDNOV20.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
            return getCleintKey(response.getBody());

        }catch (Exception e)
        {
            e.fillInStackTrace();
            String clientKey[] = new String[1];
            clientKey[0] = null;
            return clientKey;
        }

    }

    private String[] getCleintKey(String body)  {
        String clientKey[] = new String[3];
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray array = jsonObject.getJSONObject("CLSSIDO").getJSONObject("outputCopybookClssido").getJSONObject("outputTableEntries").getJSONArray("tableEntry");

            for(int i = 0;i<array.length();i++)
            {
                JSONObject jsonObjectClientKey = array.getJSONObject(i);
                String temp = jsonObjectClientKey.getString("clientKey");
                String temp1 = jsonObjectClientKey.getString("clientSurname");
                String temp2 = jsonObjectClientKey.getString("clientInitials");

                clientKey[0] = temp;
                clientKey[1] = temp1;
                clientKey[2] = temp2;
                break;
            }

            return clientKey;
        }catch (Exception e)
        {
            clientKey[0] = null;
            return clientKey;
        }
    }


    public Timer checkHowLongDoesTheAPITake()
    {
        Timer timer = new Timer();
        return timer;
    }
}
