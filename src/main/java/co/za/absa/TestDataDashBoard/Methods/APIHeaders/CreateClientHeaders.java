package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class CreateClientHeaders {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public CreateClientHeaders() {

        httpHeaders.set("X-IBM-Client-Id", "27de9b02-38be-4185-91c1-2767e3940e61");
        httpHeaders.set("X-IBM-Client-Secret", "lR8eG7kP2xX1mT7uS0pT1cE1uR5eJ5jO4cR2aX2xL8lH5eX2oX");
        httpHeaders.set("X-API-Key", "fb4199fd-6b4e-460b-bec6-9a146356502c");
        httpHeaders.set("tellerid", "1196035");
        httpHeaders.set("branch", "8198");
        httpHeaders.set("applicationID", "SSD");
        httpHeaders.set("deviceid", "22");
        httpHeaders.set("devicetype", "22");
        httpHeaders.set("channel", "DATAPORTAL");
        httpHeaders.set("userid", "ABKS580");
        httpHeaders.set("req", "888");
        httpHeaders.set("clientGroup", "individual");
        httpHeaders.set("Content-Type", "Application/json");
    }



    public HttpHeaders configureCIFHeaders()
    {
        return httpHeaders;
    }
}
