package co.za.absa.TestDataDashBoard.Methods;

import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetAcctLinkedToClientCodeV20APIHeadersIMSS;
import co.za.absa.TestDataDashBoard.Methods.APIHeaders.CIgetAcctLinkedToClientCodeV20APIHeadersIMSV;
import co.za.absa.TestDataDashBoard.model.Existing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class CIgetAcctLinkedToClientCodeV20API {

    private RestTemplate restTemplate = new RestTemplate();
    public Existing getClientDetailsLinkedClientCode(String[] clientKey, Existing existings, String reProduct, List<String> linked, String reCombi,String enviroment)  {

        String url = null;

        HttpHeaders headers;

        Requests requests = new Requests();

        Map<String,String> map = new HashMap<>();
        String[]  clientKeys = clientKey;

        if(enviroment.equalsIgnoreCase("IMSS")){

            url = "https://esb-api-sit.absa.co.za:3443/sit/sb/rbb/cigetacctlinkedtoclientcodev20api/CIgetAcctLinkedToClientCodeV20API";
            headers = new CIgetAcctLinkedToClientCodeV20APIHeadersIMSS().configureCIFHeaders();

        }else{
            url = "https://esb.api.uat.absa.co.za:1001/enterprise-uat/uat/rbb/cigetacctlinkedtoclientcodev20api/CIgetAcctLinkedToClientCodeV20API";
            headers = new CIgetAcctLinkedToClientCodeV20APIHeadersIMSV().configureCIFHeaders();
        }

        map.put("clientKey",clientKey[0]);
        String request = requests.getRequest("CIgetAcctLinkedToClientCodeV20API.json",map);

        HttpEntity<String> entity = new HttpEntity<String>(request, headers);

        ResponseEntity<String> response;
        try {
            response=  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

            JSONObject jsonObject = new JSONObject(response.getBody());

            Existing existing = new Existing();

            JSONArray array = jsonObject.getJSONObject("CIgetAcctLinkedToClientCodeV20Response").getJSONObject("outputArea").getJSONArray("outputTable");

            String account = mapAccountLinkedToClientCodes(array,reProduct,linked,reCombi);

            if(account.length()!=0 )
            {
                CIgetClientDetailsV22 cIgetClientDetailsV22 = new CIgetClientDetailsV22();
                existing.setClientCode(clientKey[0]);
                existing.setAccountNo(account);
                existing.setIdtype(existings.getIdtype());
                existing.setName(clientKey[2]);
                existing.setSurname(clientKey[1]);
                existing.setSecondname(existings.getSecondname());
                existing.setScore(existings.getScore());
                existing.setUsed(existings.getUsed());
                existing.setBureauType(existings.getBureauType());
                existing.setAol(cIgetClientDetailsV22.getAOL(clientKey[0]));

            }

            return existing;

        }catch (Exception e)
        {
            e.fillInStackTrace();
            return null;
        }

    }

    public String mapAccountLinkedToClientCodes(JSONArray array, String reProduct, List<String> linked, String reCombi) {

        Map<String,String> map = new HashMap<>();
        linked=new ArrayList<>(new LinkedHashSet<>(linked));
        linked.add(reProduct);

        if(linked.size()>0)
        {
            linked = mapProducts(linked);
        }

        String account = "[";
        String response = array.toString();
        for (int i = 0; i < array.length(); i++) {

            try {

                JSONObject jsonObject = array.getJSONObject(i);
                String status = jsonObject.getString("status");
                String product = jsonObject.getString("product");
                String productType = jsonObject.getString("productType");
                String accountNumber = jsonObject.getString("oAccountNumber");

                if(accountNumber.isEmpty())
                {
                    continue;
                }

                if(status.contains("CURRENT") || status.contains("ACTIVE") || status.contains("W/DRAWN") || status.contains("OPEN") || status.contains("ALLOCATE"))
                {
                    if(reCombi.equalsIgnoreCase("Yes"))
                    {
                        if(!response.contains("COMBI"))
                        {
                            break;
                        }else if(productType.equalsIgnoreCase("CO"))
                        {
                            account+=accountNumber+"  "+product+",";

                        }

                    }else{
                        map.put(accountNumber,productType);
                        account+=accountNumber+"  "+product+",";
                    }

                    if(linked.contains(productType))
                    {
                        map.put(accountNumber,productType);
                         account+=accountNumber+"  "+product+",";
                         continue;
                    }


                }

            }catch (Exception e)
            {
                e.fillInStackTrace();
            }

        }


        boolean isAllAcoountRetrieved = checkIfAllAccountsAreRetrieved(map,linked);

        if(!isAllAcoountRetrieved)
        {
            account="00";
        }

        return account.substring(0,account.length()-1)+"]";
    }

    private boolean checkIfAllAccountsAreRetrieved(Map<String, String> map, List<String> linked) {

        boolean filter = false;

        for (String s:linked ) {
            if(map.containsValue(s))
            {
                filter=true;
            }else
            {
                filter=false;
                break;
            }
        }

        return filter;
    }

    private List<String> mapProducts(List<String> linked) {

        for (int i = 0;i<linked.size();i++ ) {
            switch (linked.get(i))
            {
                case "ExCheque":
                case "ExChequeNewToProduct":
                case "LinkedCheque":
                    linked.set(i,"CQ");
                    break;
                case "ExSavings":
                case "ExSavingsNewToProduct":
                case "LinkedSavings":
                    linked.set(i,"SA");
                    break;
                case "ExPL":
                case "ExPLNewToProduct":
                case "LinkedPL":
                    linked.set(i,"PL");
                    break;
                case "ExMln":
                case "ExMlnNewToProduct":
                case "LinkedHome":
                    linked.set(i,"ML");
                    break;
                case "ExAvaf":
                case "ExAvafNewToProduct":
                case "LinkedAvaf":
                    linked.set(i,"AF");
                    break;
                case "ExCard":
                case "ExCardNewToProduct":
                case "LinkedCard":
                    linked.set(i,"CA");
                    break;

            }
        }

        return linked;
    }

}
