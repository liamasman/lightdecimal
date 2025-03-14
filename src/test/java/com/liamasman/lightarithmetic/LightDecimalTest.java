package com.liamasman.lightarithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    }
}