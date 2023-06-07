package co.za.absa.TestDataDashBoard.Methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map;
import java.util.TreeMap;

public class Requests {
    public String getRequest(String filename, Map<String,String> params)
    {

        StringBuilder request=new StringBuilder();
        SetContext setContext = new SetContext();
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("requests/"+filename);
        try (InputStreamReader isr = new InputStreamReader(ioStream);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while ((line = br.readLine()) != null) {
                request.append(line);
            }
            isr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return setContext.setParametizedValues(request.toString(),params);
    }


}
