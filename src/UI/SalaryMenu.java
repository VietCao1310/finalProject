package UI;

import manager.SalaryManager;

import java.util.Scanner;

public class SalaryMenu {

    private SalaryManager salaryManager;

    public SalaryMenu(SalaryManager salaryManager) {
        this.salaryManager = salaryManager;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("===========================================");
            System.out.println("              SALARY MANAGEMENT            ");
            System.out.println("===========================================");
            System.out.println("1. Calculate Salary");
            System.out.println("2. Back");
            System.out.println("-------------------------------------------");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:
                    calculateSalaryUI(sc);
                    break;

                case 2:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 2);
    }

    private void calculateSalaryUI(Scanner sc) {

        System.out.println();
        System.out.println("------------ CALCULATE SALARY ------------");

        System.out.print("Employee ID: ");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("Employee ID cannot be empty.");
            return;
        }

        System.out.print("Month / Year (MM/YYYY): ");
        String input = sc.nextLine().trim();

        String[] parts = input.split("/");

        if (parts.length != 2) {
            System.out.println("Invalid format. Please use MM/YYYY.");
            return;
        }

        int month;
        int year;

        try {
            month = Integer.parseInt(parts[0]);
            year = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Month and Year must be numbers.");
            return;
        }

        if (month < 1 || month > 12) {
            System.out.println("Month must be between 1 and 12.");
            return;
        }

        if (year < 1900) {
            System.out.println("Invalid year.");
            return;
        }

        System.out.println();
        System.out.println("Output");

        try {
            salaryManager.calculateSalary(id, month, year);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("-------------------------------------------");
        System.out.println();
    }
}