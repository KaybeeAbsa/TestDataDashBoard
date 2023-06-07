package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class Policy_CreateNewPolicyHeaders {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public Policy_CreateNewPolicyHeaders() {

    }


    public HttpHeaders configureCIFHeaders()
    {
        return httpHeaders;
    }
}




