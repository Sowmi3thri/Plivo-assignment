package pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
@Getter
@Setter
public class PricingResponse {
    public String api_id;
    public String country;
    public String country_code;
    public String country_iso;
    public Message message;
    public Map phone_numbers;
    public Map voice;
    public Map outbound;
    public Map tollfree;
}
