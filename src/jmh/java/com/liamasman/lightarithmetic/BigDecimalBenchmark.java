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
    private static final BigDecimal[] BIG_DECIMALS = {
            new BigDecimal("47982487324874987234.89347834786487264723"),
            new BigDecimal("12398479823589534850.00340342347835746324"),
            new BigDecimal("38970156546516841842.45365273689791827324"),
            new BigDecimal("92948439493491283414.78641674612764786431"),
            new BigDecimal("90481098342385824534.89217473473289473289"),
            new BigDecimal("34781909585923842343.38467816478365873426"),
            new BigDecimal("39482398759348768911.88832838219439483984"),
            new BigDecimal("11123583925798247562.52837548735425765322"),
            new BigDecimal("19298498328523853858.38549283759832759221"),
            new BigDecimal("19294959234502341410.29490032401491240148"),
            new BigDecimal("99124831823743253726.82184783147723471674"),
            new BigDecimal("46124514932588927582.98634876238563278283")
    };

    @State(Scope.Thread)
    public static class ThreadState
    {
        private int index = 0;

        private int nextIndex()
        {
            if (index >= BIG_DECIMALS.length)
            {
                index = 1;
                return 0;
            }
            return index++;
        }

        public BigDecimal nextBigDecimal()
        {
            return BIG_DECIMALS[nextIndex()];
        }

        public BigDecimal constructNextBigDecimal()
        {
            final BigDecimal bigDecimal = nextBigDecimal();
            return new BigDecimal(bigDecimal.unscaledValue(), bigDecimal.scale());
        }

        public String nextString()
        {
            return STRINGS[nextIndex()];
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void fromString(final ThreadState state, final Blackhole bh)
    {
        bh.consume(new BigDecimal(state.nextString()));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void toString(final ThreadState state, final Blackhole bh)
    {
        //BigDecimal caches the string value, so it'd lie about performance if we didn't construct it again
        bh.consume(state.constructNextBigDecimal().toString());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void additionSameScale(final ThreadState state, final Blackhole bh)
    {
        final BigDecimal a = state.nextBigDecimal();
        final BigDecimal b = state.nextBigDecimal();
        bh.consume(a.add(b));
    }

    public static void main(String[] args) throws Exception
    {
        org.openjdk.jmh.Main.main(args);
    }
}