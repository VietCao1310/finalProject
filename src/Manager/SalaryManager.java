package manager;

import Entities.Attendance;
import Entities.AttendanceStatus;
import Entities.Employee;

import java.time.LocalDate;
import java.util.List;

public class SalaryManager {

    private EmployeeManager employeeManager;
    private AttendanceManager attendanceManager;

    public SalaryManager(EmployeeManager employeeManager,
                         AttendanceManager attendanceManager) {
        this.employeeManager = employeeManager;
        this.attendanceManager = attendanceManager;
    }

    public void calculateSalary(String employeeId, int month, int year) {

        Employee employee = employeeManager.findById(employeeId);

        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        List<Attendance> list =
                attendanceManager.getAttendanceByEmployee(employeeId);

        int totalWorkingDays = 0;
        int totalAbsentDays = 0;
        int totalOvertimeHours = 0;

        for (Attendance a : list) {

            LocalDate date = a.getDate();

            if (date.getMonthValue() == month &&
                date.getYear() == year) {

                if (a.getStatus() == AttendanceStatus.PRESENT) {
                    totalWorkingDays++;
                    totalOvertimeHours += a.getOvertimeHours();
                }
                else if (a.getStatus() == AttendanceStatus.ABSENT) {
                    totalAbsentDays++;
                }
            }
        }

        double totalSalary =
                employee.calculateSalary(totalOvertimeHours, totalAbsentDays);

        System.out.println("Salary calculated successfully.");
        System.out.println("Total Working Days: " + totalWorkingDays);
        System.out.println("Overtime Hours: " + totalOvertimeHours);
        System.out.println("Absence Days: " + totalAbsentDays);
        System.out.println("Total Salary: " +
                String.format("%,.0f", totalSalary) + " VND");
    }
}