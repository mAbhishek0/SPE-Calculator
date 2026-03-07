package org.example;
public class Calculator {
    public double squareRoot(double x) {
        if (x < 0) throw new IllegalArgumentException("Cannot calculate square root of a negative number.");
        return Math.sqrt(x);
    }
    public long factorial(int x) {
        if (x < 0) throw new IllegalArgumentException("Factorial of negative number is undefined.");
        long fact = 1;
        for (int i = 1; i <= x; i++) fact *= i;
        return fact;
    }
    public double naturalLog(double x) {
        if (x <= 0) throw new IllegalArgumentException("Natural log is only defined for positive numbers.");
        return Math.log(x);
    }
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}
