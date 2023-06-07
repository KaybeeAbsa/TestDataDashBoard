package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class CreateBusinessClientHeaders {
    private HttpHeaders httpHeader = new HttpHeaders();

    public CreateBusinessClientHeaders() {

        httpHeader.set("X-IBM-Client-Id", "27de9b02-38be-4185-91c1-2767e3940e61");
        httpHeader.set("X-IBM-Client-Secret", "lR8eG7kP2xX1mT7uS0pT1cE1uR5eJ5jO4cR2aX2xL8lH5eX2oX");
        httpHeader.set("X-API-Key", "fb4199fd-6b4e-460b-bec6-9a146356502c");
        httpHeader.set("tellerid", "1196035");
        httpHeader.set("branch", "8198");
        httpHeader.set("applicationID", "SSD");
        httpHeader.set("deviceid", "22");
        httpHeader.set("devicetype", "22");
        httpHeader.set("channel", "DATAPORTAL");
        httpHeader.set("userid", "ABKS580");
        httpHeader.set("req", "888");
        httpHeader.set("clientGroup", "individual");
        httpHeader.set("Content-Type", "application/json; charset=UTF-8");
    }



    public HttpHeaders configureCIFHeaders()
    {
        return httpHeader;
    }
}
