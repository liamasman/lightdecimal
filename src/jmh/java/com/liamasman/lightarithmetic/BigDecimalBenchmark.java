package com.liamasman.lightarithmetic;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;


import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(1)
public class BigDecimalBenchmark
{
    private static final String[] STRINGS = {
            "47982487324874987234.89347834786487264723",
            "12398479823589534850.00340342347835746324",
            "38970156546516841842.45365273689791827324",
            "92948439493491283414.78641674612764786431",
            "90481098342385824534.89217473473289473289",
            "34781909585923842343.38467816478365873426",
            "39482398759348768911.88832838219439483984",
            "11123583925798247562.52837548735425765322",
            "19298498328523853858.38549283759832759221",
            "19294959234502341410.29490032401491240148",
            "99124831823743253726.82184783147723471674",
            "46124514932588927582.98634876238563278283"
    };
    private static final LightDecimal[] BIG_DECIMALS = {
            new LightDecimal("47982487324874987234.89347834786487264723"),
            new LightDecimal("12398479823589534850.00340342347835746324"),
            new LightDecimal("38970156546516841842.45365273689791827324"),
            new LightDecimal("92948439493491283414.78641674612764786431"),
            new LightDecimal("90481098342385824534.89217473473289473289"),
            new LightDecimal("34781909585923842343.38467816478365873426"),
            new LightDecimal("39482398759348768911.88832838219439483984"),
            new LightDecimal("11123583925798247562.52837548735425765322"),
            new LightDecimal("19298498328523853858.38549283759832759221"),
            new LightDecimal("19294959234502341410.29490032401491240148"),
            new LightDecimal("99124831823743253726.82184783147723471674"),
            new LightDecimal("46124514932588927582.98634876238563278283")
    };

    @State(Scope.Thread)
    public static class ThreadState
    {
        public int index = 0;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 5, time = 5)
    public void fromString(final ThreadState state, final Blackhole bh)
    {
        bh.consume(new BigDecimal(STRINGS[state.index++]));

        if (state.index >= STRINGS.length)
        {
            state.index = 0;
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 5, time = 5)
    public void toString(final ThreadState state, final Blackhole bh)
    {
        bh.consume(BIG_DECIMALS[state.index++].toString());

        if (state.index >= BIG_DECIMALS.length)
        {
            state.index = 0;
        }
    }

    public static void main(String[] args) throws Exception
    {
        org.openjdk.jmh.Main.main(args);
    }
}