package workflow;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
@FunctionalInterface
public interface ResponseConverter<T> {
    T transform(String json, String jsonPaths);
}
