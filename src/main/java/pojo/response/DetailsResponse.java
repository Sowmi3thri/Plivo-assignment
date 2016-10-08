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
public class DetailsResponse {
    public String api_id;
    public String error_code;
    public String from_number;
    public String message_direction;
    public String message_state;
    public String message_time;
    public String message_type;
    public String message_uuid;
    public String resource_uri;
    public String to_number;
    public String total_amount;
    public String total_rate;
    public String units;


}
