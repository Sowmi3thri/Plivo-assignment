package pojo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
@Getter
@Setter
public class MessageRequest {
    public String src;
    public String dst;
    public String text;
    public String type;
    public String url;
    public String method;
    public String log;
}
