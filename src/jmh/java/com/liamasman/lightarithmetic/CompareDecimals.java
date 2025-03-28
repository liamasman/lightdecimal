package com.liamasman.lightarithmetic;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class CompareDecimals {
    public static void main(String[] args) throws Exception {
        final Options opt = new OptionsBuilder()
                .include(LightDecimalBenchmark.class.getSimpleName())
                .include(BigDecimalBenchmark.class.getSimpleName())
                .forks(1)
                .threads(1)
                .warmupIterations(5)
                .warmupTime(TimeValue.seconds(5))
                .measurementIterations(5)
                .measurementTime(TimeValue.seconds(5))
                .build();

        new Runner(opt).run();
    }
}
