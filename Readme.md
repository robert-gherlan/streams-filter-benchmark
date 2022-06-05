# Streams Filter Benchmark

This project contains a JMH benchmark for using filters over a stream with 100_000 products with multiple conditions.

## Result of the benchmark

```
Benchmark                                                                 Mode  Cnt     Score     Error  Units
StreamsFilterBenchmark.benchmark_stream_multiple_filters_price_first     thrpt    5  1205.427 ±   7.858  ops/s
StreamsFilterBenchmark.benchmark_stream_multiple_filters_quantity_first  thrpt    5  1244.136 ±   6.703  ops/s
StreamsFilterBenchmark.benchmark_stream_single_filter_price_first        thrpt    5  1791.481 ±  23.033  ops/s
StreamsFilterBenchmark.benchmark_stream_single_filter_quantity_first     thrpt    5  1881.266 ±  53.630  ops/s
StreamsFilterBenchmark.benchmark_stream_single_predicate_price_first     thrpt    5  1893.977 ±  40.984  ops/s
StreamsFilterBenchmark.benchmark_stream_single_predicate_quantity_first  thrpt    5  1821.884 ± 251.842  ops/s
```