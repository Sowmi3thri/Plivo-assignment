package workflow;

/**
 * Created by sowmithri.ravi on 10/10/16.
 */
@FunctionalInterface
public interface BooleanPredicate<T> {
    boolean checkParams(T object);
}


