package co.za.absa.TestDataDashBoard.Methods.casa;

import co.za.absa.TestDataDashBoard.commonMethods.ResponseFromAPI;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class StatusByRefNoSA {
    public String ecasaStatus(String refNo)
    {

        HttpHeaders headers  = new HttpHeaders();
        headers.set("Content-type","application/json");
        ResponseFromAPI  responseFromAPI = new ResponseFromAPI();
        Map<String,String> params = new HashMap<>();
        params.put("refNo",refNo);


        String response = responseFromAPI.getResponseFromAPI("StatusByRefNoSA","StatusByRefNoSA.json",params,headers);
        return getEcasaStatus(response);

    }

    private String getEcasaStatus(String response) {
        String eCasaStatus = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            eCasaStatus = jsonObject.getString("status");

        }catch (Exception e)
        {
            e.fillInStackTrace();
        }
        return eCasaStatus;
    }
}
