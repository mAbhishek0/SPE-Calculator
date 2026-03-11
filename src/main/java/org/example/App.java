package org.example;
import java.util.InputMismatchException;
import java.util.Scanner;
// test webhook
public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Scientific Calculator ---");

        while (true) {
            System.out.println("\n1. Square Root (√x)\n2. Factorial (x!)\n3. Natural Log (ln(x))\n4. Power (x^b)\n5. Addition (a+b)\n6. Subtraction (a-b)\n7. Multiplication (a*b)\n8. Division (a/b)\n9. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try { choice = scanner.nextInt(); }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); continue;
            }
            if (choice == 9) break;

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter number: ");
                        System.out.println("Result: " + calculator.squareRoot(scanner.nextDouble())); break;
                    case 2:
                        System.out.print("Enter integer: ");
                        System.out.println("Result: " + calculator.factorial(scanner.nextInt())); break;
                    case 3:
                        System.out.print("Enter number: ");
                        System.out.println("Result: " + calculator.naturalLog(scanner.nextDouble())); break;
                    case 4:
                        System.out.print("Enter base: "); double b = scanner.nextDouble();
                        System.out.print("Enter exponent: "); double e = scanner.nextDouble();
                        System.out.println("Result: " + calculator.power(b, e)); break;
                    case 5:
                        System.out.print("Enter first number: "); double add1 = scanner.nextDouble();
                        System.out.print("Enter second number: "); double add2 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.add(add1, add2)); break;
                    case 6:
                        System.out.print("Enter first number: "); double sub1 = scanner.nextDouble();
                        System.out.print("Enter second number: "); double sub2 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.subtract(sub1, sub2)); break;
                    case 7:
                        System.out.print("Enter first number: "); double mul1 = scanner.nextDouble();
                        System.out.print("Enter second number: "); double mul2 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.multiply(mul1, mul2)); break;
                    case 8:
                        System.out.print("Enter numerator: "); double div1 = scanner.nextDouble();
                        System.out.print("Enter denominator: "); double div2 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.divide(div1, div2)); break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
        scanner.close();
    }
}
