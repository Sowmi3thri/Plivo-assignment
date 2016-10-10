package workflow;

import java.util.Map;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
@FunctionalInterface
public interface RequestParamConstructor {
    Map<String,String> constructRequestParams(String jsonString);
}
