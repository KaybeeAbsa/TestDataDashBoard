package co.za.absa.TestDataDashBoard.Methods.casa;

import co.za.absa.TestDataDashBoard.commonMethods.ResponseFromAPI;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class ScreenCustomer {

    public String screenACustomer(String name,String surname,String idNumber)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type","application/json");
        ResponseFromAPI responseFromAPI = new ResponseFromAPI();
        Map<String,String> params = new HashMap<>();
        params.put("firstName",name);
        params.put("surname",surname);
        params.put("identificationNumber",idNumber);

        String response = responseFromAPI.getResponseFromAPI("ScreenCustomer","ScreenCustomer.json",params,headers);
        return getEcasaRefNo(response);

    }

    private String getEcasaRefNo(String response) {

        String eCasaRefNo = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            eCasaRefNo = jsonObject.getString("eCASARefNo");

        }catch (Exception e)
        {
            e.fillInStackTrace();
        }
        return eCasaRefNo;
    }
}
