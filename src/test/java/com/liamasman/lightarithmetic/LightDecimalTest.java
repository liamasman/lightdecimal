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
            assertAdd("0", "0", "0");
            assertAdd("1", "1", "0");
            assertAdd("6", "4", "2");
            assertAdd("6.0", "4.0", "2.0");
            assertAdd("6.00", "4.00", "2.00");
            assertAdd("6.84", "4.31", "2.53");
            assertAdd("137.314", "137.000", "0.314");
            assertAdd("38303510801479704771.99237713288569866258", "13244537261834214431.42366548277345533417", "25058973539645490340.56871165011224332841");
            assertAdd(new LightDecimal(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 1, 1),
                    new LightDecimal(0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 0xFFFFFFFFL, 1, 1),
                    new LightDecimal(0L, 0L, 0L, 0L, 0, 1));
            assertAdd(new LightDecimal(0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 1, 1),
                    new LightDecimal(0xFFFFFFFFFFFFFFFFL, 0x0L, 0xFFFFFFFFFFFFFFFFL, 0x0L, 1, 1),
                    new LightDecimal(0x0L, 0xFFFFFFFFFFFFFFFFL, 0, 0xFFFFFFFFFFFFFFFFL, 1, 1));
            assertAdd(new LightDecimal(0x8000000000000000L, 0x0L, 0x0L, 0x0L, 1, 20),
                    new LightDecimal(0x7FFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFFFL, 1, 20),
                    new LightDecimal(0L, 0L, 0L, 1L, 1, 20));

        }

        @Test
        void add_negative_values_of_same_scale()
        {
            assertAdd("-1", "-1", "0");
            assertAdd("-6", "-2", "-4");
            assertAdd("-6.0", "-2.0", "-4.0");
            assertAdd("-6.7", "-2.2", "-4.5");
            assertAdd("-6.90", "-2.20", "-4.70");
            assertAdd("-6.00", "-2.00", "-4.00");
            assertAdd("-126.842344", "-126.000000", "-0.842344");
        }

        @Test
        void add_opposite_signs_of_same_scale() {
            assertAdd("0", "1", "-1");
            assertAdd("0.00", "-3.45", "3.45");
            assertAdd("-2", "2", "-4");
            assertAdd("2", "-2", "4");
            assertAdd("77.021", "78.342", "-1.321");
            assertAdd("-77.021", "-78.342", "1.321");
            assertAdd("0.00000000000000000000", "-0.00000000000000000001", "0.00000000000000000001");
            assertAdd("-0.00000000000000000002", "0.00000000000000000001", "-0.00000000000000000003");
            assertAdd("-15495694344618058519.50025048496593210042",
                    "-49783459797852745754.23872346872436782367",
                    "34287765453234687234.73847298375843572325");
            assertAdd("-25298359503841384045043476902.017610388074041301171654248122",
                    "38547252749823759872395872745.476582736872365874658236593845",
                    "-13248893245982375827352395843.458972348798324573486582345723");
        }

        private static void assertAdd(final String expected, final String a, final String b)
        {
            assertEquals(new LightDecimal(expected), new LightDecimal(a).add(new LightDecimal(b)), () -> a + " + " + b);
            assertEquals(new LightDecimal(expected), new LightDecimal(b).add(new LightDecimal(a)), () -> b + " + " + a);
        }

        private static void assertAdd(final LightDecimal expected, final LightDecimal a, final LightDecimal b)
        {
            assertEquals(expected, new LightDecimal(a).add(b), () -> a + " + " + b);
            assertEquals(expected, new LightDecimal(b).add(a), () -> b + " + " + a);
        }
    }
}