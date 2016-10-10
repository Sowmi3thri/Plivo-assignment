package workflow;

import org.apache.http.HttpResponse;
import pojo.request.RequestType;
import util.HttpUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */

public class PlivoOperations {
    Map<String,String> requestParams;
    List<String> pathParams;
    String api_url;
    HttpResponse httpResponse;
    RequestType request_type;

    public PlivoOperations setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        return this;
    }


    public PlivoOperations setRequestParams(RequestParamConstructor requestParamConstructor,String jsonString) {
        this.requestParams = requestParamConstructor.constructRequestParams(jsonString);
        return this;
    }
    public PlivoOperations setPathParams(PathParams pathParams) {
        this.pathParams =  pathParams.constructPathParams();
        return this;
    }

    public PlivoOperations setApiUrl(ApiUrlConstructor apiUrlConstructor,String ...args) {
        this.api_url = apiUrlConstructor.constructApi(args);
        return this;
    }

    public PlivoOperations hitApi() throws IOException, URISyntaxException {
        HttpResponse httpResponse;
        if(this.request_type.equals(RequestType.GET))
            httpResponse = HttpUtil.makeGetRequest(this.api_url, this.requestParams);
        else
            httpResponse = HttpUtil.makePostRequest(this.api_url,this.requestParams);
        setHttpResponse(httpResponse);
        return this;
    }

    public PlivoOperations addCheck(BooleanPredicate booleanPredicate,String jsonString ) {
        booleanPredicate.checkParams(jsonString);
        return this;
    }

    public PlivoOperations validateParams(BooleanPredicate booleanPredicate, Map<String,String> requestParams) {
        booleanPredicate.checkParams(requestParams);
        return this;
    }

    public PlivoOperations processResponse(ResponseConverter responseConverter,String json,String jsonpath) {
        responseConverter.transform(json,jsonpath);
        return this;
    }
}
