import java.util.Scanner;

public class LoanCalculator {

    public static double calculateEMI(double principal, double annualInterestRate, int tenureMonths) {
        // Convert annual interest rate (%) to monthly interest rate (decimal)
        double monthlyInterestRate = annualInterestRate / (12 * 100);

        // Apply EMI formula
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths)) /
                     (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

        return emi;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Loan EMI Calculator ===");

        try {
            // User input
            System.out.print("Enter the loan amount (Principal): ₹ ");
            double principal = scanner.nextDouble();

            System.out.print("Enter the annual interest rate (in %): ");
            double annualInterestRate = scanner.nextDouble();

            System.out.print("Enter the loan tenure (in months): ");
            int tenureMonths = scanner.nextInt();

            // Input validation
            if (principal <= 0 || annualInterestRate < 0 || tenureMonths <= 0) {
                System.out.println("Error: Please enter valid positive values.");
                return;
            }

            // Calculate EMI
            double emi = calculateEMI(principal, annualInterestRate, tenureMonths);

            // Output
            System.out.printf("\nYour Monthly EMI is: ₹ %.2f%n", emi);
            System.out.printf("Total Payment (Principal + Interest): ₹ %.2f%n", emi * tenureMonths);
            System.out.printf("Total Interest Payable: ₹ %.2f%n", (emi * tenureMonths - principal));

        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please enter numeric values only.");
        } finally {
            scanner.close();
        }
    }
}
