package co.za.absa.TestDataDashBoard.Methods.APIHeaders;

import org.springframework.http.HttpHeaders;

public class preAssessedHeaders {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public preAssessedHeaders() {

        httpHeaders.set("X-IBM-Client-Id", "8a43a7fc-916b-4263-a41d-5fa7555378e1");
        httpHeaders.set("X-IBM-Client-Secret", "J6cI7uC3eR0gM6iN1lH4oC0rV0aP3dN8gE2oK7sF2jJ5uM6gC6");
        httpHeaders.set("X-API-Key", "f07ae6cf-2ce0-4cd6-8696-715139e96fb3");
       // httpHeaders.set("Content-Type", "application/soap+xml; charset=utf-8");
          httpHeaders.set("Content-Type", "text/xml");

    }

    public HttpHeaders configureCIFHeaders() {
        return httpHeaders;

    }
}
