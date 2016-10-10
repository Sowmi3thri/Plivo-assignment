package workflow;

import java.util.List;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
@FunctionalInterface
public interface PathParams {
    List<String> constructPathParams();
}
