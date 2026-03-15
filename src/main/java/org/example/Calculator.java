package org.example;

import java.math.BigInteger;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return a / b;
    }

    public double squareRoot(double x) {
        if (x < 0)
            throw new IllegalArgumentException("Cannot calculate square root of a negative number.");
        return Math.sqrt(x);
    }

    public BigInteger factorial(int x) {
        if (x < 0)
            throw new IllegalArgumentException("Factorial of negative number is undefined.");
        if (x > 10_000)
            throw new ArithmeticException(
                    "Input too large: factorial of " + x + " would produce 35,000+ digits " +
                            "and may cause memory/performance issues. Maximum allowed input is 10,000."
            );
        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= x; i++)
            fact = fact.multiply(BigInteger.valueOf(i));
        return fact;
    }

    public double naturalLog(double x) {
        if (x <= 0)
            throw new IllegalArgumentException("Natural log is only defined for positive numbers.");
        return Math.log(x);
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}