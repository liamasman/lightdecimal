# LightDecimal

When working with financial numbers, we need to be careful about floating point numbers.

Famously, 0.1 + 0.2 != 0.3 in floating point arithmetic.

In java, we have BigDecimal to help us with this problem.
However, BigDecimal is immutable, and creates a lot of garbage.

When working with fiat currencies, we can often get away with using a single `long` and representing the number as
cents, or a fixed-scale decimal of 8 if dealing with FX pricing.

Crypto-currencies, however, can have wide variations in scale. We need to be able to represent numbers with much larger
scales than a single long can support, but we also want to be able to do this without creating a lot of garbage.

Introducing ***LightDecimal***.

## Structure

*LightDecimal* uses four `longs` to represent the significand, an `int` to represent the (base-10) scale, and an `int`
for the signum (-1, 0, 1 for negative, 0, or positive numbers, respectively).

The resulting value is calculated as:

`signum * significand * 10^-scale`

***LightDecimal* is mutable.** Lots of calculations with BigDecimal are done in chains, creating lots of intermediate
objects. LightDecimal avoids this.

***LightDecimal* is _not_ thread-safe.** It is designed to be used in a single-threaded context, or with external
synchronization.

## Work in progress

This is a work in progress. The API is not yet stable, and the implementation is not yet complete.

### Current task:

- Adding numbers

### Future tasks:

- Subtracting numbers
- Multiplying numbers
- Raising to a power

