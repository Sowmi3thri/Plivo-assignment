package api;

import pojo.request.DetailsRequest;
import pojo.response.DetailsResponse;
import pojo.request.RequestType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class DetailsApi extends ApiBase {
    public String base_url = "https://api.plivo.com/v1/Account/%s/Message/%s/";
    public RequestType request_type = RequestType.GET;

    /**
     * Method to convert a pojo of request parameters into a map
     * As of now there are no compulsory parameters to this API
     * @param detailsRequest
     * @return
     */
    public Map<String,String> constructRequestParams(DetailsRequest detailsRequest) {
        Map<String,String> requestParams = new HashMap<String, String>();
        return requestParams;
    }

    /**
     * Method to process the response given by details API
     * Method extracts out the total_rate field from the response
     * @param jsonResponse
     * @return
     * @throws IOException
     */
    public float processResponse(String jsonResponse) throws IOException {
        DetailsResponse detailsResponse = getPojo(jsonResponse,DetailsResponse.class);
        String stringRate = detailsResponse.getTotal_rate();
        return Float.parseFloat(stringRate);
    }

}
