package co.za.absa.TestDataDashBoard.Methods.APIHeaders;



import org.springframework.http.HttpHeaders;

public class LoginHeaders {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public LoginHeaders() {
        httpHeaders.set("Content-type","application/json");
    }


    public HttpHeaders configureLoginHeaders()
    {
        return httpHeaders;
    }
}

