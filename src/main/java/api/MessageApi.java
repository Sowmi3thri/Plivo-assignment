package api;

import pojo.request.MessageRequest;
import pojo.response.MessageResponse;
import pojo.request.RequestType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class MessageApi extends ApiBase {
    public String base_url ="https://api.plivo.com/v1/Account/%s/Message/";
    public RequestType request_type = RequestType.POST;

    /**
     * Method to validate the request parameters
     * For this scenario, source, destination, text are mandatory
     * @param messageRequest
     * @return
     */
    private boolean validateParams(MessageRequest messageRequest) {
        if(messageRequest!=null && messageRequest.getDst()!=null && messageRequest.getSrc()!=null && messageRequest.getText()!=null)
            return true;
        return false;
    }

    /**
     * Helper method tp convert pojo of request params into a map
     * A map would be useful in constructing http get/post requests
     * @param messageRequest
     * @return
     */
    public Map<String,String> constructRequestParams(MessageRequest messageRequest) {
        if(!validateParams(messageRequest)) {
            throw new RuntimeException("Parameters are not valid");
        }
        Map<String,String> requestParams = new HashMap<String, String>();
        requestParams.put("src", messageRequest.getSrc());
        requestParams.put("dst",messageRequest.getDst());
        requestParams.put("text",messageRequest.getText());
        return requestParams;
    }

    /**
     * Method to validate the response given by message API
     * Method looks for a particular string 'message(s) queued' to check for validity
     * @param messageResponse
     * @return
     */
    private boolean validateResponse(MessageResponse messageResponse) {
        String message = messageResponse.getMessage();
        if(message.equalsIgnoreCase("message(s) queued"))
            return true;
        return false;
    }


    /**
     * Method that processes the response given by message API
     * Method extracts out the uuid from the response
     * @param jsonResponse
     * @return
     * @throws IOException
     */
    public List<String> processResponse(String jsonResponse) throws IOException {
        MessageResponse messageResponse = getPojo(jsonResponse,MessageResponse.class);
        if(!validateResponse(messageResponse)) {
            System.out.println("Message sending failed");
            System.exit(0);
        }
        return messageResponse.getMessage_uuid();

    }

}
