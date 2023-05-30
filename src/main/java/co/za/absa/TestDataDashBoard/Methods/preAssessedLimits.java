package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.InitiateMandatesHeader;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.preAssessedHeaders;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class preAssessedLimits {

    APITimer apiTimer = new APITimer();
    private RestTemplate restTemplate = new RestTemplate(apiTimer.getClientHttpRequestFactory());
    public void prePropulation(String clientKey, String netSalary1, String netSalary2, String netSalary3, String salaryAverage, String grossSalary, String netSalary) throws JSONException, ParseException {
        String endpoint = "http://xzaobcc2web0406:57130/AffordabilityPrepopulationService/AffordabilityPrepopulationService.asmx";
        HttpHeaders httpHeaders = new preAssessedHeaders().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> xmlMap = new TreeMap<>();
        xmlMap.put("cifKey", clientKey);
        xmlMap.put("NetSalaryIncomeMonth1", netSalary1);
        xmlMap.put("NetSalaryIncomeMonth2", netSalary2);
        xmlMap.put("NetSalaryIncomeMonth3", netSalary3);
        xmlMap.put("NetSalaryIncomeAverage", salaryAverage);
        xmlMap.put("GrossIncome", grossSalary);
        xmlMap.put("NettIncome", netSalary);

        String request = requests.getRequest("affordabilityprepopulationservice.xml", xmlMap);

        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);
    }

    public String verifyPreAssessedLimits(String clientKey) throws Exception {
        String endpoint = "http://xzaobcc2web0406:57130/AffordabilityPrepopulationService/AffordabilityPrepopulationService.asmx";
        HttpHeaders httpHeaders = new preAssessedHeaders().configureCIFHeaders();

        Requests requests = new Requests();

        Map<String, String> xmlMap = new TreeMap<>();
        xmlMap.put("cifKey", clientKey);

        String request = requests.getRequest("retrievePrepopulationsvalues.xml", xmlMap);

        HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

        return response.toString();
    }


    private String getPressAssessed(String body) throws JSONException {

        JSONObject jsonObject = new JSONObject(body);
        JSONObject pressed = jsonObject.getJSONObject("RetrieveIncomeCalculationResponse").getJSONObject("RetrieveIncomeCalculationResult");

        return pressed.getString("NetSalaryIncomeMonth1");
    }



}
