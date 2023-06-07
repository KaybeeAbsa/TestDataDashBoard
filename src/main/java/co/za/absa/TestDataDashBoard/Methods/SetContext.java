package co.za.absa.TestDataDashBoard.Methods;

import java.util.Map;

public class SetContext {

    public String setParametizedValues(String request, Map<String,String> params) {

        for (Map.Entry<String,String> param:params.entrySet()) {
            request = request.replace("${"+param.getKey()+"}",param.getValue());
        }
        return request;
    }


}
