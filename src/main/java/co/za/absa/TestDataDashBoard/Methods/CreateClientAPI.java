package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CQcreateAndLinkAccountV9Headers;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CreateBusinessClientHeaders;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.SvcreateLinkSavingsNoticeDepaccountv6Headers;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CreateClientHeaders;
import co.za.absa.TestDataDashBoard.model.Newtobank;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class CreateClientAPI {

    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());

    public String createClient(String idType, String firstNames, String surname, String idNumber, String clientTypeCode,
                               String emailAddress, String initials, String mobile, String passport, String passportCountry) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/v2/create";
        HttpHeaders httpHeaders = new CreateClientHeaders().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> params = new TreeMap<>();
        params.put("idDocTypeCode", idType);
        params.put("firstNames", firstNames);
        params.put("surname", surname);
        if (passport.length() == 0){
            params.put("idNumber", idNumber);
        }else{
            params.put("idNumber", passport);
        }
        params.put("clientTypeCode", clientTypeCode);
        params.put("emailAddress", emailAddress);
        params.put("initials", initials);
        params.put("cellphoneNumber", mobile);
        params.put("countryPassportIssuedCode", passportCountry);
        //String preRequest = requests.multipleParams(params, "CreateClient.json");
        String preRequest = requests.getRequest( "CreateClient.json",params);
        JSONObject jsonObject = new JSONObject(preRequest);
        CurrentDate currentDate = new CurrentDate();

        int gen = Integer.parseInt(idNumber.substring(6, 7));

        if (gen <= 4) {
            //set gender code to female
            jsonObject.getJSONObject("CIB003I").getJSONObject("CIcreateClientV22Request")
                    .put("titleCode", 2)
                    .put("genderCode", 2);

        } else {
            jsonObject.getJSONObject("CIB003I").getJSONObject("CIcreateClientV22Request")
                    .put("titleCode", 1)
                    .put("genderCode", 1);
        }

        jsonObject.getJSONObject("CIB003I").getJSONObject("CIcreateClientV22Request")
                .put("dateIdentified", currentDate.getTodayDate())
                .put("dateOfBirth", getDateOfBirth(idNumber))
                .put("dateVerified", currentDate.getTodayDate());

       // System.out.println("new request " + jsonObject);

        try {
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

            if (getClientKey(response.getBody()).isEmpty()) {
                return getCreateClientErrorResponse(response.getBody());
            } else return getClientKey(response.getBody());
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }

    }


    public String createBusinessClients(String idType, String firstNames, String surname, String idNumber, String clientTypeCode, String emailAddress, String initials, String mobile) throws JSONException {

        System.out.println(idType + " : " + surname + " : " + idNumber + " : " + clientTypeCode + " : " + emailAddress + " : " + mobile);

        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cicreateclientv22api/CIcreateClientV22";
        HttpHeaders httpHeaders = new CreateBusinessClientHeaders().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> params = new TreeMap<>();
        params.put("idDocTypeCode", idType);
        //params.put("firstNames", firstNames);
        params.put("surname", surname);
        params.put("idNumber", idNumber);
        params.put("clientTypeCode", clientTypeCode);
        params.put("emailAddress", emailAddress);
       //params.put("initials", initials);
        mobile = mobile.substring(1,mobile.length());
        //System.out.println("Mobile: " + mobile);
        params.put("cellphoneNumber", mobile);

        String preRequest = requests.getRequest( "CreateBusinessClient.json",params);
        JSONObject jsonObject = new JSONObject(preRequest);
        CurrentDate currentDate = new CurrentDate();
        jsonObject.getJSONObject("CIB003I").getJSONObject("CIcreateClientV22Request")
                .put("dateIdentified", currentDate.getTodayDate())
                .put("dateVerified", currentDate.getTodayDate());

        try {
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
            // HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

            if (getBusinessClientKey(response.getBody()).isEmpty()) {
                return getBusinessCreateClientErrorResponse(response.getBody());
            } else return getBusinessClientKey(response.getBody());
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }


    public String CQCreateAndLinkAccountV9(String cifKey, String product,String sourceFund) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cqcreateandlinkaccountv9api/CQcreateAndLinkAccountV9";
        HttpHeaders httpHeaders = new CQcreateAndLinkAccountV9Headers().configureCIFHeaders();
        CurrentDate currentDate = new CurrentDate();
        Requests requests = new Requests();
       // Map<String, String> params = requests.getParameters(cifKey, product);

        Map<String, String> params = new TreeMap<>();
        params.put("cifKey", cifKey);
        params.put("product", product);
        params.put("SourceOfFunds1", sourceFund);
      //  String preRequest = requests.multipleParams(params, "CQcreateAndLinkAccountV9.json");
        String preRequest = requests.getRequest("CQcreateAndLinkAccountV9.json",params);

        JSONObject jsonObject = new JSONObject(preRequest);

        jsonObject.getJSONObject("CQS411I").getJSONObject("CQN411I_INPUT_AREA")
                .put("cifKey", cifKey)
                .put("product", product)
                .put("effectiveDate", currentDate.getTodayDate())
                .put("SourceOfFunds1", sourceFund);

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

       // System.out.println("account number: " + getChequeAccountNumber(response.getBody()));
     //   System.out.println("error: " + getChequeCreateAndLinkAccountErrorResponse(response.getBody()));

        if (getChequeAccountNumber(response.getBody()).equalsIgnoreCase("0")) {
            return getChequeCreateAndLinkAccountErrorResponse(response.getBody());
        }
        return getChequeAccountNumber(response.getBody());
    }

    public String svcreatelinksavingsnoticedepaccountv6(String cifKey, String product, String sourceFund) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/svcreatelinksavingsnoticedepaccountv6api/svcreatelinksavingsnoticedepaccountv6";
        HttpHeaders httpHeaders = new SvcreateLinkSavingsNoticeDepaccountv6Headers().configureCIFHeaders();
        CurrentDate currentDate = new CurrentDate();
        Requests requests = new Requests();
        // Map<String, String> params = requests.getParameters(cifKey, product);

        Map<String, String> params = new TreeMap<>();
        params.put("cifKey", cifKey);
        params.put("product", product);
        params.put("sourceOfFunds1", sourceFund);
        String preRequest = requests.getRequest("svcreatelinksavingsnoticedepaccountv6.json",params);

        JSONObject jsonObject = new JSONObject(preRequest);

        jsonObject.getJSONObject("SVSP15I").getJSONObject("SVNP15I_INPUT_AREA")
                .put("cifKey", cifKey)
                .put("product", product)
                .put("effectiveDate", currentDate.getTodayDate())
                .put("sourceOfFunds1", sourceFund);

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

      //  System.out.println("account number: " + getSavingsAccountNumber(response.getBody()));
        //System.out.println("error: " + getSavingsCreateAndLinkAccountErrorResponse(response.getBody()));

        if (getSavingsAccountNumber(response.getBody()).equalsIgnoreCase("0")) {
            return getSavingsCreateAndLinkAccountErrorResponse(response.getBody());
        }
        return getSavingsAccountNumber(response.getBody());
    }

    private String getClientKey(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject outputClientKey = jsonObject.getJSONObject("body").getJSONObject("CIB003O").getJSONObject("CIcreateClientV22Response");

        return outputClientKey.getString("outputClientKey");
    }

    private String getBusinessClientKey(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject outputClientKey = jsonObject.getJSONObject("CIB003O").getJSONObject("CIcreateClientV22Response");

        return outputClientKey.getString("outputClientKey");
    }

    private String getCreateClientErrorResponse(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONArray array = jsonObject.getJSONObject("body").getJSONObject("NBSMSGO3")
                .getJSONObject("nbsmsgo3").getJSONArray("msgEntry");
        JSONObject jsonObjectClientKey = array.getJSONObject(0);

        return jsonObjectClientKey.getString("msgTxt");
    }

    private String getBusinessCreateClientErrorResponse(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONArray array = jsonObject.getJSONObject("NBSMSGO3")
                .getJSONObject("nbsmsgo3").getJSONArray("msgEntry");
        JSONObject jsonObjectClientKey = array.getJSONObject(0);

        return jsonObjectClientKey.getString("msgTxt");
    }

    private String getChequeCreateAndLinkAccountErrorResponse(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONArray array = jsonObject.getJSONObject("MSGO")
                .getJSONObject("NBSMSGO3").getJSONArray("NBNMSGO3_MSG_ENTRY");
        JSONObject errorMsg = array.getJSONObject(0);

        return errorMsg.getString("NBNMSGO3_MSG_TXT");
    }

    private String getSavingsCreateAndLinkAccountErrorResponse(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONArray array = jsonObject.getJSONObject("MSGO")
                .getJSONObject("NBSMSGO3").getJSONArray("messageEntry");
        JSONObject errorMsg = array.getJSONObject(0);

        return errorMsg.getString("messageText");
    }

    private String getChequeAccountNumber(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject accountNo = jsonObject.getJSONObject("CQS411O").getJSONObject("outputErrorMessage");

        return accountNo.getString("AccountNbrOut");
    }

    private String getSavingsAccountNumber(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject accountNo = jsonObject.getJSONObject("SVSP15O").getJSONObject("SVNP15O_OUTPUT_AREA");

        return accountNo.getString("AccountNumber");
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
        System.out.println("ID Number: " + idNumber + " Date of birth: " +dateOfBirth);
        return Integer.parseInt(dateOfBirth);
    }

}
