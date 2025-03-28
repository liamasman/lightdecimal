package com.liamasman.lightarithmetic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Fork(1)
public class BigDecimalBenchmark
{
    private static final String[] STRINGS_SCALE_20 = {
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

    private static final String[] STRINGS_VARIOUS_SCALE = {
            "384843285552.4897232384723848572",
            "347232957342.52455783257894357",
            "443825439867.72482853582557",
            "892390459849.8357485739845792",
            "-34724756834.7248672935235",
            "-43825439867.3852745894736876589",
            "443825439867.54228375829759246346523",
            "993125398345.8347598237648769835769842745",
            "059459230100.358927589274687438768437687",
            "935774620104.59823758924767483768274",
            "986341194943.23543698952001010439513523",
            "432421334841.0009304810401348023523014302",
            "059823410102.357293756047023570237502",
            "947568910010.5842752387532857385",
            "-34235235246.235246355235",
            "443825439867.58427523832857385"
    };

    @State(Scope.Thread)
    public static class ThreadState
    {
        private final BigDecimal[] decimals20 = Arrays.stream(STRINGS_SCALE_20)
                .map(BigDecimal::new)
                .toArray(BigDecimal[]::new);
        private final BigDecimal[] decimals = Arrays.stream(STRINGS_VARIOUS_SCALE)
                .map(BigDecimal::new)
                .toArray(BigDecimal[]::new);
        private int index = 0;

        private int nextIndex()
        {
            index = index & 0x0F;
            return index++;
        }

        public BigDecimal nextBigDecimal20()
        {
            return decimals20[nextIndex()];
        }

        public BigDecimal nextBigDecimal() {
            return decimals[nextIndex()];
        }

        public BigDecimal constructNextBigDecimal()
        {
            final BigDecimal bigDecimal = nextBigDecimal20();
            return new BigDecimal(bigDecimal.unscaledValue(), bigDecimal.scale());
        }

        public String nextString20()
        {
            return STRINGS_SCALE_20[nextIndex()];
        }

        public String nextString() {
            return STRINGS_VARIOUS_SCALE[nextIndex()];
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
        final BigDecimal a = state.nextBigDecimal20();
        final BigDecimal b = state.nextBigDecimal20();
        bh.consume(a.add(b));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 3, time = 3)
    @Measurement(iterations = 3, time = 5)
    public void additionDifferentScale(final ThreadState state, final Blackhole bh) {
        final BigDecimal a = state.nextBigDecimal();
        final BigDecimal b = state.nextBigDecimal();
        bh.consume(a.add(b));
    }

    public static void main(String[] args) throws Exception
    {
        final Options opt = new OptionsBuilder()
                .include(BigDecimalBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}