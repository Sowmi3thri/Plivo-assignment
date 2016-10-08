package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.PlivoAuthenticator;
import pojo.request.RequestType;
import org.apache.http.HttpResponse;
import util.HttpUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */


public abstract class ApiBase {
    PlivoAuthenticator plivoAuthenticator = PlivoAuthenticator.getInstance();

    /**
     * This method is to help construct a complete url by replacing the placeholders with values
     * Here first argument should always be auth_id
     * @param base_url
     * @param args
     * @return
     */
    public String completeUrl(String base_url,String... args) {
        String[] newArgs = new String[args.length+1];
        newArgs[0] = plivoAuthenticator.getAuth_id();
        for(int i=0;i<args.length;i++) {
            newArgs[i+1] = args[i];
        }
        return String.format(base_url,newArgs);
    }

    /**
     * Method to call get or post method depending on the request type
     * Method also passes params to the method if set
     * @param base_url
     * @param requestParams
     * @param request_type
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public HttpResponse hitApi(String base_url, Map<String,String> requestParams, RequestType request_type) throws IOException, URISyntaxException {
        HttpResponse httpResponse;
        if(request_type.equals(RequestType.GET))
            httpResponse = HttpUtil.makeGetRequest(base_url, requestParams);
        else
            httpResponse = HttpUtil.makePostRequest(base_url,requestParams);
        return httpResponse;
    }

    /**
     * Method to help convert httpresponse to a string response
     * @param httpResponse
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String getStringResponse(HttpResponse httpResponse) throws IOException, URISyntaxException {
        return HttpUtil.getStringResponseContent(httpResponse);
    }

    /**
     * Method to map a json string to a pojo
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public  static <T> T  getPojo(String jsonString,Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, clazz);
    }


    /**
     * Method to check if an API was successful or not
     * @param httpResponse
     * @return
     */
    public boolean isApiSuccessful(HttpResponse httpResponse) {
        int code = HttpUtil.getResponseCode(httpResponse);
        if(code==200||code==202)
            return true;
        return false;
    }


}
