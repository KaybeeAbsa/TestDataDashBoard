package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.InitiateMandatesHeader;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.preAssessedHeaders;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class InitiateMandates {

    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());

    public String initiate(String accountNumber, String IDNumber, String accountType, String colltnAmt) throws JSONException, ParseException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/cms/v1.0/payments/creditor/ac/mandates/initiations";
        HttpHeaders httpHeaders = new InitiateMandatesHeader().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> xmlMap = new TreeMap<>();
        xmlMap.put("accountNumber", accountNumber);
        xmlMap.put("accountType", accountType);
        xmlMap.put("MsgId", getMsgId());
        xmlMap.put("FrDt", getFromDt());
        xmlMap.put("CreDtTm", getCreDtTm());
        xmlMap.put("CllnStDt", String.valueOf(getCllnStDt()));
        xmlMap.put("DebtorID", IDNumber);
        xmlMap.put("MndtId", getAlphaNumericString(10));
        xmlMap.put("MndtReqId", getAlphaNumericString(14));
      //  xmlMap.put("ColltnAmt", colltnAmt + ".00");

        if (colltnAmt.contains(".")) {
            xmlMap.put("ColltnAmt", colltnAmt);
        } else {
            xmlMap.put("ColltnAmt", colltnAmt + ".00");
        }

        String request = requests.getRequest("InitiateMandate.xml", xmlMap);

        System.out.println("my request" + request);
        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

        System.out.println("initiate: " + getMRTI(response.getBody()));
        return getMRTI(response.getBody());

    }

    public String authoriseMandate(String mandateRequestTransactionId, String accountNumber, String userName) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/mandates/v1.0/payments/debtor/ac/mandates/initiations/" + mandateRequestTransactionId;
        HttpHeaders httpHeaders = new InitiateMandatesHeader().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> xmlMap = new TreeMap<>();
        xmlMap.put("mandateRequestTransactionId", mandateRequestTransactionId);
        xmlMap.put("accountNumber", accountNumber);
        xmlMap.put("userName", userName);

        String request = requests.getRequest("AuthoriseMandate.xml", xmlMap);

        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

        if (getStatus(response.getBody()).equalsIgnoreCase("ACCEPTED")) {
            System.out.println("mandate successfully loaded on account");
        } else {
            System.out.println("authorise: " + getStatus(response.getBody()));
        }
        return getStatus(response.getBody());

    }

    public String statusReports(String mandateRequestTransactionId) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/cms/v1.0/payments/creditor/ac/mandates/initiations/statusReports/" + mandateRequestTransactionId;
        HttpHeaders httpHeaders = new InitiateMandatesHeader().configureCIFHeaders();

        String request = "";

        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);

        if (getStatus(response.getBody()).equalsIgnoreCase("ACCEPTED")) {
            System.out.println("mandate successfully loaded on account");
        } else {

        }
        System.out.println("status report: " + getStatus(response.getBody()));
        return getStatus(response.getBody());

    }

    public String acceptanceReports(String mandateRequestTransactionId) throws JSONException {
        String endpoint = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/cms/v1.0/payments/creditor/ac/mandates/initiations/acceptanceReports/" + mandateRequestTransactionId;
        HttpHeaders httpHeaders = new InitiateMandatesHeader().configureCIFHeaders();

        String request = "";

        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

        if (MndtRfNbr(response.getBody()).equalsIgnoreCase("OK")) {
            System.out.println("mnadate ref number generated");
        }
        System.out.println("acceptance: " + MndtRfNbr(response.getBody()));
        return MndtRfNbr(response.getBody());

    }

    private String getErrStatusReport(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);

        JSONObject state = jsonObject.getJSONObject("data").getJSONArray("data").getJSONObject(0).getJSONObject("cstmrPmtStsRpt")
                .getJSONArray("orgnlPmtInfAndSts").getJSONObject(0).getJSONArray("txInfAndSts").getJSONObject(0).getJSONArray("stsRsnInf").getJSONObject(0).getJSONArray("addtlInf").getJSONObject(0);


        return state.getString("AddtlInf");
    }

    private String getStatus(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject state = jsonObject.getJSONObject("metadata");

        return state.getString("status");
    }

    private String MndtRfNbr(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        String mndtRfNbr = "";

        JSONObject state = jsonObject.getJSONObject("data").getJSONObject("data")
                .getJSONObject("mndtAccptncRpt").getJSONArray("undrlygAccptncDtls").getJSONObject(0)
                .getJSONObject("splmtryData").getJSONObject("envlp").getJSONObject("cnts");

        mndtRfNbr = state.getString("mndtRfNbr");

        if (mndtRfNbr == null) {

        }
        return state.getString("mndtRfNbr");
    }

    private String getMRTI(String body) throws JSONException {
        JSONObject jsonObject = new JSONObject(body);
        JSONObject mrti = jsonObject.getJSONObject("data");

        return mrti.getString("mandateRequestTransactionId");
    }

    private String getCreDtTm() {
        LocalDateTime myObj = LocalDateTime.now();
        return String.valueOf(myObj);

    }


    public String getCllnStDt() {

        LocalDateTime now = LocalDateTime.now();

        // create instance of the SimpleDateFormat that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //create instance of the Calendar class and set the date to the given date
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(String.valueOf(now)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // use add() method to add the days to the given date
        cal.add(Calendar.DAY_OF_MONTH, 3);
        String dateAfter = sdf.format(cal.getTime());


        return dateAfter;

    }

    public String getFromDt() throws ParseException {

        // create instance of the SimpleDateFormat that matches the given date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);

        return strDate;

    }

    public String getMsgId() {
        String msgID = "A0130/ABSACC/";
        String newMsgID = msgID + getTodayDate() + "08/" + getAlphaNumericString(8);

        return newMsgID;
    }

    public int getTodayDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDateTime now = LocalDateTime.now();
        return Integer.parseInt((dtf.format(now)));
    }

    public String getAlphaNumericString(int n) {
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString().toUpperCase();
    }


}
