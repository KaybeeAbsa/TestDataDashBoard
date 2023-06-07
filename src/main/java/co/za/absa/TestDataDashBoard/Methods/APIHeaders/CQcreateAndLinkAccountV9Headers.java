package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class CQcreateAndLinkAccountV9Headers {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public CQcreateAndLinkAccountV9Headers() {

        httpHeaders.set("X-IBM-Client-Id", "27de9b02-38be-4185-91c1-2767e3940e61");
        httpHeaders.set("X-IBM-Client-Secret", "lR8eG7kP2xX1mT7uS0pT1cE1uR5eJ5jO4cR2aX2xL8lH5eX2oX");
        httpHeaders.set("userid", "ABKS580");
        httpHeaders.set("applicationID", "DATAPORTAL");
        httpHeaders.set("deviceid", "22");
        httpHeaders.set("userid", "ABKS580");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Content-Type", "application/json");


    }

    public HttpHeaders configureCIFHeaders()
    {
        return httpHeaders;
    }

}
