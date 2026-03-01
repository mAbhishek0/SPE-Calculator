package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Scientific Calculator ---");

        while (true) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Square Root (√x)");
            System.out.println("2. Factorial (x!)");
            System.out.println("3. Natural Log (ln(x))");
            System.out.println("4. Power (x^b)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // clear the bad input
                continue;
            }

            if (choice == 5) {
                System.out.println("Exiting... Goodbye!");
                break;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter a number: ");
                        double num1 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.squareRoot(num1));
                        break;
                    case 2:
                        System.out.print("Enter an integer: ");
                        int num2 = scanner.nextInt();
                        System.out.println("Result: " + calculator.factorial(num2));
                        break;
                    case 3:
                        System.out.print("Enter a number: ");
                        double num3 = scanner.nextDouble();
                        System.out.println("Result: " + calculator.naturalLog(num3));
                        break;
                    case 4:
                        System.out.print("Enter the base: ");
                        double base = scanner.nextDouble();
                        System.out.print("Enter the exponent: ");
                        double exp = scanner.nextDouble();
                        System.out.println("Result: " + calculator.power(base, exp));
                        break;
                    default:
                        System.out.println("Invalid choice. Please select from 1 to 5.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid numeric input.");
                scanner.next(); // clear the bad input
            }
        }
        scanner.close();
    }
}