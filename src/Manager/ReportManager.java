package manager;

import Entities.Employee;
import Entities.Attendance;
import Enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;

public class ReportManager {

    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;

    public ReportManager(EmployeeManager employeeManager,
                         AttendanceManager attendanceManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
    }

    //Total salary of all employees in month
    public void totalSalaryReport(int month, int year) {
        double totalCompanySalary = 0;

        for (Employee e : employeeManager.getAllEmployees()) {
            List<Attendance> list =
                    attendanceManager.getAttendanceByEmployee(e.getId());

            int absentDays = 0;
            int overtimeHours = 0;

            for (Attendance a : list) {

                LocalDate date = a.getDate();

                if (date.getMonthValue() == month &&
                        date.getYear() == year) {

                    if (a.getStatus() == AttendanceStatus.ABSENT) {
                        absentDays++;
                    }

                    if (a.getStatus() == AttendanceStatus.PRESENT) {
                        overtimeHours += a.getOvertimeHours();
                    }
                }
            }

            double salary =
                    e.calculateSalary(overtimeHours, absentDays);

            totalCompanySalary += salary;
        }

        System.out.println("Total Salary of Company: "
                + String.format("%,.0f", totalCompanySalary) + " VND");
    }

    //List inactive employees
    public void listInactiveEmployees() {
        System.out.println("Inactive Employees:");
        for (Employee e : employeeManager.getAllEmployees()) {
            if (!e.isActive()) {
                System.out.println("- " + e.getId()
                        + " | " + e.getFullName());
            }
        }
    }
    
    //low attendance 
    public void lowAttendanceReport(int month, int year, int threshold) {

        System.out.println("------------ LOW ATTENDANCE REPORT ------------");

        boolean found = false;

        for (Employee e : employeeManager.getAllEmployees()) {

            int absentDays = 0;

            for (Attendance a : attendanceManager.getAttendanceByEmployee(e.getId())) {

                if (a.getDate().getMonthValue() == month &&
                    a.getDate().getYear() == year &&
                    a.getStatus() == AttendanceStatus.ABSENT) {

                    absentDays++;
                }
            }

            if (absentDays >= threshold) {
                System.out.println(e.getId() + "  "
                        + e.getFullName() + "  "
                        + absentDays + " absent days");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No employees with low attendance.");
        }

        System.out.println("-----------------------------------------------");
    }
    
    //high pay 
    public void highestPaidEmployee(int month, int year) {

        System.out.println("------------ HIGHEST PAID EMPLOYEES ------------");

        Employee topEmployee = null;
        double maxSalary = 0;

        for (Employee e : employeeManager.getAllEmployees()) {

            int absentDays = 0;
            int overtimeHours = 0;

            for (Attendance a : attendanceManager.getAttendanceByEmployee(e.getId())) {

                if (a.getDate().getMonthValue() == month &&
                    a.getDate().getYear() == year) {

                    if (a.getStatus() == AttendanceStatus.ABSENT)
                        absentDays++;

                    if (a.getStatus() == AttendanceStatus.PRESENT)
                        overtimeHours += a.getOvertimeHours();
                }
            }

            double salary = e.calculateSalary(overtimeHours, absentDays);

            if (salary > maxSalary) {
                maxSalary = salary;
                topEmployee = e;
            }
        }

        if (topEmployee != null) {
            System.out.println(topEmployee.getId() + "  "
                    + topEmployee.getFullName() + "  "
                    + String.format("%,.0f", maxSalary) + " VND");
        } else {
            System.out.println("No salary data.");
        }

        System.out.println("------------------------------------------------");
    }
}