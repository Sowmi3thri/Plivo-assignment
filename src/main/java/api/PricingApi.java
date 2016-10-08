package api;

import pojo.request.PricingRequest;
import pojo.request.RequestType;
import pojo.response.Message;
import pojo.response.PricingResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class PricingApi extends ApiBase{
    public String base_url ="https://api.plivo.com/v1/Account/%s/Pricing/";
    public RequestType request_type = RequestType.GET;

    /**
     * Method to validate the request parameters
     * For this scenario, country_iso is mandatory
     * @param pricingRequest
     * @return
     */
    private boolean validateParams(PricingRequest pricingRequest) {
        if(pricingRequest!=null &&pricingRequest.getCountry_iso()!=null) {
            return true;
        }
        return false;
    }

    /**
     * Helper method to convert pojo of request params into a map
     * A map of request params would be useful in creating http get/post request
     * @param pricingRequest
     * @return
     */
    public Map<String,String> constructRequestParams(PricingRequest pricingRequest) {
        if(!validateParams(pricingRequest)) {
            throw new RuntimeException("Parameters are not valid");
        }
        Map<String,String> requestParams = new HashMap<String, String>();
        requestParams.put("country_iso", pricingRequest.getCountry_iso());
        return requestParams;
    }

    /**
     * Method that processes the response given by pricing API
     * For this scenario, we need to extract out the 'rate' field from the response
     * @param jsonResponse
     * @return
     * @throws IOException
     */
    public float processResponse(String jsonResponse) throws IOException {
        PricingResponse pricingResponse = getPojo(jsonResponse,PricingResponse.class);
        Message message = pricingResponse.getMessage();
        String stringRate = message.getOutbound().get("rate");
        return Float.parseFloat(stringRate);

    }
}
