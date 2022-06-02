package com.robertg.benchmark;

import com.github.javafaker.Faker;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.stream.IntStream;

public class StreamsFilterBenchmark {

    private static final List<Product> PRODUCTS = getProducts();

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_multiple_filters() {
        PRODUCTS.stream().filter(product -> product.quantity() > 40).filter(product -> product.price() > 2_000).count();
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 5)
    public void benchmark_stream_single_filter() {
        PRODUCTS.stream().filter(product -> product.quantity() > 40 && product.price() > 2_000).count();
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
