package com.test.utils;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Description: Builder模式构造器
 * @Author: 青衣醉
 * @Date: 2022/8/17 10:42 上午
 */
public class Builder<T>{
    private final Supplier<T> instantiator;
    private List<Consumer<T>> modifiers = Lists.newArrayList ();
    public Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }
    public static <T> Builder<T> of(Supplier<T> instantiator) {
        return new Builder<>(instantiator);
    }
    public <P1> Builder<T> with(Consumer1<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }
    public <P1, P2> Builder<T> with(Consumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }
    public T build() {
        T value = instantiator.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }
    /**
     * 1 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer1<T, P1> {
        void accept(T t, P1 p1);
    }
    /**
     * 2 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer2<T, P1, P2> {
        void accept(T t, P1 p1, P2 p2);
    }
}
