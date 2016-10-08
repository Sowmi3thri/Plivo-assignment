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

    public Map<String,String> constructRequestParams(DetailsRequest detailsRequest) {
        Map<String,String> requestParams = new HashMap<String, String>();
        return requestParams;
    }

    public float processResponse(String jsonResponse) throws IOException {
        DetailsResponse detailsResponse = getPojo(jsonResponse,DetailsResponse.class);
        String stringRate = detailsResponse.getTotal_rate();
        return Float.parseFloat(stringRate);
    }

}
