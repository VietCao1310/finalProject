package UI;

import manager.ReportManager;

import java.util.Scanner;

public class ReportMenu {

    private ReportManager reportManager;

    public ReportMenu(ReportManager reportManager) {
        this.reportManager = reportManager;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("===========================================");
            System.out.println("             REPORT MANAGEMENT             ");
            System.out.println("===========================================");
            System.out.println("1. Total Salary (MM/YYYY)");
            System.out.println("2. Employees with Low Attendance");
            System.out.println("3. Highest Paid Employees");
            System.out.println("4. Back");
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
                    System.out.print("Month / Year (MM/YYYY): ");
                    String input = sc.nextLine();
                    String[] parts = input.split("/");
                    reportManager.totalSalaryReport(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]));
                    break;
                case 2:
                    lowAttendanceUI(sc);
                    break;
                case 3:
                    highestPaidUI(sc);
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 4);
    }
    
    private void lowAttendanceUI(Scanner sc) {

        System.out.println();
        System.out.println("Task B7 — Employees with Low Attendance");
        System.out.println("------------ LOW ATTENDANCE REPORT ------------");

        System.out.print("Month / Year (MM/YYYY): ");
        String input = sc.nextLine();
        String[] parts = input.split("/");

        if (parts.length != 2) {
            System.out.println("Invalid format.");
            return;
        }

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        reportManager.lowAttendanceReport(month, year, 3); // >=3 absent days

        System.out.println("Press ENTER to return...");
        sc.nextLine();
    }
    
    private void highestPaidUI(Scanner sc) {

        System.out.println();
        System.out.println("Task B8 — Highest Paid Employees");
        System.out.println("------------ HIGHEST PAID EMPLOYEES ------------");

        System.out.print("Month / Year (MM/YYYY): ");
        String input = sc.nextLine();
        String[] parts = input.split("/");

        if (parts.length != 2) {
            System.out.println("Invalid format.");
            return;
        }

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        reportManager.highestPaidEmployee(month, year);

        System.out.println("Press ENTER to return...");
        sc.nextLine();
    }

}