package api;

import pojo.request.AccountRequest;
import pojo.response.AccountResponse;
import pojo.request.RequestType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class AccountApi extends ApiBase {
    public String base_url ="https://api.plivo.com/v1/Account/%s/";
    public RequestType request_type = RequestType.GET;

    public Map<String,String> constructRequestParams(AccountRequest accountRequest) {
        Map<String,String> requestParams = new HashMap<String, String>();
        return requestParams;
    }

    public float processResponse(String jsonResponse) throws IOException {
        AccountResponse accountResponse = getPojo(jsonResponse, AccountResponse.class);
        String cash_credits = accountResponse.getCash_credits();
        return Float.parseFloat(cash_credits);

    }
}
