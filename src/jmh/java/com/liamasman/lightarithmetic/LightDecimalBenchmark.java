package com.liamasman.lightarithmetic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(1)
public class LightDecimalBenchmark
{
    private static final String[] STRINGS = {
            "47982487324874987234.89347834786487264723",
            "-12398479823589534850.00340342347835746324",
            "38970156546516841842.45365273689791827324",
            "92948439493491283414.78641674612764786431",
            "90481098342385824534.89217473473289473289",
            "-39482398759348768911.88832838219439483984",
            "-34781909585923842343.38467816478365873426",
            "11123583925798247562.52837548735425765322",
            "89234726347234782364.89543897765348967382",
            "19298498328523853858.38549283759832759221",
            "-19294959234502341410.29490032401491240148",
            "-99124831823743253726.82184783147723471674",
            "46124514932588927582.98634876238563278283",
            "-58573846578346578345.34582375982748724623",
            "45645873465786347520.00000357622100000124",
            "99583487573747834783.34661000450345234000"
    };

    @State(Scope.Thread)
    public static class ThreadState
    {
        private final LightDecimal[] decimals = Arrays.stream(STRINGS)
                .map(LightDecimal::new)
                .toArray(LightDecimal[]::new);
        private int index = 0;

        private int nextIndex()
        {
            index = index & 0x0F;
            return index++;
        }

        public LightDecimal nextLightDecimal()
        {
            return decimals[nextIndex()];
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
        bh.consume(new LightDecimal(state.nextString()));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void toString(final ThreadState state, final Blackhole bh)
    {
        bh.consume(state.nextLightDecimal().toString());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void additionSameScale(final ThreadState state, final Blackhole bh)
    {
        final LightDecimal a = state.nextLightDecimal();
        final LightDecimal b = state.nextLightDecimal();
        bh.consume(a.add(b));
    }

    public static void main(String[] args) throws Exception
    {
        org.openjdk.jmh.Main.main(args);
    }
}