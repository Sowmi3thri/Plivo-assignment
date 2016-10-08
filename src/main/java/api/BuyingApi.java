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

    /**
     * Method to validate the request parameters
     * There are no compulsory parameters for this API as of now
     * @param buyingRequest
     * @return
     */
    private boolean validateParams(BuyingRequest buyingRequest) {
        if(buyingRequest!=null)
            return true;
        return false;
    }

    /**
     * Method that converts a pojo of request parameters into a map
     * A map would be useful in constructing http get/post requests
     * @param buyingRequest
     * @return
     */
    public Map<String,String> constructRequestParams(BuyingRequest buyingRequest) {
        if(!validateParams(buyingRequest)) {
            throw new RuntimeException("Parameters are not valid");
        }
        Map<String,String> requestParams = new HashMap<String, String>();
        return requestParams;
    }

    /**
     * Method that validates the response given by buying API
     * Method looks for 'Success' for the status field
     * @param buyingResponse
     * @return
     */
    private boolean validateResponse(BuyingResponse buyingResponse) {
        List<Map<String,String>> numbers = buyingResponse.getNumbers();
        if(numbers.size()<1) {
            return false;
        }
        Map<String,String> number = numbers.get(0);
        if(number.get("status").equalsIgnoreCase("success"))
            return true;
        return false;
    }

    /**
     * Method that processes the response given by buying API
     * No major processing here. Just ensures the response is valid
     * @param jsonResponse
     * @return
     * @throws IOException
     */
    public boolean processResponse(String jsonResponse) throws IOException {
        BuyingResponse buyingResponse = getPojo(jsonResponse,BuyingResponse.class);
        if(!validateResponse(buyingResponse)) {
            System.out.println("Not a valid buying API response");
            return false;
        }

        return true;

    }

}
