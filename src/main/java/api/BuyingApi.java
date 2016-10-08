package api;

import pojo.request.BuyingRequest;
import pojo.response.BuyingResponse;
import pojo.request.RequestType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class BuyingApi extends ApiBase{
    public String base_url ="https://api.plivo.com/v1/Account/%s/PhoneNumber/%s/";
    public RequestType request_type = RequestType.POST;
    public boolean validateParams(BuyingRequest buyingRequest) {
        if(buyingRequest!=null)
            return true;
        return false;
    }

    public Map<String,String> constructRequestParams(BuyingRequest buyingRequest) {
        if(!validateParams(buyingRequest)) {
            throw new RuntimeException("Parameters are not valid");
        }
        Map<String,String> requestParams = new HashMap<String, String>();
        return requestParams;
    }

    public boolean validateResponse(BuyingResponse buyingResponse) {
        List<Map<String,String>> numbers = buyingResponse.getNumbers();
        if(numbers.size()<1) {
            return false;
        }
        Map<String,String> number = numbers.get(0);
        if(number.get("status").equalsIgnoreCase("success"))
            return true;
        return false;
    }

    public boolean processResponse(String jsonResponse) throws IOException {
        BuyingResponse buyingResponse = getPojo(jsonResponse,BuyingResponse.class);
        if(!validateResponse(buyingResponse)) {
            System.out.println("Not a valid buying API response");
            return false;
        }

        return true;

    }

}
