package com.exampapers;

import com.exampapers.service.image.ImageConverter;
import com.exampapers.service.image.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.springframework.util.Assert.notNull;

@SpringBootApplication
public class ExamPapersApplication implements CommandLineRunner {

    private final ImageConverter imageConverter;

    private final Validator validator;


    @Autowired
    public ExamPapersApplication(ImageConverter imageConverter, Validator validator) {
        this.imageConverter = imageConverter;
        this.validator = validator;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamPapersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        imageConverter.convertImageToSVG();
       // System.out.println(validate(validator));

        Service.ServiceBuilder<Integer, Integer> builder = Service.builder();

        Service.MapPredicate.MapPredicateBuilder<Integer, Integer> builder1 = Service.MapPredicate.builder();
        Service.MapPredicate.MapPredicateBuilder<Integer, Integer> builder2 = Service.MapPredicate.builder();

        Service.Branch.BranchBuilder<Integer, Integer> builder3 = Service.Branch.builder();

        Service.Branch<Integer, Integer> build = builder3
                .left(builder1
                        .predicate(x -> x % 2 == 0)
                        .function(x -> x * 2)
                        .build())
                .right(builder2
                        .predicate(x -> x % 2 != 0)
                        .function(x -> x * 3)
                        .build())
                .build();

        builder.lazy(() -> Stream.of(1, 2, 3))
                .branch(build)
                .build()
                .toStream()
                .forEach(System.out::println);

        builder.lazy(() -> Stream.of(1, 2, 3))
                .branch(build)
                .build()
                .toStreamSequential()
                .forEach(System.out::println);

        new Service<Integer, Integer>()
                .lazy(() -> Stream.of(1, 2, 3))
                .add(x -> x % 2 == 0, x -> x * 2)
                .add(x -> x % 2 != 0, x -> x * 3)
                .stream()
                .forEach(System.out::println);
    }

    @Valid
    @AllArgsConstructor
    public static class Person {

        @NotNull
        private Integer age;

        @NotNull
        @javax.validation.constraints.Max(10)
        private Long id;
    }

    public static boolean validate(Validator validator) {
        Set<Class<?>> groups = Sets.newHashSet(Default.class);
        return validate(new Person(null, 11l), validator, groups.toArray(new Class<?>[0]));
    }

    public static <T> boolean validate(T domainObject, Validator validator, Class<?>... groups) {
        notNull(domainObject, "domainObject must not be null");
        notNull(validator, "validator must not be null");

        List<Class<?>> validationGroups = groups == null ? Lists.newArrayList() : Lists.newArrayList(groups);
        if (!validationGroups.contains(Default.class)) {
            validationGroups.add(Default.class);
        }

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(
                domainObject, validationGroups.toArray(new Class<?>[0]));

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        return true;
    }


}
