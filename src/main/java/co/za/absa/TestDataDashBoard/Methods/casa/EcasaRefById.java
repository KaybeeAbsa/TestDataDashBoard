package co.za.absa.TestDataDashBoard.Methods.casa;

import co.za.absa.TestDataDashBoard.commonMethods.ResponseFromAPI;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class EcasaRefById {
    public String ecasaRefById(String idNumber)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type","application/json");
        ResponseFromAPI responseFromAPI = new ResponseFromAPI();
        Map<String,String> params = new HashMap<>();
        params.put("idNumber",idNumber);


        String response = responseFromAPI.getResponseFromAPI("getcasabyid","getcasabyid.json",params,headers);
        return getEcasaRef(response);

    }

    private String getEcasaRef(String response) {

        String eCasaRef = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            eCasaRef = jsonObject.getJSONArray("results").getJSONObject(0).getString("refNo");

        }catch (Exception e)
        {
            e.fillInStackTrace();
        }
        return eCasaRef;
    }
}
