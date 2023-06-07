package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CCDebitCardIssuanceV1Headers;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetAcctLinkedToClientCodeV20APIHeadersIMSV;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CCDebitCardIssuanceV1 {

    private RestTemplate restTemplate = new RestTemplate();


    public String getCombies(String account, String clientCode, String brand, String productCode,String name) throws JSONException {
        String url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbbedb/ccdebitcardissuancev1api/CCDebitCardIssuanceV1";

        HttpHeaders httpHeaders = new CCDebitCardIssuanceV1Headers().configureCIFHeaders();

        HttpHeaders headers = new CIgetAcctLinkedToClientCodeV20APIHeadersIMSV().configureCIFHeaders();

        Requests requests = new Requests();
        Map<String,String> map = new HashMap<>();
        map.put("account",account);
        map.put("clientCode",clientCode);
        map.put("brand",brand);
        map.put("productCode",productCode);
        map.put("name",name);
        String request =requests.getRequest("CCDebitCardIssuanceV1.json",map);

        HttpEntity<String> httpEntity = new HttpEntity<>(request,httpHeaders);


        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);

        System.out.println("response:  "+getCreateClientKey(response.getBody()));
        return
                getCreateClientKey(response.getBody());
        //return response.getBody();

    }


    private String getCombiCardKey(String body)  {
        String combiKey;
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray array = jsonObject.getJSONObject("CCS884O").getJSONObject("outputArea")
                    .getJSONArray("CCS884O_OUT_COMBI_NBR");
            JSONObject jsonObjectCombiKey = array.getJSONObject(0);
            //combiKey = jsonObjectCombiKey.getString("CCS884O_OUT_COMBI_NBR");

            System.out.println("combi:   "+jsonObjectCombiKey);
            return jsonObjectCombiKey.getString("");
        }catch (Exception e)
        {
            return null;
        }
    }



    private String getCreateClientKey(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        long array = jsonObject.getJSONObject("CCS884O").getJSONObject("outputArea").getJSONArray("CCS884O_OUT_COMBI_NBR").getLong(0);
        return String.valueOf(array);
    }
}