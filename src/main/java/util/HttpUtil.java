package util;


import com.google.gson.GsonBuilder;
import core.PlivoAuthenticator;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * Created by sowmithri.ravi on 10/7/16.
 */
public class HttpUtil {
   static PlivoAuthenticator plivoAuthenticator = PlivoAuthenticator.getInstance();
    public static String getStringResponseContent(HttpResponse httpResponse) throws URISyntaxException, IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static int getResponseCode(HttpResponse httpResponse) {
        return httpResponse.getStatusLine().getStatusCode();
    }

    public static HttpResponse makeGetRequest(String url, Map<String,String> parameters) throws URISyntaxException, IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(plivoAuthenticator.getAuth_id(),plivoAuthenticator.getAuth_token());
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        URIBuilder uriBuilder = new URIBuilder(url);
        Set<String> paramKeys = parameters.keySet();
        for(String key : paramKeys) {
            uriBuilder.addParameter(key,parameters.get(key));
        }
        HttpGet request = new HttpGet(uriBuilder.build());
        HttpResponse response = client.execute(request);
        return response;
    }
    public static HttpResponse makePostRequest(String url, Map<String,String> parameters) throws URISyntaxException, IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(plivoAuthenticator.getAuth_id(),plivoAuthenticator.getAuth_token());
        provider.setCredentials(AuthScope.ANY, credentials);
        HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
        HttpPost request = new HttpPost(url);
        String json = new GsonBuilder().create().toJson(parameters, Map.class);
        StringEntity se = new StringEntity(json);
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return response;
    }
}
