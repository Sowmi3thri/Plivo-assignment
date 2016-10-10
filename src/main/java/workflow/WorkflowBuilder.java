package workflow;

import api.BuyingApi;
import api.MessageApi;
import api.PhoneNumberApi;
import util.HttpUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
public class WorkflowBuilder {
    static  List<PlivoOperations> plivoOperations = new ArrayList<>();
    String jsonString = ""; //Construct this from the USer input of country_iso and pattern
    PhoneNumberApi phoneNumberApi = new PhoneNumberApi();
    String dest = "";
    String message = "";
    BuyingApi buyingApi = new BuyingApi();
    MessageApi messageApi = new MessageApi();


    public void simulatePlivoScenario1() throws IOException, URISyntaxException {
        PlivoOperations searchOperation = new PlivoOperations()
                .setApiUrl(phoneNumberApi.searchApi)
                .setRequestParams(phoneNumberApi.searchRequestParamConstructor,jsonString)
                .hitApi();
        String searchResponse = HttpUtil.getStringResponseContent(searchOperation.httpResponse);

        PlivoOperations buyOperation = new PlivoOperations()
                .setApiUrl(buyingApi.buyingApi,searchResponse)
                .setRequestParams(buyingApi.buyingRequestParams,null)
                .hitApi();
        String buyingResponse = HttpUtil.getStringResponseContent(buyOperation.httpResponse);

        PlivoOperations messageOperation = new PlivoOperations()
                .setApiUrl(messageApi.messageApiUrl,searchResponse,dest,message)
                .setRequestParams(messageApi.messageRequestParameter,null)
                .hitApi();
        String messageResponse = HttpUtil.getStringResponseContent(messageOperation.httpResponse);




    }

}
