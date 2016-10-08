package pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
@Getter
@Setter
public class BuyingResponse {
    public String api_id;
    public String message;
    public List<Map<String,String>> numbers;
    public String status;
}
