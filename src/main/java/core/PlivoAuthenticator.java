package core;

import api.AccountApi;
import lombok.Getter;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class PlivoAuthenticator {
    private static PlivoAuthenticator instance = null;
    String base_url = "https://api.plivo.com/v1/Account/%s/";

    @Getter private String auth_id;
    @Getter private String auth_token;

    private PlivoAuthenticator(String auth_id,String auth_token){
        this.auth_id = auth_id;
        this.auth_token = auth_token;
    }
    public static PlivoAuthenticator getInstance(String auth_id,String auth_token) {
        if(null == instance) {
            instance = new PlivoAuthenticator(auth_id,auth_token);
        }
        return instance;
    }

    public static PlivoAuthenticator getInstance() {
        return instance;
    }


    public boolean isAuthenticUser() throws IOException, URISyntaxException {
        AccountApi accountApi = new AccountApi();
        String url = accountApi.completeUrl(base_url,auth_id);
        HttpResponse httpResponse  = accountApi.hitApi(url,new HashMap<String, String>(),accountApi.request_type);
        if(accountApi.isApiSuccessful(httpResponse))
            return true;
        return false;
    }

}
