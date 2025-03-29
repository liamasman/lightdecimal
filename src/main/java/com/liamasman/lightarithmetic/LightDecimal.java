package com.liamasman.lightarithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class LightDecimal implements Comparable<LightDecimal>, Cloneable {
    private static final double LOG_TWO_BASE_TEN = Math.log(2.0) / Math.log(10.0);
    private static final long HIGH_MASK = 0xFFFFFFFFL << 32;
    private static final long LOW_MASK = 0xFFFFFFFFL;
    private static final int[] POWERS_OF_TEN = {1, 10, 100, 1000, 10000, 100000,
            1000000, 10000000, 100000000, 1000000000};

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

    public LightDecimal(final CharSequence in) {
        this(in, 0, in.length());
    }

    public LightDecimal(final CharSequence in, final int offset, final int len) {
        if (len == 0) {
            throw new NumberFormatException("Empty input.");
        }

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
            int digitsSeen = 0;

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
            for (; length > 0; cursor++, length--) {
                c = in.charAt(cursor);

                if ((c >= '0' && c <= '9') || Character.isDigit(c)) {
                    int characterValue = Character.digit(c, 10);
                    multiplyBy10AndAdd(characterValue);
                    ++digitsSeen;
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
                throw new NumberFormatException("Unexpected character: " + c);
            }

            //All characters processed
            if (digitsSeen == 0) {
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

    public LightDecimal(final LightDecimal lightDecimal) {
        bytes0 = lightDecimal.bytes0;
        bytes1 = lightDecimal.bytes1;
        bytes2 = lightDecimal.bytes2;
        bytes3 = lightDecimal.bytes3;
        signum = lightDecimal.signum;
        scale = lightDecimal.scale;
    }

    LightDecimal(long bytes0, long bytes1, long bytes2, long bytes3, int signum, int scale) {
        this.bytes0 = bytes0;
        this.bytes1 = bytes1;
        this.bytes2 = bytes2;
        this.bytes3 = bytes3;
        this.signum = signum;
        this.scale = scale;
    }

    public void copyFrom(final LightDecimal val) {
        bytes0 = val.bytes0;
        bytes1 = val.bytes1;
        bytes2 = val.bytes2;
        bytes3 = val.bytes3;
        signum = val.signum;
        scale = val.scale;
    }

    public LightDecimal negate() {
        signum *= -1;
        return this;
    }

    public LightDecimal add(final LightDecimal val) {
        if (this.scale == val.scale) {
            if (val.isZero()) {
                this.scale = Math.max(this.scale, val.scale);
            } else if (this.isZero()) {
                copyFrom(val);
                return this;
            } else if (val.signum == signum) {
                addSameScaleSameSignum(val);
            } else {
                addSameScaleDifferentSignum(val);
            }
        } else {
            if (this.isZero() && val.isZero()) {
                this.scale = Math.max(this.scale, val.scale);
            } else {
                addDifferentScale(val);
            }
        }

        return this;
    }

    private void addSameScaleSameSignum(final LightDecimal val) {
        addSameScaleSameSignum(this.bytes0, this.bytes1, this.bytes2, this.bytes3,
                val.bytes0, val.bytes1, val.bytes2, val.bytes3);
    }

    private static long getHighBits(long bytes) {
        return bytes >>> 32;
    }

    private static long getLowBits(long bytes) {
        return bytes & LOW_MASK;
    }

    private void addSameScaleDifferentSignum(final LightDecimal val) {
        addSameScaleDifferentSignum(signum, bytes0, bytes1, bytes2, bytes3, val.bytes0, val.bytes1, val.bytes2, val.bytes3);
    }

    private void addSameScaleDifferentSignum(int aSignum, long aBytes0, long aBytes1, long aBytes2, long aBytes3,
                                             long bBytes0, long bBytes1, long bBytes2, long bBytes3) {
        int cmp = compareMagnitude(aBytes0, aBytes1, aBytes2, aBytes3,
                bBytes0, bBytes1, bBytes2, bBytes3);
        if (cmp == 0) {
            setToZeroPreserveScale();
            return;
        }

        if (cmp > 0) //This is the bigger number
        {
            subtractBytes(aBytes0, aBytes1, aBytes2, aBytes3, bBytes0, bBytes1, bBytes2, bBytes3);
        } else {
            subtractBytes(bBytes0, bBytes1, bBytes2, bBytes3, aBytes0, aBytes1, aBytes2, aBytes3);
        }
        signum = aSignum == cmp ? 1 : -1;
    }

    private void addDifferentScale(final LightDecimal val) {
        int scaleDiff = Math.abs(this.scale - val.scale); // Anti-branching candidate

        if (scaleDiff < POWERS_OF_TEN.length) {
            addDifferentScaleSmallScaleDiff(val, scaleDiff);
        } else {
            //TODO
            throw new ArithmeticException("Scale difference too large");
        }
    }

    private void addDifferentScaleSmallScaleDiff(final LightDecimal val, final int scaleDiff) {
        final LightDecimal bigScale;
        final LightDecimal smallScale;
        if (this.scale < val.scale) {
            bigScale = val;
            smallScale = this;
        } else {
            bigScale = this;
            smallScale = val;
        }

        if (smallScale.isZero()) {
            copyFrom(bigScale);
            return;
        }

        this.scale = bigScale.scale;

        //multiply lower scale number by 10^scaleDiff
        long bytes0high = getHighBits(smallScale.bytes0);
        long bytes0low = getLowBits(smallScale.bytes0);
        long bytes1high = getHighBits(smallScale.bytes1);
        long bytes1low = getLowBits(smallScale.bytes1);
        long bytes2high = getHighBits(smallScale.bytes2);
        long bytes2low = getLowBits(smallScale.bytes2);
        long bytes3high = getHighBits(smallScale.bytes3);
        long bytes3low = getLowBits(smallScale.bytes3);

        final int multiplier = POWERS_OF_TEN[scaleDiff];

        long carry = 0;
        bytes3low = bytes3low * multiplier + carry;
        carry = getHighBits(bytes3low);

        bytes3high = bytes3high * multiplier + carry;
        carry = getHighBits(bytes3high);

        bytes2low = bytes2low * multiplier + carry;
        carry = getHighBits(bytes2low);

        bytes2high = bytes2high * multiplier + carry;
        carry = getHighBits(bytes2high);

        bytes1low = bytes1low * multiplier + carry;
        carry = getHighBits(bytes1low);

        bytes1high = bytes1high * multiplier + carry;
        carry = getHighBits(bytes1high);

        bytes0low = bytes0low * multiplier + carry;
        carry = getHighBits(bytes0low);

        bytes0high = bytes0high * multiplier + carry;
        carry = getHighBits(bytes0high);
        if (carry != 0) {
            throw new ArithmeticException("Overflow");
        }

        // Now we have the smallScale number in the same scale as bigScale
        if (bigScale.isZero()) {
            // Nothing to add
            this.bytes0 = combineLongHalves(bytes0high, bytes0low);
            this.bytes1 = combineLongHalves(bytes1high, bytes1low);
            this.bytes2 = combineLongHalves(bytes2high, bytes2low);
            this.bytes3 = combineLongHalves(bytes3high, bytes3low);
            this.signum = smallScale.signum;
            return;
        }

        // Add the two numbers together
        add(smallScale.signum, combineLongHalves(bytes0high, bytes0low), combineLongHalves(bytes1high, bytes1low), combineLongHalves(bytes2high, bytes2low), combineLongHalves(bytes3high, bytes3low),
                bigScale.signum, bigScale.bytes0, bigScale.bytes1, bigScale.bytes2, bigScale.bytes3);
    }

    private void add(int aSignum, long aBytes0, long aBytes1, long aBytes2, long aBytes3, int bSignum, long bBytes0, long bBytes1, long bBytes2, long bBytes3) {
        if (aSignum == bSignum) {
            addSameScaleSameSignum(aBytes0, aBytes1, aBytes2, aBytes3, bBytes0, bBytes1, bBytes2, bBytes3);
        } else {
            addSameScaleDifferentSignum(aSignum, aBytes0, aBytes1, aBytes2, aBytes3, bBytes0, bBytes1, bBytes2, bBytes3);
        }
    }

    private void addSameScaleSameSignum(long aBytes0, long aBytes1, long aBytes2, long aBytes3, long bBytes0, long bBytes1, long bBytes2, long bBytes3) {
        // Long addition using 32-bit words
        long carry = 0;
        long highBits;
        long highBitsVal;
        long lowBits;
        long lowBitsVal;

        //byte 3
        highBits = getHighBits(aBytes3);
        lowBits = getLowBits(aBytes3);
        highBitsVal = getHighBits(bBytes3);
        lowBitsVal = getLowBits(bBytes3);
        lowBits = lowBits + lowBitsVal + carry;
        carry = lowBits >>> 32;
        highBits = highBits + highBitsVal + carry;
        carry = highBits >>> 32;
        bytes3 = combineLongHalves(highBits, lowBits);

        //byte 2
        highBits = getHighBits(aBytes2);
        lowBits = getLowBits(aBytes2);
        highBitsVal = getHighBits(bBytes2);
        lowBitsVal = getLowBits(bBytes2);
        lowBits = lowBits + lowBitsVal + carry;
        carry = lowBits >>> 32;
        highBits = highBits + highBitsVal + carry;
        carry = highBits >>> 32;
        bytes2 = combineLongHalves(highBits, lowBits);

        //byte 1
        highBits = getHighBits(aBytes1);
        lowBits = getLowBits(aBytes1);
        highBitsVal = getHighBits(bBytes1);
        lowBitsVal = getLowBits(bBytes1);
        lowBits = lowBits + lowBitsVal + carry;
        carry = lowBits >>> 32;
        highBits = highBits + highBitsVal + carry;
        carry = highBits >>> 32;
        bytes1 = combineLongHalves(highBits, lowBits);

        //byte 0
        highBits = getHighBits(aBytes0);
        lowBits = getLowBits(aBytes0);
        highBitsVal = getHighBits(bBytes0);
        lowBitsVal = getLowBits(bBytes0);
        lowBits = lowBits + lowBitsVal + carry;
        carry = lowBits >>> 32;
        highBits = highBits + highBitsVal + carry;
        carry = highBits >>> 32;
        if (carry != 0) {
            throw new ArithmeticException("Overflow");
        }
        bytes0 = combineLongHalves(highBits, lowBits);
    }

    /**
     * Subtract the given bytes from our bytes.
     * Our bytes must represent the larger number.
     * Adapted from BigInteger
     */
    private void subtractBytes(
            final long bigBytes0, final long bigBytes1, final long bigBytes2, final long bigBytes3,
            final long littleBytes0, final long littleBytes1, final long littleBytes2, final long littleBytes3) {

        long difference = 0;

        // Subtract common parts of both numbers
        for (int index = 7; index >= 0; index--) {
            long big = get32BitWordFromLongsWithWordIndex(bigBytes0, bigBytes1, bigBytes2, bigBytes3, index);
            long little = get32BitWordFromLongsWithWordIndex(littleBytes0, littleBytes1, littleBytes2, littleBytes3, index);
            difference = big - little + (difference >> 32);
            switch (index) {
                case 0:
                    this.bytes0 = (difference << 32) | getLowBits(this.bytes0);
                    break;
                case 1:
                    this.bytes0 = (this.bytes0 & HIGH_MASK) | getLowBits(difference);
                    break;
                case 2:
                    this.bytes1 = (difference << 32) | getLowBits(this.bytes1);
                    break;
                case 3:
                    this.bytes1 = (this.bytes1 & HIGH_MASK) | getLowBits(difference);
                    break;
                case 4:
                    this.bytes2 = (difference << 32) | getLowBits(this.bytes2);
                    break;
                case 5:
                    this.bytes2 = (this.bytes2 & HIGH_MASK) | getLowBits(difference);
                    break;
                case 6:
                    this.bytes3 = (difference << 32) | getLowBits(this.bytes3);
                    break;
                case 7:
                    this.bytes3 = (this.bytes3 & HIGH_MASK) | getLowBits(difference);
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private long get32BitWordFromLongsWithWordIndex(
            long bytes0,
            long bytes1,
            long bytes2,
            long bytes3,
            int index) {
        return switch (index) {
            case 0 -> getHighBits(bytes0);
            case 1 -> getLowBits(bytes0);
            case 2 -> getHighBits(bytes1);
            case 3 -> getLowBits(bytes1);
            case 4 -> getHighBits(bytes2);
            case 5 -> getLowBits(bytes2);
            case 6 -> getHighBits(bytes3);
            case 7 -> getLowBits(bytes3);
            default -> throw new IllegalStateException();
        };
    }

    private int compareMagnitude(long aBytes0, long aBytes1, long aBytes2, long aBytes3, long bBytes0, long bBytes1, long bBytes2, long bBytes3) {
        if (aBytes0 != bBytes0) {
            return Long.compareUnsigned(aBytes0, bBytes0);
        }
        if (aBytes1 != bBytes1) {
            return Long.compareUnsigned(aBytes1, bBytes1);
        }
        if (aBytes2 != bBytes2) {
            return Long.compareUnsigned(aBytes2, bBytes2);
        }
        if (aBytes3 != bBytes3) {
            return Long.compareUnsigned(aBytes3, bBytes3);
        }
        return 0;
    }

    /**
     * Returns a new LightDecimal whose value is the sum of the two
     * operands. The original operands are not modified.
     */
    public static LightDecimal add(LightDecimal a, LightDecimal b) {
        return new LightDecimal(a).add(b);
    }

    public LightDecimal subtract(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal subtract(LightDecimal a, LightDecimal b) {
        return new LightDecimal(a).subtract(b);
    }

    public LightDecimal multiply(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal multiply(LightDecimal a, LightDecimal b) {
        return new LightDecimal(a).multiply(b);
    }

    public LightDecimal divide(LightDecimal lightDecimal) {
        return this;
    }

    public static LightDecimal divide(LightDecimal a, LightDecimal b) {
        return new LightDecimal(a).divide(b);
    }

    public LightDecimal min(LightDecimal other) {
        return this.compareTo(other) >= 0 ? other : this;
    }

    public static LightDecimal min(LightDecimal a, LightDecimal b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public LightDecimal max(LightDecimal other) {
        return this.compareTo(other) <= 0 ? other : this;
    }

    public static LightDecimal max(LightDecimal a, LightDecimal b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    public int signum() {
        return signum;
    }

    private void multiplyBy10AndAdd(final int digit) {
        // In order to capture overflow, treat each long as two 32-bit words in a 64-bit long workspace,
        // carrying between each 32-bit word.
        long carry = digit;
        long highBits;
        long lowBits;
        //byte 3
        highBits = getHighBits(bytes3);
        lowBits = getLowBits(bytes3);
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes3 = (highBits << 32) | (lowBits & LOW_MASK);

        if ((bytes2 | bytes1 | bytes0 | carry) == 0) {
            return;
        }

        //byte 2
        highBits = getHighBits(bytes2);
        lowBits = getLowBits(bytes2);
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes2 = (highBits << 32) | (lowBits & LOW_MASK);

        if ((bytes1 | bytes0 | carry) == 0) {
            return;
        }

        //byte 1
        highBits = getHighBits(bytes1);
        lowBits = getLowBits(bytes1);
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        bytes1 = (highBits << 32) | (lowBits & LOW_MASK);

        if ((bytes0 | carry) == 0) {
            return;
        }

        //byte 0
        highBits = getHighBits(bytes0);
        lowBits = getLowBits(bytes0);
        lowBits = lowBits * 10 + carry;
        carry = lowBits >>> 32;
        highBits = highBits * 10 + carry;
        carry = highBits >>> 32;
        if (carry != 0) {
            throw new ArithmeticException("Overflow");
        }
        bytes0 = (highBits << 32) | (lowBits & LOW_MASK);
    }

    private void setToZeroPreserveScale() {
        bytes0 = 0;
        bytes1 = 0;
        bytes2 = 0;
        bytes3 = 0;
        signum = 0;
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
                signum == other.signum;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(bytes3) ^
                Long.hashCode(bytes2) ^
                Long.hashCode(bytes1) ^
                Long.hashCode(bytes0) ^
                Integer.hashCode(scale);
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
            // Significand is stored in a single long, so no need to do long division
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
        // Long division on 32-bit words
        long highBytes0 = getHighBits(bytes0);
        long lowBytes0 = getLowBits(bytes0);
        long highBytes1 = getHighBits(bytes1);
        long lowBytes1 = getLowBits(bytes1);
        long highBytes2 = getHighBits(bytes2);
        long lowBytes2 = getLowBits(bytes2);
        long highBytes3 = getHighBits(bytes3);
        long lowBytes3 = getLowBits(bytes3);

        while ((highBytes0 | lowBytes0 | highBytes1 | lowBytes1 | highBytes2 | lowBytes2 | highBytes3 | lowBytes3) != 0) {
            long remainder;
            long temp;

            temp = highBytes0;
            highBytes0 = Long.divideUnsigned(temp, 10);
            remainder = remainderUnsignedDividedBy10(temp, highBytes0);

            temp = (remainder << 32) | lowBytes0;
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

    public BigDecimal toBigDecimal() {
        final BigInteger bi = new BigInteger(
                signum(),
                new byte[]{
                        (byte) (bytes0 >>> 56),
                        (byte) (bytes0 >>> 48),
                        (byte) (bytes0 >>> 40),
                        (byte) (bytes0 >>> 32),
                        (byte) (bytes0 >>> 24),
                        (byte) (bytes0 >>> 16),
                        (byte) (bytes0 >>> 8),
                        (byte) bytes0,
                        (byte) (bytes1 >>> 56),
                        (byte) (bytes1 >>> 48),
                        (byte) (bytes1 >>> 40),
                        (byte) (bytes1 >>> 32),
                        (byte) (bytes1 >>> 24),
                        (byte) (bytes1 >>> 16),
                        (byte) (bytes1 >>> 8),
                        (byte) bytes1,
                        (byte) (bytes2 >>> 56),
                        (byte) (bytes2 >>> 48),
                        (byte) (bytes2 >>> 40),
                        (byte) (bytes2 >>> 32),
                        (byte) (bytes2 >>> 24),
                        (byte) (bytes2 >>> 16),
                        (byte) (bytes2 >>> 8),
                        (byte) bytes2,
                        (byte) (bytes3 >>> 56),
                        (byte) (bytes3 >>> 48),
                        (byte) (bytes3 >>> 40),
                        (byte) (bytes3 >>> 32),
                        (byte) (bytes3 >>> 24),
                        (byte) (bytes3 >>> 16),
                        (byte) (bytes3 >>> 8),
                        (byte) bytes3
                });
        return new BigDecimal(bi, scale);
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

    private boolean isZero() {
        return signum() == 0;
    }

    @Override
    public LightDecimal clone() {
        try {
            return (LightDecimal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    private long combineLongHalves(long high, long low) {
        return (high << 32) | (low & LOW_MASK);
    }
}
