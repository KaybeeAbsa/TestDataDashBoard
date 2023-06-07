package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class CompliantStatusHeaders {

        private HttpHeaders httpHeaders = new HttpHeaders();

        public CompliantStatusHeaders() {
           /* httpHeaders.set("X-IBM-Client-Id","36650eb3-92dc-40fd-9842-3fc91393e711");
            httpHeaders.set("X-IBM-Client-Secret","dJ8dF8mQ0tC6kM3uH8qO8kC5wB8kK7dG2eK3bC2vB7iY6oJ3yX");
            httpHeaders.set("tellerid","1196035");
            httpHeaders.set("branch","8198");
            httpHeaders.set("applicationID","ABBOT");
            httpHeaders.set("deviceid","22");
            httpHeaders.set("devicetype","22");
            httpHeaders.set("channel","INTERNAL");
            httpHeaders.set("X-API-Key","fb4199fd-6b4e-460b-bec6-9a146356502c");
            httpHeaders.set("userid","ABKS580");*/
            httpHeaders.set("Content-type","application/json");

        }

        public HttpHeaders configureCIFHeaders()
        {
            return httpHeaders;
        }

}
