package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;
public class CIgetAcctLinkedToClientCodeV20APIHeadersIMSS {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public CIgetAcctLinkedToClientCodeV20APIHeadersIMSS() {
        httpHeaders.set("X-IBM-Client-Id","06074a6129b7b56e7cdfc61a218d1110");
        httpHeaders.set("X-IBM-Client-Secret","19fb4495c5b82abfb404c44dd8da8370");
        httpHeaders.set("tellerid","1196035");
        httpHeaders.set("branch","8198");
        httpHeaders.set("applicationID","DATAPORTAL");
        httpHeaders.set("deviceid","22");
        httpHeaders.set("devicetype","22");
        httpHeaders.set("channel","INTERNAL");
        httpHeaders.set("X-API-Key","fb4199fd-6b4e-460b-bec6-9a146356502c");
        httpHeaders.set("userid","ABKS580");
        httpHeaders.set("Content-type","application/json");
    }


    public HttpHeaders configureCIFHeaders()
    {
        return httpHeaders;
    }
}
