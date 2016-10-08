package pojo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sowmithri.ravi on 10/8/16.
 */
@Getter
@Setter
public class PhoneNumberRequest {
    public String country_iso;
    public String type;
    public String pattern;
    public String region;
    public String services;
    public String lata;
    public String rate_center;
    public String limit;
    public String offset;
}
