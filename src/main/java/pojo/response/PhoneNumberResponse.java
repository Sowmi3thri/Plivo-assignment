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
public class PhoneNumberResponse {
    public String api_id;
    public Map<String,String> meta;
    public List<Map<String,String>> objects;
}
