package UI;

import manager.AttendanceManager;
import manager.EmployeeManager;
import Entities.Attendance;
import Entities.AttendanceStatus;
import Entities.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AttendanceMenu {

    private AttendanceManager attendanceManager;
    private EmployeeManager employeeManager;
    private Scanner scanner;

    public AttendanceMenu(AttendanceManager attendanceManager,
                          EmployeeManager employeeManager) {
        this.attendanceManager = attendanceManager;
        this.employeeManager = employeeManager;
        this.scanner = new Scanner(System.in);
    }

    public void show() {

        while (true) {

            System.out.println("\n===== ATTENDANCE MANAGEMENT =====");
            System.out.println("1. Record Attendance");
            System.out.println("2. View Attendance By Employee");
            System.out.println("3. View All Attendance");
            System.out.println("4. Back");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    recordAttendance();
                    break;
                case 2:
                    viewByEmployee();
                    break;
                case 3:
                    viewAll();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ================= RECORD =================
    private void recordAttendance() {

        System.out.println("\n----- RECORD ATTENDANCE -----");

        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();

        Employee employee = employeeManager.findById(id);

        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }

        if (!employee.isActive()) {
            System.out.println("Employee is inactive.");
            return;
        }

        System.out.print("Enter date (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.println("Select Status:");
        System.out.println("1. PRESENT");
        System.out.println("2. ABSENT");
        System.out.println("3. LEAVE");
        System.out.print("Choose: ");

        int statusChoice = Integer.parseInt(scanner.nextLine());
        AttendanceStatus status;

        switch (statusChoice) {
            case 1:
                status = AttendanceStatus.PRESENT;
                break;
            case 2:
                status = AttendanceStatus.ABSENT;
                break;
            case 3:
                status = AttendanceStatus.LEAVE;
                break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }

        int overtime = 0;

        if (status == AttendanceStatus.PRESENT) {
            System.out.print("Enter overtime hours: ");
            overtime = Integer.parseInt(scanner.nextLine());
        }

        try {
            attendanceManager.recordAttendance(id, date, status, overtime);
            System.out.println("Attendance recorded successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ================= VIEW BY EMPLOYEE =================
    private void viewByEmployee() {

        System.out.print("\nEnter Employee ID: ");
        String id = scanner.nextLine();

        List<Attendance> list = attendanceManager.getAttendanceByEmployee(id);

        if (list.isEmpty()) {
            System.out.println("No attendance found.");
            return;
        }

        printAttendanceTable(list);
    }

    // ================= VIEW ALL =================
    private void viewAll() {

        List<Attendance> list = attendanceManager.getAllAttendance();

        if (list.isEmpty()) {
            System.out.println("No attendance records.");
            return;
        }

        printAttendanceTable(list);
    }

    // ================= PRINT TABLE =================
    private void printAttendanceTable(List<Attendance> list) {

        System.out.printf("%-10s %-12s %-10s %-10s%n",
                "EmpID", "Date", "Status", "OT");

        System.out.println("---------------------------------------------");

        for (Attendance a : list) {
            System.out.printf("%-10s %-12s %-10s %-10d%n",
                    a.getEmployeeId(),
                    a.getDate(),
                    a.getStatus(),
                    a.getOvertimeHours());
        }
    }
}