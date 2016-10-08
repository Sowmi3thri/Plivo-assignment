package api;

import pojo.request.PhoneNumberRequest;
import pojo.response.PhoneNumberResponse;
import pojo.request.RequestType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
public class PhoneNumberApi extends ApiBase  {
    public String base_url = "https://api.plivo.com/v1/Account/%s/PhoneNumber/";
    public  RequestType request_type = RequestType.GET;
    int phone_number_count = 2;

    public boolean validateParams(PhoneNumberRequest phoneNumberRequest) {
        if(phoneNumberRequest!=null &&phoneNumberRequest.getCountry_iso()!=null && phoneNumberRequest.getPattern()!=null) {
            return true;
        }
        return false;
    }

    public boolean validateResponse(PhoneNumberResponse phoneNumberResponse) {
        List<Map<String,String>> objects = phoneNumberResponse.getObjects();
        if(objects.size()>=phone_number_count)
            return true;
        return false;
    }

    public Map<String,String> constructRequestParams(PhoneNumberRequest phoneNumberRequest) {
        if(!validateParams(phoneNumberRequest)) {
            throw new RuntimeException("Parameters are not valid");
        }
        Map<String,String> requestParams = new HashMap<String, String>();
        requestParams.put("country_iso", phoneNumberRequest.getCountry_iso());
        requestParams.put("pattern",phoneNumberRequest.getPattern());
        return requestParams;
    }


    public List<String> processResponse(String jsonResponse) throws IOException {
        List<String> numbers = new ArrayList<String>();
        PhoneNumberResponse phoneNumberResponse = getPojo(jsonResponse,PhoneNumberResponse.class);
        if(!validateResponse(phoneNumberResponse)) {
            System.out.println("Less than two numbers in the response");
            System.exit(0);
        }
        List<Map<String,String>> objects = phoneNumberResponse.getObjects();
        Random random = new Random();
        for(int i = 0;i < phone_number_count;i++) {
            int index = random.nextInt(objects.size());
            Map<String,String> numberObject = objects.get(index);
            numbers.add(numberObject.get("number"));
        }
        return numbers;

    }






}
