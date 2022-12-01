package com.exampapers.service.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service<T, R> {

    private Supplier<Stream<T>> lazy;

    private Branch<T, R> branch;

    private List<MapPredicate<T, R>> list = new ArrayList<>();

    @Autowired
    public Service(Supplier<Stream<T>> lazy, Branch<T, R> branch) {
        this.lazy = lazy;
        this.branch = branch;
    }

    @Autowired
    public Service<T, R> lazy(Supplier<Stream<T>> lazy) {
        this.lazy = lazy;
        return this;
    }

    public Service<T, R> add(Predicate<T> predicate, Function<T, R> function) {
        MapPredicate.MapPredicateBuilder<T, R> builder = MapPredicate.builder();
        list.add(builder.predicate(predicate).function(function).build());
        return this;
    }

    public Stream<R> stream() {
        return lazy.get()
                .flatMap(t -> list.stream()
                        .filter(a -> a.predicate.test(t))
                        .map(a -> a.function.apply(t)))
                .filter(Objects::nonNull);
    }

    public Stream<R> toStream() {
        Stream<R> lStream = lazy.get()
                .filter(branch.left.predicate)
                .map(branch.left.function);

        Stream<R> rStream = lazy.get()
                .filter(branch.right.predicate)
                .map(branch.right.function);

        return Streamable.of(() -> lStream).and(() -> rStream).stream();
    }

    public Stream<R> toStreamSequential() {
        return lazy.get()
                .map(t -> {
                            if (branch.left.predicate.test(t)) {
                                return branch.left.function.apply(t);
                            }

                            if (branch.right.predicate.test(t)) {
                                return branch.right.function.apply(t);
                            }
                            return null;
                        }
                ).filter(Objects::nonNull);
    }

    @Builder
    public static class Branch<T, R> {
        private MapPredicate<T, R> right;

        private MapPredicate<T, R> left;
    }

    @Builder
    public static class MapPredicate<T, R> {
        private Predicate<T> predicate;

        private Function<T, R> function;
    }

}
