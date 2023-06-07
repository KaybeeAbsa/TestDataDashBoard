package co.za.absa.TestDataDashBoard.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CIgetAcctLinkedToClientCodeV20APITest {


    @Test
    public void mapAccountLinkedToClientCodes() throws JSONException {
        CIgetAcctLinkedToClientCodeV20API cIgetAcctLinkedToClientCodeV20API = new CIgetAcctLinkedToClientCodeV20API();
        String product = "ExCheque";
        String reCombi = "Yes";

        JSONObject jsonObject = new JSONObject(getRequest());
        JSONArray array = jsonObject.getJSONObject("CIgetAcctLinkedToClientCodeV20Response").getJSONObject("outputArea").getJSONArray("outputTable");

        //System.out.println(cIgetAcctLinkedToClientCodeV20API.mapAccountLinkedToClientCodes(array,product,new ArrayList<>(),reCombi));

    }


    @Test
    public void getAccountNumber() throws JSONException {
        String req = getRequest1();

        JSONObject jsonObject = new JSONObject(req);
        String acc = jsonObject.
                     getJSONObject("SVSP15O").
                     getJSONObject("SVNP15O_OUTPUT_AREA").
                     getString("AccountNumber");

        System.out.println(acc);
    }


    String getRequest1()
    {
        return "{\n" +
                "    \"NBSAPDPO\": {\n" +
                "        \"NBSAPLO\": {\n" +
                "            \"returnCode\": 0,\n" +
                "            \"outputServiceVersion\": \"BUSO006\",\n" +
                "            \"reasonCode\": 0\n" +
                "        }\n" +
                "    },\n" +
                "    \"SVSP15O\": {\n" +
                "        \"SVNP15O_OUTPUT_AREA\": {\n" +
                "            \"AccountNumber\": 9051576063\n" +
                "        }\n" +
                "    },\n" +
                "    \"MSGO\": {\n" +
                "        \"NBSMSGO3\": {\n" +
                "            \"systemErrorText\": \"\",\n" +
                "            \"messageEntry\": [\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageErrorIndicator\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageErrorIndicator\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageErrorIndicator\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageErrorIndicator\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageErrorIndicator\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"version\": \"\",\n" +
                "            \"numberUserErrors\": 0,\n" +
                "            \"numberUserMessages\": 0\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
    String getRequest()
    {
        return "{\n" +
                "    \"outputMessageHeaders\": {\n" +
                "        \"outputErrorMessage\": {\n" +
                "            \"systemErrorText\": \"\",\n" +
                "            \"messageEntry\": [\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageErrorInd\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageErrorInd\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageErrorInd\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageErrorInd\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"messageText\": \"\",\n" +
                "                    \"messageErrorInd\": \"\",\n" +
                "                    \"messageClass\": \"\",\n" +
                "                    \"messageCode\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"version\": \"\",\n" +
                "            \"numberUserErrors\": 0,\n" +
                "            \"numberUserMessages\": 0\n" +
                "        }\n" +
                "    },\n" +
                "    \"outputHeaders\": {\n" +
                "        \"outputErrorHeaders\": {\n" +
                "            \"returnCode\": 0,\n" +
                "            \"outputServiceVersion\": \"BUSO020\",\n" +
                "            \"reasonCode\": 0,\n" +
                "            \"version\": \"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"CIgetAcctLinkedToClientCodeV20Response\": {\n" +
                "        \"outputArea\": {\n" +
                "            \"oRestart\": \"\",\n" +
                "            \"outputTable\": [\n" +
                "                {\n" +
                "                    \"product\": \"PBWPACC\",\n" +
                "                    \"oAccountNumber\": 4048037980,\n" +
                "                    \"corp\": \"ABS\",\n" +
                "                    \"sourceOfFunds1\": \"20\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 8198,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": -19213.98,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"CQ\",\n" +
                "                    \"status\": \"CURRENT\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"COMBI\",\n" +
                "                    \"oAccountNumber\": 4483850000112108,\n" +
                "                    \"corp\": \"ABS\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 8198,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"CO\",\n" +
                "                    \"status\": \"ACTIVE\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"product\": \"\",\n" +
                "                    \"oAccountNumber\": 0,\n" +
                "                    \"corp\": \"\",\n" +
                "                    \"sourceOfFunds1\": \"\",\n" +
                "                    \"sourceOfFunds2\": \"\",\n" +
                "                    \"sourceOfFunds3\": \"\",\n" +
                "                    \"sourceOfFunds4\": \"\",\n" +
                "                    \"sourceOfFunds5\": \"\",\n" +
                "                    \"dateClosed\": \"\",\n" +
                "                    \"balanceText\": \"\",\n" +
                "                    \"branch\": 0,\n" +
                "                    \"availableBalance\": 0.00,\n" +
                "                    \"balance\": 0.00,\n" +
                "                    \"limit\": 0.00,\n" +
                "                    \"detail\": \"\",\n" +
                "                    \"productType\": \"\",\n" +
                "                    \"status\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"counter\": 2\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}