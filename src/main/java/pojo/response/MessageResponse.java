package pojo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
@Getter
@Setter
public class MessageResponse {
    public String api_id;
    public String message;
    public List<String> message_uuid;
}
