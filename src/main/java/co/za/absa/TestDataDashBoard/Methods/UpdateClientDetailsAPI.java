package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CQcreateAndLinkAccountV9Headers;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CreateClientHeaders;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.SvcreateLinkSavingsNoticeDepaccountv6Headers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

public class UpdateClientDetailsAPI {
    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());

    public String updateClientDetails(String clientCode, String firstNames, String surname, String idNumber
            , String initials, String mobile, String emailAddress) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/v2/update";
        HttpHeaders httpHeaders = new CreateClientHeaders().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> params = new TreeMap<>();
        params.put("clientCode", clientCode);
        //params.put("idDocTypeCode", idType);
        params.put("firstNames", firstNames);
        params.put("surname", surname);
        params.put("idNumber", idNumber);
        params.put("initials", initials);
        params.put("cellphoneNumber", mobile);
        params.put("emailAddress", emailAddress);

        //String preRequest = requests.multipleParams(params, "CreateClient.json");
        String preRequest = requests.getRequest( "UpdateClient.json",params);
        JSONObject jsonObject = new JSONObject(preRequest);
        CurrentDate currentDate = new CurrentDate();

        int gen = Integer.parseInt(idNumber.substring(6, 7));

        if (gen <= 4) {
            //set gender code to female
            jsonObject.getJSONObject("CIB004I").getJSONObject("CIupdateClientDetailsV22Request")
                    .put("titleCode", 2)
                    .put("genderCode", 2);

        } else {
            jsonObject.getJSONObject("CIB004I").getJSONObject("CIupdateClientDetailsV22Request")
                    .put("titleCode", 1)
                    .put("genderCode", 1);
        }

        jsonObject.getJSONObject("CIB004I").getJSONObject("CIupdateClientDetailsV22Request")
                .put("dateIdentified", currentDate.getTodayDate())
                .put("dateOfBirth", getDateOfBirth(idNumber))
                .put("dateVerified", currentDate.getTodayDate());

       // System.out.println("new request " + jsonObject);

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
        //ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

        try {
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

            if (getClientKey(response.getBody()).isEmpty()) {
                return getCreateClientErrorResponse(response.getBody());
            } else return getClientKey(response.getBody());
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }

    }

    private String getClientKey(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject outputClientKey = jsonObject.getJSONObject("body");//.getJSONObject("CIB003O").getJSONObject("CIcreateClientV22Response");

        return outputClientKey.getString("statusCode");
    }

    private String getCreateClientErrorResponse(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);

        JSONArray array = jsonObject.getJSONObject("body").getJSONObject("NBSMSGO3")
                .getJSONObject("nbsmsgo3").getJSONArray("msgEntry");
        JSONObject jsonObjectClientKey = array.getJSONObject(0);

        return jsonObjectClientKey.getString("msgTxt");
    }

    public int getDateOfBirth(String idNumber) {

        String century = idNumber.substring(0, 2);
        String dateOfBirth="";
        int intYear = Integer.parseInt(century);

        if (intYear >= 00 && intYear <= 30) {
            dateOfBirth = "20" + idNumber.substring(0, 6);
        } else if (intYear >= 31 && intYear <= 99) {
            dateOfBirth = "19" + idNumber.substring(0, 6);

        }
     //   System.out.println("ID Number: " + idNumber + " Date of birth: " +dateOfBirth);
        return Integer.parseInt(dateOfBirth);
    }
}
