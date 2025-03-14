package com.liamasman.lightarithmetic;

import java.math.BigDecimal;
import java.util.Objects;

public class LightDecimal implements Comparable<LightDecimal>, Cloneable {
    public static final long[] POWERS_OF_10 = new long[]{
            1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L,
            100000000L, 1000000000L, 10000000000L, 100000000000L,
            1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L,
            10000000000000000L, 100000000000000000L, 1000000000000000000L
    };
    private static final long[] POWERS_OF_TEN = {
            1L,
            10L,
            100L,
            1000L,
            10000L,
            100000L,
            1000000L,
            10000000L,
            100000000L,
            1000000000L,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L
    };

    private static final double LOG_TWO_BASE_TEN = Math.log(2.0) / Math.log(10.0);

    /*
     * Stores the significand as an integer in big-endian format, where bytes3
     * contains the least significant byte and bytes0 contains the most
     * significant byte.
     */
    private long bytes0;
    private long bytes1;
    private long bytes2;
    private long bytes3;
    private int signum;
    private int scale;
    private int precision;

    public LightDecimal(final CharSequence in) {
        this(in, 0, in.length());
    }

    public LightDecimal(final CharSequence in, final int offset, final int len) {
        try {
            Objects.checkFromIndexSize(offset, len, in.length());
        } catch (IndexOutOfBoundsException e) {
            throw new NumberFormatException
                    ("Bad offset or len arguments for CharSequence input.");
        }

        long tmpScale = 0;
        try {
            int cursor = offset;
            int length = len;
            boolean isNegative = false;

            // Handle the sign
            if (in.charAt(cursor) == '-') {
                isNegative = true;
                cursor++;
                length--;
            } else if (in.charAt(cursor) == '+') {
                cursor++;
                length--;
            }

            // Handle numeric part
            boolean dot = false;        // true if decimal point seen
            char c;                     // current character

            for (; length >0; cursor++, length--) {
                c = in.charAt(cursor);

                if ((c >= '0' && c <= '9') || Character.isDigit(c)) {
                    int characterValue = Character.digit(c, 10);
                    if (characterValue == 0) {
                        if (precision == 0) {
                            precision = 1;
                        } else {
                            multiplyBy10AndAdd(characterValue);
                            ++precision;
                        }
                    } else {
                        if (precision != 1) {
                            ++precision;
                        }
                        multiplyBy10AndAdd(characterValue);
                    }
                    if (dot) {
                        tmpScale++;
                    }
                    continue;
                }
                if (c == '.') {
                    if (dot) {
                        throw new NumberFormatException("Multiple decimal points.");
                    }
                    dot = true;
                    continue;
                }
                // TODO handle exponents?
                break; //saves a test
            }

            //All characters processed
            if (precision == 0) {
                throw new NumberFormatException("No digits found.");
            }

            if ((bytes0 | bytes1 | bytes2 | bytes3) == 0) {
                signum = 0;
            } else {
                signum = isNegative ? -1 : 1;
            }

        } catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException e) {
            final NumberFormatException nfe = new NumberFormatException();
            nfe.initCause(e);
            throw nfe;
        }
        if ((int) tmpScale != tmpScale) {
            throw new NumberFormatException("Exponent overflow.");
        }
        scale = (int) tmpScale;
    }

    private void multiplyBy10AndAdd(final int digit) {
        // In order to capture overflow, treat each long as two 32-bit words in a 64-bit long workspace,
        // carrying between each 32-bit word.
        long carry = digit;
        long highBits;
        long lowBits;
        //byte 3
        highBits = bytes3 >>> 32;
        lowBits = bytes3 & 0xFFFFFFFFL;
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes3 = (highBits << 32) | (lowBits & 0xFFFFFFFFL);
        
        //byte 2
        highBits = bytes2 >>> 32;
        lowBits = bytes2 & 0xFFFFFFFFL;
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes2 = (highBits << 32) | (lowBits & 0xFFFFFFFFL);
        
        //byte 1
        highBits = bytes1 >>> 32;
        lowBits = bytes1 & 0xFFFFFFFFL;
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes1 = (highBits << 32) | (lowBits & 0xFFFFFFFFL);
        
        //byte 0
        highBits = bytes0 >>> 32;
        lowBits = bytes0 & 0xFFFFFFFFL;
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        if (carry != 0) {
            throw new ArithmeticException("Overflow");
        }
        bytes0 = (highBits << 32) | (lowBits & 0xFFFFFFFFL);
    }

    public LightDecimal negate() {
        signum *= -1;
        return this;
    }

    public LightDecimal(final LightDecimal lightDecimal) {
        bytes0 = lightDecimal.bytes0;
        bytes1 = lightDecimal.bytes1;
        bytes2 = lightDecimal.bytes2;
        bytes3 = lightDecimal.bytes3;
        scale = lightDecimal.scale;
        precision = lightDecimal.precision;
    }

    private LightDecimal(long bytes0, long bytes1, long bytes2, long bytes3, int scale, int precision) {
        this.bytes0 = bytes0;
        this.bytes1 = bytes1;
        this.bytes2 = bytes2;
        this.bytes3 = bytes3;
        this.scale = scale;
        this.precision = precision;
    }

    public LightDecimal add(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal add(LightDecimal a, LightDecimal b) {
        return null;
    }

    public LightDecimal subtract(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal subtract(LightDecimal a, LightDecimal b) {
        return null;
    }

    public LightDecimal multiply(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal multiply(LightDecimal a, LightDecimal b) {
        return null;
    }

    public LightDecimal divide(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal divide(LightDecimal a, LightDecimal b) {
        return null;
    }

    public LightDecimal min(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal min(LightDecimal a, LightDecimal b) {
        return null;
    }

    public LightDecimal max(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal max(LightDecimal a, LightDecimal b) {
        return null;
    }

    public int signum() {
        return signum;
    }

    private boolean isZero()
    {
        return signum() == 0;
    }

    private boolean isNegative()
    {
        return (bytes0 & 0x8000000000000000L) != 0;
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(toString());
    }

    @Override
    public int compareTo(LightDecimal o) {
        //TODO
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LightDecimal other)) {
            return false;
        }
        return bytes3 == other.bytes3 &&
                bytes2 == other.bytes2 &&
                bytes1 == other.bytes1 &&
                bytes0 == other.bytes0 &&
                scale == other.scale &&
                precision == other.precision;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bytes3) ^
                Long.hashCode(bytes2) ^
                Long.hashCode(bytes1) ^
                Long.hashCode(bytes0) ^
                Integer.hashCode(scale) ^
                Integer.hashCode(precision);
    }

    @Override
    public String toString() {
        //Ensure buffer has enough space for the largest possible number, plus
        // a decimal point and a minus sign.
        int b = bitLength();
        int numChars = (int) (Math.floor(b * LOG_TWO_BASE_TEN) + 1) +
                (signum() < 0 ? 1 : 0) +
                (scale > 0 ? 1 : 0);

        final StringBuilder sb = new StringBuilder(numChars);
        if (b <= 63) {
            sb.append(bytes3);
        } else {
            toIntegerString(sb, bytes0, bytes1, bytes2, bytes3);
        }

        if (scale <= 0) { //No decimal point
            if (signum() < 0) {
                sb.insert(0, '-');
            }
            //TODO add trailing zeros
            return sb.toString();
        }

        // Insert decimal point
        int insertionPoint = sb.length() - scale;
        if (insertionPoint == 0) {
            sb.insert(insertionPoint, signum() < 0 ? "-0." : "0.");
        } else if (insertionPoint > 0) {
            if (signum() < 0) {
                sb.insert(0, '-');
                sb.insert(insertionPoint + 1, '.');
            } else {
                sb.insert(insertionPoint, '.');
            }
        } else {
            //Insert leading zeros
            sb.insert(0, signum() < 0 ? "-0." : "0.");
            for (int i = 0; i < -insertionPoint; i++) {
                sb.insert(signum() < 0 ? 3 : 2, '0');
            }
        }
        return sb.toString();
    }

    private static void toIntegerString(final StringBuilder sb, final long bytes0, final long bytes1, final long bytes2, final long bytes3) {
        // Long division
        long highBytes0 = bytes0 >>> 32;
        long lowBytes0 = bytes0 & 0xFFFFFFFFL;
        long highBytes1 = bytes1 >>> 32;
        long lowBytes1 = bytes1 & 0xFFFFFFFFL;
        long highBytes2 = bytes2 >>> 32;
        long lowBytes2 = bytes2 & 0xFFFFFFFFL;
        long highBytes3 = bytes3 >>> 32;
        long lowBytes3 = bytes3 & 0xFFFFFFFFL;

        while ((highBytes0 | lowBytes0 | highBytes1 | lowBytes1 | highBytes2 | lowBytes2 | highBytes3 | lowBytes3) != 0) {
            long remainder;
            long temp;

            temp = highBytes0;
            highBytes0 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, highBytes0);

            temp =(remainder << 32) | lowBytes0;
            lowBytes0 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, lowBytes0);

            temp = (remainder << 32) | highBytes1;
            highBytes1 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, highBytes1);

            temp = (remainder << 32) | lowBytes1;
            lowBytes1 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, lowBytes1);

            temp = (remainder << 32) | highBytes2;
            highBytes2 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, highBytes2);

            temp = (remainder << 32) | lowBytes2;
            lowBytes2 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, lowBytes2);

            temp = (remainder << 32) | highBytes3;
            highBytes3 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, highBytes3);

            temp = (remainder << 32) | lowBytes3;
            lowBytes3 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, lowBytes3);

            sb.append((char) ('0' + remainder));
        }
        sb.reverse();
    }
    
    private static long remainderUnsignedDividedBy10(long dividend, long quotient) {
        /*
         * See Long.remainderUnsigned(long, long) for explanation
         */
        final long r = dividend - quotient * 10;
        return r - ((~(r - 10) >> (Long.SIZE - 1)) & 10);
    }

    private int bitLength() {
        if (bytes0 != 0) {
            return 64 * 4 - Long.numberOfLeadingZeros(bytes0);
        }
        if (bytes1 != 0) {
            return 64 * 3 - Long.numberOfLeadingZeros(bytes1);
        }
        if (bytes2 != 0) {
            return 64 * 2 - Long.numberOfLeadingZeros(bytes2);
        }
        if (bytes3 != 0) {
            return 64 - Long.numberOfLeadingZeros(bytes3);
        }
        return 0;
    }

    @Override
    public LightDecimal clone() {
        try {
            return (LightDecimal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
