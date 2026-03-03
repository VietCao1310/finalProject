package manager;

import DAO.AttendanceDAO;
import Entities.Attendance;
import Entities.AttendanceStatus;
import Entities.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManager {

    private List<Attendance> attendanceList;
    private AttendanceDAO attendanceDAO;
    private EmployeeManager employeeManager;

    public AttendanceManager(AttendanceDAO attendanceDAO,
                             EmployeeManager employeeManager) {

        this.attendanceDAO = attendanceDAO;
        this.employeeManager = employeeManager;
        this.attendanceList = attendanceDAO.loadAttendance();
    }

    // ================= RECORD ATTENDANCE =================
    public void recordAttendance(String employeeId,
                                 LocalDate date,
                                 AttendanceStatus status,
                                 int overtimeHours) {

        // BR1: Employee must exist
        Employee employee = employeeManager.findById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        // BR2: Employee must be active
        if (!employee.isActive()) {
            throw new IllegalStateException("Cannot record attendance for inactive employee.");
        }

        // BR3: No duplicate attendance for same date
        if (isDuplicate(employeeId, date)) {
            throw new IllegalArgumentException(
                    "Attendance already recorded for this employee on this date.");
        }

        Attendance attendance = new Attendance(
                employeeId,
                date,
                status,
                overtimeHours
        );

        attendanceList.add(attendance);
        attendanceDAO.saveAttendance(attendanceList);
    }

    // ================= DUPLICATE CHECK =================
    private boolean isDuplicate(String employeeId, LocalDate date) {

        for (Attendance a : attendanceList) {
            if (a.getEmployeeId().equalsIgnoreCase(employeeId)
                    && a.getDate().equals(date)) {
                return true;
            }
        }

        return false;
    }

    // ================= GET ALL =================
    public List<Attendance> getAllAttendance() {
        return new ArrayList<>(attendanceList);
    }

    // ================= GET BY EMPLOYEE =================
    public List<Attendance> getAttendanceByEmployee(String employeeId) {

        List<Attendance> result = new ArrayList<>();

        for (Attendance a : attendanceList) {
            if (a.getEmployeeId().equalsIgnoreCase(employeeId)) {
                result.add(a);
            }
        }

        return result;
    }

    // ================= GET BY MONTH =================
    public List<Attendance> getAttendanceByMonth(String employeeId,
                                                 int month,
                                                 int year) {

        List<Attendance> result = new ArrayList<>();

        for (Attendance a : attendanceList) {

            if (a.getEmployeeId().equalsIgnoreCase(employeeId)
                    && a.getDate().getMonthValue() == month
                    && a.getDate().getYear() == year) {

                result.add(a);
            }
        }

        return result;
    }

    // ================= HELPER: COUNT ABSENT =================
    public int countAbsentDays(String employeeId, int month, int year) {

        int count = 0;

        for (Attendance a : getAttendanceByMonth(employeeId, month, year)) {
            if (a.getStatus() == AttendanceStatus.ABSENT) {
                count++;
            }
        }

        return count;
    }

    // ================= HELPER: TOTAL OVERTIME =================
    public int calculateTotalOvertime(String employeeId, int month, int year) {

        int total = 0;

        for (Attendance a : getAttendanceByMonth(employeeId, month, year)) {
            if (a.getStatus() == AttendanceStatus.PRESENT) {
                total += a.getOvertimeHours();
            }
        }

        return total;
    }
}