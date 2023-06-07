package co.za.absa.TestDataDashBoard.commonMethods;

import co.za.absa.TestDataDashBoard.Methods.Requests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Properties;

public class ResponseFromAPI {

    @Autowired
    private RestTemplate restTemplate=new RestTemplate();
    public String getResponseFromAPI(String api, String requestFile, Map<String,String> params, HttpHeaders httpHeaders)
    {
       Requests requests = new Requests();
       String url =  loadAPI(api);

       String request =  requests.getRequest(requestFile,params);
       HttpEntity<String> entity = new HttpEntity<String>(request, httpHeaders);
        ResponseEntity<String>response;
       try {
           response = restTemplate.exchange(url, HttpMethod.POST,entity,String.class);
       }catch (Exception e)
       {
           e.fillInStackTrace();
           return null;
       }

       return response.getBody();
    }

    public String loadAPI(String url)
    {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/apis/apis.properties"));
        }catch (Exception e)
        {
            e.fillInStackTrace();
        }

        return properties.getProperty(url);
    }
}
