package core;

import api.AccountApi;
import api.BuyingApi;
import api.DetailsApi;
import api.MessageApi;
import api.PhoneNumberApi;
import api.PricingApi;
import pojo.request.AccountRequest;
import pojo.request.BuyingRequest;
import pojo.request.DetailsRequest;
import pojo.request.MessageRequest;
import pojo.request.PhoneNumberRequest;
import pojo.request.PricingRequest;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class Orchestrator {
    String api_url;
    Map<String,String> request_params;
    HttpResponse http_response;
    String string_response;

    /**
     * This method is to hit the search API with the given country_iso and pattern
     * and return two matches if they exist
     * otherwise, the program quits
     * @param country_iso
     * @param pattern
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public List<String> searchNumber(String country_iso,String pattern) throws IOException, URISyntaxException {
        PhoneNumberApi phoneNumberApi = new PhoneNumberApi();
        api_url = phoneNumberApi.completeUrl(phoneNumberApi.base_url);
        System.out.println("Search API - " + api_url);
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest();
        phoneNumberRequest.setCountry_iso(country_iso);
        phoneNumberRequest.setPattern(pattern);
        request_params = phoneNumberApi.constructRequestParams(phoneNumberRequest);
        http_response = phoneNumberApi.hitApi(api_url, request_params, phoneNumberApi.request_type);
        string_response = phoneNumberApi.getStringResponse(http_response);
        if(!phoneNumberApi.isApiSuccessful(http_response))  {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        return phoneNumberApi.processResponse(string_response);
    }

    /**
     * This method is to hit the buying API with the given phone number
     * @param phoneNumber
     * @throws IOException
     * @throws URISyntaxException
     */
    public void buyNumber(String phoneNumber) throws IOException, URISyntaxException {
        BuyingApi buyingApi = new BuyingApi();
        api_url = buyingApi.completeUrl(buyingApi.base_url,phoneNumber);
        System.out.println("Buying API - " + api_url);
        BuyingRequest buyingRequest = new BuyingRequest();
        request_params = buyingApi.constructRequestParams(buyingRequest);
        http_response = buyingApi.hitApi(api_url, request_params, buyingApi.request_type);
        string_response = buyingApi.getStringResponse(http_response);
        if(!buyingApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        if(buyingApi.processResponse(string_response)) {
            System.out.println("Bought number : " +phoneNumber);
        } else {
            System.out.println("Unable to buy number : " + phoneNumber);
        }
    }

    /**
     * This method is to hit the message API and send a message from one number to another
     * The method returns the uuid of the message
     * @param src
     * @param dst
     * @param text
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String sendMessage(String src, String dst, String text) throws IOException, URISyntaxException {
        MessageApi messageApi = new MessageApi();
        MessageRequest messageRequest = new MessageRequest();
        api_url = messageApi.completeUrl(messageApi.base_url);
        System.out.println("Send message API - " + api_url);
        messageRequest.setDst(dst);
        messageRequest.setSrc(src);
        messageRequest.setText(text);
       request_params = messageApi.constructRequestParams(messageRequest);
       http_response = messageApi.hitApi(api_url, request_params, messageApi.request_type);
      string_response = messageApi.getStringResponse(http_response);
        if(!messageApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        List<String> uuid = messageApi.processResponse(string_response);
        return uuid.get(0);
    }

    /**
     * This method is to hit the details API and display the message details for a given uuid
     * @param uuid
     * @throws IOException
     * @throws URISyntaxException
     */
    public void printMessageDetails(String uuid) throws IOException, URISyntaxException {
        DetailsApi detailsApi = new DetailsApi();
        DetailsRequest detailsRequest = new DetailsRequest();
        api_url = detailsApi.completeUrl(detailsApi.base_url, uuid);
        System.out.println("Message details API - " + api_url);
        request_params = detailsApi.constructRequestParams(detailsRequest);
        http_response = detailsApi.hitApi(api_url, request_params, detailsApi.request_type);
       string_response = detailsApi.getStringResponse(http_response);
        if(!detailsApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        System.out.println("Message details : " + string_response);
    }

    /**
     * This method is to extract the message outbound rate from the message detail for a given uuid
     * @param uuid
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public float getMessageRate(String uuid) throws IOException, URISyntaxException {
        DetailsApi detailsApi = new DetailsApi();
        DetailsRequest detailsRequest = new DetailsRequest();
        api_url = detailsApi.completeUrl(detailsApi.base_url, uuid);
        System.out.println("Message details API - " + api_url);
        request_params = detailsApi.constructRequestParams(detailsRequest);
        http_response = detailsApi.hitApi(api_url, request_params, detailsApi.request_type);
        string_response = detailsApi.getStringResponse(http_response);
        if(!detailsApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
       return detailsApi.processResponse(string_response);

    }

    /**
     * This method id to get the pricing information for the given country
     * @param country_iso
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public float getPricing(String country_iso) throws IOException, URISyntaxException {
        PricingApi pricingApi = new PricingApi();
        PricingRequest pricingRequest = new PricingRequest();
        pricingRequest.setCountry_iso(country_iso);
        request_params = pricingApi.constructRequestParams(pricingRequest);
        api_url = pricingApi.completeUrl(pricingApi.base_url);
        System.out.println("Pricing API - " + api_url);
        http_response = pricingApi.hitApi(api_url, request_params, pricingApi.request_type);
        string_response = pricingApi.getStringResponse(http_response);
        if(!pricingApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        return pricingApi.processResponse(string_response);

    }

    /**
     * This method is to extract the cash credit from account information
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public float getCashCredit() throws IOException, URISyntaxException {
        AccountApi accountApi = new AccountApi();
        AccountRequest accountRequest = new AccountRequest();
        request_params = accountApi.constructRequestParams(accountRequest);
        api_url = accountApi.completeUrl(accountApi.base_url);
        System.out.println("Account API - " + api_url);
        http_response = accountApi.hitApi(api_url, request_params, accountApi.request_type);
        string_response = accountApi.getStringResponse(http_response);
        if(!accountApi.isApiSuccessful(http_response)) {
            System.out.println("API call did not succeed");
            System.out.println("Response from the API: " + string_response);
            System.exit(1);
        }
        return accountApi.processResponse(string_response);

    }



}
