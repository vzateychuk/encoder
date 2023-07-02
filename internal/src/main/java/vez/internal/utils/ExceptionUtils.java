package vez.internal.utils;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExceptionUtils {

    /**
     * Wrap Consumer<T> and swallow any throwable exception and re-throw
     * Allow to use methods which throws checked exceptions in streams.
     * Example:
     * .forEach( rethrowConsumer( Exception::new )
     */
    public static <T> Consumer<T> rethrowConsumer(Consumer_WithExceptions<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throwAsUnchecked(e);
            }
        };
    }

    /**
     * Wrap Function<T> and swallow any throwable exception and re-throw
     * Allow to use methods which throws checked exceptions in streams.
     * Example:
     * .map(rethrowFunction(name -> Class.forName(name)))
     * Example:
     * .map(rethrowFunction(Class::forName))
     */
    public static <T, R> Function<T, R> rethrowFunction(Function_WithExceptions<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T, Q, R> BiFunction<T, Q, R> rethrowBiFunction(BiFunction_WithExceptions<T, Q, R> function) {
        return (t, q) -> {
            try {
                return function.apply(t, q);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    public interface Consumer_WithExceptions<T> {
        void accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface BiFunction_WithExceptions<T, Q, R> {
        R apply(T t, Q q) throws Exception;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }

}
