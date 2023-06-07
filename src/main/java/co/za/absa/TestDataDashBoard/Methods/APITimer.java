package co.za.absa.TestDataDashBoard.Methods;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class APITimer {

    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(7000);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(7000);
        return clientHttpRequestFactory;
    }

}
