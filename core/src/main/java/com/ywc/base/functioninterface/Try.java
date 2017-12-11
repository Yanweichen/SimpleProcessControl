package com.ywc.base.functioninterface;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 包裹lambda的受检测异常
 */
public class Try {

    public static <T, R> Function<T, R> of(UncheckedFunction<T, R> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                return mapper.apply(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, U, R> BiFunction<T, U, R> of(UncheckedBiFunction<T, U, R> mapper) {
        Objects.requireNonNull(mapper);
        return (t,u) -> {
            try {
                return mapper.apply(t,u);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, U, V, R> SuFunction<T, U, V, R> of(UncheckedSuFunction<T, U, V, R> mapper) {
        Objects.requireNonNull(mapper);
        return (t,u,v) -> {
            try {
                return mapper.apply(t,u,v);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T> Consumer<T> of(UncheckedConsumer<T> mapper) {
        Objects.requireNonNull(mapper);
        return t -> {
            try {
                mapper.accept(t);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T,V> BiConsumer<T,V> of(UncheckedBiConsumer<T,V> mapper) {
        Objects.requireNonNull(mapper);
        return (t,v) -> {
            try {
                mapper.accept(t,v);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @FunctionalInterface
    public interface UncheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface UncheckedBiFunction<T, U, R> {
        R apply(T t, U u) throws Exception;
    }

    @FunctionalInterface
    public interface UncheckedSuFunction<T, U, V, R> {
        R apply(T t, U u, V v) throws Exception;
    }

    @FunctionalInterface
    public interface UncheckedConsumer<T> {
        void accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface UncheckedBiConsumer<T,V> {
        void accept(T t, V v) throws Exception;
    }
}