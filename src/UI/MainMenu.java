package UI;

import manager.EmployeeManager;
import manager.AttendanceManager;
import manager.SalaryManager;
import manager.ReportManager;

import java.util.Scanner;

public class MainMenu {

    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;
    private SalaryManager salaryManager;
    private ReportManager reportManager;

    public MainMenu(EmployeeManager employeeManager,
                    AttendanceManager attendanceManager,
                    SalaryManager salaryManager,
                    ReportManager reportManager) {

        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
        this.salaryManager = salaryManager;
        this.reportManager = reportManager;
    }

    public void show() {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("===========================================");
            System.out.println("         HUMAN RESOURCE MANAGEMENT         ");
            System.out.println("===========================================");
            System.out.println("1. Manage Employees");
            System.out.println("2. Attendance Management");
            System.out.println("3. Salary Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("-------------------------------------------");
            System.out.print("Choose an option: ");

            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1-5).");
                continue; 
            }
            
            switch (choice) {

                case 1:
                    new EmployeeMenu(employeeManager).show();
                    break;

                case 2:
                    new AttendanceMenu(attendanceManager, employeeManager).show();
                    break;

                case 3:
                    new SalaryMenu(salaryManager).show();
                    break;

                case 4:
                    new ReportMenu(reportManager).show();
                    break;

                case 5:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose from 1-5.");
                    break;
            }
        } while (choice != 5);
    }
}