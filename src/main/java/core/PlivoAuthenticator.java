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

    /**
     * Private constructor for the singleton class
     * This takes care of one time setting of auth for the entire run of the program
     * @param auth_id
     * @param auth_token
     */
    private PlivoAuthenticator(String auth_id,String auth_token){
        this.auth_id = auth_id;
        this.auth_token = auth_token;
    }

    /**
     * Method to get the instance of the singleton class
     * @param auth_id
     * @param auth_token
     * @return
     */
    public static PlivoAuthenticator getInstance(String auth_id,String auth_token) {
        if(null == instance) {
            instance = new PlivoAuthenticator(auth_id,auth_token);
        }
        return instance;
    }

    /**
     * Method to get the instance of the singleton class
     * @return
     */
    public static PlivoAuthenticator getInstance() {
        return instance;
    }


    /**
     * Method to check whether the user is authenticated to perform operations with Plivo
     * Method checks for 200/202 response code for the account API
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public boolean isAuthenticUser() throws IOException, URISyntaxException {
        AccountApi accountApi = new AccountApi();
        String url = accountApi.completeUrl(base_url,auth_id);
        HttpResponse httpResponse  = accountApi.hitApi(url,new HashMap<String, String>(),accountApi.request_type);
        if(accountApi.isApiSuccessful(httpResponse))
            return true;
        return false;
    }

}
