package org.example;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    Calculator calculator = new Calculator();
    private static final double DELTA = 1e-15;

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