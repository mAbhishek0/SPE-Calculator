package org.example;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    Calculator calculator = new Calculator();
    private static final double DELTA = 1e-15;

    @Test
    public void testAdd() {
        assertEquals(5.5, calculator.add(2.0, 3.5), DELTA);
        assertEquals(-1.0, calculator.add(2.0, -3.0), DELTA);
    }

    @Test
    public void testSubtract() {
        assertEquals(1.5, calculator.subtract(5.0, 3.5), DELTA);
        assertEquals(5.0, calculator.subtract(2.0, -3.0), DELTA);
    }

    @Test
    public void testMultiply() {
        assertEquals(15.0, calculator.multiply(5.0, 3.0), DELTA);
        assertEquals(-6.0, calculator.multiply(2.0, -3.0), DELTA);
        assertEquals(0.0, calculator.multiply(5.0, 0.0), DELTA);
    }

    @Test
    public void testDivide() {
        assertEquals(2.5, calculator.divide(5.0, 2.0), DELTA);
        assertEquals(-2.0, calculator.divide(6.0, -3.0), DELTA);
    }

    // Testing the Edge Case
    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(10.0, 0.0);
    }
    @Test
    public void testSquareRoot() { assertEquals(4.0, calculator.squareRoot(16.0), DELTA); }
    @Test
    public void testFactorial() { assertEquals(120, calculator.factorial(5)); }
    @Test
    public void testNaturalLog() { assertEquals(0.0, calculator.naturalLog(1.0), DELTA); }
    @Test
    public void testPower() { assertEquals(8.0, calculator.power(2.0, 3.0), DELTA); }
    @Test(expected = IllegalArgumentException.class)
    public void testSquareRootNegative() { calculator.squareRoot(-1.0); }
}