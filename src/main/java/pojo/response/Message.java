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
public class Message {
    public Map<String,String> inbound;
    public Map<String,String> outbound;
    public List<Map<String,String>> outbound_networks_list;
}