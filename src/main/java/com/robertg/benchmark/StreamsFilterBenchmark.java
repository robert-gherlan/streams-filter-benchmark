package com.robertg.benchmark;

import com.github.javafaker.Faker;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class StreamsFilterBenchmark {

    private static final int QUANTITY = 40;

    private static final int PRICE = 2_000;

    private static final List<Product> PRODUCTS = getProducts();

    private static final Predicate<Product> QUANTITY_PREDICATE = product -> product.quantity() > QUANTITY;

    private static final Predicate<Product> PRICE_PREDICATE = product -> product.price() > PRICE;

    private static final Predicate<Product> PRODUCTS_FILTER_QUANTITY_FIRST = QUANTITY_PREDICATE.and(PRICE_PREDICATE);

    private static final Predicate<Product> PRODUCTS_FILTER_PRICE_FIRST = PRICE_PREDICATE.and(QUANTITY_PREDICATE);

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_multiple_filters_quantity_first() {
        PRODUCTS.stream().filter(product -> product.quantity() > QUANTITY).filter(product -> product.price() > PRICE).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_multiple_filters_price_first() {
        PRODUCTS.stream().filter(product -> product.price() > PRICE).filter(product -> product.quantity() > QUANTITY).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_single_filter_quantity_first() {
        PRODUCTS.stream().filter(PRODUCTS_FILTER_QUANTITY_FIRST).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_single_filter_price_first() {
        PRODUCTS.stream().filter(PRODUCTS_FILTER_PRICE_FIRST).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_single_predicate_quantity_first() {
        PRODUCTS.stream().filter(product -> product.quantity() > QUANTITY && product.price() > PRICE).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_single_predicate_price_first() {
        PRODUCTS.stream().filter(product -> product.price() > PRICE && product.quantity() > QUANTITY).count();
    }

    private static List<Product> getProducts() {
        final var faker = Faker.instance();
        return IntStream.range(0, 100_000).mapToObj(e -> toProduct(faker)).toList();
    }

    private static Product toProduct(Faker faker) {
        return new Product(faker.name().name(), faker.number().numberBetween(0, 100), faker.number().numberBetween(1_000, 5_000));
    }

    private record Product(String name, int quantity, int price) {
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
