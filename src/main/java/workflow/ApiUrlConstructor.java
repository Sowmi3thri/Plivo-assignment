package workflow;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
@FunctionalInterface
public interface ApiUrlConstructor {
    String constructApi(String... args);
}
