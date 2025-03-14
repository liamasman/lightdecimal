package com.liamasman.lightarithmetic;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LightDecimalTest {

    @Test
    void toStringTest() {
        assertEquals("0", new LightDecimal("0").toString());
        assertEquals("1", new LightDecimal("1").toString());
        assertEquals("1.1", new LightDecimal("1.1").toString());
        assertEquals("1.01", new LightDecimal("1.01").toString());
        assertEquals("1.001", new LightDecimal("1.001").toString());
        assertEquals("1.100000000000000001", new LightDecimal("1.100000000000000001").toString());
        assertEquals("1.1000000000000000011", new LightDecimal("1.1000000000000000011").toString());
        assertEquals("1.10000000000000000111", new LightDecimal("1.10000000000000000111").toString());
        assertEquals("1.10000000000000000011", new LightDecimal("1.10000000000000000011").toString());
        assertEquals("1.10000000000000000101", new LightDecimal("1.10000000000000000101").toString());
        assertEquals("1.1000000000000000001", new LightDecimal("1.1000000000000000001").toString());
        assertEquals("1.10000000000000000001", new LightDecimal("1.10000000000000000001").toString());
        assertEquals("0.100000000000000001", new LightDecimal("0.100000000000000001").toString());
        assertEquals("0.1000000000000000011", new LightDecimal("0.1000000000000000011").toString());
        assertEquals("0.10000000000000000111", new LightDecimal("0.10000000000000000111").toString());
        assertEquals("0.10000000000000000011", new LightDecimal("0.10000000000000000011").toString());
        assertEquals("0.10000000000000000101", new LightDecimal("0.10000000000000000101").toString());
        assertEquals("0.1000000000000000001", new LightDecimal("0.1000000000000000001").toString());
        assertEquals("0.10000000000000000001", new LightDecimal("0.10000000000000000001").toString());
        assertEquals("1.000000000000000001", new LightDecimal("1.000000000000000001").toString());
        assertEquals("1.0000000000000000011", new LightDecimal("1.0000000000000000011").toString());
        assertEquals("1.00000000000000000111", new LightDecimal("1.00000000000000000111").toString());
        assertEquals("1.00000000000000000011", new LightDecimal("1.00000000000000000011").toString());
        assertEquals("1.00000000000000000101", new LightDecimal("1.00000000000000000101").toString());
        assertEquals("1.0000000000000000001", new LightDecimal("1.0000000000000000001").toString());
        assertEquals("1.00000000000000000001", new LightDecimal("1.00000000000000000001").toString());
        assertEquals("0.000000000000000001", new LightDecimal("0.000000000000000001").toString());
        assertEquals("0.0000000000000000011", new LightDecimal("0.0000000000000000011").toString());
        assertEquals("0.00000000000000000111", new LightDecimal("0.00000000000000000111").toString());
        assertEquals("0.00000000000000000011", new LightDecimal("0.00000000000000000011").toString());
        assertEquals("0.00000000000000000101", new LightDecimal("0.00000000000000000101").toString());
        assertEquals("0.0000000000000000001", new LightDecimal("0.0000000000000000001").toString());
        assertEquals("0.00000000000000000001", new LightDecimal("0.00000000000000000001").toString());
        assertEquals("123", new LightDecimal("123").toString());
        assertEquals("123.456", new LightDecimal("123.456").toString());
        assertEquals("-42", new LightDecimal("-42").toString());
        assertEquals("-42.75", new LightDecimal("-42.75").toString());
        assertEquals("0.5", new LightDecimal("0.5").toString());
        assertEquals("-0.125", new LightDecimal("-0.125").toString());
        assertEquals("9007199254740992.5", new LightDecimal("9007199254740992.5").toString());
        assertEquals("0.33333333333333333333", new LightDecimal("0.33333333333333333333").toString());
        assertEquals("123456789012345.12345678901234567891", new LightDecimal("123456789012345.12345678901234567891").toString());
        assertEquals("1.00", new LightDecimal("1.00").toString());
        assertEquals("1.10", new LightDecimal("1.10").toString());
        assertEquals("10.00", new LightDecimal("10.00").toString());
        assertEquals("1", new LightDecimal("01").toString());
        assertEquals("1.0", new LightDecimal("01.0").toString());
        assertEquals("0.00", new LightDecimal("00.00").toString());
        assertEquals("0", new LightDecimal("0000").toString());
        assertEquals("0.00005300", new LightDecimal("00.00005300").toString());
        assertEquals("500000", new LightDecimal("500000").toString());
        assertEquals("500000.0", new LightDecimal("500000.0").toString());
        assertEquals("512", new LightDecimal("00512").toString());
        assertEquals("512.0", new LightDecimal("00512.0").toString());
        assertEquals("42.635400", new LightDecimal("0000042.635400").toString());
        assertEquals("12000", new LightDecimal("000012000").toString());
        assertEquals("12000.00", new LightDecimal("000012000.00").toString());
        assertEquals("12000.0650", new LightDecimal("000012000.0650").toString());
        assertEquals("-1.00", new LightDecimal("-1.00").toString());
        assertEquals("-1.10", new LightDecimal("-1.10").toString());
        assertEquals("-10.00", new LightDecimal("-10.00").toString());
        assertEquals("-1", new LightDecimal("-01").toString());
        assertEquals("-1.0", new LightDecimal("-01.0").toString());
        assertEquals("0.00", new LightDecimal("-00.00").toString());
        assertEquals("0.00", new LightDecimal("-0.00").toString());
        assertEquals("0", new LightDecimal("-0").toString());
        assertEquals("-0.00005300", new LightDecimal("-00.00005300").toString());
        assertEquals("-500000", new LightDecimal("-500000").toString());
        assertEquals("-500000.0", new LightDecimal("-500000.0").toString());
        assertEquals("-512", new LightDecimal("-00512").toString());
        assertEquals("-512.0", new LightDecimal("-00512.0").toString());
        assertEquals("-42.635400", new LightDecimal("-0000042.635400").toString());
        assertEquals("-12000", new LightDecimal("-000012000").toString());
        assertEquals("-12000.00", new LightDecimal("-000012000.00").toString());
        assertEquals("-12000.0650", new LightDecimal("-000012000.0650").toString());
        assertEquals("473749274975984758436738476456.3523723748237424536644363", new LightDecimal("473749274975984758436738476456.3523723748237424536644363").toString());
        assertEquals("0.1", new LightDecimal(".1").toString());
        assertEquals("1", new LightDecimal("1.").toString());
    }
    
    @Test
    void toBigDecimalTest() {
        assertEquals(new BigDecimal("0"), new LightDecimal("0").toBigDecimal());
        assertEquals(new BigDecimal("1"), new LightDecimal("1").toBigDecimal());
        assertEquals(new BigDecimal("1.1"), new LightDecimal("1.1").toBigDecimal());
        assertEquals(new BigDecimal("1.01"), new LightDecimal("1.01").toBigDecimal());
        assertEquals(new BigDecimal("1.001"), new LightDecimal("1.001").toBigDecimal());
        assertEquals(new BigDecimal("1.100000000000000001"), new LightDecimal("1.100000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("1.1000000000000000011"), new LightDecimal("1.1000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("1.10000000000000000111"), new LightDecimal("1.10000000000000000111").toBigDecimal());
        assertEquals(new BigDecimal("1.10000000000000000011"), new LightDecimal("1.10000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("1.10000000000000000101"), new LightDecimal("1.10000000000000000101").toBigDecimal());
        assertEquals(new BigDecimal("1.1000000000000000001"), new LightDecimal("1.1000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("1.10000000000000000001"), new LightDecimal("1.10000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.100000000000000001"), new LightDecimal("0.100000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.1000000000000000011"), new LightDecimal("0.1000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("0.10000000000000000111"), new LightDecimal("0.10000000000000000111").toBigDecimal());
        assertEquals(new BigDecimal("0.10000000000000000011"), new LightDecimal("0.10000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("0.10000000000000000101"), new LightDecimal("0.10000000000000000101").toBigDecimal());
        assertEquals(new BigDecimal("0.1000000000000000001"), new LightDecimal("0.1000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.10000000000000000001"), new LightDecimal("0.10000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("1.000000000000000001"), new LightDecimal("1.000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("1.0000000000000000011"), new LightDecimal("1.0000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("1.00000000000000000111"), new LightDecimal("1.00000000000000000111").toBigDecimal());
        assertEquals(new BigDecimal("1.00000000000000000011"), new LightDecimal("1.00000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("1.00000000000000000101"), new LightDecimal("1.00000000000000000101").toBigDecimal());
        assertEquals(new BigDecimal("1.0000000000000000001"), new LightDecimal("1.0000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("1.00000000000000000001"), new LightDecimal("1.00000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.000000000000000001"), new LightDecimal("0.000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.0000000000000000011"), new LightDecimal("0.0000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("0.00000000000000000111"), new LightDecimal("0.00000000000000000111").toBigDecimal());
        assertEquals(new BigDecimal("0.00000000000000000011"), new LightDecimal("0.00000000000000000011").toBigDecimal());
        assertEquals(new BigDecimal("0.00000000000000000101"), new LightDecimal("0.00000000000000000101").toBigDecimal());
        assertEquals(new BigDecimal("0.0000000000000000001"), new LightDecimal("0.0000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("0.00000000000000000001"), new LightDecimal("0.00000000000000000001").toBigDecimal());
        assertEquals(new BigDecimal("123"), new LightDecimal("123").toBigDecimal());
        assertEquals(new BigDecimal("123.456"), new LightDecimal("123.456").toBigDecimal());
        assertEquals(new BigDecimal("-42"), new LightDecimal("-42").toBigDecimal());
        assertEquals(new BigDecimal("-42.75"), new LightDecimal("-42.75").toBigDecimal());
        assertEquals(new BigDecimal("0.5"), new LightDecimal("0.5").toBigDecimal());
        assertEquals(new BigDecimal("-0.125"), new LightDecimal("-0.125").toBigDecimal());
        assertEquals(new BigDecimal("9007199254740992.5"), new LightDecimal("9007199254740992.5").toBigDecimal());
        assertEquals(new BigDecimal("0.33333333333333333333"), new LightDecimal("0.33333333333333333333").toBigDecimal());
        assertEquals(new BigDecimal("123456789012345.12345678901234567891"), new LightDecimal("123456789012345.12345678901234567891").toBigDecimal());
        assertEquals(new BigDecimal("1.00"), new LightDecimal("1.00").toBigDecimal());
        assertEquals(new BigDecimal("1.10"), new LightDecimal("1.10").toBigDecimal());
        assertEquals(new BigDecimal("10.00"), new LightDecimal("10.00").toBigDecimal());
        assertEquals(new BigDecimal("01"), new LightDecimal("01").toBigDecimal());
        assertEquals(new BigDecimal("01.0"), new LightDecimal("01.0").toBigDecimal());
        assertEquals(new BigDecimal("00.00"), new LightDecimal("00.00").toBigDecimal());
        assertEquals(new BigDecimal("0000"), new LightDecimal("0000").toBigDecimal());
        assertEquals(new BigDecimal("00.00005300"), new LightDecimal("00.00005300").toBigDecimal());
        assertEquals(new BigDecimal("500000"), new LightDecimal("500000").toBigDecimal());
        assertEquals(new BigDecimal("500000.0"), new LightDecimal("500000.0").toBigDecimal());
        assertEquals(new BigDecimal("00512"), new LightDecimal("00512").toBigDecimal());
        assertEquals(new BigDecimal("00512.0"), new LightDecimal("00512.0").toBigDecimal());
        assertEquals(new BigDecimal("0000042.635400"), new LightDecimal("0000042.635400").toBigDecimal());
        assertEquals(new BigDecimal("000012000"), new LightDecimal("000012000").toBigDecimal());
        assertEquals(new BigDecimal("000012000.00"), new LightDecimal("000012000.00").toBigDecimal());
        assertEquals(new BigDecimal("000012000.0650"), new LightDecimal("000012000.0650").toBigDecimal());
        assertEquals(new BigDecimal("-1.00"), new LightDecimal("-1.00").toBigDecimal());
        assertEquals(new BigDecimal("-1.10"), new LightDecimal("-1.10").toBigDecimal());
        assertEquals(new BigDecimal("-10.00"), new LightDecimal("-10.00").toBigDecimal());
        assertEquals(new BigDecimal("-01"), new LightDecimal("-01").toBigDecimal());
        assertEquals(new BigDecimal("-01.0"), new LightDecimal("-01.0").toBigDecimal());
        assertEquals(new BigDecimal("-00.00"), new LightDecimal("-00.00").toBigDecimal());
        assertEquals(new BigDecimal("-0.00"), new LightDecimal("-0.00").toBigDecimal());
        assertEquals(new BigDecimal("-0"), new LightDecimal("-0").toBigDecimal());
        assertEquals(new BigDecimal("-00.00005300"), new LightDecimal("-00.00005300").toBigDecimal());
        assertEquals(new BigDecimal("-500000"), new LightDecimal("-500000").toBigDecimal());
        assertEquals(new BigDecimal("-500000.0"), new LightDecimal("-500000.0").toBigDecimal());
        assertEquals(new BigDecimal("-00512"), new LightDecimal("-00512").toBigDecimal());
        assertEquals(new BigDecimal("-00512.0"), new LightDecimal("-00512.0").toBigDecimal());
        assertEquals(new BigDecimal("-0000042.635400"), new LightDecimal("-0000042.635400").toBigDecimal());
        assertEquals(new BigDecimal("-000012000"), new LightDecimal("-000012000").toBigDecimal());
        assertEquals(new BigDecimal("-000012000.00"), new LightDecimal("-000012000.00").toBigDecimal());
        assertEquals(new BigDecimal("-000012000.0650"), new LightDecimal("-000012000.0650").toBigDecimal());
    }

    @Test
    void constructFromStringTest() {
        assertThrows(NumberFormatException.class, () -> new LightDecimal("1.1.1"), "Should not allow multiple decimal points");
        assertThrows(NumberFormatException.class, () -> new LightDecimal(""), "Should not allow empty string");
        assertThrows(NumberFormatException.class, () -> new LightDecimal(" "), "Should not allow blank");
        assertThrows(NumberFormatException.class, () -> new LightDecimal(" 1"), "Should not allow whitespace at start");
        assertThrows(NumberFormatException.class, () -> new LightDecimal("1 "), "Should not allow whitespace at end");
        assertThrows(NumberFormatException.class, () -> new LightDecimal(" 1 "), "Should not allow whitespace around number");
    }

    @Nested
    class mutableAddTest {
        @Test
        void add_positive_values_of_same_scale() {
            assertEquals(new LightDecimal("4"), new LightDecimal("2").add(new LightDecimal("2")));
            assertEquals(new LightDecimal("4.0"), new LightDecimal("2.0").add(new LightDecimal("2.0")));
            assertEquals(new LightDecimal("4.00"), new LightDecimal("2.00").add(new LightDecimal("2.00")));
            assertEquals(new LightDecimal("137.314"), new LightDecimal("137.000").add(new LightDecimal("0.314")));
            assertEquals(new LightDecimal(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 1, 1),
                    new LightDecimal(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 1, 1).add(new LightDecimal(0L, 0L, 0L, 0L, 0, 1)));
            assertEquals(new LightDecimal(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 1, 1),
                    new LightDecimal(0xFFFFFFFFFFFFFFFFL, 0x0L, 0xFFFFFFFFFFFFFFFFL, 0x0L, 1, 1).add(new LightDecimal(0x0L, 0xFFFFFFFFFFFFFFFFL, 0, 0xFFFFFFFFFFFFFFFFL, 1, 1)));
            assertEquals(new LightDecimal(0x8000000000000000L, 0x0L, 0x0L, 0x0L, 1, 20),
                    new LightDecimal(0x7FFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 1, 20).add(new LightDecimal(0L, 0L, 0L, 1L, 1, 20)));

        }
    }
}