package com.liamasman.lightarithmetic;

public class Util {
    private Util() {
    }

    private static final long LOW_MASK = 0xFFFFFFFFL;

    public static long combineLongHalves(long high, long low) {
        return (high << 32) | (low & LightDecimal.LOW_MASK);
    }

    public static long getHighBits(long bytes) {
        return bytes >>> 32;
    }

    public static long getLowBits(long bytes) {
        return bytes & LightDecimal.LOW_MASK;
    }

    public static long remainderUnsignedDividedBy10(long dividend, long quotient) {
        /*
         * See Long.remainderUnsigned(long, long) for explanation
         */
        final long r = dividend - quotient * 10;
        return r - ((~(r - 10) >> (Long.SIZE - 1)) & 10);
    }
}
